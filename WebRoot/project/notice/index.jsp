<%@page contentType="text/html; charset=UTF-8"%>
<%
	String contentPage="index_content";
	if(request.getParameter("content_page")!=null){
		contentPage=request.getParameter("content_page");
	}
	contentPage=contentPage+".jsp";
%>
<html lang="en" class="no-js">
	<!--<![endif]-->
	<!-- BEGIN HEAD -->
	<head>
		<meta charset="utf-8" />
		<title>管理系统</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta content="width=device-width, initial-scale=1" name="viewport" />
		<meta content="" name="description" />
		<meta content="" name="author" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@include file="../../home/frame/frame_style.jsp"%>
	</head>
	<!-- END HEAD -->
	<!-- BEGIN BODY -->
	<body class="page-header-fixed page-quick-sidebar-over-content page-sidebar-closed-hide-logo page-container-bg-solid">
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
					<%@include file="../../home/frame/frame_theme_color.jsp"%>
					<%@include file="../../home/frame/frame_page_header.jsp"%>
					<jsp:include page="<%=contentPage %>"></jsp:include>
				</div>
			</div>
			<!-- END CONTENT -->
			<%@include file="../../home/frame/frame_sidebar.jsp"%>
		</div>
		<!-- END CONTAINER -->
		<%@include file="../../home/frame/frame_bottom.jsp"%>
		<%@include file="../../home/frame/frame_ajax_modal.jsp"%>
	</body>
	<!-- END BODY -->
</html>
