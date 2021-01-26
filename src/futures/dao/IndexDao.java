package futures.dao;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import dao.ylx_db;

public class IndexDao{
	public void updateIndex(Index record) throws JSONException, SQLException{
		ylx_db update_db = new ylx_db(record.getDbName());
		//构造sql语句，根据传递过来的查询条件参数
		String tableName="index_data";
		String sql="update "+tableName+" set index_name='"+record.getIndexName()+"',latest_price='"+record.getLatestPrice()+"',change_amount="+record.getChangeAmount()+",";
		sql = sql +"change_rate='"+record.getChangeRate()+"',price_yesterday='"+record.getPriceYesterday()+"',price_today='";
		sql = sql +record.getPriceToday()+"',highest_price='"+record.getHighestPrice()+"',lowest_price='"+record.getLowestPrice()+"',";
		sql = sql +"buy_price='"+record.getBuyPrice()+"',sell_price='"+record.getSellPrice()+"',inventory='"+record.getInventory()+"' where index_id='"+record.getIndexId()+"';";
		update_db.executeUpdate(sql);
		update_db.close();
	}

	public JSONObject getWealth(Index query) throws SQLException, IOException, JSONException {
		String tableName="user_wealth";
		//开始查询数据库
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		try {
			ylx_db query_db = new ylx_db(query.getDbName());
			//构造sql语句，根据传递过来的查询条件参数
			String sql="select * from "+tableName+" where user_id='"+query.getUserId()+"';";
			int count=0;
			query.setTableName(tableName);
			System.out.println("sql="+sql);
			ResultSet rs = query_db.executeQuery(sql);
			while (rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("id"));	
				list.add(rs.getString("id"));
				list.add(rs.getString("index_id"));
				list.add(rs.getString("index_name"));
				list.add(rs.getString("index_quantity"));
				list.add(rs.getString("bought_price"));
				
				if(query.getCreatorId()!=null && query.getCreatorId().equals(rs.getString("creator_id"))){
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
	
	public JSONObject testTrade(Index query,HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, JSONException {
		String tableName="index_data";
		//开始查询数据库
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		try {
			ylx_db query_db = new ylx_db(query.getDbName());
			//构造sql语句，根据传递过来的查询条件参数
			String sql="select * from "+tableName+" where index_id ='"+query.getIndexId()+"';";
			query.setTableName(tableName);
			ResultSet rs = query_db.executeQuery(sql);
			String latestPrice ="";
			if(rs.next()){
				latestPrice = rs.getString("latest_price");
			}
			System.out.println("latestPrice"+latestPrice);
			Double double_latest_price = Double.parseDouble(latestPrice); 
			Double double_order_number = Double.parseDouble(query.getOrderNumber());
			Double double_user_balance = Double.parseDouble(query.getUserBalance());
			System.out.println("double_user_balance="+double_user_balance);
			if (double_latest_price*double_order_number <= double_user_balance){
				String new_balance =(double_user_balance-double_latest_price*double_order_number)+"";
				System.out.println("new_balance="+new_balance);
				
				//需要更新用户余额
				//需要插入user_wealth
				sql  = "update users set user_balance = '"+new_balance+"' where user_email='"+query.getUserId()+"';";
				System.out.println("sql="+sql);
				query_db.executeUpdate(sql);
				
				sql  = "insert into user_wealth(user_id,index_id,index_name,index_quantity,bought_price) values('";
				sql  = sql +query.getUserId()+"','"+query.getIndexId()+"','"+query.getIndexName()+"','"+query.getOrderNumber();
				sql = sql +"','"+latestPrice+"');";
				
				
				System.out.println("sql="+sql);
				query_db.executeUpdate(sql);
				
				resultMsg="购买成功";
			}else{
				resultCode=1; //余额不足
				resultMsg="余额不足，请充值后购买";
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
	public JSONObject sell(Index query,HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, JSONException {
		String tableName="index_data";
		//开始查询数据库
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		try {
			ylx_db query_db = new ylx_db(query.getDbName());
			//构造sql语句，根据传递过来的查询条件参数
			String sql="select * from "+tableName+" where index_id ='"+query.getIndexId()+"';";
			query.setTableName(tableName);
			System.out.println("sql="+sql);
			ResultSet rs = query_db.executeQuery(sql);
			String latestPrice ="";
			if(rs.next()){
				latestPrice = rs.getString("latest_price");
			}
			System.out.println("latestPrice="+latestPrice);
			sql = "select * from user_wealth where index_id = '"+query.getIndexId()+"';";
			rs = query_db.executeQuery(sql);
			String index_quantity = "";
			if(rs.next()){
				index_quantity = rs.getString("index_quantity");
			}
			System.out.println("index_quantity="+index_quantity);
			
			Double double_latest_price = Double.parseDouble(latestPrice); 
			
			Double double_order_number = Double.parseDouble(query.getOrderNumber());
			Double double_user_balance = Double.parseDouble(query.getUserBalance());
			System.out.println("double_user_balance="+double_user_balance);
			
			if(double_order_number < Double.parseDouble(index_quantity)){
				String new_number = (Double.parseDouble(index_quantity) - double_order_number)+"";
				String new_balance =(double_user_balance+double_latest_price*double_order_number)+"";
				System.out.println("new_balance="+new_balance);
				//需要更新用户余额
				//需要更新user_wealth
				sql  = "update users set user_balance = '"+new_balance+"' where user_email='"+query.getUserId()+"';";
				System.out.println("sql="+sql);
				query_db.executeUpdate(sql);
				
				sql = "update user_wealth set index_quantity= '"+new_number+"' where index_id='"+query.getIndexId()+"' and user_id='"+query.getUserId()+"';";
				System.out.println("sql="+sql);
				query_db.executeUpdate(sql);
				
				resultMsg="卖出成功";
			}else if(double_order_number == Double.parseDouble(index_quantity)){
				String new_balance =(double_user_balance+double_latest_price*double_order_number)+"";
				System.out.println("new_balance="+new_balance);
				//需要更新用户余额
				//需要更新user_wealth
				sql  = "update users set user_balance = '"+new_balance+"' where user_email='"+query.getUserId()+"';";
				System.out.println("sql="+sql);
				query_db.executeUpdate(sql);
				
				sql = "delete from user_wealth where index_id='"+query.getIndexId()+"' and user_id='"+query.getUserId()+"';";
				System.out.println("sql="+sql);
				query_db.executeUpdate(sql);
				resultMsg="卖出成功";
			}else{
				resultMsg="卖出失败，已有的期货数量不足";
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
	
	
	/*
	 * 功能：返回结果集
	 */
	public JSONObject getRecord(Index query) throws SQLException, IOException, JSONException {
		String tableName="index_data";
		//开始查询数据库
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		try {
			ylx_db query_db = new ylx_db(query.getDbName());
			//构造sql语句，根据传递过来的查询条件参数
			String sql="select * from "+tableName+";";
			int count=0;
			if(query.getAction().equals("query_record")){
				
				sql = "select * from "+tableName+" where index_id='"+query.getIndexId()+"';";
			}
			query.setTableName(tableName);
			ResultSet rs = query_db.executeQuery(sql);
			while (rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("id"));
				list.add(rs.getString("index_id"));
				list.add(rs.getString("index_name"));
				list.add(rs.getString("change_amount"));
				list.add(rs.getString("latest_price"));
				list.add(rs.getString("price_yesterday"));
				list.add(rs.getString("price_today"));
				list.add(rs.getString("highest_price"));
				list.add(rs.getString("lowest_price"));
				list.add(rs.getString("buy_price"));
				list.add(rs.getString("sell_price"));
				list.add(rs.getString("inventory"));
				if (sql.length() > 30 ){
					resultCode=3;
				}
				if(query.getCreatorId()!=null && query.getCreatorId().equals(rs.getString("creator_id"))){
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

	
	public JSONObject getRecordById(String action,String dbName,String id) throws JSONException{
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		try {
			ylx_db query_db = new ylx_db(dbName);
			//构造sql语句，根据传递过来的查询条件参数
			String tableName="trade_index";
			String sql="select * from "+tableName+" where id="+id+" order by create_time desc";
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
	public JSONObject addRecord(Index todo) throws JSONException, SQLException{
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		ylx_db query_db = new ylx_db(todo.getDbName());
		//构造sql语句，根据传递过来的查询条件参数
		String tableName="trade_index";
		String sql="insert into "+tableName+"(index_id,index_name,index_value,up_down,rise_fall,quantity,price,creator_id,creator,create_time) values('"+todo.getIndexId()+"','"+todo.getIndexName()+"','"+todo.getIndexValue()+
			"','"+todo.getUpDown()+"','"+todo.getRiseFall()+"','"+todo.getQuantity()+"','"+todo.getPrice()+"','"+todo.getCreatorId()+"','"+todo.getCreator()+"','"+todo.getCreateTime()+"')";
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
		String tableName="trade_index";
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
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
	public JSONObject setRecordTop(String action,String dbName,String type,String userId,String id) throws JSONException, SQLException{
		String tableName="trade_index";
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		ylx_db query_db = new ylx_db(dbName);
		ylx_db update_db = new ylx_db(dbName);
		//构造sql语句，根据传递过来的查询条件参数
		//String sql="select max(priority) as priority from trade_index where creator_id='"+userId+"'";
		//int priority=0;
		//ResultSet rs=query_db.executeQuery(sql);
		/*if(rs.next()){
			priority=rs.getInt("priority");
		}
		query_db.close();
		update_db.executeUpdate("update trade_index set priority="+(priority+1)+" where id="+id);
		update_db.close();*/
		//下面开始构建返回的json
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("aaData",jsonList);
		jsonObj.put("action",action);
		jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
		jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}
	/*
	 * 功能：构造历史记录查询的sql语句,type=all查询所有，type=id查询某个记录，余下按照条件设置查询
	 */
	private String createGetRecordSql(Index query){
		String sql="";
		String where="";
		if(query.getId()!=null && !query.getId().equals("null")){
			where="where id="+query.getId();
		}
		if(query.getIndexName()!=null && !query.getIndexName().equals("null") && !query.getIndexName().isEmpty()){
			if(!where.isEmpty()){
				where=where+" and title like '%"+query.getIndexName()+"%'";
			}else{
				where="where title like '%"+query.getIndexName()+"%'";
			}
		}
		if(query.getTimeFrom()!=null && query.getTimeTo()!=null && !query.getTimeFrom().isEmpty()){
			if(!where.isEmpty()){
				where=where+" and create_time between '"+query.getTimeFrom()+"' and '"+query.getTimeTo()+"'";
			}else{
				where="where create_time between '"+query.getTimeFrom()+"' and '"+query.getTimeTo()+"'";
			}
		}
		if(query.getType()!=null && query.getType().equals("all") && query.getCreator().equals("manager")){
			sql="select * from "+query.getTableName()+" order by create_time desc";
		}else{
			if(query.getId()!=null && !query.getId().equals("null")){
				sql="select * from "+query.getTableName()+" where id="+query.getId();
			}else{
				if(where.isEmpty()){
					sql="select * from "+query.getTableName()+" where user_id='"+query.getCreatorId()+"' order by create_time desc";
				}else{
					sql="select * from "+query.getTableName()+" "+where+" and user_id='"+query.getCreatorId()+"' order by create_time desc";
				}
			}
		}
		return sql;
	}
}
