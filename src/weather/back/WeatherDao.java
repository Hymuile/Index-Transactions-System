package weather.back;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONException;
import org.json.JSONObject;

import dao.ylx_db;

public class WeatherDao {
	Statement statement=null;
	private ylx_db db = new ylx_db("xm09");
	public String city ;
	public String creattime;

	public void addWeatherRecord(StringBuffer sb) throws JSONException, SQLException {

		String result=sb.toString();
		System.out.println(result);
		if(!result.isEmpty()){
			JSONObject json=new JSONObject(result);
			String array=json.getString("now");
			JSONObject record=new JSONObject(array);
			String sql = "select * from weather where city='"+city+"';";
			System.out.println("即将运行的SQL语句是："+sql);

			java.sql.ResultSet rs = db.executeQuery(sql);

			if(rs.next()){
				System.out.println("更新数据");
				queryWeatherRecord(record);
			}
			else{
				System.out.println("插入数据");
				addWeatherRecord(record);
			}
		}
		db.close();
		System.out.println("Database Close！<br>");
	}

	private void queryWeatherRecord(JSONObject record) throws JSONException {	
		String obsTime="---";
		String temp="---";
		String icon="---";
		String text="---";
		String windDir="---";
		String windScale="---";
		String windSpeed="---";
		String vis="---";

		obsTime=record.getString("obsTime");
		temp=record.getString("temp");
		icon=record.getString("icon");
		text=record.getString("text");
		windDir=record.getString("windDir");
		windScale=record.getString("windScale");
		windSpeed=record.getString("windSpeed");
		vis=record.getString("vis");
		//

		String sql = "update weather set obs_time='"+obsTime+"', temp='"+temp+"', icon='"+icon+"', text='"+text+"', wind_dir='"+windDir+"', wind_scale='"+windScale+"', wind_speed='"+windSpeed+"', vis='"+vis+"', create_time='"+creattime+"' where city='"+city+"'";
		System.out.println("即将运行的SQL语句是："+sql);
		db.executeUpdate(sql);


	}

	private void addWeatherRecord(JSONObject record) throws JSONException {	
		String obsTime="---";
		String temp="---";
		String icon="---";
		String text="---";
		String windDir="---";
		String windScale="---";
		String windSpeed="---";
		String vis="---";

		obsTime=record.getString("obsTime");
		temp=record.getString("temp");
		icon=record.getString("icon");
		text=record.getString("text");
		windDir=record.getString("windDir");
		windScale=record.getString("windScale");
		windSpeed=record.getString("windSpeed");
		vis=record.getString("vis");
		//
		String sql = "insert into weather(city,obs_time,temp,icon,text,wind_dir,wind_scale,wind_speed,vis,create_time) values('"
				+city+"','"+obsTime+"','"+temp+"','"+icon+"','"+text+"','"+windDir+"','"+windScale+"','"+windSpeed+"','"+vis+"','"+creattime+"')";
		System.out.println("即将运行的SQL语句是："+sql);
		db.executeUpdate(sql);

	}


	public void getcityname(String CT){
		this.city = CT;
	}
	public void getCreateTime(String creattime){
		this.creattime = creattime;
	}

}
