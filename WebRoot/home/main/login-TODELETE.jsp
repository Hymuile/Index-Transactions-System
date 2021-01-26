<%@page contentType="text/html; charset=UTF-8"
	import="java.sql.*,java.text.*"%>
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
<!DOCTYPE html>

<html lang="en">
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>欢迎登录</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description" />
<meta content="" name="author" />
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="../../assets/module/css/fonts.css" rel="stylesheet"
	type="text/css" />
<link
	href="../../assets/global/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="../../assets/global/plugins/simple-line-icons/simple-line-icons.min.css"
	rel="stylesheet" type="text/css" />
<link href="../../assets/global/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link href="../../assets/global/plugins/uniform/css/uniform.default.css"
	rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<!-- END PAGE LEVEL SCRIPTS -->
<!-- BEGIN THEME STYLES -->
<link href="../../assets/global/css/components.css"
	id="style_components" rel="stylesheet" type="text/css" />
<link href="../../assets/global/css/plugins.css" rel="stylesheet"
	type="text/css" />
<link href="../../assets/admin/layout/css/layout.css" rel="stylesheet"
	type="text/css" />
<link href="../../assets/module/css/home/login.css" rel="stylesheet"
	type="text/css" />
<link href="../../assets/admin/layout/css/themes/darkblue.css"
	rel="stylesheet" type="text/css" id="style_color" />
<link href="../../assets/admin/layout/css/custom.css" rel="stylesheet"
	type="text/css" />
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="favicon.ico" />
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="login">
	<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
	<div class="menu-toggler sidebar-toggler"></div>
	<!-- END SIDEBAR TOGGLER BUTTON -->
	<!-- BEGIN LOGO -->
	<div class="logo">
		<h3 style="color:#AD0">欢迎登录XM-09期货交易系统</h3>
	</div>
	<!-- END LOGO -->
	<!-- BEGIN LOGIN -->
	<div class="content">

		<!-- 登录 below -->
		<form class="login-form" action="../../Login" method="post">
			<input type="hidden" id="open_id" name="open_id" value="<%=openId%>" />
			<h3 class="form-title">登录</h3>
			<div class="alert alert-danger display-hide">
				<button class="close" data-close="alert"></button>
				<span class="help-block"></span>
				<span>请输入邮箱和密码登陆</span>
			</div>
			
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9"> 用户邮箱：</label>
				<input class="form-control form-control-solid placeholder-no-fix"
					type="text" autocomplete="off" placeholder="请输入邮箱" id="user_id"
					name="user_id" />
			</div>
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9"> 登陆密码：</label>
				<input class="form-control form-control-solid placeholder-no-fix"
					type="password" autocomplete="off" placeholder="请输入密码"
					id="user_password" name="user_password" />
			</div>
			<div class="form-actions">
				<button type="submit" class="btn btn-success uppercase" id="login_btn">登陆</button>
				<label class="rememberme check"> <input type="checkbox"
					id="remember" name="remember" value="1" /> 记住我
				</label> <a href="javascript:;" id="forget-password" class="forget-password">忘记密码？</a>
			</div>
			<div class="login-options">
				<h4 style="color:red;">本系统仅支持Chrome浏览器，请用Chrome浏览器访问平台。</h4>
			</div>
			<div class="create-account">
				<p>
					<a href="javascript:;" id="register-btn" class="uppercase">注册新账户</a>
				</p>
			</div>
			
		</form>
		<!-- 登录 above -->

		<!-- 忘记密码 below -->
		<form class="forget-form">
			<h3>忘记密码？</h3>
			<p>请输入您的邮箱</p>
			<div class="form-group">
				<input class="form-control placeholder-no-fix" type="text"
					autocomplete="off" placeholder="请输入邮箱" id="forget_email"
					name="forget_email" />
			</div>
			<div class="form-group">
				<button type="button" id="forget-verify-btn"
					class="btn btn-success uppercase pull-right">发送验证码</button>
				<br>
				<br> <label class="control-label visible-ie8 visible-ie9">
					验证码 </label> <input class="form-control placeholder-no-fix" type="text"
					placeholder="输入验证码" name="forget-verify" id="forget-verify" />
			</div>
			<span id="forget_code" style="display:none"></span>
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9"> 新密码 </label> <input
					class="form-control placeholder-no-fix" type="password"
					autocomplete="off" id="forget_password" placeholder="新密码"
					name="forget_password" id="forget_password" />
			</div>
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9"> 再次输入新密码
				</label> <input class="form-control placeholder-no-fix" type="password"
					autocomplete="off" placeholder="再次输入新密码" id="forget_password_again"
					name="forget_password_again" />
			</div>
			<div class="form-actions">
				<button type="button" id="back-btn" class="btn btn-default">返回</button>
				<button type="button" id="forget-submit-btn"
					class="btn btn-success uppercase pull-right">提交</button>
			</div>

		</form>
		<!-- 忘记密码 above -->
		
		<!-- 注册 below -->
		<form class="register-form" id="register-form">
			<h3>注册</h3>
			<p class="hint">请输入您的账户信息（必填）</p>
			<div class="container" style="font-color:#F00"></div>
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9"> 用户名 </label> <input
					class="form-control placeholder-no-fix" type="text"
					placeholder="用户账号，建议中文、字母、数字及下划线" id="register_user_id"
					name="register_user_id" />
			</div>
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9"> 密码 </label> <input
					class="form-control placeholder-no-fix" type="password"
					autocomplete="off" id="register_password" placeholder="密码"
					name="register_password" id="register_password" />
			</div>
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9"> 再次输入密码
				</label> <input class="form-control placeholder-no-fix" type="password"
					autocomplete="off" placeholder="再次输入密码"
					id="register_password_again" name="register_password_again" />
			</div>
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9"> 邮箱 </label> <input
					class="form-control placeholder-no-fix" type="text"
					placeholder="输入邮箱，忘记密码时系统可凭邮箱恢复密码" name="register-email"
					id="register-email" />
			</div>
			<div class="form-group">
				<button type="button" id="register-verify-btn"
					class="btn btn-success uppercase pull-right">发送验证码</button>
				<br>
				<br> <label class="control-label visible-ie8 visible-ie9">
					验证码 </label> <input class="form-control placeholder-no-fix" type="text"
					placeholder="输入验证码" name="register-verify" id="register-verify" />
			</div>
			<span id="verify_code" style="display:none"></span>
			<div class="form-actions">
				<button type="button" id="register-back-btn" class="btn btn-default">返回</button>
				<button type="button" id="register_submit_btn"
					class="btn btn-success uppercase pull-right">提交</button>
			</div>
		</form>
		<!-- 注册 above -->

	</div>
	<div class="copyright">2020 © XM-09. 期货交易系统 Co.Ltd.</div>
	<!-- END LOGIN -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<script src="../../assets/global/plugins/jquery.min.js"
		type="text/javascript"></script>
	<script src="../../assets/global/plugins/jquery-migrate.min.js"
		type="text/javascript"></script>
	<script src="../../assets/global/plugins/bootstrap/js/bootstrap.min.js"
		type="text/javascript"></script>
	<script src="../../assets/global/plugins/jquery.blockui.min.js"
		type="text/javascript"></script>
	<script src="../../assets/global/plugins/jquery.cokie.min.js"
		type="text/javascript"></script>
	<script src="../../assets/global/plugins/uniform/jquery.uniform.min.js"
		type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script
		src="../../assets/global/plugins/jquery-validation/js/jquery.validate.min.js"
		type="text/javascript"></script>
	<script
		src="../../assets/module/plugins/jquery-qrcode-master/jquery.qrcode.min.js"
		type="text/javascript"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="../../assets/global/scripts/metronic.js"
		type="text/javascript"></script>
	<script src="../../assets/admin/layout/scripts/layout.js"
		type="text/javascript"></script>
	<script src="../../assets/admin/layout/scripts/demo.js"
		type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<script src="../../assets/module/scripts/home/login.js"
		type="text/javascript"></script>
</body>
<!-- END BODY -->
</html>

