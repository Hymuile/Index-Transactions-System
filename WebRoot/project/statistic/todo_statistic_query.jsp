<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.*"%>
<%
	//做调试用，这里要在正式发布的时候去掉
	System.out.println("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date()) + "]=======================调试：" + request.getServletPath() + "开始==============================");
%>
<%
	String id = request.getParameter("id");
	String existResultset = request.getParameter("exist_resultset");
%>
<!DOCTYPE html>
<head>
	<title>管理系统</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1" name="viewport" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../../home/frame/frame_style.jsp"%>
	<!-- BEGIN PAGE LEVEL STYLES -->
	<!-- END PAGE LEVEL SCRIPTS -->
</head>
<body class="page-header-fixed page-quick-sidebar-over-content page-sidebar-closed-hide-logo page-container-bg-solid">
	<%@include file="../../home/frame/frame_top.jsp"%>
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<%@include file="../../home/frame/frame_left.jsp"%>
		<div class="page-content-wrapper">
			<!-- BEGIN CONTENT -->
			<div class="page-content">
				<%@include file="../../home/frame/frame_portlet.jsp"%>
				<%@include file="../../home/frame/frame_theme_color.jsp"%>
				<%@include file="../../home/frame/frame_page_header.jsp"%>
				<!-- BEGIN PAGE CONTENT-->
				<!-- ----------------------------------------页面开始，替换这里即可---------------------------------------- -->
				<div class="row">
					<div class="col-md-12">
						<div class="portlet box blue ">
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-gift"></i><span id="title_div">记录显示</span>
								</div>
								<div class="tools">
									<a id="tools_menu_reload" href="" class="reload"> </a>
									<a id="tools_menu_remove" href="" class="remove"> </a>
								</div>
							</div>
							<div class="portlet-body form">
								<form class="form-horizontal" role="form" method="post" id="page_form" name="page_form" action="#">
									<input type="hidden" id="action" name="action" value="statistic_record" />
									<input type="hidden" id="id" name="id" value="<%=id%>" />
									<div style="float: left; margin-bottom: 10px; margin-right: 10px; margin-left: 10px; margin-top: 10px;">
										<button type="button" id="statistic_button" class="btn green" title="设置 条件统计对应的记录">
											开始统计
										</button>
										<button type="button" id="return_button" class="btn green">
											返回
										</button>
										<button type="button" id="help_button" class="btn green" title="在线帮助">
											<i class="fa fa-question"></i>
										</button>
									</div>
									<div class="form-body">
										<div class="form-group">
											<label id="page_title" class="col-md-12">
												请在下面的条件设置里选择对应的条件，然后点【开始统计】按钮开始统计
											</label>
										</div>
										<div class="form-group">
											<label class="control-label col-md-1" style="display: none;">
												<input type="checkbox" class="icheck" id="record_select_all" name="record_select_all" data-checkbox="icheckbox_minimal-grey" style="border: 0px solid red; display: none;" />
											</label>
											<label class="control-label col-md-3">
												请选择时间段
											</label>
											<div class="col-md-3">
												<span>从</span>
												<div class="input-group date form_datetime">
													<input type="text" id="time_from" name="time_from" size="16" class="form-control" readonly="true">
													<span class="input-group-btn">
														<button class="btn default date-set" type="button">
															<i class="fa fa-calendar"></i>
														</button> </span>
												</div>
												<span>到</span>
												<div class="input-group date form_datetime">
													<input type="text" id="time_to" name="time_to" size="16" class="form-control" readonly="true">
													<span class="input-group-btn">
														<button class="btn default date-set" type="button">
															<i class="fa fa-calendar"></i>
														</button> </span>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label class="control-label col-md-1" style="display: none;">
												<input type="checkbox" class="icheck" id="record_select_all" name="record_select_all" data-checkbox="icheckbox_minimal-grey" style="border: 0px solid red;" />
											</label>
											<label class="control-label col-md-3">
												请选择统计间隔
											</label>
											<div class="col-md-3">
												<select class="table-group-action-input form-control input-medium" id="time_interval" name="time_interval">
													<option value="hour">
														按小时
													</option>
													<option value="day" selected="selected">
														按每天
													</option>
													<option value="month">
														按每月
													</option>
												</select>
											</div>
										</div>
										<div class="form-group">
											<label class="control-label col-md-1" style="display: none;">
												<input type="checkbox" class="icheck" id="address_select" name="address_select" data-checkbox="icheckbox_minimal-grey" style="border: 0px solid red;" />
											</label>
											<label class="control-label col-md-3">
												请选择统计类型（暂无）
											</label>
											<div class="col-md-3">
												<select class="table-group-action-input form-control input-medium" id="address_id" name="address_id">
													<option value="no">
														（请选择）
													</option>
												</select>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
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
<script type="text/javascript" src="../../assets/module/scripts/project/statistic/todo_statistic_query.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->