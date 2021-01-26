<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="portlet-title">
	<div class="caption">
		<i class="fa fa-globe"></i>搜索框
	</div>
</div>
<div class="portlet-body">
	<div class="table-container">
		<!-- BEGIN HEADER SEARCH BOX -->
			<div class="input-group">
				<input type="text" class="form-control" placeholder="请输入期货/股票ID" id="query_id">
				<span class="input-group-btn">
				<button class="btn submit" id="query_single_index"><i class="icon-magnifier"></i></button>
				</span>
			</div>
		<!-- END HEADER SEARCH BOX -->
		<div id="query-result">
		</div>
	</div>
</div>
