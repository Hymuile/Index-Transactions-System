$(document).ready(function(){
	checkRemember();
	Page.init();
});


var checkRemember =function(){
	// Remember checkbox
	if($('.chk-remember').length){
		$('.chk-remember').iCheck({
			checkboxClass: 'icheckbox_square-blue',
			radioClass: 'iradio_square-blue',
		});
	}
}

var Page = function(){
	var handleLogin = function(){
		$("#login_btn").click(function(){
			var user_id = $("#user-name").val();
			var user_pwd = $("#user-password").val();
			if(user_id!="" && user_pwd!=""){
				$.ajax({
					type:"POST",
					url:"../../security_user_data_action",
					data:{
						action:"login",
						userId:user_id,
						userPwd:user_pwd
					},
					success:function(data){
						if(200!=data.result_code){
							alert(data.result_msg);
						}else{
							location.href="../../home/main/index.jsp";
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert("登录失败");
					}
				});
			}else{
				alert("账号或密码为空！");
			}
		});
	}

	var handleRegister = function(){
		$("#sendVerifyCode").click(function() {
			var register_email = $("#user-email").val();
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
							email : $("#user-email").val(),
						},
						success : function(data) {
							alert("验证码已发送，请注意查收。");
						},
					});
				}
			}
		}
		);

		$("#register_btn").click(function(){
			var user_name = $("#user-name").val();
			if(user_name != ""){
				var rege=new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5]+$");
				if(rege.test(user_name)){
					var user_pwd = $("#user-password").val();
					var user_pwd_again = $("#user-password-again").val();
					if(user_pwd.length<6||user_pwd.length>20){
						alert("密码的长度为6至20位！");
					}else{
						if(user_pwd!=user_pwd_again){
							alert("两次输入密码不一致");
						}else{
							if($("#user-email").val()){
								$.ajax({
									type:"POST",
									url:"../../security_user_data_action",
									data:{
										action:"register",
										user_name:  $("#user-name").val(),
										user_email : $("#user-email").val(),
										user_password:$("#user-password").val(),
										verify_code:$("#verify-code").val()
									},
									success:function(data){
										if(data.result_code==300){
											alert(data.result_msg);
										}else{
											alert("注册成功");
											window.location="login.jsp";
										}
									},
								});
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
		});
	}

	var handleRecoverPwd = function(){
		$("#recover_btn").click(function(){
			if($("#user-email").val()&&$("#verify-code").val()){
				var user_pwd = $("#user-password").val();
				var user_pwd_again = $("#user-password-again").val();
				if(user_pwd.length<6||user_pwd.length>20){
					alert("密码的长度为6至20位！");
				}else{
					if(user_pwd!=user_pwd_again){
						alert("两次输入密码不一致");
					}else{
						$.ajax({
							type:"POST",
							url:"../../security_user_data_action",
							data:{
								action:"recover_pwd",
								userPwd:user_pwd,
								userPwdAgain:user_pwd_again,
								userEmail:$("#user-email").val(),
								verifyCode:$("#verify-code").val()
							},
							success:function(e){
								alert(e.result_msg);
							}
						});
					}
				}
			}
		});
	}

	return {
		init: function() {
			handleLogin();
			handleRegister();
			handleRecoverPwd();
		}

	};

}();
