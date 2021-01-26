<%@page contentType="text/html; charset=UTF-8"%>
<%
//closing_price&future_index&index_name
	String index_name=request.getParameter("index_name");
	String future_index=request.getParameter("future_index");
	String closing_price=request.getParameter("closing_price");
	System.out.println(closing_price);
	request.setAttribute("index_name", index_name);
	request.setAttribute("future_index",future_index);
	request.setAttribute("closing_price", closing_price);
%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	<h4 class="modal-title">
		新增交易
	</h4>
</div>
<div class="modal-body">
	<div class="portlet light profile-sidebar-portlet">

		<!-- SIDEBAR USER TITLE -->
		<div class="profile-usertitle">
			<div class="profile-usertitle-name">
				<%=request.getAttribute("index_name")%>
			</div>
			<div class="profile-usertitle-job">
				<%=request.getAttribute("future_index")%>
			</div>
		</div>
		
		
	</div>
	<div class="row">
		<div class="col-md-12">
			<p>
				指数名称: <input type="hidden" id="index_id" name="index_id"
					class="form-control" value="<%=request.getAttribute("future_index")%>" /><%=request.getAttribute("future_index")%>
			</p>
			<p>
				交易数量： <input type="text" id="amount" name="amount"
					class="form-control" placeholder="请输入交易的数量" value="" />
			</p>
			<p>
				交易方式 :<select
					class="table-group-action-input form-control input-medium"
					id="order_type" name="order_type">
					<option value="buy" id="buy">买入</option>
					<option value="sell" id="sell">卖出</option>
				</select>
			</p>
		</div>
	</div>
	<div class="profile-userbuttons">
			<button type="button" id="sell_or_buy"
				class="btn btn-circle green-haze btn-sm" data-dismiss="modal" onclick="add()">
				提交</button>
			<button type="button" class="btn btn-circle btn-danger btn-sm"
				data-dismiss="modal">取消</button>
		</div>
</div>

<p>
<p>
<script type="text/javascript" src="../../assets/module/scripts/property_manage/todo/list_add.js"></script>
<%@include file="../../home/frame/frame_javascript.jsp"%>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script type="text/javascript" src="../../assets/global/plugins/bootstrap-daterangepicker/moment.min.js"></script>
<script type="text/javascript" src="../../assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>

<script>
console.log("运行到了这里");
var module="property_manage";
var sub="list";
function add(){//首先得到数值
		var index_id=$('#index_id').val();
		//var index_id=$('#index_id').val();
		var amount=$('#amount').val();
		var order_type=$('#order_type').val();
		console.log("校验："+index_id+" "+amount+" "+order_type);
		if(index_id==""||amount==""){
			alert("输入存在空！");
		}
		else if(order_type==""){
			alert("请选择操作类型！");
		}
		else if (!(/(^\d+$)/.test(amount))){
			alert("输入数量有问题哟（提示：应当是整数）！！！");
		}
		else{
			var url="../../"+module+"_"+sub+"_servlet_action?action=sell_or_buy&index_id="+index_id+"&quantity="+amount+"&order_type="+order_type;
			console.log(url);
			$.post(url,function(e){
				console.log(e);
				var bill_code=e.bill_code;
				//买：3：不存在该股1：仓库存量2：钱不够了9：买入交易成功
				//卖：4不含有5持有不足10卖出完成
				if(bill_code=="1"){
					alert("抱歉，目前期货不足……");
					window.location.reload();
				}
				else if(bill_code=="2"){
					alert("您的余额不足，请充值！");
					window.location.reload();
				}
				else if(bill_code=="3"){
					alert("您输入的期货不存在，请检查！");
					window.location.reload();
				}
				else if(bill_code=="4"){
					alert("您还未拥有该期货，请购买吧！");
					window.location.reload();
				}
				else if(bill_code=="5"){
					alert("您持有的期货不足！");
					window.location.reload();
				}
				else if(bill_code=="9"){
					alert("买入交易成功！");
					window.location.reload();
				}
				else if(bill_code=="10"){
					alert("卖出交易成功！");
					window.location.reload();
				}
			});
			
		}
}

</script>