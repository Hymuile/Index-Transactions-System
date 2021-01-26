<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.*"%>
<%
	//做调试用，这里要在正式发布的时候去掉
	System.out.println("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date()) + "]=======================调试：" + request.getServletPath() + "开始==============================");
%>
<%
	String id = request.getParameter("content");
	String projectId = request.getParameter("project_id");
	String projectName = request.getParameter("project_name");
%>
<!DOCTYPE html>
<input type="hidden" id="record_id" name="record_id" value="<%=id%>" />
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
									<i class="fa fa-gift"></i>记录显示
								</div>
								<div class="tools">
									<a href="" class="reload"> </a>
									<a href="" class="remove"> </a>
								</div>
							</div>
							<div class="portlet-body form">
								<form class="form-horizontal" role="form" id="page_form" name="page_form" action="#">
									<input type="hidden" id="action" name="action" value="apply_join" />
									<input type="hidden" id="redirect_url" name="redirect_url" value="member_list.jsp" />
									<div class="form-body">
										<div class="form-group">
											<label class="col-md-3 control-label">
												项目ID
											</label>
											<div class="col-md-9">
												<label class="col-md-3 control-label" id="project_id" name="project_id">
												</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">
												项目名称
											</label>
											<div class="col-md-9">
												<label class="col-md-3 control-label" id="project_name" name="project_name">
												</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">
												申请类型
											</label>
											<div class="col-md-9">
												<label class="col-md-3 control-label" id="apply_type" name="apply_type">
												</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">
												申请留言
											</label>
											<div class="col-md-9">
												<label class="col-md-3 control-label" id="notepad" name="notepad">
												</label>
											</div>
										</div>
									</div>
									<div class="form-actions right1">
										<button type="button" id="return_button" class="btn green">
											返回
										</button>
										<button type="button" id="agree_button" class="btn green">
											通过申请
										</button>
										<button type="button" id="deny_button" class="btn green">
											驳回申请
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
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE SCRIPTS -->
<script src="../../assets/module/scripts/project/file/member_apply_check.js?v" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->