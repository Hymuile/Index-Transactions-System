<%@page contentType="text/html; charset=UTF-8"%>
<!-- /.modal -->
<div class="modal fade bs-modal-sm" id="small" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 id="msg_box_title" class="modal-title"></h4>
			</div>
			<div id="msg_box_content" class="modal-body">
			</div>
			<div class="modal-footer">
				<input type="hidden" id="msg_box_return" value=""/>
				<button type="button" id="button_cancel" class="btn default" data-dismiss="modal">取消</button>
				<button type="button" id="button_ok" class="btn blue" data-dismiss="modal">确定</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<script>
function confirmMessage(title,content,returnFunction){
	if(title!=null){
		$("#msg_box_title").html(title);
	}
	if(content!=null){
		$("#msg_box_content").html(content);
	}
	$("#button_cancel").show();
	$('#button_cancel').unbind('click').click(function () {});
	$('#button_ok').unbind('click').click(function () {});
	$("#button_cancel").click(function(){
		$("#msg_box_return").val("0");
		returnFunction(false);
	});
	$("#button_ok").click(function(){
		$("#msg_box_return").val("1");
		returnFunction(true);
	});
	$("#small").modal("show");
}
function showMessage(title,content){
	if(title!=null){
		$("#msg_box_title").html(title);
	}
	if(content!=null){
		$("#msg_box_content").html(content);
	}
	$("#button_cancel").hide();
	$("#small").modal("show");
}
</script>