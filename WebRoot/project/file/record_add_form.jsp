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
				属于哪个项目：
				<input type="text" class="col-md-12 form-control" id="project_id" value="XXX项目">
			</p>
			<p>
				待办事项标题：
				<input type="text" class="col-md-12 form-control" id="title" value="吃饭">
			</p>
			<p>
				事项内容：
				<input type="text" class="col-md-12 form-control" id="content" value="XXX比赛">
			</p>
			<p>
				承担者：
				<input type="text" class="col-md-12 form-control" id="executor" value="张三">
			</p>
			<p>
				开始时间：
				<input type="text" class="col-md-12 form-control" id="begin_time" value="2015-11-29">
			</p>
			<p>
				期望完成时间：
				<input type="text" class="col-md-12 form-control" id="expect_end_time" value="2015-11-29">
			</p>
			<p>
				完成时间：
				<input type="text" class="col-md-12 form-control" id="end_time" value="2015-11-29">
			</p>
			<p>
				完成标志：
				<input type="text" class="col-md-12 form-control" id="end_tag" value="0">
			</p>
			<p>
				状态：
				<input type="text" class="col-md-12 form-control" id="status" value="准备执行">
			</p>
		</div>
	</div>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">
		取消
	</button>
	<button type="button" class="btn btn-success" data-dismiss="modal" onclick="addNew()">
		保存
	</button>
</div>
