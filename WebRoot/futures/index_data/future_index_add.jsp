<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.*"%>
<%
	//做调试用，这里要在正式发布的时候去掉
	System.out.println("["+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date())+"]=======================调试："+request.getServletPath()+"开始==============================");
%>
<!DOCTYPE html>
	<head>
		<title>管理系统</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta content="width=device-width, initial-scale=1" name="viewport" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@include file="../../home/frame/frame_style.jsp"%>
		<!-- BEGIN PAGE LEVEL STYLES -->
		<link rel="stylesheet" type="text/css" href="../../assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />
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
										<i class="fa fa-gift"></i>记录显示
									</div>
									<div class="tools">
										<a href="" class="reload"> </a>
										<a href="" class="remove"> </a>
									</div>
								</div>
								<div class="portlet-body form">
									<form class="form-horizontal" role="form" id="page_form" name="page_form" action="#">
										<input type="hidden" id="action" name="action" value="add_record"/>
										<div class="form-body">
											<div class="form-group">
												<label class="col-md-3 control-label">
													项目名称
												</label>
												<div class="col-md-9">
													<select class="table-group-action-input form-control input-medium" id="project_id" name="project_id">
														<option value="1">（无）</option>
													</select>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-3 control-label">标题（必填）<font color="red">*</font></label>
												<div class="col-md-9">
													<input type="text" id="title" name="title" class="form-control" placeholder="请填写标题" value=""/>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-3 control-label">内容</label>
												<div class="col-md-9">
													<input type="text" id="content" name="content" class="form-control" placeholder="请输入内容" value=""/>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-3 control-label">限制时间</label>
												<div class="col-md-4">
													<div class="input-group date form_datetime">
														<input type="text" id="limit_time" name="limit_time" class="form-control" size="16" placeholder="请输入期限完成时间" value=""/>
														<span class="input-group-btn">
															<button class="btn default date-set" type="button">
																<i class="fa fa-calendar"></i>
															</button>
														</span>
													</div>
												</div>
											</div>
										</div>
										<div class="form-actions right1">
											<button type="button" id="search_button" class="btn green">
												查询记录
											</button>
											<button type="button" id="submit_button" class="btn green">
												添加记录
											</button>
											<button type="button" id="return_button" class="btn green">
												返回
											</button>
											<button type="button" id="help_button" class="btn green" title="在线帮助">
												<i class="fa fa-question"></i>
											</button>
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
<script type="text/javascript" src="../../assets/global/plugins/bootstrap-daterangepicker/moment.min.js"></script>
<script type="text/javascript" src="../../assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE SCRIPTS -->
<script type="text/javascript" src="../../assets/module/scripts/project/todo/todo_add.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->
