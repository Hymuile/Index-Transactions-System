<%@page contentType="text/html; charset=UTF-8"%>
<div id="record_modify_div" aria-hidden="true" style="position:absolute;display:none; margin:auto;z-index:9999;background-color:#FFF;">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
		<h4 class="modal-title">
			修改待办事项记录
		</h4>
	</div>
	<div class="modal-body">
		<div class="row">
			<div class="col-md-12">
				<h4>
					请修改待办事项的信息
				</h4>
				<input type="hidden" id="id" name="id" value="" />
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
					<input type="text" class="col-md-12 form-control" id="content" value="注意要吃好睡好">
				</p>
				<p>
					限制时间：
					<input type="text" class="col-md-12 form-control" id="limit_time" value="2015-11-29">
				</p>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">
			取消
		</button>
		<button type="button" class="btn btn-success" data-dismiss="modal" onclick="Record.modify();">
			保存
		</button>
	</div>
</div>
<script>
	Record.showRecord();
</script>