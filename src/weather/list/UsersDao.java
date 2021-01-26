package weather.list;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import dao.ylx_db;

public class UsersDao{
	
	public JSONObject queryRecord(Users query) throws SQLException, IOException, JSONException {
		String tableName="weather";
		//开始查询数据库
		String resultMsg="ok";
		int resultCode=0;
		JSONObject jsonObj=new JSONObject();
		try {
			ylx_db query_db = new ylx_db(query.getDbName());
			//构造sql语句，根据传递过来的查询条件参数
			String sql="";
			int count=0;
			query.setTableName(tableName);
			sql="select * from "+tableName+" where city='"+query.getCity()+"';";
			ResultSet rs = query_db.executeQuery(sql);
			if (rs.next()) {
				System.out.println("——————————————————————————————————————————————————————————————");
				jsonObj.put("id", rs.getString("id"));
				jsonObj.put("obs_time", rs.getString("obs_time"));
				jsonObj.put("temp", rs.getString("temp"));
				jsonObj.put("icon", rs.getString("icon"));
				jsonObj.put("text", rs.getString("text"));
				jsonObj.put("wind_dir", rs.getString("wind_dir"));
				jsonObj.put("wind_scale", rs.getString("wind_scale"));
				jsonObj.put("wind_speed", rs.getString("wind_speed"));
				jsonObj.put("vis", rs.getString("vis"));
				
			}
			rs.close();
			query_db.close();
		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
			resultCode=10;
			resultMsg="查询数据库出现错误！"+sqlexception.getMessage();
		}
		//////////数据库查询完毕，得到了json数组jsonList//////////
		//jsonList.clear();
		//下面开始构建返回的json
	
		jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
		jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}
	
	/*
	 * 功能：返回结果集
	 */
	public JSONObject getRecord(Users query) throws SQLException, IOException, JSONException {
		String tableName="weather";
		//开始查询数据库
		String resultMsg="ok";
		int resultCode=0;
		List<List<Comparable>> jsonList = new ArrayList<List<Comparable>>();
		try {
			ylx_db query_db = new ylx_db(query.getDbName());
			//构造sql语句，根据传递过来的查询条件参数
			String sql="";
			int count=0;
			query.setTableName(tableName);
			sql=createGetRecordSql(query);
			ResultSet rs = query_db.executeQuery(sql);
			int recordCount=1;
			while (rs.next()) {
				List<Comparable> list = new ArrayList<Comparable>();
				list.add(rs.getString("id"));
				//list.add(rs.getString("id"));
				list.add(rs.getString("city"));
				list.add(rs.getString("obs_time"));
				list.add(rs.getString("temp"));
				list.add(rs.getString("icon"));
				list.add(rs.getString("text"));
				list.add(rs.getString("wind_dir"));
				list.add(rs.getString("wind_scale"));
				list.add(rs.getString("wind_speed"));
				list.add(rs.getString("vis"));
				if(query.getId()!=null && query.getId().equals(rs.getString("user_id"))){
					list.add("1");
				}else{
					list.add("0");
				}
				list.add(count);
				count=count+1;	//做上下记录导航用
				jsonList.add(list);
			}
			rs.close();
			query_db.close();
		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
			resultCode=10;
			resultMsg="查询数据库出现错误！"+sqlexception.getMessage();
		}
		//////////数据库查询完毕，得到了json数组jsonList//////////
		//jsonList.clear();
		//下面开始构建返回的json
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("aaData",jsonList);
		jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
		jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}
	
	/*public JSONObject modifyUserInfo(Users todo) throws JSONException{
		//String action,String dbName,String id,String title,String content,String creator,String createTime
		String resultMsg="ok";
		int resultCode=0;
		List<List<Comparable>> jsonList = new ArrayList<List<Comparable>>();
		try {
			ylx_db query_db = new ylx_db(todo.getDbName());
			//构造sql语句，根据传递过来的查询条件参数
			String tableName="weather";
			String sql="";
			if(todo.getPwd()!=null && todo.getPwd() != ""){
				sql = "update users set user_pwd='"+todo.getPwd()+"' where user_email='"+todo.getEmail()+"';";
			}
			if(todo.getBalance()!=null && todo.getBalance() !=""){
				sql = "select user_balance from users where user_email='"+todo.getEmail()+"';";
				ResultSet rsTemp = query_db.executeQuery(sql);
				String cur_balance="0";
				if(rsTemp.next()){
					cur_balance = rsTemp.getString("user_balance");
				}
				Double balance = Double.parseDouble(cur_balance)+Double.parseDouble(todo.getBalance());
				System.out.println("[users.list/UsersDao/modifyUserInfo]更新用户余额为"+balance.toString());
				sql = "update users set user_balance="+balance.toString()+" where user_email='"+todo.getEmail()+"';";
			}
			if(todo.getName()!=null){
				if(todo.getCity()!=null){
					sql = "update users set user_name='"+todo.getName()+"',user_city='"+todo.getCity()+"' where user_email='"+todo.getEmail()+"';";
				}else{
					sql = "update users set user_name='"+todo.getName()+"' where user_email='"+todo.getEmail()+"';";
				}
			}
			System.out.println("[users.list/UsersDao/modifyUserInfo]执行的sql语句是"+sql);
			query_db.executeUpdate(sql);
			sql="select * from "+tableName+" order by create_time desc";
			ResultSet rs = query_db.executeQuery(sql);
			while (rs.next()) {
				List<Comparable> list = new ArrayList<Comparable>();
				list.add(rs.getString("id"));
				jsonList.add(list);
			}
			rs.close();
			query_db.close();
		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
			resultCode=10;
			resultMsg="查询数据库出现错误！"+sqlexception.getMessage();
		}
		//下面开始构建返回的json
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("aaData",jsonList);
		jsonObj.put("action",todo.getAction());
		jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
		jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}*/
	
	public JSONObject modifyRecord(Users todo) throws JSONException{
		//String action,String dbName,String id,String title,String content,String creator,String createTime
		String resultMsg="ok";
		int resultCode=0;
		List<List<Comparable>> jsonList = new ArrayList<List<Comparable>>();
		try {
			ylx_db query_db = new ylx_db(todo.getDbName());
			//构造sql语句，根据传递过来的查询条件参数
			String tableName="weather";
			String sql="update "+tableName+" set temp='"+todo.getTemp()+"',text='"+todo.getText()+"',wind_dir='"+todo.getWinddir()+
					"',wind_scale='"+todo.getWindscale()+"',wind_speed='"+todo.getWindspeed()+"',vis='"+todo.getVis()+"' where city='"+todo.getCity()+"'";
			System.out.println(sql);
			query_db.executeUpdate(sql);
			System.out.println(sql);
			sql="select * from "+tableName+" order by create_time desc";
			ResultSet rs = query_db.executeQuery(sql);
			while (rs.next()) {
				List<Comparable> list = new ArrayList<Comparable>();
				list.add(rs.getString("id"));
				jsonList.add(list);
			}
			rs.close();
			query_db.close();
		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
			resultCode=10;
			resultMsg="查询数据库出现错误！"+sqlexception.getMessage();
		}
		//下面开始构建返回的json
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("aaData",jsonList);
		jsonObj.put("action",todo.getAction());
		jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
		jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}
	/*public JSONObject getRecordById(String action,String dbName,String id) throws JSONException{
		String resultMsg="ok";
		int resultCode=0;
		List<List<Comparable>> jsonList = new ArrayList<List<Comparable>>();
		try {
			ylx_db query_db = new ylx_db(dbName);
			//构造sql语句，根据传递过来的查询条件参数
			String tableName="weather";
			String sql="select * from "+tableName+" where id="+id+" order by create_time desc";
			ResultSet rs = query_db.executeQuery(sql);
			while (rs.next()) {
				List<Comparable> list = new ArrayList<Comparable>();
				list.add(rs.getString("id"));
				list.add(rs.getString("content"));
				jsonList.add(list);
			}
			rs.close();
			query_db.close();
		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
			resultCode=10;
			resultMsg="查询数据库出现错误！"+sqlexception.getMessage();
		}
		//下面开始构建返回的json
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("aaData",jsonList);
		jsonObj.put("action",action);
		jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
		jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}*/
	public JSONObject addRecord(Users todo) throws JSONException, SQLException{
		String resultMsg="ok";
		int resultCode=0;
		List<List<Comparable>> jsonList = new ArrayList<List<Comparable>>();
		ylx_db query_db = new ylx_db(todo.getDbName());
		//构造sql语句，根据传递过来的查询条件参数
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String create_time = formatter.format(date).toString();
		
		String tableName="weather";
		String sql="insert into "+tableName+"(city,temp,text,wind_dir,wind_scale,wind_speed,vis,create_time) values('"
				+todo.getCity()+"','"+todo.getTemp()+"','"+todo.getText()+
			"','"+todo.getWinddir()+"','"+todo.getWindscale()+"','"+todo.getWindspeed()+"','"+todo.getVis()+"','"+create_time+"');";
		System.out.println("WeatherDao addRecord执行的sql="+sql);
		query_db.executeUpdate(sql);
		query_db.close();
		//下面开始构建返回的json
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("aaData",jsonList);
		jsonObj.put("action",todo.getAction());
		jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
		jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}
	public JSONObject deleteRecord(String action,String dbName,String[] ids,String creator,String createTime) throws JSONException, SQLException{
		String tableName="weather";
		String resultMsg="ok";
		int resultCode=0;
		List<List<Comparable>> jsonList = new ArrayList<List<Comparable>>();
		ylx_db query_db = new ylx_db(dbName);
		//构造sql语句，根据传递过来的查询条件参数
		for(int i=0;i<ids.length;i++){
			String sql="delete from "+tableName+" where id="+ids[i];
			query_db.executeUpdate(sql);
		}
		query_db.close();
		//下面开始构建返回的json
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("aaData",jsonList);
		jsonObj.put("action",action);
		jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
		jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}
	/*public JSONObject setRecordTop(String action,String dbName,String type,String userId,String id) throws JSONException, SQLException{
		String tableName="users";
		String resultMsg="ok";
		int resultCode=0;
		List<List<Comparable>> jsonList = new ArrayList<List<Comparable>>();
		ylx_db query_db = new ylx_db(dbName);
		ylx_db update_db = new ylx_db(dbName);
		//构造sql语句，根据传递过来的查询条件参数
		String sql="select max(priority) as priority from project_todo where user_id='"+userId+"'";
		int priority=0;
		ResultSet rs=query_db.executeQuery(sql);
		if(rs.next()){
			priority=rs.getInt("priority");
		}
		query_db.close();
		update_db.executeUpdate("update project_todo set priority="+(priority+1)+" where id="+id);
		update_db.close();
		//下面开始构建返回的json
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("aaData",jsonList);
		jsonObj.put("action",action);
		jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
		jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}*/
	/*
	 * 功能：构造历史记录查询的sql语句,type=all查询所有，type=id查询某个记录，余下按照条件设置查询
	 */
	private String createGetRecordSql(Users query){
		System.out.println("执行到了weatherDao 的createGetRecordSql语句，query.getId()="+query.getId()+",query.getAction="+query.getAction());
		String sql="";
		String where="";
		if(query.getId()!=null){
			where="where id="+query.getId();
		}
		if(query.getType().equals("all") && query.getRole().equals("manager") && query.getAction().toString() !="query_record"){
			sql="select * from "+query.getTableName()+" order by create_time desc";
		}else{
			if(query.getId()!=null && !query.getId().equals("null")){
				sql="select * from "+query.getTableName()+" where id="+query.getId();
			}else{
				if(where.isEmpty()){
					sql="select * from "+query.getTableName()+" order by create_time desc";
				}else{
					sql="select * from "+query.getTableName()+" "+where+" and id='"+query.getId()+"' order by create_time desc";
				}
			}
		}
		
		System.out.println("WeatherDao/createGetRecordSql中构造的sql语句是"+sql);
		return sql;
	}
}
