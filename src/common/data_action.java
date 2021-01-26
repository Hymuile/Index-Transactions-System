package common;


import dao.ylx_db;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class data_action extends HttpServlet {

	private static final long serialVersionUID = 1L;
	ylx_db query_db = null;
	String refreshCount = "";
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	//get_home_menu 功能：document_main菜单，按照角色来查询对应的权限.查询出来的结果显示在首页菜单的面板上，大图标排列显示
	//get_main_menu 功能: 获取模块单独的菜单，xxx_tree表(左侧导航栏)里的

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Cache-Control", "no-cache");
		String action = request.getParameter("action");
		System.out.println("[common_data_action]收到的action是：" + action);

		boolean actionOk = false;
		if((action == null) && !action.equals("get_home_menu")){
			try {
				processError(request, response, 1, "传递过来的action是null！");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if (action.equals("get_user_info")) {
			actionOk = true;
			try {
				getUserInfo(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if (action.equals("get_main_menu")) {
			actionOk = true;
			try {
				getMainmenu(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if (action.equals("get_home_menu")) {
			actionOk = true;
			try {
				getHomemenu(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if (action.equals("get_system_status")) {
			actionOk = true;
			try {
				getSystemStatus(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if (!actionOk) {
			try {
				processError(request, response, 2, "没有对应的action处理过程，请检查action是否正确！");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public void processError(HttpServletRequest request, HttpServletResponse response, int errorNo, String errorMsg) throws JSONException {
		String action = request.getParameter("action");
		// 构造返回结果的json
		JSONObject jsonObj = new JSONObject();
		// 共同部分
		jsonObj.put("version", "1.0");
		jsonObj.put("action", action);
		jsonObj.put("result_msg", "ok");
		jsonObj.put("result_code", 0);
		String json = jsonObj.toString();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, JSONException {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String user_email = (String) session.getAttribute("user_email");
		System.out.println("get_user_info里的action="+action+",user_email="+user_email);
		List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		String resultMsg="success";

		JSONObject jsonObj = new JSONObject();

		int resultCode=0;
		if (session.getAttribute("unit_db_name") == null) {
			resultCode=1;
			resultMsg="数据库为空！";
		} else {
			query_db = new ylx_db((String) session.getAttribute("unit_db_name"));

			String sql="select * from users where user_email='"+user_email+"';";

			System.out.println("get_user_info里构造的sql="+sql);
			ResultSet rs = query_db.executeQuery(sql);

			if(rs.next()) {
				jsonObj.put("user_name", rs.getString("user_name"));
				jsonObj.put("user_email", rs.getString("user_email"));
				jsonObj.put("user_role", rs.getString("user_role"));
				jsonObj.put("user_margin", rs.getString("user_margin"));
				jsonObj.put("user_balance", rs.getString("user_balance"));
				jsonObj.put("user_city", rs.getString("user_city"));
			}
			rs.close();
			query_db.close();
		}

		// 共同部分
		jsonObj.put("version", "1.0");
		jsonObj.put("action", action);
		jsonObj.put("result_msg", resultMsg);
		jsonObj.put("result_code", resultCode);
		String json = jsonObj.toString();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * 功能：获取模块单独的菜单，xxx_tree表里的
	 */
	public void getMainmenu(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, JSONException {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String module = request.getParameter("module");
		String role=(String)session.getAttribute("user_role");
		System.out.println("get_main_menu里的action="+action+",module="+module);
		List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		String resultMsg="success";
		int resultCode=0;
		if (session.getAttribute("unit_db_name") == null) {
			resultCode=1;
			resultMsg="数据库为空！";
		} else {
			query_db = new ylx_db((String) session.getAttribute("unit_db_name"));
			String id = request.getParameter("id");
			String tableName = request.getParameter("table_name");
			String order = request.getParameter("order");
			if (order == null)
				order = "";
			String where = " where role_id='"+role+"'";
			System.out.println("table_name="+tableName);
			String sql= "select value as module_name,reason as category_id,0 as parent_category_id" +
					",reason as file_id,value as file_name,href from " + tableName + where;
			sql=sql.replace("role_id","role");
			System.out.println("get_main_menu里构造的sql="+sql);
			ResultSet rs = query_db.executeQuery(sql);
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				// ////////////////////////////////////////独有部分，要修改的是这里
				map.put("module_name", rs.getString("module_name"));
				map.put("category_id", rs.getString("category_id"));
				map.put("parent_item_id", rs.getString("parent_category_id"));
				map.put("item_id", rs.getString("file_id"));
				map.put("item_name", rs.getString("file_name"));
				map.put("href_link", rs.getString("href"));
				// ////////////////////////////////////////独有部分修改完毕
				jsonList.add(map);
			}
			rs.close();
			query_db.close();
			//System.out.println(jsonList.toString());
		}
		JSONObject jsonObj = new JSONObject();
		// 共同部分
		jsonObj.put("version", "1.0");
		jsonObj.put("action", action);
		jsonObj.put("record_list", jsonList);
		jsonObj.put("result_msg", resultMsg);
		jsonObj.put("result_code", resultCode);
		String json = jsonObj.toString();

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(json);
	}
	/*
	 * 功能：document_main菜单，按照角色来查询对应的权限
	 * 查询出来的结果显示在首页菜单的面板上，大图标排列显示
	 */
	public void getHomemenu(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, JSONException {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String role=(String)session.getAttribute("user_role");
		List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		String resultMsg="success";
		int resultCode=0;
		if(role!=null){
			if (session.getAttribute("unit_db_name") == null) {
				resultCode=10;
				resultMsg="数据库为空！";
			} else {
				query_db = new ylx_db((String) session.getAttribute("unit_db_name"));
				String id = request.getParameter("id");
				String tableName = request.getParameter("table_name");
				String order = request.getParameter("order");
				if (order == null)
					order = "";
				String where = request.getParameter("where");
				if (where == null)
					where = " where role='"+role+"'";
				else
					where = " where role='"+role+"'";
				String sql = "select * from " + tableName + where;
				ResultSet rs = query_db.executeQuery(sql);
				while (rs.next()) {
					Map<String, String> map = new HashMap<String, String>();
					// ////////////////////////////////////////独有部分，要修改的是这里
					map.put("reason", rs.getString("REASON"));
					map.put("value", rs.getString("value"));
					// ////////////////////////////////////////独有部分修改完毕
					jsonList.add(map);
				}
				rs.close();
				query_db.close();
				//System.out.println(jsonList.toString());
			}
		}else{
			resultCode=3;
			resultMsg="session超时，请重新登陆！";
		}
		JSONObject jsonObj = new JSONObject();
		// 共同部分
		jsonObj.put("version", "1.0");
		jsonObj.put("action", action);
		jsonObj.put("record_list", jsonList);
		jsonObj.put("result_msg", resultMsg);
		jsonObj.put("result_code", resultCode);
		String json = jsonObj.toString();

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void getSystemStatus(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, JSONException {
		HttpSession session = request.getSession();
		String[] id = request.getParameterValues("id");
		String action = request.getParameter("action");
		String resultMsg="ok";
		int resultCode=0;
		JSONObject jsonObj = new JSONObject();
		List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		// 进行计算
		String userName=(String)session.getAttribute("user_name");
		String userEmail = (String)session.getAttribute("user_email");
		String userCity = (String)session.getAttribute("user_city");
		
		String userBalance = (String)session.getAttribute("user_balance")+"";
		
		String userMargin = (String)session.getAttribute("user_margin")+"";

		if(userEmail==null){
			resultCode=3;
			resultMsg="session 超时，请重新登陆！";
		}
		/*
		 * 
		 */
		// 构造返回结果的json
		jsonObj.put("user_name",userName);
		jsonObj.put("user_email",userEmail);
		jsonObj.put("user_city",userCity);
		jsonObj.put("user_balance",userBalance);
		jsonObj.put("user_margin",userMargin);

		// 共同部分
		jsonObj.put("aaData", jsonList);
		jsonObj.put("version", "1.0");
		jsonObj.put("action", action);
		jsonObj.put("result_msg", resultMsg);
		jsonObj.put("result_code", resultCode);
		String json = jsonObj.toString();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
