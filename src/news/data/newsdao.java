package news.data;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dao.ylx_db;

public class newsdao {
	//Statement statement=null;
	private ylx_db db = new ylx_db("xm09");
	public void addWeatherRecord(StringBuffer sb) throws JSONException, SQLException {
		/*try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException classnotfoundexception) {
			classnotfoundexception.printStackTrace();
		}
		System.out.println("连接数据库：");
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/xm09?user=XM09&password=123456&useUnicode=true&characterEncoding=UTF-8");
		System.out.println("运行statement：");
		statement = conn.createStatement();
		System.out.println("Connect Database Ok！<br>");
		 */
		////////////////////////////////////////////////////

		String result=sb.toString();
		String[] list;
		if(!result.isEmpty()){
			JSONObject json=new JSONObject(result);
			String array=json.getString("newslist");
			JSONArray json1 = new JSONArray(array);
			if(json1.length() != 0)
				for(int i = 0; i<json1.length();i++){
					String record = json1.getString(i);
					JSONObject json2 = new JSONObject(record);
					addWeatherRecord(json2);
				}
		}
	}

	private void addWeatherRecord(JSONObject record) throws JSONException, SQLException {	
		String creattime="---";
		String title="---";
		String description="---";
		String picurl="---";
		String url="---";

		creattime=record.getString("ctime");
		title=record.getString("title");
		description=record.getString("description");
		picurl=record.getString("picUrl");
		url=record.getString("url");
		
		String sql = "select * from news where title='"+title+"';";

		java.sql.ResultSet rs = db.executeQuery(sql);
		
		if(rs.next()){
			System.out.println("更新数据");
			sql = "update news set create_time='"+creattime+"', description='"+description+"', pic_url='"+picurl+"', url='"+url+"' where title='"+title+"'";
			System.out.println("即将运行的SQL语句是："+sql);
			db.executeUpdate(sql);
		}
		else{
			System.out.println("插入数据");
			sql = "insert into news(create_time,title,description,pic_url,url) values('"
					+creattime+"','"+title+"','"+description+"','"+picurl+"','"+url+"')";
			System.out.println("即将运行的SQL语句是："+sql);
			db.executeUpdate(sql);
		}
	}
}
