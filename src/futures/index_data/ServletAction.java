package futures.index_data;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

import futures.dao.Index;
import futures.dao.IndexDao;

import utility.LogEvent;
import utility.excel.ExcelWriter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class ServletAction extends HttpServlet  {
private static final long serialVersionUID = 1L;
	
	//这里是需要改的,module和sub
	public String module = "futures";
	public String sub = "index_data";
	
	public String preFix = module + "_" + sub;
	public String resultPath = module + "/" + sub;
	public String resultPage = "result.jsp";
	public String resultUrl=resultPath+"/"+resultPage;
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
				if (action.equals("delete_record")) {
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
				if (action.equals("get_k_line_data")) {
					actionOk=true;
					try {
						getKLineData(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("test_trade")) {
					actionOk=true;
					try {
						testTrade(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("get_wealth")) {
					actionOk=true;
					try {
						getWealth(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("sell")) {
					actionOk=true;
					try {
						sell(request, response);
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
	
	public void testTrade(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String dbName=(String)session.getAttribute("unit_db_name");
		String user_id = (String)session.getAttribute("user_email");
		String user_balance = (String)session.getAttribute("user_balance");
		String existResultset= request.getParameter("exist_resultset");
		if((existResultset==null) ||(existResultset.equals("null") || existResultset.isEmpty())) existResultset="0";
		String action = request.getParameter("action");
		String index_id = request.getParameter("order_id");
		String index_name = request.getParameter("order_name");
		String order_number = request.getParameter("order_number");
		System.out.println("action="+action+",index_id="+index_id+",index_name="+index_name+",order_numer="+order_number);
		//这里可以修改成统一一个函数读取变量，下面的session里的attr可以用一个变量代替
		//检查输入参数是否正确先
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		String creator=(String)session.getAttribute("user_name");
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		Index query=new Index();
		query.setAction(action);
		query.setDbName(dbName);
		query.setIndexId(index_id);
		query.setIndexName(index_name);
		query.setUserId(user_id);
		query.setUserBalance(user_balance);
		query.setOrderNumber(order_number);
		response.setCharacterEncoding("UTF-8");
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
			IndexDao indexDao=new IndexDao();
			
			jsonObj=indexDao.testTrade(query,request,response);
			
			session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj);
		}
		jsonObj.put("action",action);
		/*--------------------数据查询完毕，根据交互方式返回数据--------------------*/

		response.getWriter().print(jsonObj);
		response.getWriter().flush();
		response.getWriter().close();
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
	public void sell(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String dbName=(String)session.getAttribute("unit_db_name");
		String user_id = (String)session.getAttribute("user_email");
		String user_balance = (String)session.getAttribute("user_balance");
		String existResultset= request.getParameter("exist_resultset");
		if((existResultset==null) ||(existResultset.equals("null") || existResultset.isEmpty())) existResultset="0";
		String action = request.getParameter("action");
		String index_id = request.getParameter("order_id");
		String index_name = request.getParameter("order_name");
		String order_number = request.getParameter("order_number");
		
		System.out.println("action="+action+",index_id="+index_id+",index_name="+index_name+",order_number="+order_number);
		
		//这里可以修改成统一一个函数读取变量，下面的session里的attr可以用一个变量代替
		//检查输入参数是否正确先
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		String creator=(String)session.getAttribute("user_name");
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		Index query=new Index();
		
		query.setAction(action);
		query.setDbName(dbName);
		query.setIndexId(index_id);
		query.setIndexName(index_name);
		query.setUserId(user_id);
		query.setUserBalance(user_balance);
		query.setOrderNumber(order_number);
		response.setCharacterEncoding("UTF-8");
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
			IndexDao indexDao=new IndexDao();
			
			jsonObj=indexDao.sell(query,request,response);
			
			session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj);
		}
		jsonObj.put("action",action);
		/*--------------------数据查询完毕，根据交互方式返回数据--------------------*/

		response.getWriter().print(jsonObj);
		response.getWriter().flush();
		response.getWriter().close();
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
	
	public void getWealth(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		String dbName=(String)session.getAttribute("unit_db_name");
		String user_id = (String)session.getAttribute("user_email");
		
		String action = request.getParameter("action");
		String existResultset= request.getParameter("exist_resultset");
		
		if((existResultset==null) ||(existResultset.equals("null") || existResultset.isEmpty())) existResultset="0";

		//这里可以修改成统一一个函数读取变量，下面的session里的attr可以用一个变量代替
		//检查输入参数是否正确先
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		String creator=(String)session.getAttribute("user_name");
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		Index query=new Index();
		
		query.setAction(action);
		query.setDbName(dbName);
		query.setUserId(user_id);
		
		response.setCharacterEncoding("UTF-8");
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
			IndexDao indexDao=new IndexDao();
			jsonObj=indexDao.getWealth(query);
			session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj);
		}
		jsonObj.put("action",action);
		/*--------------------数据查询完毕，根据交互方式返回数据--------------------*/

		response.getWriter().print(jsonObj);
		response.getWriter().flush();
		response.getWriter().close();
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
		String existResultset= request.getParameter("exist_resultset");
		if((existResultset==null) ||(existResultset.equals("null") || existResultset.isEmpty())) existResultset="0";

		String action = request.getParameter("action");
		String indexId = request.getParameter("index_id");
		System.out.println("queryRecord()/收到的action="+action+",indexId="+indexId);
		//这里可以修改成统一一个函数读取变量，下面的session里的attr可以用一个变量代替
		//检查输入参数是否正确先
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		String creator=(String)session.getAttribute("user_name");
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		
		Index query=new Index();
		query.setAction(action);
		query.setDbName(dbName);
		query.setIndexId(indexId);
		response.setCharacterEncoding("UTF-8");
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
			IndexDao indexDao=new IndexDao();
			jsonObj=indexDao.getRecord(query);
			session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj);
		}
		jsonObj.put("action",action);
		/*--------------------数据查询完毕，根据交互方式返回数据--------------------*/

		response.getWriter().print(jsonObj);
		response.getWriter().flush();
		response.getWriter().close();
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
	
	public void getKLineData(HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, JSONException, SQLException{
		try {
			String indexCode = request.getParameter("index_id");
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy_MM_dd");
			Date date = new Date(System.currentTimeMillis());
			String year = formatter.format(date).toString().substring(0, 4);
			String month = formatter.format(date).toString().substring(5, 7);
			String day = formatter.format(date).toString().substring(8, 10);
			if(day.charAt(0) == '0'){
				day = day.substring(1,2);
			}
			
			String target = "https://stock2.finance.sina.com.cn/futures/api/jsonp.php/var%20_"+indexCode+year+"_"+month+"_"+day+"=/GlobalFuturesService.getGlobalFuturesDailyKLine?symbol="+indexCode+"&_=2020_12_1&source=web";
			System.out.println("target="+target);
			URL url = new URL(target);
			
			try {
				//通过URL的openStrean方法获取URL对象所表示的自愿字节输入流
				InputStream is = url.openStream();
				InputStreamReader isr = new InputStreamReader(is,"GBK");

				//为字符输入流添加缓冲
				BufferedReader br = new BufferedReader(isr);
				String line = br.readLine();//读取数据
				String data="";
				while (line!=null){//循环读取数据
					data = data + line;
					line = br.readLine();
				}
				br.close();
				isr.close();
				is.close();
				
				JSONArray jsonArray = processKLineData(data);
				response.getWriter().print(jsonArray);
				response.getWriter().flush();
				response.getWriter().close();
				boolean isAjax=true;
				if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
				if(isAjax){
					response.setContentType("application/json; charset=UTF-8");
					PrintWriter out;
					try {
						System.out.println("getKline是异步请求");
						out = response.getWriter();
						out.print(jsonArray);
						out.flush();
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{
					System.out.println("getKline是同步请求");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	public JSONArray processKLineData(String data) throws JSONException{
		String[] _=data.split("=");
		String target= _[2];
		String list = _[2].substring(1,_[2].length()-2);
		
		JSONArray jsonArray = new JSONArray(list); 
		List result = new ArrayList();
		
		if(jsonArray.length() > 0){
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				List temp= new ArrayList();
				temp.add(jsonObject.get("date").toString().replace("-", "/"));
				temp.add(Double.parseDouble(jsonObject.get("open").toString()));
				temp.add(Double.parseDouble(jsonObject.get("close").toString()));
				temp.add(Double.parseDouble(jsonObject.get("low").toString()));
				temp.add(Double.parseDouble(jsonObject.get("high").toString()));
				result.add(temp);
			}
		}
		return jsonArray;
	}
	
	/*
	 * 功能：查询记录
	 */
	public void getRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String dbName=(String)session.getAttribute("unit_db_name");
		String action = request.getParameter("action");
		String existResultset= request.getParameter("exist_resultset");
		
		if((existResultset==null) ||(existResultset.equals("null") || existResultset.isEmpty())) existResultset="0";

		//这里可以修改成统一一个函数读取变量，下面的session里的attr可以用一个变量代替
		//检查输入参数是否正确先
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		String creator=(String)session.getAttribute("user_name");
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		Index query=new Index();
		query.setAction(action);
		query.setDbName(dbName);
		response.setCharacterEncoding("UTF-8");
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
			IndexDao indexDao=new IndexDao();
			jsonObj=indexDao.getRecord(query);
			session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj);
		}
		jsonObj.put("action",action);
		/*--------------------数据查询完毕，根据交互方式返回数据--------------------*/

		response.getWriter().print(jsonObj);
		response.getWriter().flush();
		response.getWriter().close();
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
	
	public void getRecordView(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String id=request.getParameter("id");
		String index=request.getParameter("index");
		String existResultset= request.getParameter("exist_resultset");
		if((existResultset==null) ||(existResultset.equals("null") || existResultset.isEmpty())) existResultset="0";
		String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
		String userName=session.getAttribute("user_name")==null?null:(String)session.getAttribute("user_name");
		//检查输入参数是否正确先
		showDebug("收到页面传过来的参数是：exist_resultset="+existResultset+",action="+action+",id="+id+",index="+index);
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		Index query=new Index();
		query.setAction(action);
		query.setDbName(dbName);
		//query.setUserId(userId);
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
					IndexDao todoDao=new IndexDao();
					jsonObj=todoDao.getRecord(query);
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
				IndexDao todoDao=new IndexDao();
				jsonObj=todoDao.getRecord(query);
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
		String projectId=request.getParameter("project_id");
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		String limitTime=request.getParameter("limit_time");
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(dbName!=null){
			String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
			String creator=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			/*----------------------------------------数据获取完毕，开始和数据库交互*/
			IndexDao todoDao=new IndexDao();
			Index todo=new Index();
			todo.setAction(action);
			todo.setDbName(dbName);
			//todo.setParentId(projectId);
			//todo.setTitle(title);
			//todo.setContent(content);
			//todo.setLimitTime(limitTime);
			//todo.setUserId(userId);
			todo.setCreator(creator);
			todo.setCreateTime(createTime);
			jsonObj=todoDao.addRecord(todo);
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
			redirectUrl="record_list.jsp";
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
		System.out.println("futures.index_data/ServletAction/modifyRecord 执行到了这里了，但是空函数");
	}
	public void deleteRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
			/*----------------------------------------数据获取完毕，开始和数据库交互*/
			IndexDao todoDao=new IndexDao();
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
			IndexDao todoDao=new IndexDao();
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
		//这里可以修改成统一一个函数读取变量，下面的session里的attr可以用一个变量代替
		//检查输入参数是否正确先
		showDebug("[exportRecord]收到页面传过来的参数是："+existResultset+","+action);
		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		String creator=(String)session.getAttribute("user_name");
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		String exportFilePathName="C:\\upload\\project\\export\\todo_export_"+(new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())+".xls";
		JSONObject jsonObj=null;
		if(existResultset.equals("1")){
			//要求从session提取之前查询结果，如果有就取出来，如果没有就保存
			if(session.getAttribute(module+"_"+sub+"_get_record_result")!=null){
				jsonObj=(JSONObject)session.getAttribute(module+"_"+sub+"_get_record_result");
				//取出来以后，导出
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
			redirectUrl="../export/export_result.jsp?exist_resultset=1";
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
			String[] cols={"index⊙text⊙nick","time_interval⊙text⊙nick","count⊙text⊙nick","color⊙text⊙nick","color_name⊙text⊙nick"};	//name⊙type⊙nick
			ew.CreExcel(jsonObj, "卡口统计", cols, filePathName);
			jsonObj.put("result_code",0);
			jsonObj.put("result_msg","读取了上一次的查询配置");
		}
	}
}
