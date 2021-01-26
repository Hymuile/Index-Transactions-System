package money_manage.list;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import money_manage.list.Users;
import money_manage.list.UsersDao;

import utility.LogEvent;
import utility.excel.ExcelWriter;

public class ServletAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//这里是需要改的,module和sub
	public String module = "money_manage";
	public String sub = "list";

	public String preFix = module + "_" + sub;
	public String resultPath = module + "/" + sub;
	public String resultPage = "result.jsp";
	public String resultUrl="project/money_manage/"+resultPage;
	public String redirectPath = module + "/" + sub;
	public String redirectPage = "record_list.jsp";
	public String redirectUrl=redirectPath+"/"+redirectPage;
	public String databaseName="ylxdb";
	public SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public LogEvent ylxLog = new LogEvent();

	/*
	 * 处理顺序：先是service，后根据情况doGet或者doPost
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		processAction(request,response);
	}
	public void processAction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		try {
			ylxLog.setSession(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String action = request.getParameter("action");
		boolean actionOk = false;
		showDebug("processAction收到的action是："+action);
		if(session.getAttribute("user_role")==null){
			try {
				processError(request, response,3,"session超时，请重新登录系统！");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else{
			if (action == null){
				try {
					processError(request, response,1,"传递过来的action是null！");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}else{
				//这几个常规增删改查功能
				if (action.equals("get_record")) {
					actionOk=true;
					try {
						getRecord(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("get_record_view")) {
					actionOk=true;
					try {
						getRecordView(request, response);
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
				if (action.equals("add_record")) {
					actionOk=true;
					try {
						addRecord(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("modify_record")) {
					actionOk=true;
					try {
						modifyRecord(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(action.equals("op_money")){
					System.out.println("================完成op_money提交==================");
					actionOk=true;
					try {
						opMoney(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("delete_record")) {
					System.out.println("================开始delete_record操作==================");
					actionOk=true;
					try {
						deleteRecord(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("set_record_top")) {
					actionOk=true;
					try {
						setRecordTop(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("query_record")) {
					actionOk=true;
					try {
						queryRecord(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("modify_user_info")) {
					actionOk=true;
					try {
						modifyUserInfo(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("export_record")) {
					actionOk=true;
					try {
						exportRecord(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (!actionOk) {
					try {
						processError(request, response,2,"["+module+"/"+sub+"/ServletAction]没有对应的action处理过程，请检查action是否正确！action="+action);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
 	public void processError(HttpServletRequest request, HttpServletResponse response,int errorNo,String errorMsg) throws JSONException, IOException{
		String action = request.getParameter("action");
		//errorNo=0->没有错误
		//errorNo=1->action是空值
		//errorNo=2->没有对应的处理该action的函数
		//errorNo=3->session超时
		showDebug("错误信息："+errorMsg+"，代码："+errorNo);
		JSONObject jsonObj=new JSONObject();
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			jsonObj.put("result_code",errorNo);
			jsonObj.put("result_msg",errorMsg);
			jsonObj.put("action",action);
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			errorMsg = java.net.URLEncoder.encode(errorMsg, "UTF-8");
			String url = resultPath+"/"+resultPage+"?action="+action+"&result_code="+errorNo+"&redirect_path=" + redirectPath + "&redirect_page=" + redirectPage + "&result_msg=" + errorMsg;
			showDebug(url);
			response.sendRedirect(url);
		}
	}
	/*
	 * 功能：进行一个本类测试，不用启动整个项目，测试所写的Java
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("");
	}
	public void showDebug(String msg){
		System.out.println("["+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())+"]["+module+"/"+sub+"/ServletAction]"+msg);
	}
	/*
	 * 操作金额：返回值：1：表示操作成功，2：表示操作失败，钱不够
	 */
	public void opMoney(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		
		String trading_number = request.getParameter("trading_number");
		String op_type=request.getParameter("op_type");
		System.out.println("[Money_manage.list/ServletAction/op_money]收到的参数trading_number="+trading_number+",op_type="+op_type);
		//json对象
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(dbName!=null){
			String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
			String creator=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			//添加的数据
			String user_email=session.getAttribute("user_email")==null?null:(String)session.getAttribute("user_email");
			/*----------------------------------------数据获取完毕，开始和数据库交互*/
			money_manage.list.UsersDao usersDao=new money_manage.list.UsersDao();

			money_manage.list.Users user=new money_manage.list.Users();
			user.setAction(action);
			user.setDbName(dbName);
			user.set_op_type(op_type);
			user.set_trading_money(trading_number);
			user.setObstime(createTime);
			user.set_user_email(user_email);
			
			System.out.println("操作金额[money_manage对象内部分参数]-"+user.getAction()+",trading_money="+trading_number+",creatTime:"+user.getObstime());
			if(op_type.equals("in")) jsonObj=usersDao.addRecord(user);
			else if(op_type.equals("out")) jsonObj=usersDao.updateRecord(user);
			//money_code在内部
			System.out.println("运行到了此处！！！");
			ylxLog.log("用户 "+creator+" 于 "+createTime+" 添加了 ["+module+"]["+sub+"] 记录","添加记录",module);
		}
		System.out.println(jsonObj);
		String json = jsonObj.toString();
		//返回
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			
			System.out.println(json);
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}}else{
				String resultMsg="ok";
				int resultCode=0;
				resultMsg="操作已经执行，请按返回按钮返回列表页面！";
				redirectUrl="../../project/money_manange/record_list.jsp";
				resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
				String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
				showDebug("[updateRecord]结果URL："+url);
				response.sendRedirect(url);
		}
	}
	
	
	
	/*
	 * 功能：查询记录
	 */
	
	public void getRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String dbName=(String)session.getAttribute("unit_db_name");
		
		
		String action = request.getParameter("action");
		String id=request.getParameter("id");
		String user_name=request.getParameter("user_name");
		String existResultset= request.getParameter("exist_resultset");
		String sortIndex=request.getParameter("sort_index");
		String orderBy=request.getParameter("order_by");
		
		
		
		if((existResultset==null) ||(existResultset.equals("null") || existResultset.isEmpty())) existResultset="0";
		String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
		String userName=session.getAttribute("user_name")==null?null:(String)session.getAttribute("user_name");
		String userRole=session.getAttribute("user_role")==null?null:(String)session.getAttribute("user_role");
		String userAvatar=session.getAttribute("user_avatar")==null?null:(String)session.getAttribute("user_avatar");
		String user_email=session.getAttribute("user_email")==null?null:(String) session.getAttribute("user_email");
		//这里可以修改成统一一个函数读取变量，下面的session里的attr可以用一个变量代替
		//检查输入参数是否正确先
		showDebug("[getRecord]收到页面传过来的参数是："+existResultset+","+action+","+id);
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		String creator=(String)session.getAttribute("user_name");
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		
		money_manage.list.Users query=new money_manage.list.Users();
		query.setAction(action);
		query.setDbName(dbName);
		query.setName(user_name);
		query.setRole(userRole);
		query.set_user_email(user_email);
		
		//供排序新增
		query.setSortIndex(sortIndex);
		query.setOrderBy(orderBy);

		System.out.println("-----------------------------");
		System.out.println("dbname"+dbName+";action"+action+";user_name"+user_name+";"+userRole+" "+"sortIndex"+sortIndex);
		System.out.println("-----------------------------");
		
		JSONObject jsonObj=null;
		if(existResultset.equals("1")){
			//要求提取之前查询结果，如果有就取出来，如果没有就重新查询一次，并且保存进session里
			if(session.getAttribute(module+"_"+sub+"_get_record_result")!=null){
				jsonObj=(JSONObject)session.getAttribute(module+"_"+sub+"_get_record_result");
			}else{
				//如果没有就报错
				jsonObj=new JSONObject();
				jsonObj.put("result_code",10);
				jsonObj.put("result_msg","exist_resultset参数不当，服务器当前没有结果数据！请重新设置！");
			}
		}else{
			//如果是新查询
			money_manage.list.UsersDao usersDao=new money_manage.list.UsersDao();
			
			jsonObj=usersDao.getRecord(query);
			session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj);
		}
		jsonObj.put("user_id",userId);
		jsonObj.put("user_name",userName);
		jsonObj.put("user_role",userRole);
		jsonObj.put("user_avatar",userAvatar);
		jsonObj.put("action",action);
		/*--------------------数据查询完毕，根据交互方式返回数据--------------------*/
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl="record_list.jsp?exist_resultset=1";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = redirectPath+"/"+redirectUrl;
			showDebug("[getRecord]结果URL："+url);
			response.sendRedirect(url);
		}
	}

	public void queryRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String dbName=(String)session.getAttribute("unit_db_name");

		String action = request.getParameter("action");
		String user_name=request.getParameter("user_name");
		
		String city=request.getParameter("city");
		String existResultset="0";
		String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
		String userName=session.getAttribute("user_name")==null?null:(String)session.getAttribute("user_name");
		String userRole=session.getAttribute("user_role")==null?null:(String)session.getAttribute("user_role");
		String user_email=session.getAttribute("user_email")==null?null:(String) session.getAttribute("user_email");
		//这里可以修改成统一一个函数读取变量，下面的session里的attr可以用一个变量代替
		//检查输入参数是否正确先
		showDebug("[queryRecord]收到页面传过来的参数是：existResultset="+existResultset+",user_email="+user_email);
		/*---------------------数据获取完毕，开始和数据库交互----------------*/
		String creator=(String)session.getAttribute("user_name");
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		money_manage.list.Users query=new money_manage.list.Users();

		query.setAction(action);
		query.setDbName(dbName);
		query.setName(user_name);
		query.setRole(userRole);
		//query.setCity(city);
		query.set_user_email(user_email);

		System.out.println("queryRecord/query 对象的email="+query.get_user_email()+",action="+query.getAction()+",DbName="+query.getDbName());
		JSONObject jsonObj=null;

		money_manage.list.UsersDao usersDao=new money_manage.list.UsersDao();
		jsonObj=usersDao.queryRecord(query);

		session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj);
		jsonObj.put("action",action);
		/*--------------------数据查询完毕，根据交互方式返回数据--------------------*/

		
		response.setContentType("text/html; charset=UTF-8");
		try {
			System.out.println("=======异步请求返回数据="+jsonObj.toString());
			response.getWriter().print(jsonObj);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void getRecordView(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String id=request.getParameter("id");
		String user_name =request.getParameter("user_name");
		String index=request.getParameter("index");
		String existResultset= request.getParameter("exist_resultset");
		if((existResultset==null) ||(existResultset.equals("null") || existResultset.isEmpty())) existResultset="0";
		String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
		String userName=session.getAttribute("user_name")==null?null:(String)session.getAttribute("user_name");
		//检查输入参数是否正确先
		showDebug("收到页面传过来的参数是：exist_resultset="+existResultset+",action="+action+",id="+id+",index="+index);
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		weather.list.Users query=new weather.list.Users();
		query.setId(id);
		query.setAction(action);
		query.setDbName(dbName);
		query.setName(user_name);

		if(existResultset.equals("1")){			//如果是新查询
			//如果有就取出来，如果没有就重新查询一次，并且保存进session里
			if(session.getAttribute(module+"_"+sub+"_get_record_result")!=null){
				JSONObject json=(JSONObject)session.getAttribute(module+"_"+sub+"_get_record_result");
				showDebug(json.toString());
				jsonObj=getResultSetNavigateId(id,index,json);
				jsonObj.put("user_id",userId);
				jsonObj.put("user_name",userName);
				jsonObj.put("action",action);
				jsonObj.put("result_code",0);
				jsonObj.put("result_msg","ok");
				//然后还有导航信息
				json=(JSONObject)session.getAttribute(module+"_"+sub+"_get_record_result");
				//showDebug("[getRecordView]重新取出来的数据是："+json.toString());
			}else{
				//如果没有就重新查询一次
				showDebug("[getRecordView]没有就重新查询一次。");
				if(dbName!=null){
					String creator=(String)session.getAttribute("user_name");
					String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
					weather.list.UsersDao usersDao=new weather.list.UsersDao();
					jsonObj=usersDao.getRecord(query);

					jsonObj.put("user_id",userId);
					jsonObj.put("user_name",userName);
					jsonObj.put("action",action);
					jsonObj.put("result_code",0);
					jsonObj.put("result_msg","ok");
					session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj);
				}
			}
		}else{
			showDebug("[getRecordView]existsResult=0，重新查询");
			if(dbName!=null){
				String creator=(String)session.getAttribute("user_name");
				String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
				weather.list.UsersDao usersDao=new weather.list.UsersDao();
				jsonObj=usersDao.getRecord(query);

				jsonObj.put("user_id",userId);
				jsonObj.put("user_name",userName);
				jsonObj.put("action",action);
				session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj);
			}
		}

		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl="record_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("getRecordById结果URL："+url);
			response.sendRedirect(url);
		}
	}
	private JSONObject getResultSetNavigateId(String id,String index,JSONObject json) throws NumberFormatException, JSONException{
		//然后找到对应id的记录
		JSONArray jsonList=new JSONArray();
		//根据id获得index
		if(id!=null && !id.isEmpty()){
			index=getRecordIndexFromId(id,json);
		}
		ArrayList list=(ArrayList)json.getJSONArray("aaData").get(Integer.parseInt(index));
		jsonList.put(list);
		int count=json.getJSONArray("aaData").length();
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("first",0);
		if(Integer.parseInt(index)>0)
			jsonObj.put("prev",Integer.parseInt(index)-1);
		else
			jsonObj.put("prev",0);
		if(Integer.parseInt(index)<(count-1))
			jsonObj.put("next",Integer.parseInt(index)+1);
		else
			jsonObj.put("next",count-1);
		jsonObj.put("last",count-1);
		jsonObj.put("total",count);
		jsonObj.put("current_index",index);
		jsonObj.put("aaData",jsonList);
		return jsonObj;
	}
	private String getRecordIndexFromId(String id,JSONObject json) throws JSONException{
		String index="-1";
		JSONArray jsonArr=(JSONArray)json.getJSONArray("aaData");
		for(int i=0;i<jsonArr.length();i++){
			ArrayList list=(ArrayList)jsonArr.get(i);
			if(id.equals(list.get(0)+"")){
				index=list.get(11)+"";
				break;
			}
		}
		return index;
	};
	public void addRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		
		String trading_number = request.getParameter("trading_number");
		
		System.out.println("[Money_manage.list/ServletAction/addRecord]收到的参数trading_number="+trading_number);
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(dbName!=null){
			String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
			String creator=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			//添加的数据
			String user_email=session.getAttribute("user_email")==null?null:(String)session.getAttribute("user_email");
			/*----------------------------------------数据获取完毕，开始和数据库交互*/
			money_manage.list.UsersDao usersDao=new money_manage.list.UsersDao();

			money_manage.list.Users user=new money_manage.list.Users();
			user.setAction(action);
			user.setDbName(dbName);
//			user.setCity(city);
//			user.setTemp(temp);
//			user.setText(text);
//			user.setWinddir(wind_dir);
//			user.setWindscale(wind_scale);
//			user.setWindspeed(wind_speed);
//			user.setVis(vis);
			user.set_trading_money(trading_number);
			user.setObstime(createTime);
			user.set_user_email(user_email);
			
			//System.out.println("[Weather对象内部分参数]-"+user.getAction()+",city="+city+",temp="+temp+",text="+text+",wind_dir="+wind_dir+",wind_scale="+wind_scale+",wind_speed="+wind_speed+",vis="+vis);
			System.out.println("[money_manage对象内部分参数]-"+user.getAction()+",trading_money="+trading_number+",creatTime:"+user.getObstime());
			jsonObj=usersDao.addRecord(user);

			ylxLog.log("用户 "+creator+" 于 "+createTime+" 添加了 ["+module+"]["+sub+"] 记录","添加记录",module);
		}
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			String resultMsg="ok";
			int resultCode=0;
			resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			redirectUrl="../../project/weather/record_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("[addRecord]结果URL："+url);
			response.sendRedirect(url);
		}
	}
	/*
	 * 功能：修改记录
	 */
	public void modifyRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String id=request.getParameter("user_id");
		String city=request.getParameter("city");
		//String obstime=request.getParameter("obstime");
		String temp=request.getParameter("temp");
		String icon=request.getParameter("icon");
		String text=request.getParameter("text");
		String winddir=request.getParameter("winddir");
		String windscale=request.getParameter("windscale");
		String windspeed=request.getParameter("windspeed");
		String vis=request.getParameter("vis");
		//String createtime=request.getParameter("createtime");
		//String user_pwd=request.getParameter("user_pwd");
		//String user_balance=request.getParameter("user_balance");
		//String user_margin=request.getParameter("user_margin");
		//String user_city=request.getParameter("user_city");
		System.out.println(action);
		System.out.println(dbName);
		System.out.println(id);
		System.out.println(winddir);
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(id!=null && dbName!=null){
			String creator=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			UsersDao todoDao=new UsersDao();
			Users todo=new Users();
			
			//需要改、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、
			todo.setDbName(dbName);
			todo.setId(id);
			todo.setTemp(temp);
			todo.setIcon(icon);
			todo.setText(text);
			todo.setWinddir(winddir);
			todo.setWindscale(windscale);
			todo.setWindspeed(windspeed);
			todo.setVis(vis);
			todo.setCity(city);

			jsonObj=todoDao.modifyRecord(todo);
			ylxLog.log("用户 "+creator+" 于 "+createTime+" 修改了 ["+module+"]["+sub+"] 记录","修改记录",module);
		}
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl="record_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("[modifyRecord]结果URL："+url);
			response.sendRedirect(url);
		}
	}
	public void deleteRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("执行到了[money_manage]UsersDao deleteRecord()");
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String[] ids = request.getParameterValues("id");

		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(ids!=null && dbName!=null){
			String creator=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			/*---------数据获取完毕，开始和数据库交互---------------*/
			UsersDao todoDao=new UsersDao();
			jsonObj=todoDao.deleteRecord(action,dbName,ids,creator,createTime);
			ylxLog.log("用户 "+creator+" 于 "+createTime+" 删除了 ["+module+"]["+sub+"] 记录","删除记录",module);
		}
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl="record_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("[deleteRecord]结果URL："+url);
			response.sendRedirect(url);
		}
	}
	public void setRecordTop(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String id=request.getParameter("id");
		String content=request.getParameter("content");
		String type= request.getParameter("type");
		String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
		String userName=session.getAttribute("user_name")==null?null:(String)session.getAttribute("user_name");

		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(dbName!=null){
			String creator=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			TodoDao todoDao=new TodoDao();
			jsonObj=todoDao.setRecordTop(action,dbName,type,userId,id);
			jsonObj.put("user_id",userId);
			jsonObj.put("user_name",userName);
		}
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj);
			String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl="record_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("[setRecordTop]结果URL："+url);
			response.sendRedirect(url);
		}
	}
	
	/*
	 * 普通用户修改个人信息（用户名和所在地）
	 */
	public void modifyUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("modifyUserInfo() 函数开始执行");
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String id=(String)session.getAttribute("user_email");		
		 
		String new_user_name=request.getParameter("user_name");
		String new_user_pwd=request.getParameter("user_pwd");
		String new_user_balance=request.getParameter("user_balance");
		String new_user_city=request.getParameter("user_city");
		System.out.println("新密码为"+new_user_pwd);
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(id!=null && dbName!=null){
			String creator=(String)session.getAttribute("user_name");
			String user_email = (String)session.getAttribute("user_email");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			UsersDao todoDao=new UsersDao();
			Users todo=new Users();
			todo.setId(id);
			todo.setDbName(dbName);
			//todo.setEmail(user_email);
			todo.setCity(new_user_city);
			todo.setName(new_user_name);
			//todo.setPwd(new_user_pwd);
			//todo.setBalance(new_user_balance);
			//jsonObj=todoDao.modifyUserInfo(todo);
			ylxLog.log("用户 "+creator+" 于 "+createTime+" 修改了 ["+module+"]["+sub+"] 记录","修改记录",module);
		}else{
			System.out.println("id为空或者dbName为空");
		}
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl="record_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("[modifyRecord]结果URL："+url);
			response.sendRedirect(url);
		}
	}
	public void exportRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		String dbName=(String)session.getAttribute("unit_db_name");
		String action = request.getParameter("action");
		String existResultset= request.getParameter("exist_resultset");
		if((existResultset==null) ||(existResultset.equals("null") || existResultset.isEmpty())) existResultset="0";
		String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
		String userName=session.getAttribute("user_name")==null?null:(String)session.getAttribute("user_name");
		String userRole=session.getAttribute("user_role")==null?null:(String)session.getAttribute("user_role");
		String userAvatar=session.getAttribute("user_avatar")==null?null:(String)session.getAttribute("user_avatar");
		String userEmail=session.getAttribute("user_email")==null?null:(String)session.getAttribute("user_email");
		//这里可以修改成统一一个函数读取变量，下面的session里的attr可以用一个变量代替
		//检查输入参数是否正确先
		showDebug("[exportRecord]收到页面传过来的参数是："+existResultset+","+action);
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		String creator=(String)session.getAttribute("user_name");
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		
		String exportFilePathName="C:\\upload\\project\\export\\XM09_property_mannage_export_"+(new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())+".xls";
		
		JSONObject jsonObj=null;
		if(existResultset.equals("1")){
			//要求从session提取之前查询结果，如果有就取出来，如果没有就保存
			if(session.getAttribute(module+"_"+sub+"_get_record_result")!=null){
				jsonObj=(JSONObject)session.getAttribute(module+"_"+sub+"_get_record_result");
				//取出来以后，导出
				System.out.println("exportData() jsonObj="+jsonObj.toString());
				exportData(jsonObj,exportFilePathName);
			}else{
				//如果没有就报错
				jsonObj=new JSONObject();
				jsonObj.put("result_code",10);
				jsonObj.put("result_msg","exist_resultset参数不当，服务器当前没有结果数据！请重新设置！");
			}
		}else{
			//如果没有就报错
			jsonObj=new JSONObject();
			jsonObj.put("result_code",10);
			jsonObj.put("result_msg","exist_resultset参数不当，服务器当前没有结果数据！请重新设置！");
		}
		jsonObj.put("user_id",userId);
		jsonObj.put("user_name",userName);
		jsonObj.put("user_role",userRole);
		jsonObj.put("user_avatar",userAvatar);
		jsonObj.put("action",action);
		/*--------------------数据查询完毕，根据交互方式返回数据--------------------*/
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			String resultMsg="操作已经执行，文件已导出到：“"+exportFilePathName+"”，请按返回按钮返回列表页面！";
			int resultCode=0;
			redirectUrl="export_result.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl+"&redirect_param_name=exist_resultset&redirect_param=1";
			showDebug("[exportRecord]结果URL："+url);
			response.sendRedirect(url);
		}
	}
	public void exportData(JSONObject jsonObj,String filePathName) throws Exception {
		if(jsonObj!=null){
			//开始导出到excel
			ExcelWriter ew=new ExcelWriter();
			showDebug("导出开始！");
			String[] cols={"time_interval⊙text⊙ID","time_interval⊙text⊙操作类型","count⊙"
					+ "text⊙金额","color⊙text⊙操作时间"};	//name⊙type⊙nick
			ew.CreExcel(jsonObj, "交易信息", cols, filePathName);
			jsonObj.put("result_code",0);
			jsonObj.put("result_msg","读取了上一次的查询配置");
		}
	}
}
