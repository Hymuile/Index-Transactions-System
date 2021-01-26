jQuery(document).ready(function() {
	console.log("执行js初始化开始");
	Metronic.init(); // init metronic core componets
	Layout.init(); // init layout

	Demo.init(); // init demo features
	initHomeMenu();
	Page.init();
	btnClicked();
	console.log("执行js初始化结束");

});

function btnClicked(){
	$("#user_info_btn").click(function(){
		var user_name = $("#user_name").val();
		var rege=new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5]+$");
		if(user_name!=""){
			if(rege.test(user_name)){
				if($("#user_city").val()!=""){
					$.ajax({
						type:"POST",
						url:"../../users_list_servlet_action",
						data:{
							action: "modify_user_info",
							user_name: $("#user_name").val(),
							user_city: $("#user_city").val()
						},
						success:function(e){alert("修改成功");}
					});
				}else{
					alert("城市不允许为空");
				}
			}else{
				alert("用户名仅支持汉字、数字、字母及下划线！");
			}
		}else{
			alert("用户名不能为空");
		}
	});

	$("#pwd_btn").click(function(){
		var current_pwd = $("#password").val();
		var current_pwd_again = $("#password_again").val();
		if(current_pwd.length <6 || current_pwd.length >20){
			alert("新密码长度应为6-20位");
		}else{
			if(current_pwd == current_pwd_again){
				$.ajax({
					type:"POST",
					url:"../../users_list_servlet_action",
					data:{
						action: "modify_user_info",
						user_pwd: $("#password").val()
					},
					success:function(e){alert("修改成功");}
				});
			}else{
				alert("两次密码输入不一致");
			}
		}
	});

	$("#balance_btn").click(function(){
		var recharge = $("#recharge_balance").val();
		if(recharge!=""){
			var reg = /^\d+(\.\d+)?$/;
			if(reg.test(recharge)){
				if(recharge<100000){
					$.ajax({
						type:"POST",
						url:"../../users_list_servlet_action",
						data:{
							action: "modify_user_info",
							user_balance: $("#recharge_balance").val()
						},
						success:function(e){alert("充值成功");}
					});
				}else{
					alert("单次充值额度不能超过10万");
				}
			}else{
				alert("充值额度只能输入数字");
			}
		}else{
			alert("充值额度不能为空");
		}
	});

}

function initHomeMenu(){
	delHomeMenu();
	addUserInfo();
}

function delHomeMenu(){
	jQuery("#home_content_div").empty();
}

function addUserInfo(){
	$.post("../../common_data_action?action=get_user_info",function(e){processUserInfo(e)});
}

function processUserInfo(data){
	var json=eval("("+data+")");
	if(json.result_code == 0){
		var user_name= json.user_name;
		var user_role="普通用户";
		if(json.user_role == "manager")
			user_role="管理员";
		var user_email = json.user_email;
		var user_margin = json.user_margin;
		var user_balance = json.user_balance;
		var user_city= json.user_city;

		$("#user_name").val(user_name);
		$("#user_city").val(user_city);

		Frame.init("home","main");
	}else{
		Page.processError(json);
	}
}


var Page = function() {
	var html="";
	var initPageStyle = function() {
		$("#header_inbox_bar").hide();
		$("#header_calendar_bar").hide();
	}
	var processError=function(json){
		if(json.result_code==1){}
		if(json.result_code==2){}
		if(json.result_code==3){
			//session超时了
			alert("长时间没有操作导致系统需要重新登陆，请您前往登陆界面登陆。");
			window.location.href="../../home/main/login.jsp";
		}
	};
	return {
		init: function() {
			initPageStyle();
		},
		processError:function(json){
			processError(json);
		}
	}
}();
/*================================================================================*/
