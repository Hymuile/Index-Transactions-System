jQuery(document).ready(function() {
	console.log("执行js初始化开始");
	Metronic.init(); // init metronic core componets
	Layout.init(); // init layout
	Demo.init(); // init demo features
	
	initHomeMenu();
	Page.init();

	console.log("执行js初始化结束");
});
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

function addWeatherInfo(city){
	$.post("../../weather_back_action?action=get_record&cityName="+city,function(e){processWeatherInfo(e)});
}

function processWeatherInfo(data){
	var json=eval("("+data+")");
	if(json.result_code == 0){
		var city= json.city;
		var obs_time = json.obs_time;
		var temp = json.temp;
		var icon = json.icon;
		var text = json.text;
		var wind_dir = json.wind_dir;
		var wind_scale = json.wind_scale;
		var wind_speed = json.wind_speed;
		var vis = json.vis;
		var create_time = json.create_time;

		addweatherInfoDiv(city,temp,text,wind_dir,wind_scale,wind_speed,vis);
		Frame.init("home","main");
	}else{
		Page.processError(json);
	}
}

function addweatherInfoDiv(city,temp,text,wind_dir,wind_scale,wind_speed,vis){
	var html="<div class=\"row margin-top-20\" style=\"margin-left:10vmin;width:40%;float:left;\"><div class=\"col-md-6\"><div class=\"profile-sidebar\">";
	html=html+"<div class=\"portlet light profile-sidebar-portlet\"><div class=\"profile-userpic\"></div><div class=\"profile-usertitle\">";
	html=html+"<div class=\"profile-usertitle-name\">"+city+"</div><div class=\"profile-usertitle-job\">"+text+"</div></div>";
	html=html+"<div class=\"profile-usermenu\"><ul class=\"nav\"><li><a href=\"javascript:return false;\"><i class=\"fa fa-sort-numeric-desc\"></i>温度:"+temp+" ℃</a></li>";
	html=html+"<li><a href=\"javascript:return false;\"><i class=\"fa fa-location-arrow\"></i>风向："+wind_dir+" </a></li>";
	html=html+"<li><a href=\"javascript:return false;\"><i class=\"fa fa-bullseye\"></i>风力:"+wind_scale+" 级</a></li>";
	html=html+"<li><a href=\"javascript:return false;\"><i class=\"fa fa-space-shuttle\"></i>风速:"+wind_speed+" 米/秒</a></li>";
	jQuery("#home_content_div").append(html);
}

function processUserInfo(data){
	var json=eval("("+data+")");
	if(json.result_code == 0){
		var user_name= json.user_name;
		var user_role;
		if(json.user_role == "normal")
			user_role="普通用户";
		else
			user_role="管理员";
		var user_email = json.user_email;
		var user_margin = json.user_margin;
		var user_balance = json.user_balance;
		var user_city = json.user_city;
		addWeatherInfo(user_city);
		addUserInfoDiv(user_name,user_role,user_email,user_margin,user_balance);
		Frame.init("home","main");
	}else{
		Page.processError(json);
	}
}
function addUserInfoDiv(user_name,user_role,user_email,user_margin,user_balance){
	var html="<div class=\"row margin-top-20\" style=\"margin-left:1vmin;width:50%;float:left;\"><div class=\"col-md-6\"><div class=\"profile-sidebar\">";
	html=html+"<div class=\"portlet light profile-sidebar-portlet\"><div class=\"profile-userpic\"></div><div class=\"profile-usertitle\">";
	html=html+"<div class=\"profile-usertitle-name\">"+user_name+"</div><div class=\"profile-usertitle-job\">"+user_role+"</div></div>";
	html=html+"<div class=\"profile-usermenu\"><ul class=\"nav\"><li><a href=\"javascript:return false;\"><i class=\"icon-home\"></i>邮箱:"+user_email+"</a></li>";
	html=html+"<li><a href=\"../user_profile/user_profile.jsp\"><i class=\"icon-settings\"></i>个人信息</a></li>";
	html=html+"<li><a href=\"javascript:return false;\"><i class=\"icon-check\"></i>预付款:"+user_margin+" </a></li>";
	html=html+"<li><a href=\"javascript:return false;\"><i class=\"icon-info\"></i>余额: "+user_balance+"</a></li></ul></div></div></div></div></div>";
	jQuery("#home_content_div").append(html);
}


var Page = function() {
	var html="";
	var initPageStyle = function() {
		//隐藏page header
		//$("#page_header_div").hide();
		//隐藏提醒图标
		$("#header_inbox_bar").hide();
		$("#header_calendar_bar").hide();
	}
	var processError=function(json){
		if(json.result_code==1){}
		if(json.result_code==2){}
		if(json.result_code==3){
			//session超时了
			//alert("长时间没有操作导致系统需要重新登陆，请您前往登陆界面登陆。");
			//window.location.href="../../home/main/login.jsp";
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
