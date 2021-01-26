var module="property_manage";
var sub="list";
jQuery(document).ready(function() {
	Metronic.init(); // init metronic core components
	Layout.init(); // init current layout
	Demo.init(); // init demo features
	ComponentsDropdowns.init();
	ComponentsPickers.init();	//这个本页面要编写对应的对象
	Frame.init(module,sub);
	Page.init();
	Record.init();
});
/*================================================================================*/
var Record = function() {
	var userId=undefined;
	var userName=undefined;
	var userRole=undefined;
	var userAvatar=undefined;
	var initRecordStyle = function() {
	};
	var initRecordList = function(){
	}
	return {
		init: function() {
			initRecordList();
			initRecordStyle();
		},
		deleteRecord:function(id){
			deleteRecord(id);
		},
		viewRecord:function(id){
			viewRecord(id);
		},
		exportRecord:function(){
			exportRecord();
		},
		search:function(){
			search();
		}
	};
}();//Record;
/* ================================================================================ */
//关于页面的控件生成等操作都放在Page里，和Record独立，Record主要是和记录集交互
var Page = function() {
	var html="";
	var initPageStyle = function() {
		hideFrameNav();
	};
	var hideFrameNav=function(){
		//根据需要隐藏掉一些不需要的面板
		//隐藏顶部菜单栏
		//$("#page_top_div").hide();
		//隐藏旁边的菜单栏
		//$("#page_sidebar_wrapper_div").hide();
		//隐藏风格设置栏
		$("#style_customizer_div").hide();
		//隐藏page header
		$("#page_header_div").hide();
		//隐藏page 底部
		$("#page_footer_div").hide();
		var displaySidebar=true;
		if(!displaySidebar){
			if($("body").hasClass("page-container-bg-solid")){
				$("body").removeClass("page-sidebar-closed-hide-logo page-container-bg-solid");
				$("body").addClass("page-full-width");
			}else{}
		}
		//隐藏提醒图标
		$("#header_inbox_bar").hide();
		$("#header_calendar_bar").hide();
	};
	var processError=function(json){
		if(Frame!=null)
			Frame.processError(json);
	};
	var handleButtonEvent=function(){
		$('#return_button').click(function() {Page.confirmBack();});
		$('#sell_or_buy').click(function() {Page.sellOrBuy();});
		$('#add_button').click(function() {Page.addRecord();});
		$('#submit_button').click(function() {Page.submitRecord();});
		$('#tools_menu_reload').click(function() {Page.reload();});
	};
	
	var addRecord =function(){
		submitRecord();
	}

	var submitRecord=function(){
		if(checkInput()==true){
			page_form.action="../../"+module+"_"+sub+"_servlet_action";
			page_form.submit();
		}
	};
	var checkInput=function(){
		var bOk=true;
		var action=$("#action").val();
		if(action==null || action==""){Frame.showMsg("名称不能为空！");bOk=false;};
		return bOk;
	};
	
	var confirmBack=function(){
		DraggableDialog.setId("confirm_back");
		DraggableDialog.setCancel(Page.onCancel);
		DraggableDialog.setButtonTitle("确定","取消");
		DraggableDialog.setOk(Page.returnBack);
		DraggableDialog.showMsg("确定要返回上一页吗？","提示");
	};
	
	var onCancel=function(){
		DraggableDialog.close();
	}
	var returnBack=function(){
		history.go(-1);
	};
	
	//买入卖出
	var sellOrBuy=function(){
		//首先得到数值
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
	};
	
	return {
		init: function() {
			initPageStyle();
			handleButtonEvent();
			//initLimitTime();
		},
		sellOrBuy:function(){
			sellOrBuy();
		},
		processError:function(json){
			processError(json);
		},
		submitRecord:function(){
			submitRecord();
		},
		addRecord:function(){
			addRecord();
		},
		reload:function(){
			window.location.reload();
		},
		confirmBack:function(){
			confirmBack();
		},
		returnBack:function(){
			returnBack();
		},
		onCancel:function(){
			onCancel();
		}
	}
}();//Page
/*================================================================================*/
