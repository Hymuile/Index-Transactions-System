package bonds_manage.back;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import dao.ylx_db;

public class action extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4472963758758669216L;
	ylx_db query_db = null;
	private String createtime;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Cache-Control", "no-cache");
		String action = request.getParameter("action");
		System.out.println("[weather_back_action]收到的action是：" + action);

		boolean actionOk = false;
		if(action == null){
			try {
				processError(request, response, 1, "传递过来的action是null！");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if (action.equals("get_record")) {
			actionOk=true;
			try {
				getRecord(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
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

	/*
	 * 功能：查询记录
	 */
	public void getRecord(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, JSONException {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String city = request.getParameter("cityName");
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

			run(city);
			String sql="select * from weather where city='"+city+"';";
			System.out.println("get_record里构造的sql="+sql);
			ResultSet rs = query_db.executeQuery(sql);

			if(rs.next()) {
				jsonObj.put("city", rs.getString("city"));
				jsonObj.put("obs_time", rs.getString("obs_time"));
				jsonObj.put("temp", rs.getString("temp"));
				jsonObj.put("icon", rs.getString("icon"));
				jsonObj.put("text", rs.getString("text"));
				jsonObj.put("wind_dir", rs.getString("wind_dir"));
				jsonObj.put("wind_scale", rs.getString("wind_scale"));
				jsonObj.put("wind_speed", rs.getString("wind_speed"));
				jsonObj.put("vis", rs.getString("vis"));
				jsonObj.put("create_time", rs.getString("create_time"));
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

	public void run(String cityname) {
		String citycode = getCityCode(cityname);
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		createtime = (String)format.format(calendar.getTime());
		System.out.println("Current Time："+format.format(calendar.getTime()));
		System.out.println(createtime);
		try {
			getWeatherData(citycode,cityname);
		} catch (MalformedURLException | JSONException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void getWeatherData(String citycode,String cityname) throws MalformedURLException,JSONException, SQLException {
		String urlData="https://devapi.qweather.com/v7/weather/now?location="+citycode+"&key=dfc6208de6c64457b59e140ee5a0208d&gzip=n";
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		System.out.println(createtime);
		URL urlConn=new URL(urlData);
		System.out.println(urlData);
		InputStream is = null;
		BufferedReader reader = null;
		StringBuffer sb = null ;
		try { 	
			HttpURLConnection connection = (HttpURLConnection) urlConn.openConnection();

			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			//connection.setReadTimeout(1000);
			//connection.setConnectTimeout(1000);
			connection.setRequestProperty("accept" , "*/*");
			connection.setRequestProperty("contentType", "application/json;charset=utf-8");
			connection.connect();
			// GET请求

			is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)); 
			String lines;
			sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				sb.append(lines);
			}
			System.out.println("获取数据：");
			System.out.println(sb);
			WeatherDao dao=new WeatherDao();
			dao.getcityname(cityname);
			dao.getCreateTime(createtime);
			dao.addWeatherRecord(sb);

			reader.close();
			connection.disconnect(); 
		} catch (MalformedURLException e) { 
			e.printStackTrace(); 
		} catch (UnsupportedEncodingException e) { 
			e.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 	
		//System.exit(0);
	}

	public String getCityCode(String cityName1) {
		String cityCode = "";
		try {
			String strUrl = "http://toy1.weather.com.cn/search?cityname=" + URLEncoder.encode(cityName1, "utf-8");
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // 200
				InputStream is = conn.getInputStream();
				byte[] buffer = new byte[is.available()];
				if (is.available() > 4) {
					is.read(buffer);
					String response = new String(buffer, "utf-8");
					cityCode = response.substring(10, 19);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cityCode;
	}
}

