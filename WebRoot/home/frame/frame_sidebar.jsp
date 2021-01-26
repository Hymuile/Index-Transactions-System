<%@page contentType="text/html; charset=UTF-8"%>
<meta charset="utf-8" />
	<!-- BEGIN QUICK SIDEBAR -->
	<a href="javascript:;" class="page-quick-sidebar-toggler"><i class="icon-close"></i></a>
	<div class="page-quick-sidebar-wrapper">
		<div class="page-quick-sidebar">
			<div class="nav-justified">
				<ul class="nav nav-tabs nav-justified">
					<li class="active">
						<a href="#quick_sidebar_tab_1" data-toggle="tab">用户 <span class="badge badge-danger">2</span></a>
					</li>
					<li>
						<a href="#quick_sidebar_tab_2" data-toggle="tab">报警 <span class="badge badge-success">7</span></a>
					</li>
					<li class="dropdown">
						<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">更多<i class="fa fa-angle-down"></i></a>
						<ul class="dropdown-menu pull-right" role="menu">
							<li><a href="#quick_sidebar_tab_3" data-toggle="tab"><i class="icon-bell"></i> 报警 </a></li>
							<li><a href="#quick_sidebar_tab_3" data-toggle="tab"><i class="icon-info"></i> 提醒 </a></li>
							<li><a href="#quick_sidebar_tab_3" data-toggle="tab"><i class="icon-speech"></i> 活动 </a></li>
							<li class="divider"></li>
							<li><a href="#quick_sidebar_tab_3" data-toggle="tab"><i class="icon-settings"></i> 设置 </a></li>
						</ul>
					</li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active page-quick-sidebar-chat" id="quick_sidebar_tab_1">
						<div class="page-quick-sidebar-chat-users" data-rail-color="#ddd" data-wrapper-class="page-quick-sidebar-list">
							<h3 class="list-heading">Staff</h3>
							<ul class="media-list list-items">
							</ul>
							<h3 class="list-heading">Customers</h3>
							<ul class="media-list list-items">
							</ul>
						</div>
						<div class="page-quick-sidebar-item">
							<div class="page-quick-sidebar-chat-user">
								<div class="page-quick-sidebar-nav">
									<a href="javascript:;" class="page-quick-sidebar-back-to-list"><i class="icon-arrow-left"></i>Back</a>
								</div>
								<div class="page-quick-sidebar-chat-user-messages">
								</div>
								<div class="page-quick-sidebar-chat-user-form">
									<div class="input-group">
										<input type="text" class="form-control" placeholder="Type a message here...">
										<div class="input-group-btn">
											<button type="button" class="btn blue"><i class="icon-paper-clip"></i></button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane page-quick-sidebar-alerts" id="quick_sidebar_tab_2">
						<div class="page-quick-sidebar-alerts-list">
							<h3 class="list-heading">General</h3>
							<ul class="feeds list-items">
							</ul>
							<h3 class="list-heading">System</h3>
							<ul class="feeds list-items">
							</ul>
						</div>
					</div>
					<div class="tab-pane page-quick-sidebar-settings" id="quick_sidebar_tab_3">
						<div class="page-quick-sidebar-settings-list">
							<h3 class="list-heading">General Settings</h3>
							<ul class="list-items borderless">
							</ul>
							<h3 class="list-heading">System Settings</h3>
							<ul class="list-items borderless">
							</ul>
							<div class="inner-content">
								<button class="btn btn-success"><i class="icon-settings"></i> Save Changes</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- END QUICK SIDEBAR -->
