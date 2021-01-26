package property_manage.list;
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
		String tableName="history_trans";
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
			sql="select * from "+tableName+" where member_email='"+query.get_user_email()+"';";
			ResultSet rs = query_db.executeQuery(sql);
			if (rs.next()) {
				System.out.println("——————————————————————————————————————————————————————————————");
				jsonObj.put("id", rs.getString("id"));
				jsonObj.put("trading_number", rs.getString("trading_number"));
				jsonObj.put("create_time", rs.getString("create_time"));
				jsonObj.put("futures_index", rs.getString("futures_index"));
				jsonObj.put("index_name", rs.getString("index_name"));
				jsonObj.put("opening_price", rs.getString("opening_price"));
				jsonObj.put("quantity", rs.getString("opening_price"));
				jsonObj.put("order_type", rs.getString("order_type"));
				jsonObj.put("closing_price", rs.getString("closing_price"));
				
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
		String tableName="history_trans";
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
				//list.add(rs.getString("trading_number"));
				list.add(rs.getString("create_time"));
				list.add(rs.getString("futures_index"));
				list.add(rs.getString("index_name"));
				//不需要开仓价格
				//list.add(rs.getString("opening_price"));
				list.add(rs.getString("quantity"));
				if(rs.getString("order_type").equals("sell")){
					list.add("卖出");
				}else{
					list.add("买入");
				}
				list.add(rs.getString("closing_price"));
				//id,时间，期货指数，指数名称，数量，方式，价格
//				if(query.getId()!=null && query.getId().equals(rs.getString("user_id"))){
//					list.add("1");
//				}else{
//					list.add("0");
//				}
//				list.add(count);
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
			String tableName="property_manage";
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
	
	//1219
	public JSONObject orderAdd(Users todo) throws JSONException, SQLException {
		System.out.println("==================================orderAdd==================================");
		String bill_code=new String();
		String resultMsg = "ok";
		int resultCode = 0;
		List<List<Comparable>> jsonList = new ArrayList<List<Comparable>>();
		ylx_db query_db = new ylx_db(todo.getDbName());
		// 构造sql语句，根据传递过来的查询条件参数
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String create_time = formatter.format(date).toString();
		// bill_dode 判断1：股数够不够 判断2：钱够不够 判断3：是否存在
		// 判断4：并不拥有，判断5：持仓储量不足
		//9,10成功
		// 判断是否存在
		boolean isExist = false;
		String sql_exist = "select * from index_data where index_id='" + todo.get_index_id() + "'";
		ResultSet re_exist = query_db.executeQuery(sql_exist);
		
		//0111注释：判断是否存在
		if (re_exist.next()) {
			System.out.println("该指数存在！");
			isExist = true;
		} else {
			bill_code = "3";
			System.out.println("该指数不存在！");
		}
		
		System.out.println("定位1------");
		System.out.println(isExist + "  " + todo.get_order_type());
		System.out.println("定位2------");
//		if(isExist) System.out.println("定位4------");
//		if(todo.get_order_type().equals("buy")) System.out.println("定位5------");
		
		if (isExist && todo.get_order_type().equals("buy")){
			// 判断股数是否足够
			System.out.println("定位3------");
			String sql_get_inventory = "select " + todo.get_quantity()
					+ "-inventory as judgeAmount from index_data where  index_data.index_id='" + todo.get_index_id()
					+ "'";
			System.out.println("sql_get_inventory=" + sql_get_inventory);
			ResultSet re_get_amount = query_db.executeQuery(sql_get_inventory);
			re_get_amount.next();
			float judgeAmount = re_get_amount.getFloat("judgeAmount");
			if (judgeAmount > 0) {
				System.out.println("定位3------");
				System.out.println("============仓库储量不足===============");
				bill_code = "1";
			}
			// 判断钱够不够语句.sql_get_remain
			else {
				String sql_get_remain = "select user_balance-latest_price*" + todo.get_quantity()
						+ " as remain from users,index_data where users.user_email='" + todo.get_user_email()
						+ "' and index_data.index_id='" + todo.get_index_id() + "'";
				System.out.println("sql_get_remain=" + sql_get_remain);
				ResultSet re_get_remain = query_db.executeQuery(sql_get_remain);
				re_get_remain.next();
				float remain = re_get_remain.getFloat("remain");
				System.out.println("剩余金额：" + remain);
				if (remain < 0) {
					System.out.println("============金额不足===============");
					bill_code = "2";
				} else {
					// 把users表单中钱扣除
					String sql_update_user_balance = "update users set user_balance=" + remain + " where user_email='"
							+ todo.get_user_email() + "'";
					query_db.executeUpdate(sql_update_user_balance);
					System.out.println("sql_update_user_balance:" + sql_update_user_balance);
					// 在history_trans中插入数据
					String sql_insert_history_trans = "insert into history_trans (create_time,futures_index,index_name,quantity,order_type, closing_price, member_email)  select  '"
							+ create_time + "', index_id,index_name," + todo.get_quantity()
							+ ",'buy',index_data.latest_price,'" + todo.get_user_email()
							+ "' from index_data where index_data.index_id='" + todo.get_index_id() + "'";
					// insert into history_trans
					// (create_time,futures_index,index_name,quantity,order_type,closing_price,
					// member_email) select '2020-12-20
					// 20:12:12',index_id,index_name,12,'buy',index_data.latest_price,'1'
					// from index_data where index_data.index_id='AHD';
					query_db.executeUpdate(sql_insert_history_trans);
					System.out.println("sql_insert_history_trans:" + sql_insert_history_trans);
					// 判断是否已经拥有
					boolean isOwn = false;
					String sql_judge_is_own = "select * from current_trans where futures_index='" + todo.get_index_id()
							+ "' and member_email='" + todo.get_user_email() + "'";
					ResultSet re_judge_own = query_db.executeQuery(sql_judge_is_own);
					if (re_judge_own.next()) {
						isOwn = true;
					}
					// 已经拥有,更新创建时间，quantity
					if (isOwn) {
						String sql_update_current_trans = "update current_trans set quantity=quantity+"
								+ todo.get_quantity() + " where futures_index='" + todo.get_index_id() + "'";
						System.out.println("sql_update_current_trans:" + sql_update_current_trans);
						query_db.executeUpdate(sql_update_current_trans);
					}
					// 未拥有
					else {
						// insert into current_trans
						// (create_time,futures_index,index_name,quantity,order_type,
						// closing_price, member_email)select '2020-10-12
						// 21:12:12',
						// index_id,index_name,12,'buy',index_data.latest_price,'1'
						// from index_data where index_data.index_id='AHD';
						String sql_insert_current_trans = "insert into current_trans (create_time,futures_index,index_name,quantity, closing_price, member_email) select '"
								+ create_time + "', index_id,index_name," + todo.get_quantity()
								+ ",index_data.latest_price,'" + todo.get_user_email()
								+ "' from index_data where index_data.index_id='" + todo.get_index_id() + "'";
						System.out.println("sql_insert_current_trans：" + sql_insert_current_trans);
						query_db.executeUpdate(sql_insert_current_trans);
						bill_code="9";//买入交易
					}
				}
			}
		}
		
		if (isExist && todo.get_order_type().equals("sell")) {
			// String sql_get_inventory="select
			// "+todo.get_quantity()+"-inventory as judgeAmount from index_data
			// where index_data.index_id='"+todo.get_index_id()+"'";
			// System.out.println("sql_get_inventory="+sql_get_inventory);
			// ResultSet re_get_amount= query_db.executeQuery(sql_check_have);
			// re_get_amount.next();
			// float judgeAmount =re_get_amount.getFloat("judgeAmount");
			// 判断自己有没有
			boolean isHave = false;
			String sql_check_have = "select * from current_trans where futures_index='" + todo.get_index_id() + "'";
			System.out.println("sql_check_have=" + sql_check_have);
			ResultSet check_have = query_db.executeQuery(sql_check_have);
			if (check_have.next()) {
				isHave = true;
			} else {
				System.out.println("您没有该期货");
				bill_code = "4";
			}

			if (isHave) {
				// 判断股数是否足够
				String sql_get_quantity = "select quantity-" + todo.get_quantity()
						+ " as judgeAmount from current_trans where futures_index='" + todo.get_index_id() + "'";
				System.out.println("sql_get_quantity=" + sql_get_quantity);
				ResultSet re_get_quantity = query_db.executeQuery(sql_get_quantity);
				re_get_quantity.next();
				float judgeAmount = re_get_quantity.getFloat("judgeAmount");
				if (judgeAmount < 0) {
					System.out.println("============持仓储量不足===============");
					bill_code = "5";
				}
				// 开始卖
				else {
					bill_code="10";
					// 得到钱的数量
					String sql_get_remain = "select user_balance+latest_price*" + todo.get_quantity()
							+ " as remain from users,index_data where users.user_email='" + todo.get_user_email()
							+ "' and index_data.index_id='" + todo.get_index_id() + "'";
					System.out.println("sql_get_remain=" + sql_get_remain);
					ResultSet re_get_remain = query_db.executeQuery(sql_get_remain);
					re_get_remain.next();
					float remain = re_get_remain.getFloat("remain");
					System.out.println("剩余金额：" + remain);

					// 把users表单中钱加上
					String sql_update_user_balance = "update users set user_balance=" + remain + " where user_email='"
							+ todo.get_user_email() + "'";
					query_db.executeUpdate(sql_update_user_balance);
					System.out.println("sql_update_user_balance:" + sql_update_user_balance);
					// 在history_trans中插入数据
					String sql_insert_history_trans = "insert into history_trans (create_time,futures_index,index_name,quantity,order_type, closing_price, member_email)  select  '"
							+ create_time + "', index_id,index_name," + todo.get_quantity()
							+ ",'sell',index_data.latest_price,'" + todo.get_user_email()
							+ "' from index_data where index_data.index_id='" + todo.get_index_id() + "'";

					query_db.executeUpdate(sql_insert_history_trans);
					System.out.println("sql_insert_history_trans:" + sql_insert_history_trans);
					// 判断是否已经卖完
					boolean isZero = false;
					String sql_judge_is_zero = "select quantity-" + todo.get_quantity()
							+ " as checkZero from current_trans where futures_index='" + todo.get_index_id()
							+ "' and member_email='" + todo.get_user_email() + "'";
					ResultSet re_judge_zero = query_db.executeQuery(sql_judge_is_zero);
					re_judge_zero.next();
					if (re_judge_zero.getFloat("checkZero") == 0) {
						isZero = true;
					}
					// 不是空
					if (!isZero) {
						String sql_update_current_trans = "update current_trans set quantity=quantity-"
								+ todo.get_quantity() + " where futures_index='" + todo.get_index_id() + "'";
						System.out.println("sql_update_current_trans:" + sql_update_current_trans);
						query_db.executeUpdate(sql_update_current_trans);
					}
					// 买完了
					else {
						String sql_del_current_trans = "delete from current_trans where member_email='"
								+ todo.get_user_email() + "' and futures_index='" + todo.get_index_id() + "'";
						System.out.println("sql_del_current_trans：" + sql_del_current_trans);
						query_db.executeUpdate(sql_del_current_trans);

					}
				}
			}
		}

		query_db.close();
		// //下面开始构建返回的json
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("aaData", jsonList);
		jsonObj.put("action", todo.getAction());
		jsonObj.put("result_msg", resultMsg);// 如果发生错误就设置成"error"等
		jsonObj.put("result_code", resultCode);// 返回0表示正常，不等于0就表示有错误产生，错误代码
		jsonObj.put("bill_code", bill_code);
		return jsonObj;
	}
	
	
	
	public JSONObject addRecord(Users todo) throws JSONException, SQLException{
		String resultMsg="ok";
		int resultCode=0;
		List<List<Comparable>> jsonList = new ArrayList<List<Comparable>>();
		ylx_db query_db = new ylx_db(todo.getDbName());
		//构造sql语句，根据传递过来的查询条件参数
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String create_time = formatter.format(date).toString();
		
		String tableName="property_manage";
		String sql="insert into "+tableName+"(city,temp,text,wind_dir,wind_scale,wind_speed,vis,create_time) values('"
				+todo.getCity()+"','"+todo.getTemp()+"','"+todo.getText()+
			"','"+todo.getWinddir()+"','"+todo.getWindscale()+"','"+todo.getWindspeed()+"','"+todo.getVis()+"','"+create_time+"');";
		System.out.println("property_manageDao addRecord执行的sql="+sql);
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
		String tableName="property_manage";
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
		System.out.println("执行到了property_manage 的createGetRecordSql语句，query.getId()="+query.getId()+",query.getAction="+query.getAction());
		String sql="";
		String where="";
		if(query.get_user_email()!=null){
			where="where member_email="+query.get_user_email();
		}
		String orderByTime=" order by create_time desc";
		if(query.getType().equals("all") && query.getRole().equals("manager") && query.getAction().toString() !="query_record"){
			sql="select * from "+query.getTableName();
		}else{
				sql="select * from "+query.getTableName()+" "+where;
		}
		//=======================构造的排序条件================
		String orderBy="";
		if((query.getSortIndex()!=null)&&query.getOrderBy()!=null){
			
				orderBy=" order by "+getOrderBy(query.getOrderBy());
			}
		System.out.println(orderBy+"         = == = = ==  === "+query.getSortIndex()+query.getOrderBy());
		//=======================构造结束=====================
		if(orderBy.equals("")){
			sql=sql+orderByTime;
		}
		else sql=sql+orderBy;
		
		System.out.println("property_manage/createGetRecordSql中构造的sql语句是"+sql);
		return sql;
	}
	
	//排序使用的函数
	private String getOrderBy(String orderName){
		if(orderName.equals("create_time")) orderName="create_time";
		if(orderName.equals("futures_index")) orderName="futures_index";
		if(orderName.equals("quantity")) orderName="quantity";		
		return orderName;
	}
}
