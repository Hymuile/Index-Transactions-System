package money_manage.dao;
import java.sql.Connection;
import java.io.IOException;
import java.sql.PreparedStatement;
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
public class StatisticDao{
	public String module = "project";
	public String sub = "todo";
	/*
	 * 功能：模板函数
	 */
	public JSONObject doAction(String action,String dbName,String id,String content,String creator,String createTime) throws JSONException{
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		try {
			ylx_db query_db = new ylx_db(dbName);
			//构造sql语句，根据传递过来的查询条件参数
			String tableName="project_statistic";
			String sql="insert into "+tableName+"(content,creator,create_time) values('"+content+"','"+creator+"','"+createTime+"')";
			query_db.executeUpdate(sql);
			sql="select * from "+tableName+" order by create_time desc";
			ResultSet rs = query_db.executeQuery(sql);
			while (rs.next()) {
				List list = new ArrayList();
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
	}
	public void showDebug(String msg){
		System.out.println("["+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())+"]["+module+"/"+sub+"/StatisticDao]"+msg);
	}
	public JSONObject statisticRecord(String action,String dbName,Statistic statistic) throws SQLException, IOException, JSONException {
		//开始查询数据库
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		int count=0;
		try {
			ylx_db query_db = new ylx_db(dbName);
			//构造sql语句，根据传递过来的查询条件参数
			String sql=getStatisticRecordSql(statistic);
			ResultSet rs = query_db.executeQuery(sql);
			while (rs.next()) {
				//查询出来有plate_color,time_interval,count,plate_color_name这几项
				List list = new ArrayList();
				list.add(count+"");
				list.add(rs.getString("time_interval"));
				list.add(rs.getString("count"));
				list.add("无区分");
				list.add("无区分");
				//showDebug(rs.getString("plate_color")+","+rs.getString("address_id")+","+rs.getString("time_interval")+","+rs.getString("count"));
				jsonList.add(list);
				count=count+1;
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
		jsonObj.put("action",action);
		jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
		jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}
	private String getStatisticRecordSql(Statistic statistic){
		String sql="";
		String timeInterval="";
		if(statistic.getTimeInterval().equals("hour")){
			timeInterval="%Y-%m-%d %h";
		}
		if(statistic.getTimeInterval().equals("day")){
			timeInterval="%Y-%m-%d";
		}
		if(statistic.getTimeInterval().equals("month")){
			timeInterval="%Y-%m";
		}
		sql="select date_format(create_time,\""+timeInterval+"\") as time_interval,count(*) as count from money_op";
		sql=sql+" where create_time between \""+statistic.getTimeFrom()+"\" and \""+statistic.getTimeTo()+"\"";
		sql=sql+" group by time_interval order by time_interval";
		return sql;
	}
}
