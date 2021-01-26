package news.data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.json.JSONException;
import news.data.newsdao;


public class newsdata implements ServletContextListener {
	private Timer timer = null;
	private String createtime="";
	
	public void contextDestroyed(ServletContextEvent arg0) {
	}


	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("================>[ServletContextListener]自动加载启动开始.");
		timer = new Timer(true);
		MyTimerTask task = new MyTimerTask();
		timer.schedule(task, 1000,600000);		//延迟1秒后，每隔10分钟运行一次
		System.out.println("[GetNewskData]运行了");
	}
	
	public class MyTimerTask extends TimerTask {

	    public void run() {
	        Calendar calendar = Calendar.getInstance();
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        createtime = (String)format.format(calendar.getTime());
	        System.out.println("Current Time："+format.format(calendar.getTime()));//获取当前系统时间
	        try {//多用函数
				getNewsData();
			} catch (MalformedURLException | JSONException | SQLException e) {
				e.printStackTrace();
			}
	    }
	}
	
	private void getNewsData() throws MalformedURLException,JSONException, SQLException {
		String urlData="http://api.tianapi.com/caijing/index?key=534134164145dacf2ef373849f98ac5d&num=15";
		URL urlConn=new URL(urlData);
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
            newsdao dao=new newsdao();
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
}