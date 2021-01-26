package project.dao;
import java.io.IOException;
import java.sql.Connection;
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

import utility.PageBean;

public class FileDao{
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
			String tableName="project_file";
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
	public JSONObject getRecord(String action,String dbName,String type,String userId) throws SQLException, IOException, JSONException {
		String tableName="project_file";
		//开始查询数据库
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		try {
			ylx_db query_db = new ylx_db(dbName);
			//构造sql语句，根据传递过来的查询条件参数
			String sql="";
			if(type!=null && type.equals("all")){
				sql="select * from "+tableName+" order by create_time desc";
			}else{
				sql="select a.*,b.member_id,b.member_role from project_file a,project_member b where b.member_id='"+userId+"' and a.project_id=b.project_id" +
						" and a.open_level<>\"all\" union select a.*,b.member_id,b.member_role from project_file a left join project_member b on a.project_id=b.project_id and b.member_id='"+userId+"' where open_level=\"all\" order by create_time desc";
			}
			ResultSet rs = query_db.executeQuery(sql);
			while (rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("id"));
				list.add(rs.getString("project_id"));
				list.add(rs.getString("project_name"));
				list.add(rs.getString("project_nick"));
				list.add(rs.getString("project_class"));
				list.add(rs.getString("project_source"));
				list.add(rs.getString("project_describe"));
				list.add(rs.getString("project_manager_name"));
				list.add(rs.getString("apply_money"));
				list.add(rs.getString("approval_money"));
				list.add(rs.getString("group_member"));
				list.add(rs.getString("start_time"));
				list.add(rs.getString("end_time"));
				list.add(rs.getString("superior_unit"));
				list.add(rs.getString("superior_manager"));
				list.add(rs.getString("attachment_name"));
				list.add(rs.getString("open_level"));
				list.add(rs.getString("check_tag"));
				list.add(rs.getString("checker"));
				list.add(rs.getString("check_time"));
				list.add(rs.getString("creator"));
				list.add(rs.getString("create_time"));
				list.add(rs.getString("modifier"));
				list.add(rs.getString("modify_time"));
				list.add(rs.getString("member_id"));
				list.add(rs.getString("member_role"));
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
	public JSONObject modifyRecord(String action,String dbName,String id,String content,String openLevel,String creator,String createTime) throws JSONException{
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		try {
			ylx_db query_db = new ylx_db(dbName);
			//构造sql语句，根据传递过来的查询条件参数
			String tableName="project_file";
			String sql="update "+tableName+"set content='"+content+"',open_level='"+openLevel+"' where id="+id;
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
	public JSONObject getRecordById(String action,String dbName,String id,String userId) throws JSONException{
		String resultMsg="ok";
		int resultCode=0;
		String projectId="";
		String projectName="";
		List jsonList = new ArrayList();
		try {
			ylx_db query_db = new ylx_db(dbName);
			//构造sql语句，根据传递过来的查询条件参数
			String tableName="project_file";
			String sql="select * from "+tableName+" where id="+id+" order by create_time desc";
			ResultSet rs = query_db.executeQuery(sql);
			while (rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("id"));
				list.add(rs.getString("project_id"));
				list.add(rs.getString("project_name"));
				list.add(rs.getString("project_nick"));
				list.add(rs.getString("project_class"));
				list.add(rs.getString("project_source"));
				list.add(rs.getString("project_describe"));
				list.add(rs.getString("project_manager_name"));
				list.add(rs.getString("apply_money"));
				list.add(rs.getString("approval_money"));
				list.add(rs.getString("group_member"));
				list.add(rs.getString("start_time"));
				list.add(rs.getString("end_time"));
				list.add(rs.getString("superior_unit"));
				list.add(rs.getString("superior_manager"));
				list.add(rs.getString("attachment_name"));
				list.add(rs.getString("open_level"));
				list.add(rs.getString("check_tag"));
				list.add(rs.getString("checker"));
				list.add(rs.getString("check_time"));
				list.add(rs.getString("creator"));
				list.add(rs.getString("create_time"));
				list.add(rs.getString("modifier"));
				list.add(rs.getString("modify_time"));
				jsonList.add(list);
				projectId=rs.getString("project_id");
				projectName=rs.getString("project_name");
			}
			rs.close();
			query_db.close();
		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
			resultCode=10;
			resultMsg="查询数据库出现错误！"+sqlexception.getMessage();
		}
		//然后查看当前的userId是否是组员
		String memberShip="0";
		ylx_db member_db = new ylx_db(dbName);
		String sql="select * from project_member where project_id='"+projectId+"' and member_id='"+userId+"'";
		ResultSet rsMember = member_db.executeQuery(sql);
		try {
			if(rsMember.next()){memberShip="1";}else{}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//下面开始构建返回的json
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("aaData",jsonList);
		jsonObj.put("action",action);
		jsonObj.put("membership",memberShip);
		jsonObj.put("project_id",projectId);
		jsonObj.put("project_name",projectName);
		jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
		jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}
	public JSONObject getRecordByProjectId(String action,String dbName,String projectId,String userId) throws JSONException{
		String resultMsg="ok";
		int resultCode=0;
		String projectName="";
		List jsonList = new ArrayList();
		try {
			ylx_db query_db = new ylx_db(dbName);
			//构造sql语句，根据传递过来的查询条件参数
			String tableName="project_file";
			String sql="select * from "+tableName+" where project_id='"+projectId+"' order by create_time desc";
			ResultSet rs = query_db.executeQuery(sql);
			while (rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("id"));
				list.add(rs.getString("project_id"));
				list.add(rs.getString("project_name"));
				list.add(rs.getString("project_nick"));
				list.add(rs.getString("project_class"));
				list.add(rs.getString("project_source"));
				list.add(rs.getString("project_describe"));
				list.add(rs.getString("project_manager_name"));
				list.add(rs.getString("apply_money"));
				list.add(rs.getString("approval_money"));
				list.add(rs.getString("group_member"));
				list.add(rs.getString("start_time"));
				list.add(rs.getString("end_time"));
				list.add(rs.getString("superior_unit"));
				list.add(rs.getString("superior_manager"));
				list.add(rs.getString("attachment_name"));
				list.add(rs.getString("open_level"));
				list.add(rs.getString("check_tag"));
				list.add(rs.getString("checker"));
				list.add(rs.getString("check_time"));
				list.add(rs.getString("creator"));
				list.add(rs.getString("create_time"));
				list.add(rs.getString("modifier"));
				list.add(rs.getString("modify_time"));
				jsonList.add(list);
				projectName=rs.getString("project_name");
			}
			rs.close();
			query_db.close();
		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
			resultCode=10;
			resultMsg="查询数据库出现错误！"+sqlexception.getMessage();
		}
		//然后查看当前的userId是否是组员
		String memberShip="0";
		ylx_db member_db = new ylx_db(dbName);
		String sql="select * from project_member where project_id='"+projectId+"' and member_id='"+userId+"'";
		ResultSet rsMember = member_db.executeQuery(sql);
		try {
			if(rsMember.next()){memberShip="1";}else{}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//下面开始构建返回的json
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("aaData",jsonList);
		jsonObj.put("action",action);
		jsonObj.put("membership",memberShip);
		jsonObj.put("project_id",projectId);
		jsonObj.put("project_name",projectName);
		jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
		jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}
	public JSONObject addRecord(String action,String dbName,File file,String avatar) throws JSONException, SQLException{
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		ylx_db query_db = new ylx_db(dbName);
		//构造sql语句，根据传递过来的查询条件参数
		String tableName="project_file";
		String sql="insert into "+tableName+"(project_id,project_name,project_nick" +
				",project_class,project_source,project_describe,project_manager_id,project_manager_name" +
				",apply_money,approval_money,group_member,start_time,end_time" +
				",superior_unit,superior_manager,attachment_name,open_level" +
				",check_tag,checker,check_time" +
				",creator,create_time) values('"+file.getProjectId()+"','"+file.getProjectName()+"','"+file.getProjectNick()+"','"+file.getProjectClass()+"'" +
				",'"+file.getProjectSource()+"','"+file.getProjectDescribe()+"','"+file.getProjectManagerId()+"','"+file.getProjectManagerName()+"','"+file.getApplyMoney()+"','"+file.getApprovalMoney()+"'" +
				",'"+file.getGroupMember()+"','"+file.getStartTime()+"','"+file.getEndTime()+"','"+file.getSuperiorUnit()+"','"+file.getSuperiorManager()+"'" +
				",'"+file.getAttachmentName()+"','"+file.getOpenLevel()+"','"+file.getCheckTag()+"','"+file.getChecker()+"','"+file.getCheckTime()+"'" +
				",'"+file.getCreator()+"','"+file.getCreateTime()+"')";
		query_db.executeUpdate(sql);
		//添加进member
		sql="insert into project_member(project_id,member_id,member_name,member_role,avatar,creator,create_time) values('"+file.getProjectId()+"','"+file.getUserId()+"','"+file.getCreator()+"','manager','"+avatar+"','"+file.getCreator()+"','"+file.getCreateTime()+"')";
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
	public JSONObject deleteRecord(String action,String dbName,String[] ids,String creator,String createTime) throws JSONException, SQLException{
		String tableName="project_file";
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
	/*
	 * 功能：返回结果集
	 */
	public JSONObject applyJoin(String action,String dbName,Apply apply) throws SQLException, IOException, JSONException {
		String tableName="project_apply";
		//开始查询数据库
		String resultMsg="ok";
		int resultCode=0;
		String applyTag="0";
		List jsonList = new ArrayList();
		try {
			ylx_db query_db = new ylx_db(dbName);
			//构造sql语句，根据传递过来的查询条件参数
			String sql="select * from "+tableName+" where project_id='"+apply.getProjectId()+"' and user_id='"+apply.getUserId()+"' and apply_type='join_apply'";
			ResultSet rs = query_db.executeQuery(sql);
			//如果申请过，就提取数据出来看看
			if (rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("id"));
				list.add(rs.getString("session_id"));
				list.add(rs.getString("project_id"));
				list.add(rs.getString("user_id"));
				jsonList.add(list);
				applyTag="1";
			}else{
				ylx_db update_db=new ylx_db(dbName);
				update_db.executeUpdate("insert into "+tableName+"(project_id,project_name,session_id,user_id,user_name,apply_type,apply_content,creator,create_time)" +
						" values('"+apply.getProjectId()+"','"+apply.getProjectName()+"','"+apply.getSessionId()+"','"+apply.getUserId()+"','"+apply.getUserName()+"','"+apply.getApplyType()+"','"+apply.getApplyContent()+"','"+apply.getCreator()+"','"+apply.getCreateTime()+"')");
				//获取新写入的记录的id
				String id="";
				ResultSet rsId=update_db.executeQuery("SELECT LAST_INSERT_ID() as id from "+tableName);
				if(rsId.next()){
					id=rsId.getString("id");
				}
				//获取项目经理信息
				String managerId="";
				String managerName="";
				ResultSet rsManager=update_db.executeQuery("select * from project_file where project_id='"+apply.getProjectId()+"'");
				if(rsManager.next()){
					managerId=rsManager.getString("project_manager_id");
					managerName=rsManager.getString("project_manager_name");
				}
				apply.setManagerId(managerId);
				apply.setManagerName(managerName);
				//然后写入user_notice表，提醒项目经理要进行及时处理
				String noticeId="project_file_join_apply";
				String noticeTitle="您有项目人员加入申请消息";
				String noticeAction="../../project_file_servlet_action";
				String modifier=apply.getCreator();
				String modifyTime=apply.getCreateTime();
				sql="select * from user_notice where user_id='"+apply.getUserId()+"' and notice_id='"+noticeId+"'";
				ResultSet rsNotice=query_db.executeQuery(sql);
				if(rsNotice.next()){
					if(rsNotice.getString("notice_content")==null || rsNotice.getString("notice_content").isEmpty()){
						sql="update user_notice set count=count+1,notice_content='"+id+"',modifier='"+modifier+"',modify_time='"+modifyTime+"' where user_id='"+apply.getManagerId()+"' and notice_id='"+noticeId+"'";
					}else{
						sql="update user_notice set count=count+1,notice_content=concat(notice_content,'_"+id+"'),modifier='"+modifier+"',modify_time='"+modifyTime+"' where user_id='"+apply.getManagerId()+"' and notice_id='"+noticeId+"'";
					}
				}else{
					sql="insert into user_notice(user_id,notice_id,notice_title,count,notice_content,action_type,action,modifier,modify_time)" +
						" values('"+apply.getManagerId()+"','"+noticeId+"','"+noticeTitle+"',1,'"+id+"','redirect','"+noticeAction+"','"+modifier+"','"+modifyTime+"')";
				}
				update_db.executeUpdate(sql);
				update_db.close();
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
		jsonObj.put("apply_tag",applyTag);
		jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
		jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}
	public JSONObject getApplyRecord(String action,String dbName,String id) throws SQLException, IOException, JSONException {
		String tableName="project_apply";
		//开始查询数据库
		String resultMsg="ok";
		int resultCode=0;
		String applyTag="0";
		List jsonList = new ArrayList();
		try {
			ylx_db query_db = new ylx_db(dbName);
			//构造sql语句，根据传递过来的查询条件参数
			String sql="select * from "+tableName+" where id="+id;
			ResultSet rs = query_db.executeQuery(sql);
			if (rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("id"));
				list.add(rs.getString("project_id"));
				list.add(rs.getString("project_name"));
				list.add(rs.getString("user_id"));
				list.add(rs.getString("user_name"));
				list.add(rs.getString("apply_type"));
				list.add(rs.getString("apply_content"));
				list.add(rs.getString("session_id"));
				jsonList.add(list);
				applyTag="1";
			}else{
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
		jsonObj.put("apply_tag",applyTag);
		jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
		jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}
	public JSONObject applyCheck(String action,String dbName,Apply apply) throws SQLException, IOException, JSONException {
		String tableName="project_apply";
		//开始查询数据库
		String resultMsg="ok";
		int resultCode=0;
		String applyTag="0";
		List jsonList = new ArrayList();
		String memberId = null,memberName=null,memberRole=null;
		String avatar=null;
		String creator=null;
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		String projectId=null;
		try {
			ylx_db query_db = new ylx_db(dbName);
			ylx_db update_db = new ylx_db(dbName);
			//构造sql语句，根据传递过来的查询条件参数
			String sql="update "+tableName+" set check_tag="+apply.getResult()+",check_time='"+apply.getCreateTime()+"',checker='"+apply.getCreator()+"' where id="+apply.getId();
			update_db.executeUpdate(sql);
			sql="select a.*,b.avatar,b.role from "+tableName+" a left join security_users b on a.user_id=b.name where a.id="+apply.getId();
			ResultSet rs = query_db.executeQuery(sql);
			if (rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("id"));
				list.add(rs.getString("project_id"));
				list.add(rs.getString("project_name"));
				list.add(rs.getString("user_id"));
				list.add(rs.getString("user_name"));
				list.add(rs.getString("apply_type"));
				list.add(rs.getString("apply_content"));
				list.add(rs.getString("session_id"));
				memberId=rs.getString("user_id");
				memberName=rs.getString("user_name");
				memberRole="member";
				avatar=rs.getString("avatar");
				creator=apply.getUserName();
				projectId=rs.getString("project_id");
				jsonList.add(list);
				applyTag="1";
			}else{
			}
			rs.close();
			if(apply.getResult().equals("1")){
				//添加进project_member表里
				sql="delete from project_member where project_id='' and member_id='"+memberId+"'";
				update_db.executeUpdate(sql);
				sql="insert into project_member(project_id,member_id,member_name,member_role,avatar,creator,create_time) values('"+projectId+"','"+memberId+"','"+memberName+"','"+memberRole+"','"+avatar+"','"+creator+"','"+createTime+"')";
				update_db.executeUpdate(sql);
			}
			query_db.close();
			update_db.close();
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
		jsonObj.put("apply_tag",applyTag);
		jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
		jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}
	/*
	 * 功能：更新通知表
	 */
	public JSONObject setNoticeRead(String action,String dbName,String userId,String creator,String createTime) throws JSONException, SQLException{
		JSONObject jsonObj=null;
		ylx_db query_db = new ylx_db(dbName);
		String sql=null;
		sql="update user_notice set count=0,notice_content='',modifier='"+creator+"',modify_time='"+createTime+"' where user_id='"+userId+"' and notice_id='"+action+"'";
		query_db.executeUpdate(sql);
		query_db.close();
		return jsonObj;
	}
}
