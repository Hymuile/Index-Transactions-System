<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.*"%>
<%
	//做调试用，这里要在正式发布的时候去掉
	System.out.println("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date())
			+ "]=======================调试：" + request.getServletPath() + "开始==============================");
%>
<%
	System.out.println("index.jsp里的session=" + session);
	
	String user_email = (String) session.getAttribute("user_email");
	String user_pwd = (String) session.getAttribute("user_pwd");
	String user_name = (String) session.getAttribute("user_name");
	String user_city = (String) session.getAttribute("user_city");
	String user_role = (String) session.getAttribute("user_role");
	String user_margin = (String) session.getAttribute("user_margin");
	
	String user_balance = (String) session.getAttribute("user_balance");
	System.out.println("==================================================");
	
	System.out.println("user_balance="+user_balance);
	System.out.println("==================================================");
	session.setAttribute("unit_db_name", "xm09");

	System.out.println("user_email=" + user_email + ",user_name=" + user_name + ",user_role=" + user_role
			+ ",user_margin=" + user_margin);
	//如果之前有跳转页面，则跳转到指定的页面去
	String redirectUrl = null;
	if (session.getAttribute("return_page") != null) {
		redirectUrl = "../../" + (String) session.getAttribute("return_page");
		//然后删除掉原有的跳转
		session.removeAttribute("return_page");
		response.sendRedirect(redirectUrl);
	}
%>
<!DOCTYPE html>
<head>
<title>期货交易-首页</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="../../home/frame/frame_style.jsp"%>
</head>

<body
	class="page-header-fixed page-quick-sidebar-over-content page-sidebar-closed-hide-logo page-container-bg-solid">
	<%@include file="../../home/frame/frame_top.jsp"%>
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<%@include file="../../home/frame/frame_left.jsp"%>
		<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper">
			<div class="page-content">
				<%@include file="../../home/frame/frame_javascript.jsp"%>
				<%@include file="../../home/frame/frame_portlet.jsp"%>

				<div class="page-bar" id="page_header_div">
					<ul class="page-breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="#">首页</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">面板</a></li>
					</ul>

				</div>
				<!-- BEGIN PAGE CONTENT-->
				
				<div class="row">
					<div class="col-md-12">
						<div class="tabbable-line boxless">
							<div class="tab-content">
								<div class="tab-pane active" id="tab_1">
									<!-- BEGIN FILTER -->
									<div class="margin-top-10">
										<div class="row mix-grid" id="home_content_div"></div>
									</div>
									<!-- END PAGE CONTENT-->
								</div>
								<!-- END FILTER -->
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- END PAGE CONTENT-->
	<!-- END CONTENT -->
	<!-- END CONTAINER -->
	<%@include file="../../home/frame/frame_bottom.jsp"%>
	<%@include file="../../home/frame/frame_ajax_modal.jsp"%>
</body>
</html>
<script src="../../assets/module/scripts/home/index.js"
	type="text/javascript"></script>