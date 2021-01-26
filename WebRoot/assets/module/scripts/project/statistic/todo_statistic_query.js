var module="project";
var sub="statistic";
jQuery(document).ready(function() {
	Metronic.init(); // init metronic core components
	Layout.init(); // init current layout
	QuickSidebar.init(); // init quick sidebar
	Demo.init(); // init demo features
	ComponentsDropdowns.init();
	ComponentsPickers.init();	//本页面如果有时间拾取框，就要初始化这一句
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
		initTimePeriod();
		getRecord();
	}
	var initTimePeriod=function(){
		var today=new Date();
		var thisYear=today.getFullYear();
		var thisMonth=today.getMonth()+1;
		var firstDay=thisYear+'-'+thisMonth+'-01';
		firstDay=ComponentsPickers.formatDate(ComponentsPickers.parseDate(firstDay),"yyyy-MM-dd")+" 00:00:00";
		var nowDay=ComponentsPickers.formatDate(today,"yyyy-MM-dd")+" 23:59:59";
		$("#time_from").val(firstDay);
		$("#time_to").val(nowDay);
	}
	var getRecord = function(){
		Metronic.startPageLoading({message: '正在查询中，请稍候...'});	//开始等待动画
		var id=$("#id").val();
		var existResultset=$("#exist_resultset").val();
		var url="../../"+module+"_"+sub+"_servlet_action?action=get_record&id="+id+"&exist_resultset="+existResultset;
		$.post(url,function(json){
			if(json.result_code==0){
				Record.userId=json.user_id;
				Record.userName=json.user_name;
				Record.userRole=json.user_role;
				Record.userAvatar=json.user_avatar;
				Page.showResult(json);
			}else{
				if(Page!=null){
					Page.processError(json);
				}
			}
			Metronic.stopPageLoading();	//停止等待动画
		}).error(function(xhr,errorText,errorType){
	        alert('错误信息：'+errorText+",错误类型："+errorType);
	    });
	};
	var viewRecord=function(id){
		window.location.href="todo_view.jsp?id="+id;
	};
	var deleteRecord = function(id){
		if(confirm("您确定要删除这条记录吗？")){
			if(id>-1){
				$.post("../../"+module+"_"+sub+"_servlet_action?action=delete_record&id="+id,function(json){
					if(json.result_code==0){
						var count=json.count;
						var amount=json.amount;
						initRecordList();
						initRecordStyle();
						alert("已经从数据表删除该记录！");
					}
				})
			}
		}
	};
	var exportRecord=function(){
		window.location.href="../../"+module+"_"+sub+"_servlet_action?action=export_record&exist_resultset=1";
	}
	var search=function(){
		page_form.submit();
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
		$('#search_button').click(function() {Page.searchRecord();});
		$('#delete_button').click(function() {Page.deleteRecord();});
		$('#add_button').click(function() {Page.addRecord();});
		$('#submit_button').click(function() {Page.submitRecord();});
		$('#tools_menu_reload').click(function() {Page.reload();});
		$('#help_button').click(function() {Page.help();});
		$('#export_button').click(function() {Page.exportRecord();});
		$('#statistic_button').click(function() {Page.statisticRecord();});
	};
	var addRecord=function(){
		window.location.href=sub+"_add.jsp";
	}
	var showResult=function(json){
		var title="待办事项统计-统计每天新建了多少待办事项";
		if($("#title_div")) $("#title_div").html(title);
		if(json!=null){
			var list=json.aaData;
			var tip="当前查询到了 "+list.length+" 条记录";
			if($("#tip_div")) $("#tip_div").html(tip);
			if($("#record_list_tip")) $("#record_list_tip").html(tip);
			showRecordList(list);
		}
	};
	var showRecordList=function(list){
		html="													<div><span id=\"tip_div\"></span>";
		for(var i=0;i<list.length;i++){
			showRecord(list[i]);
		}
		html=html+"													</div>";
		$("#record_list_div").html(html);
	};
	var showRecord = function(json){
		var id=json[0];
		var image="../../assets/module/img/public/wkbj.jpg";
		var title=json[1];
		var content=json[2];
		var createTime=json[4];
		var status=json[6];
		var me=json[10];
		html=html+"														<div style=\"clear:both;width:100%;margin-top:5px;border:0px solid blue;\">";
		html=html+"															<div style=\"float:left;border:0px solid green;\">";
		html=html+"																<img src=\""+image+"\" style=\"width:100px;height:auto;border-radius:50%!important;border:0px solid red;\"></img>";
		html=html+"															</div>";
		html=html+"															<div style=\"display:table-cell;margin-left:10px;margin-right:10px;margin-top:10px;margin-bottom:10px;border:0px solid blue;\"><p>";
		html=html+"																<span>标题："+title+"</span><p>";
		html=html+"																<span>内容："+content+"</span><p>";
		html=html+"																<span>时间："+createTime+"</span><p>";
		html=html+"																<span>状态："+status+"</span><p>";
		if(me=="1"){
			html=html+"																<button  type=\"button\" class=\"btn-small\" onclick=\"Page.deleteRecord("+id+");\">删除</button>";
			html=html+"																<button  type=\"button\" class=\"btn-small\" onclick=\"Page.modifyRecord("+id+");\">修改</button>";
		}
		html=html+"																<button  type=\"button\" class=\"btn-small\" onclick=\"Page.viewRecord("+id+");\">详细信息</button>";
		html=html+"															</div>";
		html=html+"														</div>";
	};
	var help=function(){
		var strUrl=location.pathname;
		window.open('../../help/online/new_win_help.jsp?page_url='+strUrl, 'big', 'fullscreen=yes');
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
	var deleteRecord=function(id){
		Record.deleteRecord(id);
	};
	var viewRecord=function(id){
		Record.viewRecord(id);
	};
	var modifyRecord=function(id){
		window.location.href="todo_view.jsp?id="+id;
	};
	var searchRecord=function(){
		window.location.href="todo_query.jsp";
	};
	var statisticRecord=function(){
		submitRecord();
	}
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
		},
		processError:function(json){
			processError(json);
		},
		showResult:function(json){
			showResult(json);
		},
		showRecordList:function(list){
			showRecordList(list);
		},
		submitRecord:function(){
			submitRecord();
		},
		addRecord:function(){
			addRecord();
		},
		deleteRecord:function(id){
			deleteRecord(id);
		},
		viewRecord:function(id){
			viewRecord(id);
		},
		searchRecord:function(){
			searchRecord();
		},
		exportRecord:function(){
			Record.exportRecord();
		},
		statisticRecord:function(){
			statisticRecord();
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
		},
		help:function(){
			help();
		}
	}
}();//Page
/*================================================================================*/
