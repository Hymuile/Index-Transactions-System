package security_user;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import dao.ylx_db;


public class UserDao {
	public JSONObject getRecord(User query,HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, JSONException {
		HttpSession session = request.getSession(true);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		JSONObject jsonObj=new JSONObject();
		String resultMsg="ok";
		int resultCode=200;
		try {
			ylx_db query_db = new ylx_db(query.getDbName());
			String sql="select * from users where user_email='"+query.getEmail()+"';";
			ResultSet rs = query_db.executeQuery(sql);
			if(rs.next()){
				sql ="select * from users where user_email='"+query.getEmail()+"' and user_pwd='"+query.getPwd()+"';";
				rs = query_db.executeQuery(sql);
				if(rs.next()){
					session.setAttribute("user_email",rs.getString("user_email"));
					session.setAttribute("user_name",rs.getString("user_name"));
					session.setAttribute("user_pwd",rs.getString("user_pwd"));
					session.setAttribute("user_role",rs.getString("user_role"));
					session.setAttribute("user_city",rs.getString("user_city"));
					session.setAttribute("user_margin",rs.getString("user_margin"));
					session.setAttribute("user_balance",rs.getString("user_balance"));
					System.out.println("user_email="+rs.getString("user_email"));
				}else{
					resultCode=404;
					resultMsg="密码不正确";
				}
			}else{
				sql = "select * from users where user_name='"+query.getName()+"';";
				rs = query_db.executeQuery(sql);
				if(rs.next()){
					sql ="select * from users where user_name='"+query.getName()+"' and user_pwd='"+query.getPwd()+"';";
					rs = query_db.executeQuery(sql);
					if(rs.next()){
						session.setAttribute("user_email",rs.getString("user_email"));
						session.setAttribute("user_name",rs.getString("user_name"));
						session.setAttribute("user_pwd",rs.getString("user_pwd"));
						session.setAttribute("user_role",rs.getString("user_role"));
						session.setAttribute("user_city",rs.getString("user_city"));
						session.setAttribute("user_margin",rs.getString("user_margin"));
						session.setAttribute("user_balance",rs.getString("user_balance"));
						System.out.println("user_email="+rs.getString("user_email"));
					}else{
						resultCode=404;
						resultMsg="密码不正确";
					}
				}else{
					resultCode=404;
					resultMsg="此账号未注册，请注册后登录";
				}
			}
			rs.close();
			query_db.close();
		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
			resultCode=10;
			resultMsg="查询数据库出现错误！"+sqlexception.getMessage();
		}

		jsonObj.put("result_msg",resultMsg);
		jsonObj.put("result_code",resultCode);
		return jsonObj;
	}
	public JSONObject addRecord(User query,HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, JSONException {
		HttpSession session = request.getSession(true);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		JSONObject jsonObj=new JSONObject();
		String resultMsg="ok";
		int resultCode=200;
		try {
			ylx_db query_db = new ylx_db(query.getDbName());
			String sql="select * from users where user_email='"+query.getEmail()+"';";
			ResultSet rs = query_db.executeQuery(sql);
			if(rs.next()){
				resultCode=300;
				resultMsg="邮箱已注册";
			}else{
				sql = "select * from users where user_name='"+query.getName()+"';";
				rs = query_db.executeQuery(sql);
				if(rs.next()){
					resultCode=300;
					resultMsg="用户名已注册";
				}else{
					String verify = (String)session.getAttribute("code");
					if(verify.equals(query.getVerifyCode())){
						SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date = new Date(System.currentTimeMillis());
						String create_time = formatter.format(date).toString();
						sql = "insert into users(user_name,user_email,user_pwd,";
						sql = sql+"user_role,user_balance,user_margin,create_time,user_city) values('"+query.getName();
						sql = sql+"','"+query.getEmail()+"','"+query.getPwd()+"','normal";
						sql = sql+"',0,0,'"+create_time+"','成都');";
						System.out.println("构造的sql语句是:"+sql);
						query_db.executeUpdate(sql);
					}else{
						resultCode=300;
						resultMsg="验证码错误";
					}
				}
			}
			rs.close();
			query_db.close();
		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
			resultCode=10;
			resultMsg="查询数据库出现错误！"+sqlexception.getMessage();
		}

		jsonObj.put("result_msg",resultMsg);
		jsonObj.put("result_code",resultCode);
		return jsonObj;
	}
	public JSONObject updateRecord(User query,HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, JSONException {
		HttpSession session = request.getSession(true);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		JSONObject jsonObj=new JSONObject();
		String resultMsg="ok";
		int resultCode=200;
		try {
			ylx_db query_db = new ylx_db(query.getDbName());
			String sql="select * from users where user_email='"+query.getEmail()+"';";
			ResultSet rs = query_db.executeQuery(sql);
			if(rs.next()){
				String verify = (String)session.getAttribute("code");
				
				if(verify.equals(query.getVerifyCode())){
					sql = "update users set user_pwd='"+query.getPwd()+"' where user_email='"+query.getEmail()+"';";
					query_db.executeUpdate(sql);
					resultMsg="修改成功";
				}else{
					resultCode=300;
					resultMsg="验证码错误";
				}
			}else{
				resultCode=300;
				resultMsg="邮箱未注册";
			}
			
			rs.close();
			query_db.close();
		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
			resultCode=10;
			resultMsg="查询数据库出现错误！"+sqlexception.getMessage();
		}

		jsonObj.put("result_msg",resultMsg);
		jsonObj.put("result_code",resultCode);
		return jsonObj;
	}
}
