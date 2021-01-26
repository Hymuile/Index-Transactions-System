<%@page contentType="text/html; charset=UTF-8"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	<h4 class="modal-title">
		添加记录
	</h4>
</div>
<div class="modal-body">
	<div class="row">
		<div class="col-md-12">
			<h4>
				请输入记录的信息
			</h4>
			<p>
				项目ID：
				<input type="text" class="col-md-12 form-control" id="project_id" placeholder="请输入项目ID" value="">
			</p>
			<p>
				项目名称：
				<input type="text" class="col-md-12 form-control" id="project_name" placeholder="请输入项目名称" value="">
			</p>
		</div>
	</div>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">
		取消
	</button>
	<button type="button" class="btn btn-success" data-dismiss="modal" onclick="Record.addNew()">
		保存
	</button>
</div>
