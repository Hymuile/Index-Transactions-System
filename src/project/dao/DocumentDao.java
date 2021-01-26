package project.dao;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import dao.ylx_db;

import utility.PageBean;

public class DocumentDao{
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
			String tableName="project_document";
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
	/*
	 * 功能：返回结果集
	 */
	public JSONObject getRecord(String action,String dbName,String type,String userId,String projectId,String projectName) throws SQLException, IOException, JSONException {
		String tableName="project_document";
		//开始查询数据库
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		try {
			ylx_db query_db = new ylx_db(dbName);
			//构造sql语句，根据传递过来的查询条件参数
			String sql="";
			if(type!=null && type.equals("all")){
				if(projectId!=null){
					if(projectName!=null){
						sql="select a.*,b.project_name from project_document a,project_file b where a.project_id like '%"+projectId+"%' and b.project_name like '%"+projectName+"%' and a.project_id=b.project_id order by a.project_id,download_count,a.create_time desc";
					}else{
						sql="select a.*,b.project_name from project_document a,project_file b where a.project_id=b.project_id and a.project_id like '%"+projectId+"%' order by a.project_id,a.download_count,a.create_time desc";
					}
				}
				else{
					if(projectName!=null){
						sql="select * from select a.*,b.project_name from project_document a,project_file b where a.project_id=b.project_id order by project_id,download_count,create_time desc";
					}else{
						sql="select a.*,b.project_name from project_document a,project_file b where a.project_id=b.project_id order by a.project_id,a.download_count,a.create_time desc";
					}
				}
			}else{
				if(projectId!=null){
					if(projectName!=null){
						sql="select a.project_name,member_id,c.* from (select a.*,b.member_id from project_file a,project_member b where b.member_id='"+userId+"' and a.project_id like '%"+projectId+"%' and a.project_id=b.project_id order by create_time desc) a" +
							" left join project_document c on a.project_id=c.project_id and c.project_id like '%"+projectId+"%' order by a.project_id,download_count,create_time desc";
					}else{
						sql="select a.project_name,member_id,c.* from (select a.*,b.member_id from project_file a,project_member b where b.member_id='"+userId+"' and a.project_id like '%"+projectId+"%' and a.project_id=b.project_id order by create_time desc) a" +
						" left join project_document c on a.project_id=c.project_id and c.project_id like '%"+projectId+"%' order by a.project_id,download_count,create_time desc";
					}
				}else{
					if(projectName!=null){
						sql="select a.project_name,member_id,c.* from (select a.*,b.member_id from project_file a,project_member b where b.member_id='"+userId+"' and a.project_id=b.project_id order by create_time desc) a" +
							" ,project_document c where a.project_id=c.project_id and a.project_name like '%"+projectName+"%' order by a.project_id,download_count,create_time desc";
					}else{
						sql="select a.project_name,member_id,c.* from (select a.*,b.member_id from project_file a,project_member b where b.member_id='"+userId+"' and a.project_id=b.project_id order by create_time desc) a" +
						" right join project_document c on a.project_id=c.project_id order by a.project_id,download_count,create_time desc";
					}
				}
			}
			//构造完以后，就开始查询
			ResultSet rs = query_db.executeQuery(sql);
			while (rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("id"));
				list.add(rs.getString("project_id"));
				list.add(rs.getString("project_name"));
				list.add(rs.getString("document_id"));
				list.add(rs.getString("document_name"));
				list.add(rs.getString("document_type"));
				list.add(rs.getString("page_count"));
				list.add(rs.getString("url_path"));
				list.add(rs.getString("file_path"));
				list.add(rs.getString("file_name"));
				list.add(rs.getString("file_size"));
				list.add(rs.getString("check_tag"));
				list.add(rs.getString("checker"));
				list.add(rs.getString("check_time"));
				list.add(rs.getString("creator"));
				list.add(rs.getString("create_time"));
				list.add(rs.getString("modifier"));
				list.add(rs.getString("modify_time"));
				list.add(rs.getString("download_count"));
				list.add(rs.getString("comment_count"));
				list.add(rs.getString("like_count"));
				list.add(rs.getString("hate_count"));
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
		jsonObj.put("action",action);
		jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
		jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}
	public JSONObject modifyRecord(String action,String dbName,String id,String content,String creator,String createTime) throws JSONException{
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		try {
			ylx_db query_db = new ylx_db(dbName);
			//构造sql语句，根据传递过来的查询条件参数
			String tableName="project_document";
			String sql="update "+tableName+"set content='"+content+"' where id="+id;
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
	public JSONObject getRecordById(String action,String dbName,String id) throws JSONException{
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		try {
			ylx_db query_db = new ylx_db(dbName);
			//构造sql语句，根据传递过来的查询条件参数
			String tableName="project_document";
			String sql="select * from "+tableName+" where id="+id+" order by create_time desc";
			ResultSet rs = query_db.executeQuery(sql);
			while (rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("id"));
				list.add(rs.getString("project_id"));
				list.add(rs.getString("document_id"));
				list.add(rs.getString("document_name"));
				list.add(rs.getString("document_type"));
				list.add(rs.getString("page_count"));
				list.add(rs.getString("url_path"));
				list.add(rs.getString("file_path"));
				list.add(rs.getString("file_name"));
				list.add(rs.getString("file_size"));
				list.add(rs.getString("check_tag"));
				list.add(rs.getString("checker"));
				list.add(rs.getString("check_time"));
				list.add(rs.getString("creator"));
				list.add(rs.getString("create_time"));
				list.add(rs.getString("modifier"));
				list.add(rs.getString("modify_time"));
				jsonList.add(list);
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
	public JSONObject addRecord(String action,String dbName,Document document) throws JSONException, SQLException{
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		ylx_db query_db = new ylx_db(dbName);
		//构造sql语句，根据传递过来的查询条件参数
		String tableName="project_document";
		String sql="insert into "+tableName+"(project_id,document_id,document_name,document_type,document_nick" +
				",page_count,url_path,file_path,file_name,file_size" +
				",creator,create_time)" +
				" values('"+document.getProjectId()+"','"+document.getDocumentId()+"','"+document.getDocumentName()+"'" +
				",'"+document.getDocumentType()+"','"+document.getDocumentNick()+"'" +
				",'"+document.getPageCount()+"','"+document.getUrlPath()+"','"+document.getFilePath()+"','"+document.getFileName()+"','"+document.getFileSize()+"'" +
				",'"+document.getCreator()+"','"+document.getCreateTime()+"')";
		query_db.executeUpdate(sql);
		query_db.close();
		//下面开始构建返回的json
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("aaData",jsonList);
		jsonObj.put("action",action);
		jsonObj.put("project_id",document.getProjectId());
		jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
		jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}
	public JSONObject deleteRecord(String action,String dbName,String[] ids,String creator,String createTime) throws JSONException, SQLException{
		String tableName="project_document";
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
	public JSONObject getProjectRecord(String action,String dbName,String type,String userId) throws SQLException, IOException, JSONException {
		String tableName="project_document";
		//开始查询数据库
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		try {
			ylx_db query_db = new ylx_db(dbName);
			//构造sql语句，根据传递过来的查询条件参数
			String sql="";
			if(type!=null && type.equals("all")){
				sql="select project_id,project_name from "+tableName+" where order by project_id,create_time desc";
			}else{
				sql="select a.id,a.project_id,a.project_name,b.member_id from project_file a,project_member b where b.member_id='"+userId+"' and a.project_id=b.project_id order by a.create_time desc";
			}
			ResultSet rs = query_db.executeQuery(sql);
			while (rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("id"));
				list.add(rs.getString("project_id"));
				list.add(rs.getString("project_name"));
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
		jsonObj.put("action",action);
		jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
		jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}
	public JSONObject IncDownloadCount(String action,String dbName,String id) throws JSONException{
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		ylx_db query_db = new ylx_db(dbName);
		//构造sql语句，根据传递过来的查询条件参数
		String tableName="project_document";
		String sql="update "+tableName+" set download_count=download_count+1 where id="+id;
		query_db.executeUpdate(sql);
		query_db.close();
		//下面开始构建返回的json
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("aaData",jsonList);
		jsonObj.put("action",action);
		jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
		jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}
}
