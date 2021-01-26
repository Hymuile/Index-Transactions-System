<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//做调试用，这里要在正式发布的时候去掉
System.out.println("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date())
			+ "]=======================调试：" + request.getServletPath() + "开始==============================");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html class="loading" lang="en" data-textdirection="ltr">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
  <meta name="author" content="PIXINVENT">
  <title>期货交易系统-注册</title>
  <link rel="apple-touch-icon" href="../../app-assets/images/ico/apple-icon-120.png">
  <link rel="shortcut icon" type="image/x-icon" href="../../app-assets/images/ico/favicon.ico">
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Quicksand:300,400,500,700"
  rel="stylesheet">
  <link href="https://maxcdn.icons8.com/fonts/line-awesome/1.1/css/line-awesome.min.css"
  rel="stylesheet">
  <!-- BEGIN VENDOR CSS-->
  <link rel="stylesheet" type="text/css" href="../../app-assets/css/vendors.css">
  <!-- END VENDOR CSS-->
  <!-- BEGIN MODERN CSS-->
  <link rel="stylesheet" type="text/css" href="../../app-assets/css/app.css">
  <!-- END MODERN CSS-->
  <!-- BEGIN Page Level CSS-->
  <link rel="stylesheet" type="text/css" href="../../app-assets/css/core/menu/menu-types/vertical-menu.css">
  <link rel="stylesheet" type="text/css" href="../../app-assets/css/core/colors/palette-gradient.css">
  <link rel="stylesheet" type="text/css" href="../../app-assets/css/pages/login-register.css">
  <!-- END Page Level CSS-->
  <!-- BEGIN Custom CSS-->
  <link rel="stylesheet" type="text/css" href="../../assets/css/style.css">
  <!-- END Custom CSS-->
</head>
<body class="vertical-layout vertical-menu 1-column   menu-expanded blank-page blank-page"
data-open="click" data-menu="vertical-menu" data-col="1-column">
  <!-- ////////////////////////////////////////////////////////////////////////////-->
  <div class="app-content content">
    <div class="content-wrapper">
      <div class="content-header row">
      </div>
      <div class="content-body">
        <section class="flexbox-container">
          <div class="col-12 d-flex align-items-center justify-content-center">
            <div class="col-md-4 col-10 box-shadow-2 p-0">
              <div class="card border-grey border-lighten-3 px-2 py-2 m-0">
                <div class="card-header border-0">
                  <div class="card-title text-center">
                    	期货交易系统
                  </div>
                  <h6 class="card-subtitle line-on-side text-muted text-center font-small-3 pt-2">
                    <span>注册账号</span>
                  </h6>
                </div>
                <div class="card-content">
                  <div class="card-body">
                    <form class="form-horizontal form-simple">
                      <fieldset class="form-group position-relative has-icon-left mb-1">
                        <input type="text" class="form-control form-control-lg input-lg" id="user-name" placeholder="请输入用户名">
                        <div class="form-control-position">
                          <i class="ft-user"></i>
                        </div>
                      </fieldset>
                     
                      <fieldset class="form-group position-relative has-icon-left">
                        <input type="password" class="form-control form-control-lg input-lg" id="user-password" placeholder="请输入密码">
                        <div class="form-control-position">
                          <i class="la la-key"></i>
                        </div>
                      </fieldset>
                      <fieldset class="form-group position-relative has-icon-left">
                        <input type="password" class="form-control form-control-lg input-lg" id="user-password-again" placeholder="请再次输入确认密码">
                        <div class="form-control-position">
                          <i class="la la-key"></i>
                        </div>
                      </fieldset>
                       <fieldset class="form-group position-relative has-icon-left mb-1">
                        <input type="email" class="form-control form-control-lg input-lg" id="user-email"
                        placeholder="请输入有效的邮箱地址">
                        <div class="form-control-position">
                          <i class="ft-mail"></i>
                        </div>
                      </fieldset>
                      <button type="button" class="btn btn-primary btn-min-width box-shadow-1 mr-1 mb-1" id="sendVerifyCode">发送验证码</button>
                      <input type="verify-code" class="form-control form-control-lg input-lg" style="float:left;width:50%;margin-right:3em;" id="verify-code" placeholder="请输入验证码">
                      <button type="button" class="btn btn-info btn-lg btn-block" id="register_btn"><i class="ft-unlock"></i> 注册</button>
                    </form>
                  </div>
                  <p class="text-center">已有账号 ？ <a href="login.jsp" class="card-link">登录</a></p>
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>
    </div>
  </div>
  <!-- ////////////////////////////////////////////////////////////////////////////-->
  <script src="../../app-assets/vendors/js/vendors.min.js" type="text/javascript"></script>
  <script src="../../app-assets/vendors/js/forms/validation/jqBootstrapValidation.js" type="text/javascript"></script>
  <script src="../../app-assets/js/core/app-menu.js" type="text/javascript"></script>
  <script src="../../app-assets/js/core/app.js" type="text/javascript"></script>
  <script src="../../app-assets/js/scripts/forms/form-login-register.js" type="text/javascript"></script>
</body>
</html>