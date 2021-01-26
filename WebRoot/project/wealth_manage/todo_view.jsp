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
	<title>期货交易系统-单用户信息查看</title>
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
									<i class="fa fa-gift"></i>用户信息显示
								</div>
								<div class="tools">
									<a href="" class="reload"> </a>
								</div>
							</div>
							<div class="portlet-body form">
								<form class="form-horizontal" role="form" id="page_form" name="page_form" action="#">
								
									<input type="hidden" id="action" name="action" value="modify_record" />
									<input type="hidden" id="id" name="id" value="<%=id%>" />
									<input type="hidden" id="exist_resultset" name="exist_resultset" value="<%=existResultset%>" />
									
									<div class="form-body">
										<div class="form-group">
											<label class="col-md-4" id="record_list_tip">
											</label>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">
												id
											</label>
											<div class="col-md-9">
												<input type="text" id="user_id" name="user_id" class="form-control" readonly="true" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">
												用户邮箱
											</label>
											<div class="col-md-9">
												<input type="text" id="user_email" name="user_email" class="form-control" placeholder="请填写邮箱" value="" readonly="true" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">
												密码
											</label>
											<div class="col-md-9">
												<input type="text" id="user_pwd" name="user_pwd" class="form-control" placeholder="请输入密码" value="" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">
												用户名
											</label>
											<div class="col-md-9">
												<input type="text" id="user_name" name="user_name" class="form-control" placeholder="请输入用户名" value="" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">
												用户权限
											</label>
											<div class="col-md-9">
												<input type="text" id="user_role" name="user_role" class="form-control" placeholder="请输入权限，仅限normal和manager" value="" readonly="true" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">
												用户余额
											</label>
											<div class="col-md-9">
												<input type="text" id="user_balance" name="user_balance" class="form-control" placeholder="请输入余额" value="" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">
												用户预付款
											</label>
											<div class="col-md-9">
												<input type="text" id="user_margin" name="user_margin" class="form-control" placeholder="请输入预付款" value="" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">
												用户所在地
											</label>
											<div class="col-md-9">
												<input type="text" id="user_city" name="user_city" class="form-control" placeholder="请输入城市" value="" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">
												用户创建时间
											</label>
											<div class="col-md-9">
												<input type="text" id="user_create_time" name="user_create_time" class="form-control" value="" readonly="true" />
											</div>
										</div>
									</div>
									<div class="form-actions right1">
										<button type="button" id="submit_button" class="btn green" title="把修改好的信息提交到后台保存">
											提交修改
										</button>
										<button type="button" id="return_button" class="btn green">
											返回
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
<script src="../../assets/module/scripts/user_manage/todo/todo_view.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
