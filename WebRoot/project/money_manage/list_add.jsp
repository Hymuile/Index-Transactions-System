<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.*"%>
<%
	//做调试用，这里要在正式发布的时候去掉
	System.out.println("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date())
			+ "]=======================调试：" + request.getServletPath() + "开始==============================");
%>
<!DOCTYPE html>
<head>
<title>期货交易系统-资产管理--充值</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="../../home/frame/frame_style.jsp"%>
<!-- BEGIN PAGE LEVEL STYLES -->
<link rel="stylesheet" type="text/css"
	href="../../assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />
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
				<%@include file="../../home/frame/frame_theme_color.jsp"%>
				<%@include file="../../home/frame/frame_page_header.jsp"%>
				<!-- BEGIN PAGE CONTENT-->
				<!-- ----------------------------------------页面开始，替换这里即可---------------------------------------- -->
				<div class="row">
					<div class="col-md-12">
						<div class="portlet box blue ">
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-gift"></i>充值
								</div>
								<div class="tools">
									<a href="" class="reload"> </a>
								</div>
							</div>
							<div class="portlet light bordered">
								<div class="portlet-title">
									<div class="caption font-red-sunglo">
										<i class="icon-settings font-red-sunglo"></i> <span
											class="caption-subject bold uppercase"> 操作金额</span>
									</div>
								</div>
								<input type="hidden" id="action" name="action" value="add_record">
								<div class="portlet-body form">
									<form role="form">
										<div class="form-body">
											<div class="form-group form-md-line-input">
												<input type="text" class="form-control" id="trading_number"
													placeholder="此处输入金额数值"> <label for="form_control_1"></label>
												<span class="help-block">您动用的金额</span>
											</div>
											<div class="form-group form-md-line-input has-info">
												<select class="form-control" id="form_control_1">
													<option value="">点击进行选择</option>
													<option value="in" id="in">充值</option>
													<option value="out" id="out">提现</option>
												</select> <label for="form_control_1">操作方式</label>
											</div>
										</div>
										<div class="form-actions noborder">
											<button type="button" class="btn blue" id="submit_button">提交</button>
											<a href="record_list.jsp" type="button" class="btn default">返回</a>
										</div>
									</form>
								</div>
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
<script type="text/javascript"
	src="../../assets/global/plugins/bootstrap-daterangepicker/moment.min.js"></script>
<script type="text/javascript"
	src="../../assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE SCRIPTS -->
<script type="text/javascript"
	src="../../assets/module/scripts/money_manage/todo/list_add.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->
