<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.*"%>
<%
	//做调试用，这里要在正式发布的时候去掉
	System.out.println("["+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date())+"]=======================调试："+request.getServletPath()+"开始==============================");
%>
<%
	String id=request.getParameter("id");
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
										<a href="" class="reload"> </a>
										<a href="" class="remove"> </a>
									</div>
								</div>
								<div class="portlet-body form">
									<form class="form-horizontal" role="form" id="page_form" name="page_form" action="#">
										<input type="hidden" id="record_id" name="record_id" value="<%=id %>"/>
										<input type="hidden" id="action" name="action" value="modify_record"/>
										<div class="form-actions right1">
											<div style="float:left;margin-bottom:10px;margin-right:10px;">
												<button type="button" id="return_button" class="btn green">
													返回
												</button>
												<button type="button" id="upload_button" class="btn green" title="上传项目的文档">
													我要上传
												</button>
												<button type="button" id="document_button" class="btn green" title="查看该项目的项目文档">
													查看文档
												</button>
												<button type="button" id="apply_button" class="btn green" title="申请加入该项目">
													申请加入
												</button>
												<button type="button" id="submit_button" class="btn green" title="修改项目信息">
													提交修改
												</button>
											</div>
											<div style="float:left;margin-bottom:10px;margin-right:10px;">
												<button type="button" id="weekly_button" class="btn green" title="编写该项目的项目周报">
													编写周报
												</button>
												<button type="button" id="diary_button" class="btn green" title="编写该项目的项目日志">
													编写日志
												</button>
												<button type="button" id="diarylist_button" class="btn green" title="查看该项目的项目日志">
													查看日志
												</button>
											</div>
										</div>
										<div class="form-body">
											<div class="form-group">
												<label class="col-md-3 control-label">项目ID<font color="red">*</font></label>
												<div class="col-md-9">
													<input type="text" id="project_id" name="project_id" class="form-control" placeholder="请填写项目ID" value="" readonly="readonly"/>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-3 control-label">项目名称（必填）<font color="red">*</font></label>
												<div class="col-md-9">
													<input type="text" id="project_name" name="project_name" class="form-control" placeholder="请填写项目名称" value="" readonly="readonly"/>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-3 control-label">简称</label>
												<div class="col-md-9">
													<input type="text" id="project_nick" name="project_nick" class="form-control" placeholder="请输入项目昵称" value="" readonly="readonly"/>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-3 control-label">描述</label>
												<div class="col-md-9">
													<textarea id="project_describe" name="project_describe" class="form-control" rows="10" placeholder="请输入项目描述" value="" readonly="readonly"></textarea>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-3 control-label">项目级别</label>
												<div class="col-md-9">
													<select class="table-group-action-input form-control input-medium" id="project_class" name="project_class" disabled="disabled">
														<option value="small">10万元以下</option>
														<option value="medium">10万元-100万元</option>
														<option value="large">100万元-1000万元</option>
														<option value="super_large">1000万以上</option>
													</select>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-3 control-label">项目来源</label>
												<div class="col-md-9">
													<select class="table-group-action-input form-control input-medium" id="project_source" name="project_source" disabled="disabled">
														<option value="self">公司自拟研发</option>
														<option value="client">客户合同项目</option>
													</select>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-3 control-label">项目经理</label>
												<div class="col-md-9">
													<input type="text" id="project_manager_name" name="project_manager_name" class="form-control" placeholder="请输入项目经理" value="" readonly="readonly"/>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-3 control-label">计划经费</label>
												<div class="col-md-9">
													<input type="text" id="cost" name="cost" class="form-control" placeholder="请输入项目预算经费" value="" readonly="readonly"/>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-3 control-label">开始时间</label>
												<div class="col-md-9">
													<input type="text" id="start_time" name="start_time" class="form-control" placeholder="请输入项目开始时间" value="2016-10-20 00:00:00" readonly="readonly"/>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-3 control-label">结束时间</label>
												<div class="col-md-9">
													<input type="text" id="end_time" name="end_time" class="form-control" placeholder="请输入项目结束时间" value="2017-10-20 00:00:00" readonly="readonly"/>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-3 control-label">公开级别</label>
												<div class="col-md-9">
													<select class="table-group-action-input form-control input-medium" id="open_level" name="open_level">
														<option value="project_inner">项目组内部</option>
														<option value="class_inner">班级内部</option>
														<option value="school_inner">学校</option>
														<option value="all">所有人可见</option>
													</select>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-3 control-label">附件</label>
												<div class="col-md-9">
													<input type="text" id="attachment_name" name="attachment_name" class="form-control" placeholder="请输入项目附件文件名" value=""/>
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
<script src="../../assets/module/scripts/project/file/project_view.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->