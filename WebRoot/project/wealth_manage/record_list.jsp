<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.*"%>
<!-- record_list.jsp查询所有数据，xxx_list.jsp查询自己的数据 -->
<%
	//做调试用，这里要在正式发布的时候去掉
	System.out.println("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date())
			+ "]=======================调试：" + request.getServletPath() + "开始==============================");
%>
<%
	String id = request.getParameter("id");
	String existResultset = request.getParameter("exist_resultset");
%>
<!DOCTYPE html>
<input type="hidden" id="exist_resultset" name="exist_resultset"
	value="<%=existResultset%>" />
<head>
<title>期货交易系统</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="../../home/frame/frame_style.jsp"%>
<!-- BEGIN PAGE LEVEL STYLES -->
<!-- END PAGE LEVEL SCRIPTS -->
</head>
<body
	class="page-header-fixed page-quick-sidebar-over-content page-sidebar-closed-hide-logo page-container-bg-solid">
	<%@include file="../../home/frame/frame_top.jsp"%>
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<%@include file="../../home/frame/frame_left.jsp"%>
		<div class="page-content-wrapper">
			<!-- BEGIN CONTENT -->
			<div class="page-content">
				<%@include file="../../home/frame/frame_portlet.jsp"%>

				<div class="page-bar" id="page_header_div">
					<ul class="page-breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="../../home/main/index.html">首页</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">资产信息</a></li>
					</ul>

				</div>
				<!-- BEGIN PAGE CONTENT-->
				<!-- ----------------------------------------页面开始，替换这里即可---------------------------------------- -->
				<div class="row">
					<div class="col-md-12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box grey-cascade">
							<%@include file="../../home/main/trade_table1.jsp"%>
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-globe"></i>资产信息表
								</div>
								<div class="tools">
									<a href="javascript:;" class="collapse"> </a> <a
										href="#portlet-config" data-toggle="modal" class="config">
									</a> <a href="javascript:reload();" class="reload"> </a>
								</div>
							</div>
							<div class="portlet-body">
								
								<table
									class="table table-striped table-bordered table-hover datatable"
									id="record_list">
									<thead id="record_list_head">
									</thead>
								</table>
							</div>
						</div>
						<!-- END EXAMPLE TABLE PORTLET-->
					</div>
				</div>

				<!-- ----------------------------------------页面结束，替换上面即可---------------------------------------- -->
				<!-- END PAGE CONTENT-->
			</div>
			<!-- END CONTENT -->
			<%@include file="../../home/frame/frame_sidebar.jsp"%>
		</div>
		<!-- END CONTENT WRAPPER-->
	</div>
	<!-- END CONTAINER -->
	<%@include file="../../home/frame/frame_bottom.jsp"%>
	<%@include file="../../home/frame/frame_ajax_modal.jsp"%>
</body>
</html>
<%@include file="../../home/frame/frame_page_component.jsp"%>
<%@include file="../../home/frame/frame_javascript.jsp"%>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE SCRIPTS -->
<script type="text/javascript"
	src="../../assets/module/scripts/user_manage/todo/record_list1.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->