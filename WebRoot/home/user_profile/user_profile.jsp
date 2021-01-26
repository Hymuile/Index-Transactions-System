<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.*"%>
<%
	//做调试用，这里要在正式发布的时候去掉
	System.out.println("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date())
			+ "]=======================调试：" + request.getServletPath() + "开始==============================");
%>
<!DOCTYPE html>
<head>
<title>期货交易系统-用户个人信息</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="../../home/frame/frame_style.jsp"%>
</head>
<body
	class="page-header-fixed page-quick-sidebar-over-content page-sidebar-closed-hide-logo page-container-bg-solid">
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
				<div class="page-bar" id="page_header_div">
					<ul class="page-breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="../../home/main/index.html">首页</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">个人信息</a></li>
					</ul>
				</div>
				<!-- BEGIN PAGE CONTENT-->
				<div class="row margin-top-20">
					<div class="col-md-12">
						<!-- BEGIN PROFILE CONTENT -->
						<div class="profile-content">
							<div class="row">
								<div class="col-md-12">
									<div class="portlet light">
										<div class="portlet-title tabbable-line">
											<div class="caption caption-md">
												<i class="icon-globe theme-font hide"></i> <span
													class="caption-subject font-blue-madison bold uppercase">用户信息</span>
											</div>
											<ul class="nav nav-tabs">
												<li class="active"><a href="#tab_1_1" data-toggle="tab">个人信息</a></li>
											 	<!-- <li><a href="#tab_1_2" data-toggle="tab">更换头像</a></li>  -->
												<li><a href="#tab_1_3" data-toggle="tab">修改密码</a></li>
												<!-- <li><a href="#tab_1_4" data-toggle="tab">余额充值</a></li> -->
											</ul>
										</div>
										<div class="portlet-body">
											<div class="tab-content">
												<!-- 个人信息 -->
												<div class="tab-pane active" id="tab_1_1">
													<form role="form">
														<div class="form-group">
															<label class="control-label">用户名</label> <input
																type="text" placeholder="请输入用户名，不可为空"
																class="form-control" id="user_name" />
														</div>
														<div class="form-group">
															<label class="control-label">用户所在地</label> <input
																type="text" placeholder="城市名" class="form-control"
																id="user_city" />
														</div>
														<div class="margin-top-10">
															<button type="button" class="btn btn-success uppercase"
																id="user_info_btn">保存</button>
															<a href="../main/index.jsp" class="btn default">返回 </a>
														</div>
													</form>
												</div>
												<!-- END 个人信息 -->
												<!-- 修改头像 -->
												<div class="tab-pane" id="tab_1_2">
													<form action="#" role="form">
														<div class="form-group">
															<div class="fileinput fileinput-new"
																data-provides="fileinput">
																<div class="fileinput-new thumbnail"
																	style="width: 200px; height: 150px;"></div>
																<div
																	class="fileinput-preview fileinput-exists thumbnail"
																	style="max-width: 200px; max-height: 150px;"></div>
																<div>
																	<span class="btn default btn-file"> <span
																		class="fileinput-new"> 选择图片 </span> <span
																		class="fileinput-exists"> 更改 </span> <input
																		type="file" name="...">
																	</span> <a href="javascript:;"
																		class="btn default fileinput-exists"
																		data-dismiss="fileinput"> 移除 </a>
																</div>
															</div>
															<div class="clearfix margin-top-10">
																<span class="label label-danger">注意! </span> 
																<span>附件图片缩略图只支持最新的Firefox,Chrome,Opera, Safari </span>
															</div>
														</div>
														<div class="margin-top-10">
															<button type="button" class="btn btn-success uppercase" id="avatar_btn">保存</button>
															<a href="../main/index.jsp" class="btn default"> 返回 </a>
														</div>
													</form>
												</div>
												<!-- END 修改头像 -->
												<!-- 修改密码 -->
												<div class="tab-pane" id="tab_1_3">
													<form>
														<div class="form-group">
															<label class="control-label">新密码</label> <input
																type="password" class="form-control" id="password" />
														</div>
														<div class="form-group">
															<label class="control-label">确认新密码</label> <input
																type="password" class="form-control" id="password_again" />
														</div>
														<div class="margin-top-10">
															<button type="button" class="btn btn-success uppercase"
																id="pwd_btn">保存</button>
															<a href="../main/index.jsp" class="btn default">返回 </a>
														</div>

													</form>
												</div>
												<!-- END 修改密码 -->
												<!-- 余额充值 -->
												<div class="tab-pane" id="tab_1_4">
													<form>
														<div class="form-group">
															<label class="control-label">您要充值的额度是</label> <input
																type="text" class="form-control" id="recharge_balance" />
														</div>
														<!--end profile-settings-->

														<div class="form-actions">
															<button type="button" class="btn btn-success uppercase"
																id="balance_btn">提交</button>
															<a href="../main/index.jsp" class="btn default"> 返回</a>
														</div>

													</form>
												</div>

												<!-- END 余额充值 -->
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- END PAGE CONTENT-->
		</div>
		<!-- END CONTENT -->
	</div>
	<!-- END CONTAINER -->
	<%@include file="../../home/frame/frame_bottom.jsp"%>
	<%@include file="../../home/frame/frame_ajax_modal.jsp"%>
	<script src="../../assets/module/scripts/home/user_profile.js"
		type="text/javascript"></script>
</body>
</html>
