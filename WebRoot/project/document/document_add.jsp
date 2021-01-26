<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.*"%>
<%
	//做调试用，这里要在正式发布的时候去掉
	System.out.println("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date()) + "]=======================调试：" + request.getServletPath() + "开始==============================");
%>
<%
	String projectId=request.getParameter("project_id");
%>
<!DOCTYPE html>
<head>
	<title>管理系统</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1" name="viewport" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../../home/frame/frame_style.jsp"%>
	<link rel="stylesheet" type="text/css" href="../../assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" />
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
									<a href="" class="reload"> </a>
									<a href="" class="remove"> </a>
								</div>
							</div>
							<div class="portlet-body form">
								<form class="form-horizontal" role="form" id="page_form" name="page_form" action="#" enctype="multipart/form-data" method="POST">
									<input type="hidden" id="action" name="action" value="add_record" />
									<input type="hidden" id="project_id" name="project_id" value="<%=projectId %>" />
									<div class="form-body">
										<div class="form-group">
											<label class="col-md-3 control-label">
												项目名称
											</label>
											<div class="col-md-9">
												<select class="table-group-action-input form-control input-medium" id="project_id_select" name="project_id_select">
												</select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">
												文档上传
											</label>
											<div class="col-md-9">
												<div class="fileinput fileinput-new" data-provides="fileinput">
													<div class="input-group input-large">
														<div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
															<i class="fa fa-file fileinput-exists"></i>&nbsp;
															<span class="fileinput-filename"> </span>
														</div>
														<span class="input-group-addon btn default btn-file"> <span class="fileinput-new"> 选择文件 </span> <span class="fileinput-exists"> 变更 </span> <input type="file" name="..."> </span>
														<a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> 移除 </a>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="form-actions right1">
										<button type="button" id="return_button" class="btn green" title="返回到前一个页面">
											返回
										</button>
										<button type="button" id="submit_button" class="btn green" title="添加新的记录">
											确认上传
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
<script type="text/javascript" src="../../assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE SCRIPTS -->
<script src="../../assets/module/scripts/project/document/document_add.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->