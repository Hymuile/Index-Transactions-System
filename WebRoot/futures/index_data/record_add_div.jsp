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
				<select class="table-group-action-input form-control input-medium" id="project_id" name="project_id">
					<option value="1">（无）</option>
				</select>
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
				期望完成时间：
				<div class="input-group date form_datetime">
					<input type="text" id="limit_time" name="limit_time" class="form-control" size="16" placeholder="请输入期限完成时间" value=""/>
					<span class="input-group-btn">
						<button class="btn default date-set" type="button">
							<i class="fa fa-calendar"></i>
						</button>
					</span>
				</div>
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
<script>
ComponentsPickers.init();	//这个本页面要编写对应的对象，时间拾取控件
//设置当前时间10天以后
Page.initLimitTime();
Page.initProjectInfo();
</script>