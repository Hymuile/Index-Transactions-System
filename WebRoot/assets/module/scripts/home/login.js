jQuery(document).ready(function() {
	Metronic.init(); // init metronic core componets
	Layout.init(); // init layout
	Demo.init(); // init demo features
	Login.init();
	Page.init();
});


var Login = function() {

	var handleLogin = function() {
		$('.login-form #remember').click(function() {
			if(this.checked){
				if(($("#user_id").val()=="") || ($("#user_password").val()=="")){
					alert("请您输入账号和密码！");
				}else{
					//记录进cookie里
					var userId=$("#user_id").val();
					var passwd=$("#user_password").val();
					setCookie("username",userId,30);
					setCookie("password",passwd,30);
					setCookie("remember",true,30);
				}
			}else{
				clearCookie("username");
				clearCookie("password");
				clearCookie("remember");
			}
		});
		var clearCookie=function(name) {
			setCookie(name, "", -1);
		}
		var getCookie=function(c_name){
			if (document.cookie.length>0)
			{
				c_start=document.cookie.indexOf(c_name + "=")
				if (c_start!=-1)
				{
					c_start=c_start + c_name.length+1 
					c_end=document.cookie.indexOf(";",c_start)
					if (c_end==-1) c_end=document.cookie.length
					return unescape(document.cookie.substring(c_start,c_end))
				}
			}
			return ""
		};
		var setCookie=function(c_name,value,expiredays){
			var exdate=new Date();
			exdate.setDate(exdate.getDate()+expiredays);
			document.cookie=c_name+ "=" +escape(value)+
			((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
		}
		var checkCookie=function(){
			var username=getCookie('username');
			var passwd=getCookie('password');
			var remember=getCookie('remember');
			if(remember){
				$('#uniform-remember span').addClass("checked");
				if (username!=null && username!="")
				{$("#user_id").val(username);}else{}
				if (passwd!=null && passwd!="")
				{$("#user_password").val(passwd);}else{}
			}
		};
		checkCookie();
	}

	var handleForgetPassword = function() {
		$("#forget-verify-btn").click(function(){
			var forget_email = $("#forget_email").val();
			if(forget_email == ""){
				alert("请先填写邮箱");
			}else{
				var re = new RegExp("^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$");
				if (!re.test(forget_email)) {
					alert("邮箱格式不正确！\n");
				}else{
					$.ajax({
						type:"POST",
						url:"../../SendEmailServlet",
						data:{
							email:$("#forget_email").val(),
						},
						success:function(data){
							var jsonObj= eval('(' + data + ')'); 
							document.getElementById("forget_code").value=jsonObj.code;
							var forget_code=$("#forget_code").val();
							alert("验证码已发送，请注意查收。");
						}
					})
				}
			}
		}
		);

		$("#forget-submit-btn").click(function(){
			var forget_email = $("#forget_email").val();
			if(forget_email == ""){
				alert("请先填写邮箱");
			}else{
				var forget_pwd = $("#forget_password").val();
				var forget_pwd_again = $("#forget_password_again").val();
				if(forget_pwd.length<6||forget_pwd.length>20){
					alert("新密码长度为6至20位");
				}else{
					if(forget_pwd != forget_pwd_again){
						alert("两次输入密码不一致");
					}else{
						var forget_code = $("#forget-verify").val();
						var code = $("#forget_code").val();	
						if (forget_code == code){
							$.ajax({
								type : "POST",
								url : "../../ForgetPwdServlet",
								data : {
									forget_email : $("#forget_email").val(),
									forget_password : $("#forget_password").val(),
								},
								success : function(data) {
									var jsonObj= eval('(' + data + ')'); 
									alert(jsonObj.message);
									if(jsonObj.message == "修改密码成功"){
										window.location = "login.jsp";
									}
								},
								error : function(XMLHttpRequest, textStatus, errorThrown) {
									alert("修改密码失败");
								}
							});
						}else{
							alert("验证码输入错误");
						}
					}
				}
			}
		}
		);

		
		jQuery('#forget-password').click(function() {
			jQuery('.login-form').hide();
			jQuery('.forget-form').show();
		});

		jQuery('#back-btn').click(function() {
			jQuery('.login-form').show();
			jQuery('.forget-form').hide();
		});
	};

	var handleRegister = function() {
		$("#register-verify-btn").click(function() {
			var register_email = $("#register-email").val();
			if(register_email == ""){
				alert("请先填写邮箱");
			}else{
				var re = new RegExp("^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$");
				if (!re.test(register_email)) {
					alert("邮箱格式不正确！\n");
				}else{
					$.ajax({
						type : "POST",
						url : "../../SendEmailServlet",
						data : {
							email : $("#register-email").val(),
						},
						success : function(data) {
							var jsonObj= eval('(' + data + ')'); 
							document.getElementById("verify_code").value=jsonObj.code;
							var verify_code=$("#verify_code").val();
							alert("验证码已发送，请注意查收。");
						},
					});
				}
			}
		}
		);

		$("#register_submit_btn").click(function(){
			var user_name = $("#register_user_id").val();
			if($("#register_user_id").val() != ""){
				var rege=new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5]+$");
				if(rege.test(user_name)){
					var user_pwd = $("#register_password").val();
					var user_pwd_again = $("#register_password_again").val();
					if(user_pwd.length<6||user_pwd.length>20){
						alert("密码的长度为6至20位！");
					}else{
						if(user_pwd!=user_pwd_again){
							alert("两次输入密码不一致");
						}else{
							var user_email=$("#register-email").val();
							if(user_email){
								var verify = $("#register-verify").val();
								if(verify == $("#verify_code").val()){
									$.ajax({
										type:"POST",
										url:"../../RegisterServlet",
										data:{
											register_user_id:  $("#register_user_id").val(),
											register_email : $("#register-email").val(),
											register_password:$("#register_password").val(),
										},
										success:function(data){
											var jsonObj= eval('(' + data + ')');
											alert(jsonObj.message);
											if(jsonObj.message == "注册成功"){
												window.location="login.jsp";
											}
										},
									});
								}else{
									alert("验证码错误");
								}
							}else{
								alert("请填写邮箱");
							}
						}
					}
					
				}else{
					alert("用户名只能包含汉字、字母、数字及下划线");
				}	
			}else{
				alert("请先填写用户名");
			}
		}
		);

		jQuery('#register-btn').click(function() {
			jQuery('.login-form').hide();
			jQuery('.register-form').show();
		});

		jQuery('#register-back-btn').click(function() {
			jQuery('.login-form').show();
			jQuery('.register-form').hide();
		});
		//二维码扫码登陆部分
		var getStatusTimer;
		$('#scan-qrcode-btn').click(function(){
			jQuery('.login-form').hide();
			jQuery('.qrcode-form').show();
			showQrCode();
		});
		$('#qrcode-back-btn').click(function(){
			jQuery('.login-form').show();
			jQuery('#qrcode-form').hide();
			clearTimeout(getStatusTimer);
		});
		var showQrCode=function(){
			//然后显示二维码
			$.post("../../security_user_data_action?action=create_wechat_qrcode_auth&module=teach",function(data){
				var json=eval("("+data+")");
				if(json.result_code==0){
					$("#qrcode_image").attr("src","https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+json.ticket);
					//开始定时查询
					console.log("开始getTicketStatus");
					getTicketStatus();
				}else{
					if(Page!=null){
						Page.processError(json);
					}
				}
			});
		}
		var getTicketStatus=function(){
			$.post("../../security_user_data_action?action=get_ticket_status&module=teach",function(data){
				var json=eval("("+data+")");
				if(json.result_code==0){
					Metronic.startPageLoading({message: '登陆成功，正在跳转主界面中，请稍候...'});	//开始等待动画
					window.location.href=json.redirect_url;
				}else{
					if(json.result_code>=10 && json.result_code<=12){
						if(confirm("登陆发生错误："+json.result_msg)){
							window.location.href=json.redirect_url;}else{
								$('#qrcode-back-btn').click();
							}
					}else{
						getStatusTimer=setTimeout(getTicketStatus,2000);
					}
				}
			});
		}
	}
	var setGlobalSessionConfig=function(){
		$.post("../../security_user_data_action?action=set_global_session_config",function(data){
			var json=eval("("+data+")");
			if(json.result_code==0){
			}else{
				alert(json.result_msg);
			}
		});
	}
	return {
		//main function to initiate the module
		init: function() {
			handleLogin();
			handleForgetPassword();
			handleRegister();
			setGlobalSessionConfig();
		}

	};

}();
/* ================================================================================ */
//关于页面的控件生成等操作都放在Page里，和Record独立，Record主要是和记录集交互
var Page = function() {
	var html="";
	var initPageStyle = function() {
	};
	var processError=function(json){
	};
	var help=function(){
		var strUrl=location.pathname;
		window.open('../../help/online/new_win_help.jsp?page_url='+strUrl, 'big', 'fullscreen=yes');
	};
	var serviceRule=function(){
		window.open("");
	};
	var privateRule=function(){

	};
	return {
		init: function() {
			initPageStyle();
		},
		processError:function(json){
			processError(json);
		},
		help:function(){
			help();
		},
		serviceRule:function(){

		},
		privateRule:function(){

		}
	}
}();//Page
