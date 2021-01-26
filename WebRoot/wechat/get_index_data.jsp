<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@page import="java.sql.*,java.io.*"%>
<%@page import="java.text.*, org.json.JSONArray,org.json.JSONObject,java.util.ArrayList"%>
<%@page import="java.util.HashMap,java.util.List,java.util.Map,java.io.IOException"%>
<html>
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	</head>
	<body>
		<%
			//开始连接数据库，需要先把mysql-connector-java-5.0.4-bin.jar和json.jar拷贝到ROOT/WEB-INF/lib下
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException classnotfoundexception) {
				classnotfoundexception.printStackTrace();
			}
			System.out.println("加载了JDBC驱动");
			List jsonList = new ArrayList();

			//然后链接数据库，开始操作数据表
			try {
				Connection conn = DriverManager
						.getConnection("jdbc:mysql://localhost:3366/xm09?user=XM09&password=123456&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT");
				Statement statement = conn.createStatement();
				System.out.println("已经链接上数据库！");
				String sql = "select * from index_data";
				//out.println("即将执行的SQL语句是："+sql);
				ResultSet rs = statement.executeQuery(sql);
				while(rs.next()){
					List list=new ArrayList();
					list.add(rs.getString("index_id"));
					list.add(rs.getString("index_name"));
					list.add(rs.getString("latest_price"));
					list.add(rs.getString("change_amount"));
					list.add(rs.getString("change_rate"));
					list.add(rs.getString("price_yesterday"));
					list.add(rs.getString("price_today"));
					list.add(rs.getString("highest_price"));
					list.add(rs.getString("lowest_price"));
					list.add(rs.getString("buy_price"));
					list.add(rs.getString("sell_price"));
					list.add(rs.getString("inventory"));
					jsonList.add(list);
				}
				rs.close();
				statement.close();
				conn.close();
				System.out.println("操作数据完毕，关闭了数据库！");

			} catch (SQLException sqlexception) {
				sqlexception.printStackTrace();
			}
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("aaData",jsonList);
			System.out.println(jsonObj.toString());
			response.setContentType("json/application; charset=UTF-8");
			try{
				response.getWriter().print(jsonObj);
				response.getWriter().flush();
				response.getWriter().close();
			} catch(IOException e){
				e.printStackTrace();
			}
		%>
	</body>
</html>
