package futures.statistic;
/*
 * 增删改查看导印统功能的实现
 * 待完成：用MVC模式分开DB和Action操作
 */
import dao.YlxId;
import dao.ylx_db;
import project.dao.Statistic;
import project.dao.StatisticDao;
import futures.dao.Index;
import futures.dao.IndexDao;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.DefaultCategoryDataset;
import org.jfree.data.DefaultPieDataset;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utility.LogEvent;
import utility.MD5Util;
import utility.excel.ExcelWriter;
import utility.export.Export;
import utility.export.ExportDao;
public class ServletAction extends HttpServlet {
	/*----------线程需要的信息----------*/
	public HttpSession session=null;
	String sessionId=null;
	String filePathName=null;
	String fileName=null;
	String filePath=null;
	String fileUrl=null;
	String fileSize="0";
	int	percent=0;
	boolean threadRunning=false;
	private ExportThread exportThread;
	/*----------------------------------*/
	//这里是需要改的,module和sub
	public String module = "project";
	public String sub = "futures";
	
	public String preFix = module + "_" + sub;
	public String resultPath = module + "/" + sub;
	public String resultPage = "result.jsp";
	public String resultUrl=resultPath+"/"+resultPage;
	public String redirectPath = module + "/" + sub;
	public String redirectPage = "record_list.jsp";
	public String redirectUrl=redirectPath+"/"+redirectPage;
	public String databaseName="xm09";
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
				if (action.equals("statistic_record")) {
					actionOk=true;
					try {
						statisticRecord(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("get_statistic_record")) {
					actionOk=true;
					try {
						getStatisticRecord(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("export_statistic_resultset")) {
					actionOk=true;
					try {
						exportStatisticResultset(request, response);
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
	 * 功能：根据前台页面的设置，统计对应的记录数据
	 */
	public void statisticRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String type=request.getParameter("type");
		String timeFrom=request.getParameter("time_from");
		String timeTo=request.getParameter("time_to");
		String timeInterval=request.getParameter("time_interval");
		String addressId=request.getParameter("address_id");
		String statisticType=request.getParameter("statistic_type");
		if(statisticType==null) statisticType="no";
		showDebug("[statisticRecord]收到页面传过来的参数是："+timeFrom+","+timeTo+","+timeInterval+",statisticType="+statisticType);
		String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
		String userName=session.getAttribute("user_name")==null?null:(String)session.getAttribute("user_name");

		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(dbName!=null){
			String creator=(String)session.getAttribute("user_name");
			String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			StatisticDao statisticDao=new StatisticDao();
			Statistic statistic=new Statistic();
			statistic.setTimeFrom(timeFrom);
			statistic.setTimeTo(timeTo);
			statistic.setUserId(userId);
			statistic.setTimeInterval(timeInterval);
			statistic.setAddressId(addressId);
			statistic.setStatisticType(statisticType);
			/*---------------------------------把状态保存进session---------------------------*/
			if(timeFrom!=null){session.setAttribute(module+"_"+sub+"_statistic_query_time_from",timeFrom);}
			if(timeTo!=null){session.setAttribute(module+"_"+sub+"_statistic_query_time_to",timeTo);}
			if(timeInterval!=null){session.setAttribute(module+"_"+sub+"_statistic_query_timeinterval",timeInterval);}
			if(addressId!=null){session.setAttribute(module+"_"+sub+"_statistic_query_addressid",addressId);}
			/*---------------------------------开始进行统计---------------------------*/
			jsonObj=statisticDao.statisticRecord(action,dbName,statistic);
			jsonObj.put("user_id",userId);
			jsonObj.put("user_name",userName);
		}
		//获取完毕，开始生成统计图
		//HttpSession session,String dbName,JSONObject json,String title,String column,int statisticImage,int chartWidth,int chartHeight
		String fileName=getStatisticGraph(session,dbName,jsonObj,"待办事项统计图","测试Column",statisticType,1,1280,768);
		jsonObj.put("result_image",fileName);
		session.setAttribute(module+"_"+sub+"_statistic_record_result",jsonObj);
		showDebug("[statisticRecord]统计完毕，保存进session："+module+"_"+sub+"_statistic_record_result");

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
			resultUrl=resultPath+"/todo_statistic_result.jsp";
			String url = resultUrl+"?exist_resultset=1&result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			showDebug("[statisticRecord]结果URL："+url);
			response.sendRedirect(url);
		}
	}
	public String getStatisticGraph(HttpSession session,String dbName,JSONObject json,String title,String column,String statisticType,int statisticImage,int chartWidth,int chartHeight) throws SQLException, IOException, JSONException{
		//要改动的
		String chartTitle = "统计图";
		String tmpDir = "/chart";
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		DefaultPieDataset pieDataSet =new DefaultPieDataset();
		showDebug("statisticImage的值是："+statisticImage+",column="+column+",title="+title);
		JSONArray arr=json.getJSONArray("aaData");
		showDebug(arr.toString());
		for(int i=0;i<arr.length();i++) {
			String timeInterval=(String)((List)(arr.get(i))).get(1);
			Integer count=Integer.parseInt((String)((List)(arr.get(i))).get(2));
			String countName="";
			if(statisticType.equals("no")){
				countName="所有车辆";
			}else{
				String colorId=(String)((List)(arr.get(i))).get(3);
				countName=(String)((List)(arr.get(i))).get(4);
			}
			dataset.addValue(count,countName,timeInterval);
			//pieDataSet.setValue(json.getString("colTime"),json.getInt("colCount"));
		}
		JFreeChart chart=null;
		if(statisticImage==1)
			chart = ChartFactory.createBarChart3D(chartTitle, title, "数量", dataset, PlotOrientation.VERTICAL, true, false, false);
		if(statisticImage==2)
			chart = ChartFactory.createLineChart(chartTitle,title,"数量", dataset, PlotOrientation.VERTICAL, true, false, false);
		if(statisticImage==3)
			chart = ChartFactory.createPieChart(chartTitle,pieDataSet,true,false,false);
		String chartFilename = ServletUtilities.saveChartAsJPEG(chart, chartWidth, chartHeight, null, session);
		chartFilename = tmpDir + "/" + chartFilename;
		return chartFilename;
	}
	public void getStatisticRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String dbName=(String)session.getAttribute("unit_db_name");
		String type=request.getParameter("type");
		String timeFrom=request.getParameter("time_from");
		String timeTo=request.getParameter("time_to");
		String timeInterval=request.getParameter("time_interval");
		String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
		String userName=session.getAttribute("user_name")==null?null:(String)session.getAttribute("user_name");
		String existResultset= request.getParameter("exist_resultset");
		if((existResultset==null) ||(existResultset.equals("null") || existResultset.isEmpty())) existResultset="0";
		showDebug("getStatisticRecord收到页面传过来的参数是："+existResultset+","+timeFrom+","+timeTo+","+timeInterval);

		/*----------------------------------------数据获取完毕，开始和数据库交互*/
		JSONObject jsonObj=null;
		//检查输入参数是否正确先
		if(existResultset.equals("1")){			//如果是新查询
			//如果有就取出来，如果没有就重新查询一次，并且保存进session里
			if(session.getAttribute(module+"_"+sub+"_statistic_record_result")!=null){
				jsonObj=(JSONObject)session.getAttribute(module+"_"+sub+"_statistic_record_result");
				showDebug("取出了原来的结果");
			}else{
				jsonObj=new JSONObject();
				jsonObj.put("result_code",10);
				jsonObj.put("result_msg","session里没有找到之前统计的数据！");
				showDebug("没有结果："+module+"_"+sub+"_statistic_record_result");
			}
		}else{
			//这里无法进行新的统计
			showDebug("没有结果，而且不进行新的统计");
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
			showDebug("getStatisticRecord结果URL："+url);
			response.sendRedirect(url);
		}
	}
	public void getFlowStatisticQuerySetup(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String dbName=(String)session.getAttribute("unit_db_name");
		String id=request.getParameter("id");
		String action = request.getParameter("action");
		String indexName=request.getParameter("index_name");
		String type= request.getParameter("type");
		String existResultset= request.getParameter("exist_resultset");
		String groupSelect= request.getParameter("group_select");
		String titleSearch= request.getParameter("title_search");
		String timeFrom= request.getParameter("time_from");
		String timeTo= request.getParameter("time_to");
		String status=request.getParameter("status_select");
		int statusSelect=-2;
		if(status!=null)
			statusSelect=Integer.parseInt(status);
		if((existResultset==null) ||(existResultset.equals("null") || existResultset.isEmpty())) existResultset="0";
		String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
		String userName=session.getAttribute("user_name")==null?null:(String)session.getAttribute("user_name");
		int resultCode=0;
		//检查输入参数是否正确先
		showDebug("收到页面传过来的参数是："+existResultset+","+action+","+type+","+id+","+groupSelect+","+titleSearch+","+timeFrom+","+timeTo);
		String creator=(String)session.getAttribute("user_name");
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		//0,get_record,null,null,wlw,物联网,2016-12-01 00:00:00,2016-12-31 23:59:59
		JSONObject jsonObj=new JSONObject();
		/*----------------------------------------数据获取完毕，开始返回数值*/
		if(session.getAttribute("project_todo_statistic_query_time_from")!=null){jsonObj.put("time_from",(String)session.getAttribute("project_todo_statistic_query_time_from"));}else{resultCode=1;}
		if(session.getAttribute("project_todo_statistic_query_time_to")!=null){jsonObj.put("time_to",(String)session.getAttribute("project_todo_statistic_query_time_to"));}else{}
		if(session.getAttribute("project_todo_statistic_query_timeinterval")!=null){jsonObj.put("time_interval",(String)session.getAttribute("project_todo_statistic_query_timeinterval"));}else{}
		if(session.getAttribute("project_todo_statistic_query_addressid")!=null){jsonObj.put("address_id",(String)session.getAttribute("project_todo_statistic_query_addressid"));}else{}
		jsonObj.put("result_code",resultCode);
		jsonObj.put("result_msg","读取了上一次的查询配置");
		showDebug(jsonObj.toString());
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
			redirectUrl=sub+"_list.jsp";
			resultUrl=resultPath+"/reward_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?exist_resultset=1&result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			response.sendRedirect(url);
		}
	}
	/*
	 * 功能：导出已存在的结果集的数据
	 */
	public void exportStatisticResultset(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		/*--------------------为线程里用的变量赋值--------------------*/
		String dateTime=(new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
		sessionId=session.getId();
		fileName="zakk_statistic_export_"+sessionId+"_"+dateTime+".xls";
		filePath="C:\\upload\\project\\export\\temp";
		filePathName=filePath+"\\"+fileName;
		fileUrl="/upload/project/export/temp/"+fileName;
		/*--------------------赋值完毕--------------------*/
		String dbName=(String)session.getAttribute("unit_db_name");
		String action = request.getParameter("action");
		String existResultset= request.getParameter("exist_resultset");
		if((existResultset==null) ||(existResultset.equals("null") || existResultset.isEmpty())) existResultset="0";
		String userId=session.getAttribute("user_id")==null?null:(String)session.getAttribute("user_id");
		String userName=session.getAttribute("user_name")==null?null:(String)session.getAttribute("user_name");
		int resultCode=0;
		//检查输入参数是否正确先
		showDebug("收到页面传过来的参数是："+existResultset+","+action);
		String creator=(String)session.getAttribute("user_name");
		String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		JSONObject jsonObj=null;
		String description="对导出的描述";
		/*----------------------------------------数据获取完毕，开始返回数值*/
		//如果是新的下载，就执行，否则检查有没有线程在运行，如果有就进入，如果没有就新下载
		/*----------------------------------------构造返回数值*/
		ExportDao exportDao=new ExportDao();
		Export export=new Export();
		export.setSessionId(session.getId());
		export.setCreateTime(createTime);
		export.setCreator(creator);
		export.setUserId(userId);
		export.setUserName(userName);
		export.setDbName(dbName);
		export.setExportStatus("1");
		export.setFileName(fileName);
		export.setFilePath(filePath.replaceAll("\\\\", "/"));
		export.setFileUrl(fileUrl);
		export.setFileSize(fileSize);
		export.setLimitTime(createTime);
		export.setDownloadCount("0");
		export.setExportPercent("0");
		export.setExportType("1");
		processExportThread(request,response,export);

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
			redirectUrl=sub+"_list.jsp";
			resultUrl=resultPath+"/../../user/center/export_list.jsp";
			resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
			String url = resultUrl+"?exist_resultset=1&result_msg="+resultMsg+"&result_code="+resultCode+"&redirect_url="+redirectUrl;
			response.sendRedirect(url);
		}
	}
	/*
	 * 功能：开启线程，进行导出
	 */
	public void processExportThread(HttpServletRequest request,HttpServletResponse response,Export export) throws SQLException, IOException, ServletException, JSONException {
		HttpSession session = request.getSession();
		String tempDir=filePath+"/"+session.getId()+"_"+(new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
		java.io.File f=new java.io.File(tempDir);
		if(!f.exists())
			f.mkdir();
		String zipFilename="zakk_file_export_"+session.getId()+"_"+(new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())+".rar";
		//首先找到原来的结果集
		JSONObject jsonObj=null;
		if(session.getAttribute(module+"_"+sub+"_statistic_record_result")!=null){
			jsonObj=(JSONObject)session.getAttribute(module+"_"+sub+"_statistic_record_result");
			showDebug("[getExportRecord]取出了原来的结果");
			jsonObj.put("result_code",0);
			jsonObj.put("result_msg","读取了上一次的查询配置");
		}else{
			jsonObj=new JSONObject();
			jsonObj.put("result_code",10);
			jsonObj.put("result_msg","session里没有找到之前统计的数据！");
			showDebug("[getExportRecord]没有结果");
		}
		//找到以后，进行开启线程导出

		if(exportThread != null){
			showDebug("[processExportThread]发现exportThread不为null！");
		}
		else{
			exportThread = new ExportThread();
			exportThread.setResultset(jsonObj);
			exportThread.setExport(export);
			exportThread.setTempDir(tempDir);
			exportThread.setZipFilename(zipFilename);
			exportThread.start();
		}
	}
	public void exportData(JSONObject jsonObj,Export export) throws Exception {
		if(jsonObj!=null){
			//开始导出到excel
			ExcelWriter ew=new ExcelWriter();
			String[] cols={"index⊙text⊙nick","time_interval⊙text⊙nick","count⊙text⊙nick","color⊙text⊙nick","color_name⊙text⊙nick"};	//name⊙type⊙nick
			ew.CreExcel(jsonObj, "数据统计", cols, filePathName);
			jsonObj.put("result_code",0);
			jsonObj.put("result_msg","读取了上一次的查询配置");
		}
	}
	class ExportThread extends Thread {
		private JSONObject jsonObj=null;
		private Export export=null;
		String tempPath=null;
		String zipFilename=null;
		String module=null;
		public void run() {
			if (!this.isInterrupted()) {// 线程未中断执行循环
				try {
					threadExecute(module,jsonObj,export,tempPath,zipFilename);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		public void setResultset(JSONObject jsonObj){
			this.jsonObj=jsonObj;
		}
		public void setExport(Export export){
			this.export=export;
		}
		public void setTempDir(String tempDir){
			this.tempPath=tempDir;
		}
		public void setZipFilename(String zipFilename){
			this.zipFilename=zipFilename;
		}
		public String getModule() {
			return module;
		}
		public void setModule(String module) {
			this.module = module;
		}
	}
	public void threadExecute(String module,JSONObject jsonObj,Export export,String tempPath,String zipFilename) throws Exception{
		ExportDao exportDao=new ExportDao();
		threadRunning=true;
		exportDao.setExportBegin(export);
		showDebug("threadExecute的线程开始了！");
		exportData(jsonObj,export);
		export.setExportPercent("20");
		exportDao.setExportPercent(export);
		threadRunning=false;
		showDebug("threadExecute的线程退出了！");
		//导出完毕后，就写数据库
		exportDao.setExportEnd(export);
	}
}
