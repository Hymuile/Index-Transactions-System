var module="users";
var sub="list";

jQuery(document).ready(function() {
	Metronic.init(); // init metronic core components
	Layout.init(); // init current layout

	Demo.init(); // init demo features
	ComponentsDropdowns.init();
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
		getRecord();
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
		window.location.href="todo_view.jsp?id="+id+"&exist_resultset=1";
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
	var layout=1;
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
		$('#layout_button').click(function() {Page.layoutRecord();});
		$('#print_button').click(function() {Page.printRecord();});
	};
	var addRecord=function(){
		window.location.href=sub+"_add.jsp";
	}
	var showResult=function(json){
		var title="注册用户记录";
		if($("#title_div")) $("#title_div").html(title);
		if(json!=null){
			var list=json.aaData;
			var tip="当前查询到了 "+list.length+" 条用户记录";
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
		var title=json[1];
		var content=json[2];
		var createTime=json[4];
		var status=json[6];
		html=html+"														<div style=\"clear:both;width:100%;margin-top:5px;border:0px solid blue;\">";
		html=html+"															<div style=\"display:table-cell;margin-left:10px;margin-right:10px;margin-top:10px;margin-bottom:10px;border:0px solid blue;\"><p>";
		html=html+"																<span>[id："+title+"][邮箱："+content+"][用户名："+createTime+"][创建时间："+status+"]</span><p>";
		html=html+"																<button  type=\"button\" class=\"btn-small\" onclick=\"Page.deleteRecord("+id+");\">删除</button>";
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
		window.location.href="todo_statistic.jsp";
	}
	var layoutRecord=function(){
		if(layout==1)
			window.location.href="record_list.jsp";
		if(layout==2)
			window.location.href="todo_list.jsp";
	}
	var printRecord=function(){
		window.location.href="todo_print.jsp?exist_resultset=1";
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
		printRecord:function(){
			printRecord();
		},
		modifyRecord:function(id){
			modifyRecord(id);
		},
		reload:function(){
			window.location.reload();
		},
		layoutRecord:function(){
			layoutRecord();
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
function initLeftMenu(module){
	delMenu();
	document.title="期货交易系统";
	$.post("../../common_data_action?action=get_main_menu&table_name=document_main&module="+module,function(e){processMenuItemResult(e)});
}
function processMenuItemResult(text){
	//showMsg("读取了菜单:"+text);
	var json=eval("("+text+")");
	initMenu(json);
}
function delMenu(){
	var ul = document.getElementById("page_sidebar_menu");
	if(ul!=null){
		var lilength = ul.childNodes.length;
		var str="";
		for (var i = lilength-1; i >=0; i--) {
			str=str+"\r\nindex="+i+",id="+ul.childNodes[i].id;
			var li=ul.childNodes[i];
			if(li.id=="sidebar_toggle_wrapper_li" || li.id=="sidebar_search_wrapper_li"){
			}else{
				ul.removeChild(ul.childNodes[i]);
			}
		}
	}
}
function initMenu(json){
	var ul = document.getElementById("page_sidebar_menu");
	if(ul!=null){
		json=json.record_list;
		for (var topIndex in json){
			if(json[topIndex].parent_item_id==0){
				var li= document.createElement("li");
				li.id=itemId;
				li.class="start active open";
				var itemId="menu_"+json[topIndex].item_id;
				var itemName=json[topIndex].item_name;
				var href = json[topIndex].href_link;
				var html="<a href=\""+href+"\"><i class=\"icon-home\"></i><span class=\"title\">"+itemName+"</span><span class=\"selected\"></span><span class=\"arrow open\"></span></a>";
				if(json[topIndex].details_tag==1){
					html=html+"<ul class=\"sub-menu\">";
					for (var subIndex in json){
						if(itemId=="menu_"+json[subIndex].parent_item_id){
							var subItemId="menu_"+json[subIndex].item_id;
							var subItemName=json[subIndex].item_name;
							var href="../../"+json[subIndex].file_path+json[subIndex].href_link;
							if(json[subIndex].href_link=="")
								href="../../home/main/index.jsp?content_page=under_construction";
							html=html+"<li id=\""+subItemId+"\"><a href=\""+href+"\"><i class=\"icon-bulb\"></i>"+subItemName+"</a></li>";
						}
					}
					html=html+"</ul>";
				}
				li.innerHTML=html;
				ul.appendChild(li);

			}
		}
		var menuDiv=document.getElementById("sidebar_menu_div");
		menuDiv.appendChild(ul);
	}
}