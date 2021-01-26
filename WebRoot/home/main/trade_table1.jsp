<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="portlet-title">
	<div class="caption">
		<i class="fa fa-globe"></i>卖出
	</div>
	<div class="tools">
		<a href="javascript:;" class="collapse"> </a> <a
			href="javascript:reload();" class="reload"> </a>
	</div>
</div>
<div class="portlet-body">
	<div class="table-container">
		<table class="table table-striped table-bordered table-hover"
			id="datatable_orders">
			<thead>
				<tr role="row" class="heading">
					<th width="5%">期货ID</th>
					<th width="15%">期货名称</th>
					<th width="10%">数量</th>
					<th width="10%">开仓类型</th>
					<th width="10%">操作</th>
				</tr>
				<tr role="row" class="filter">
					<td>
						<input type="text" class="form-control form-filter input-sm" name="order_id" id="order_id">
					</td>
					<td>
						<input type="text" class="form-control form-filter input-sm" name="order_name" id="order_name">
					</td>
					<td>
						<div class="margin-bottom-5">
							<input type="text" class="form-control form-filter input-sm" name="number" id="number" value="1" />
						</div>
					</td>
					<td><select name="order_status" class="form-control form-filter input-sm" id="order_type">
							<option value="sell">卖出</option>
					</select></td>
					<td>
						<div class="margin-bottom-5">
							<button class="btn btn-sm yellow filter-submit margin-bottom" id="order_confirm">确认</button>
						</div>
					</td>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</div>
