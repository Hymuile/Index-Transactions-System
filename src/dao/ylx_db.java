package dao;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class ylx_db {

	private Connection a;
	private Statement statement;
	private String drivername;
	private String database;
	private String url1;
	private String url2;
	private Integer debugLevel=1;

	public ylx_db(String s) {
		getProperty();
		database = s;
		String s1 = url1 + s + "?" + url2;
		String s2 = drivername;
		try {
			Class.forName(s2);
		} catch (ClassNotFoundException classnotfoundexception) {
			classnotfoundexception.printStackTrace();
		}
		try {
			a = DriverManager.getConnection(s1);
			statement = a.createStatement();
		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
		}
	}

	public void close() {
		try {
			statement.close();
			a.close();
		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
		}
	}

	public ResultSet executeQuery(String s) {
		ResultSet resultset = null;
		try {
			resultset = statement.executeQuery(s);
		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
		}
		return resultset;
	}

	public void executeUpdate(String s) {
		try {

			statement.executeUpdate(s);
		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
		}
	}

	public String getTable() {
		return database;
	}

	public void getProperty() {
		Properties properties = new Properties();
		Properties properties1 = new Properties();
		try {
			InputStream inputstream = getClass().getClassLoader().getResourceAsStream("/conf/db.properties");
			InputStream inputstream1 = getClass().getClassLoader().getResourceAsStream("/conf/dbip.properties");
			properties.load(inputstream);
			properties1.load(inputstream1);
			if (inputstream != null)
				inputstream.close();
			inputstream1.close();
		} catch (IOException ex) {
			System.err.println("Open Propety File Error");
		}
		drivername = properties.getProperty("DRIVER");
		//////////////////////////////////////////////////////////////////////////////////
		
		url1 = properties.getProperty("URL1") + properties1.getProperty("IP") + ":3306/";
		
		////////////////////////////////////////////////////////////////////////////////////
		
		url2 = properties.getProperty("URL2");
		//调试级别
		String level = properties.getProperty("debuglevel");
		if(level!=null){
			debugLevel=Integer.parseInt(level);
		}else{
			debugLevel=0;
		}
	}

	public void setTable(String s) {
		database = s;
	}
}
