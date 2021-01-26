<%@page contentType="text/html; charset=UTF-8"%>

<meta charset="utf-8" />
<!-- BEGIN HEADER -->

<div class="page-header navbar navbar-fixed-top" id="page_top_div">
	<!-- BEGIN HEADER INNER -->
	<div class="page-header-inner">
		<!-- BEGIN LOGO -->
		<div class="page-logo">
			<a href="../../home/main/index.jsp">
				<h3 style="color:#AD0">XM09</h3>
			</a>
			<div class="menu-toggler sidebar-toggler hide">
			</div>
		</div>
		<!-- END LOGO -->
		<!-- BEGIN RESPONSIVE MENU TOGGLER -->
		<a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse">
		</a>
		<!-- END RESPONSIVE MENU TOGGLER -->
		<!-- BEGIN TOP NAVIGATION MENU -->
		<div class="top-menu">
			<ul id="left_menu_bar" class="nav navbar-nav pull-right">
				<li class="dropdown dropdown-user">
					<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<img id="current_user_avatar" alt="" class="img-circle" src="../../assets/module/img/security/user/avatar/avatar.jpg"/>
					<span class="username username-hide-on-mobile" id="current_user"></span>
					<i class="fa fa-angle-down"></i>
					</a>
					<ul class="dropdown-menu dropdown-menu-default">
						<li>
							<a href="../../home/main/index.jsp">
							<i class="icon-user"></i>返回主界面</a>
						</li>
						<li class="divider"></li>
						<li>
							<a href="../../home/user_profile/user_profile.jsp">
							<i class="icon-user"></i>个人中心</a>
						</li>
						<li class="divider">
						</li>
						<li>
							<a href="../../home/main/logout.jsp">
							<i class="icon-key"></i> 退出系统</a>
						</li>
					</ul>
				</li>
				<!-- END USER LOGIN DROPDOWN -->
			</ul>
		</div>
		<!-- END TOP NAVIGATION MENU -->
	</div>
	<!-- END HEADER INNER -->
</div>
<!-- END HEADER -->
<div class="clearfix"></div>
<script type="text/javascript" src="../../assets/global/plugins/jquery.min.js"></script>
<script type="text/javascript" src="../../assets/global/plugins/jquery-migrate.min.js"></script>
<script>
$.post("../../common_data_action?action=get_user_info",function(e){addName(e)});
function addName(json){
	var json=eval("("+json+")");
	var user_name= json.user_name;
	var html="<span class=\"username username-hide-on-mobile\" id=\"current_user\">"+user_name+"</span>";
	$("#current_user").empty();
	jQuery("#current_user").html(html);
}
</script>