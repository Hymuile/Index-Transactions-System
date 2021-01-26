package security_user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import dao.ylx_db;
import utility.LogEvent;

public class ServletAction  extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public LogEvent ylxLog = new LogEvent();

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		processAction(request,response);
	}
	public void processAction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		try {
			ylxLog.setSession(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String action = request.getParameter("action");
		boolean actionOk = false;
		showDebug("processAction收到的action是："+action);

		if (action == null){
			try {
				processError(request, response,1,"传递过来的action是null！");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else{
			if (action.equals("login")) {
				actionOk=true;
				try {
					Login(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("register")) {
				actionOk=true;
				try {
					Register(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (action.equals("recover_pwd")) {
				actionOk=true;
				try {
					Recover(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	public void processError(HttpServletRequest request, HttpServletResponse response,int errorNo,String errorMsg) throws JSONException, IOException{
		String action = request.getParameter("action");
		//errorNo=0->没有错误
		//errorNo=1->action是空值
		//errorNo=2->没有对应的处理该action的函数
		//errorNo=3->session超时
		showDebug("错误信息："+errorMsg+"，代码："+errorNo);
		JSONObject jsonObj=new JSONObject();

		jsonObj.put("result_code",errorNo);
		jsonObj.put("result_msg",errorMsg);
		jsonObj.put("action",action);
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(jsonObj);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static void main(String[] args) throws Exception {
		System.out.println("");
	}
	public void showDebug(String msg){
		System.out.println("["+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())+"]["+"security_user/ServletAction]"+msg);
	}

	public void Login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");

		String dbName="xm09";

		String action = request.getParameter("action");
		String userId=request.getParameter("userId");
		String userPwd=request.getParameter("userPwd");

		//检查输入参数是否正确先
		showDebug("[login]收到页面传过来的参数是:"+action+","+userId+","+userPwd);
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		User query=new User();
		query.setDbName(dbName);
		query.setAction(action);
		query.setEmail(userId);
		query.setName(userId);
		query.setPwd(userPwd);
		JSONObject jsonObj=null;
		UserDao userDao=new UserDao();
		jsonObj=userDao.getRecord(query,request,response);
		
		jsonObj.put("action",action);
		/*--------------------数据查询完毕，根据交互方式返回数据--------------------*/
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out;
		showDebug(jsonObj.getString("result_code"));
		showDebug(jsonObj.getString("result_msg"));
		try {
			out = response.getWriter();
			out.print(jsonObj);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void Register(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");

		String dbName="xm09";

		String action = request.getParameter("action");
		String userEmail=request.getParameter("user_email");
		String userPwd=request.getParameter("user_password");
		String userName = request.getParameter("user_name");
		String verify = request.getParameter("verify_code");
		
		//检查输入参数是否正确先
		showDebug("[register]收到页面传过来的参数是:"+action+","+userEmail+","+userName+","+userPwd);
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		User query=new User();
		query.setDbName(dbName);
		query.setAction(action);
		query.setEmail(userEmail);
		query.setName(userName);
		query.setPwd(userPwd);
		query.setVerifyCode(verify);
		
		JSONObject jsonObj=null;
		UserDao userDao=new UserDao();
		jsonObj=userDao.addRecord(query,request,response);
		System.out.println("执行到了addRecord");
		jsonObj.put("action",action);
		/*--------------------数据查询完毕，根据交互方式返回数据--------------------*/
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out;
		showDebug(jsonObj.getString("result_code"));
		showDebug(jsonObj.getString("result_msg"));
		try {
			out = response.getWriter();
			out.print(jsonObj);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void Recover(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");

		String dbName="xm09";

		String action = request.getParameter("action");
		String userEmail=request.getParameter("userEmail");
		String userPwd=request.getParameter("userPwd");
		String userName = request.getParameter("userName");
		String verify = request.getParameter("verifyCode");
		
		//检查输入参数是否正确先
		showDebug("[recover]收到页面传过来的参数是:"+action+","+userEmail+","+userName+","+userPwd+","+verify);
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		User query=new User();
		query.setDbName(dbName);
		query.setAction(action);
		query.setEmail(userEmail);
		query.setName(userName);
		query.setPwd(userPwd);
		query.setVerifyCode(verify);
		
		JSONObject jsonObj=null;
		UserDao userDao=new UserDao();
		jsonObj=userDao.updateRecord(query,request,response);
		System.out.println("执行到了Recover");
		jsonObj.put("action",action);
		/*--------------------数据查询完毕，根据交互方式返回数据--------------------*/
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out;
		showDebug(jsonObj.getString("result_code"));
		showDebug(jsonObj.getString("result_msg"));
		try {
			out = response.getWriter();
			out.print(jsonObj);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}