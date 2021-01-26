var module="money_manage";
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

		$('#add_button').click(function() {Page.addRecord();});
		$('#submit_button').click(function() {Page.submitRecord();});
		$('#tools_menu_reload').click(function() {Page.reload();});
	};
	
	var addRecord =function(){
		submitRecord();
	}
	//提交充值或提现信息
	var submitRecord=function(){
		if(checkInput()==true){
			console.log("运行到这里————提交页面");
			//运行到这里就停下来了
			var trading_number=$('#trading_number').val();
			var op_type=$('#form_control_1').val();
			console.log("trading_number"+trading_number+"op_type"+op_type);
			//判断是否为空
			if(trading_number=="" || op_type==""){
				alert("填写信息不能为空！！！");
			}
			//通过正则表达式找到
			else if (!(/(^\d+.?\d{0,2}?$)/.test(trading_number))){
				alert("输入金额有问题哟（提示：应当是只包含数字，至多两位小数）！！！");
			    console.log("输入金额有问题哟！");
			}
			//给定金额上限
			else if(parseFloat(trading_number)>10000){
				alert("小本生意，单次操作不应超过一万人民币！");
			}
			else{
				var url="../../"+module+"_"+sub+"_servlet_action?action=op_money&trading_number="+trading_number+"&op_type="+op_type;
				console.log(url);
				console.log("定位点1-----");
				$.post(url,function(e){
					console.log(e);
					//var json=eval("("+e+")");
					var money_code=e.money_code;
					if(money_code=="0"){
						alert("充值成功！");
					}
					else if(money_code=="1"){
						alert("余额不足，提现失败！");
					}
					else{
						alert("提现完成！");
					}
					console.log("得到的信息");
					console.log("定位点3-----")
					console.log("收到数据！");
					window.location.reload();
					//返回值
					
				});
				
				
			}
		}
	};
	var checkInput=function(){
		var bOk=true;
		var action=$("#action").val();
		// alert(action);运行到了这里
		if(action==null || action==""){Frame.showMsg("不能为空！");bOk=false;};
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
	return {
		init: function() {
			initPageStyle();
			handleButtonEvent();
			//initLimitTime();
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
