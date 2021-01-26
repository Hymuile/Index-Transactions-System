<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.*"%>
<%
	//做调试用，这里要在正式发布的时候去掉
	System.out.println("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date()) + "]=======================调试：" + request.getServletPath() + "开始==============================");
%>
<%
	String userName = (String) session.getAttribute("user_name");
	String projectId = request.getParameter("project_id");
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
									<i class="fa fa-gift"></i>记录显示
								</div>
								<div class="tools">
									<a href="" class="reload"> </a>
									<a href="" class="remove"> </a>
								</div>
							</div>
							<div class="portlet-body form">
								<form class="form-horizontal" role="form" id="page_form" name="page_form" action="#">
									<input type="hidden" id="action" name="action" value="add_member_record" />
									<input type="hidden" id="redirect_url" name="redirect_url" value="member_list.jsp" />
									<input type="hidden" id="project_id" name="project_id" value="<%=projectId%>" />
									<input type="hidden" id="avatar" name="avatar" value="/upload/project/member/avatar.jpg" />
									<div class="form-body">
										<div class="form-group">
											<label class="col-md-3 control-label">
												成员ID（自动获取）
												<font color="red">*</font>
											</label>
											<div class="col-md-9">
												<input type="text" id="member_id" name="member_id" class="form-control" placeholder="成员ID，根据下面填写的名字自动获取" value="" readonly="true"/>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">
												成员名字（必填）
											</label>
											<div class="col-md-9">
												<div class="input-group">
													<span class="input-group-addon"> <i class="fa fa-user"></i> </span>
													<input type="text" id="member_name" name="member_name" class="form-control" placeholder="请输入成员姓名" value="" />
												</div>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">
												角色
											</label>
											<div class="col-md-9">
												<select class="table-group-action-input form-control input-medium" id="member_role" name="member_role">
													<option value="">
														（无）
													</option>
													<option value="manager">
														项目经理
													</option>
													<option value="member">
														组员
													</option>
													<option value="developer">
														研发
													</option>
													<option value="test">
														测试
													</option>
													<option value="support">
														后勤人员
													</option>
												</select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">
												开始时间
											</label>
											<div class="col-md-9">
												<input type="text" id="start_time" name="start_time" class="form-control" placeholder="请输入成员工作开始时间" value="2016-10-20 00:00:00" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">
												结束时间
											</label>
											<div class="col-md-9">
												<input type="text" id="end_time" name="end_time" class="form-control" placeholder="请输入成员工作结束时间" value="2017-10-20 00:00:00" />
											</div>
										</div>
									</div>
									<div class="form-actions right1">
										<button type="button" id="return_button" class="btn green">
											返回
										</button>
										<button type="button" id="search_button" class="btn green">
											查询记录
										</button>
										<button type="button" id="submit_button" class="btn green">
											保存记录
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
<script src="../../assets/module/scripts/project/file/member_add.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->