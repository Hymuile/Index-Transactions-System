<%@page contentType="text/html; charset=UTF-8"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	<h4 class="modal-title">
		添加用户
	</h4>
</div>
<div class="modal-body">
	<div class="row">
		<div class="col-md-12">
			<h4>
				请输入新用户的信息（均为必填项）
			</h4>
			<p>
				邮箱：
				<input type="text" class="col-md-12 form-control" id="user_email" placeholder="邮箱">
			</p>
			<p>
				密码：
				<input type="text" class="col-md-12 form-control" id="user_pwd" placeholder="密码，长度建议为6至20位">
			</p>
			<p>
				用户权限：
				<select class="table-group-action-input form-control input-medium" id="user_role" name="user_role">
					<option value="normal">普通用户</option>
					<option value="manager">管理员</option>
				</select>
			</p>
			<p>
				用户名：
				<input type="text" class="col-md-12 form-control" id="user_name" placeholder="建议中文、字母、数字及下划线">
			</p>
			<p>
				用户余额：
				<input type="text" class="col-md-12 form-control" id="user_balance" placeholder="0.0">
			</p>
			<p>
				用户预付款：
				<input type="text" class="col-md-12 form-control" id="user_margin" placeholder="0.0">
			</p>
			<p>
				用户所在地：
				<input type="text" class="col-md-12 form-control" id="user_city" placeholder="成都">
			</p>
		</div>
	</div>
</div>

<div id="notice"></div>
<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">
		取消
	</button>
	<button type="button" class="btn btn-success" data-dismiss="modal" onclick="Record.addNew()">
		保存
	</button>
</div>
<script>
ComponentsPickers.init();	//这个本页面要编写对应的对象，时间拾取控件
//设置当前时间10天以后
Page.initLimitTime();
Page.initProjectInfo();
</script>