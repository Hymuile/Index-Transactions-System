<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//做调试用，这里要在正式发布的时候去掉
System.out.println("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date()) + 
	"]=======================调试：" + request.getServletPath() + "开始==============================");
%>
<%
	String userId = null;
	String userName = null;
	String userAvatar = null;
	String userRole = null;
	if (session.getAttribute("user_role") != null) {
		userRole = (String) session.getAttribute("user_role");
		userAvatar = (String) session.getAttribute("user_avatar");
		userId = (String) session.getAttribute("user_id");
		userName = (String) session.getAttribute("user_name");
	}
	if (userName != null && userId != null && userAvatar != null && userRole != null) {
		response.sendRedirect("index.jsp");
	} else {
	}
	String openId = "";
%>

<html class="loading" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
<meta name="author" content="PIXINVENT">
<title>期货交易系统-登录</title>
<link rel="apple-touch-icon"
	href="../../app-assets/images/ico/apple-icon-120.png">
<link rel="shortcut icon" type="image/x-icon"
	href="../../app-assets/images/ico/favicon.ico">
<link rel="stylesheet" type="text/css"
	href="../../app-assets/css/vendors.css">
<link rel="stylesheet" type="text/css"
	href="../../app-assets/vendors/css/forms/icheck/icheck.css">
<link rel="stylesheet" type="text/css"
	href="../../app-assets/vendors/css/forms/icheck/custom.css">
<link rel="stylesheet" type="text/css"
	href="../../app-assets/css/app.css">
<link rel="stylesheet" type="text/css"
	href="../../app-assets/css/core/menu/menu-types/vertical-menu.css">
<link rel="stylesheet" type="text/css"
	href="../../app-assets/css/core/colors/palette-gradient.css">
<link rel="stylesheet" type="text/css"
	href="../../app-assets/css/pages/login-register.css">
<link rel="stylesheet" type="text/css" href="../../assets/css/style.css">
</head>
<body
	class="vertical-layout vertical-menu 1-column menu-expanded blank-page blank-page"
	data-open="click" data-menu="vertical-menu" data-col="1-column">
	<!-- ////////////////////////////////////////////////////////////////////////////-->
	<div class="app-content content">
		<div class="content-wrapper">
			<div class="content-body">
				<section class="flexbox-container">
					<div
						class="col-12 d-flex align-items-center justify-content-center">
						<div class="col-md-4 col-10 box-shadow-2 p-0">
							<div class="card border-grey border-lighten-3 m-0">
								<div class="card-header border-0">
									<div class="card-title text-center">
										<div class="p-1">期货交易系统</div>
									</div>
									<h6
										class="card-subtitle line-on-side text-muted text-center font-small-3 pt-2">
										<span>欢迎登录</span>
									</h6>
								</div>
								<div class="card-content">
									<div class="card-body">
										<form class="form-horizontal form-simple">
											<fieldset
												class="form-group position-relative has-icon-left mb-0">
												<input type="text"
													class="form-control form-control-lg input-lg"
													id="user-name" placeholder="请输入用户名/邮箱" required>
												<div class="form-control-position">
													<i class="ft-user"></i>
												</div>
											</fieldset>
											<fieldset class="form-group position-relative has-icon-left">
												<input type="password"
													class="form-control form-control-lg input-lg"
													id="user-password" placeholder="请输入密码" required>
												<div class="form-control-position">
													<i class="la la-key"></i>
												</div>
											</fieldset>
											<div class="form-group row">
												<div class="col-md-6 col-12 text-center text-md-left">
													<fieldset>
														<input type="checkbox" id="remember-me"
															class="chk-remember"> <label for="remember-me">
															记住我</label>
													</fieldset>
												</div>
												<div class="col-md-6 col-12 text-center text-md-right">
													<a href="recover-password.jsp" class="card-link">忘记密码？</a>
												</div>
											</div>
											<button type="button" class="btn btn-info btn-lg btn-block" id="login_btn">
												<i class="ft-unlock"></i> 登录
											</button>
										</form>
									</div>
								</div>
								<div class="card-footer">
									<div class="">
										<p class="float-sm-left text-center m-0">
											<a href="recover-password.jsp" class="card-link">找回密码</a>
										</p>
										<p class="float-sm-right text-center m-0">
											新用户？ <a href="register.jsp" class="card-link">注册</a>
										</p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</section>
			</div>
		</div>
	</div>
	<!-- ////////////////////////////////////////////////////////////////////////////-->
	<script src="../../app-assets/vendors/js/vendors.min.js"
		type="text/javascript"></script>
	<script src="../../app-assets/vendors/js/forms/icheck/icheck.min.js"
		type="text/javascript"></script>
	<script
		src="../../app-assets/vendors/js/forms/validation/jqBootstrapValidation.js"
		type="text/javascript"></script>
	<script src="../../app-assets/js/core/app-menu.js"
		type="text/javascript"></script>
	<script src="../../app-assets/js/core/app.js" type="text/javascript"></script>
	<script src="../../app-assets/js/scripts/forms/form-login-register.js"
		type="text/javascript"></script>
</body>
</html>