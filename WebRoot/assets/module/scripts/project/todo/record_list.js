var module="project";
var sub="todo";
var existResultset="0";
jQuery(document).ready(function() {
	console.log("js初始化执行开始！");
	Metronic.init(); // init metronic core components
	Layout.init(); // init current layout
	QuickSidebar.init(); // init quick sidebar
	ComponentsDropdowns.init();
	Demo.init(); // init demo features
	Frame.init(module,sub);
	Page.init();
	Record.init();
	
	initLeftMenu("home");
	
	console.log("js初始化执行完毕！");
});
/* ================================================================================ */
var Record = function() {
	var userId;
	var userName;
	var recordCount=0;
	var html="";
	var initRecordListHeader = function() {
		var html="";
		html=html+"						<tr>";
		html=html+"							<th class=\"table-checkbox\"><input type=\"checkbox\" class=\"group-checkable\" data-set=\"#record_list .checkboxes\" /></th>";
		html=html+"							<th>用户邮箱</th>";
		html=html+"							<th>名称</th>";
		html=html+"							<th>权限</th>";
		html=html+"							<th>创建时间</th>";
		html=html+"							<th>余额</th>";
		html=html+"							<th>预付款</th>";
		html=html+"						</tr>";
		$("#record_list_head").html(html);
	};
	var initRecordList=function(){
		existResultset=$("#exist_resultset").val();
		$('.datatable').dataTable( {
			"paging":true,
			"searching":false,
			"oLanguage": {
				"aria": {
					"sortAscending": ": activate to sort column ascending",
					"sortDescending": ": activate to sort column descending"
				},
				"sProcessing":   "<img src='../../assets/module/img/public/loading.gif' />处理中...",
				"sLengthMenu":   "_MENU_ 记录/页",
				"sZeroRecords":  "<span style='color:red;'>没有找到对应的记录！</span>",
				"sInfo":         "显示第 _START_ 至 _END_ 项记录，共 _TOTAL_ 项",
				"sInfoEmpty":    "显示第 0 至 0 项记录，共 0 项",
				"sInfoFiltered": "(由 _MAX_ 项记录过滤)",
				"sInfoPostFix":  "",
				"sSearch":       "过滤:",
				"oPaginate": {
					"sFirst":    "首页",
					"sPrevious": "上页",
					"sNext":     "下页",
					"sLast":     "末页"
				}
			},
			"aoColumns": [{"mRender": function(data, type, full) {
						sReturn = '<div style="width:100px;"><input type="checkbox" class="checkboxes" value="'+data+'"/><a href="javascript:Record.modifyRecord('+data+')"><span class=\"\" title="修改"><i class=\"icon-pencil\"></i></span></a><a href="javascript:Record.deleteRecord('+data+')"><span class=\"\" title="删除"><i class=\"icon-trash\"></i></span></a></div>';
						return sReturn;
					},"orderable": false
				},{},{},{},{},{},{}],
			"aLengthMenu": [[5,10,15,20,25,40,50,-1],[5,10,15,20,25,40,50,"所有记录"]],
			"fnDrawCallback": function(){$(".checkboxes").uniform();$(".group-checkable").uniform();},
			"fnServerData":function (sSource, aoData, fnCallback, oSettings) {
				$.ajax({
					"dataType": 'json',
					"type": "POST",
					"url": sSource,
					"data": aoData,
					"success": function(json){
						if(json.result_code==0){
							Record.userId=json.user_id;
							Record.userName=json.user_name;
							var list=json.aaData;
							if(list.length>0){
								Record.recordCount=list.length;
								//这里进行数据处理显示
								fnCallback(json);
							}else{}
						}else{
							Frame.processError(json);
						}
					}
				});
			},
			"sAjaxSource": "../../"+module+"_"+sub+"_servlet_action?action=get_record&exist_resultset="+existResultset
		});
		$('.datatable').find('.group-checkable').change(function () {
			var set = jQuery(this).attr("data-set");
			var checked = jQuery(this).is(":checked");
			jQuery(set).each(function () {
	            if (checked) {
	                $(this).attr("checked", true);
	                $(this).parents('tr').addClass("active");
	            } else {
	                $(this).attr("checked", false);
	                $(this).parents('tr').removeClass("active");
	            }
			});
			jQuery.uniform.update(set);
		});
		$('.datatable').on('change', 'tbody tr .checkboxes', function () {
			$(this).parents('tr').toggleClass("active");
		});
	}
	var addNew=function(){
		var projectId=encodeURI(document.getElementById("project_id").value);
		var title=encodeURI(document.getElementById("title").value);
		var content=encodeURI(document.getElementById("content").value);
		var executor=encodeURI(document.getElementById("executor").value);
		var beginTime=encodeURI(document.getElementById("begin_time").value);
		var endTime=encodeURI(document.getElementById("end_time").value);
		var expectEndTime=encodeURI(document.getElementById("expect_end_time").value);
		var endTag=document.getElementById("end_tag").value;
		var status=encodeURI(document.getElementById("status").value);
		var url="../../"+module+"_"+sub+"_servlet_action?action=add_record"+
			"&project_id="+projectId+"&title="+title+"&content="+content+"&executor="+
			executor+"&begin_time="+beginTime+"&end_time="+endTime+"&expect_end_time="+expectEndTime+"&end_tag="+endTag+"&status="+status;
		$.post(url,function(e){processAddRecordResult(e);}).error(function(XMLHttpRequest, textStatus, errorThrown){
			alert("addNew()发生错误："+XMLHttpRequest.status+","+XMLHttpRequest.readyState+","+textStatus);
		})
	}
	var processAddRecordResult=function(data){
		window.location.reload();
	}
	var deleteRecord=function(id){
		var selectedCount=0;
		if(id!=null){
			selectedCount=1;
			var url="../../"+module+"_"+sub+"_servlet_action?action=delete_record&id="+id;
		}else{
			var set = $('.datatable').find('.group-checkable').attr("data-set");
			var url="../../"+module+"_"+sub+"_servlet_action?action=delete_record";
			jQuery(set).each(function () {
				var checked = $(this).attr("checked");
		        if (checked) {
		        	var id=this.value;
		        	url=url+"&id="+id;
		            selectedCount++;
		        } else {
		        }
			});
		}
		if(selectedCount>0){
			var tip="";
			if(selectedCount==1){
				tip="您确定要删除这个记录吗？";
			}else{
				tip="您确定要删除这 "+selectedCount+" 条记录吗？";
			}
			if(confirm(tip)){
				$.post(url,function(e){processDeleteRecordResult(e);}).error(function(XMLHttpRequest, textStatus, errorThrown){
					alert("deleteRecord()发生错误："+XMLHttpRequest.status+","+XMLHttpRequest.readyState+","+textStatus);
				})
			}
		}
	}
	var processDeleteRecordResult=function(json){
		if(json.result_code==0){
			window.location.href="record_list.jsp";
		}
	}
	var showRecord=function(){
		var set = $('.datatable').find('.group-checkable').attr("data-set");
		var id=null;
		jQuery(set).each(function () {
			var checked = $(this).attr("checked");
	        if (checked) {
	        	id=this.value;
	        } else {
	        }
		});
		if(id!=null){
			showRecordById(id);
		}
	}
	var showRecordById=function(id){
		var url="../../"+module+"_"+sub+"_servlet_action?action=get_record_by_id&id="+id;
		$.post(url,function(e){processShowRecordById(e);}).error(function(XMLHttpRequest, textStatus, errorThrown){
			alert("processShowRecordById()发生错误："+XMLHttpRequest.status+","+XMLHttpRequest.readyState+","+textStatus);
		})
	}
	var processShowRecordById=function(data){
		data=eval("("+data+")");
		var list=data.aaData;
		document.getElementById("id").value=list[0][0];
		document.getElementById("project_id").value=list[0][1];
		document.getElementById("title").value=list[0][2];
		document.getElementById("content").value=list[0][3];
		document.getElementById("begin_time").value=list[0][4];
		document.getElementById("end_time").value=list[0][5];
		document.getElementById("expect_end_time").value=list[0][6];
		document.getElementById("executor").value=list[0][7];
		document.getElementById("end_tag").value=list[0][8];
		document.getElementById("status").value=list[0][9];
	}
	var modifyRecord=function(){
		$("#record_modify_div").show();
	}
	var modifyRecord1=function(){
		
		var id=document.getElementById("id").value;
		var projectId=encodeURI(document.getElementById("group_id").value);
		var title=document.getElementById("title").value;
		var content=encodeURI(document.getElementById("content").value);
		var beginTime=document.getElementById("begin_time").value;
		var endTime=document.getElementById("end_time").value;
		var expectEndTime=encodeURI(document.getElementById("expect_end_time").value);
		var executor=encodeURI(document.getElementById("executor").value);
		var endTag=document.getElementById("end_tag").value;
		var status=document.getElementById("status").value;
		var url="../../"+module+"_"+sub+"_servlet_action?action=modify_record"+
			"&id="+id+"&project_id="+projectId+"&title="+title+"&content="+content+"&begin_time="+
			beginTime+"&end_time="+endTime+"&expect_end_time="+expectEndTime+"&executor="+executor+"&end_tag="+endTag+"&status="+status;
		alert(url);
		$.post(url,function(e){processModifyRecordResult(e);}).error(function(XMLHttpRequest, textStatus, errorThrown){
			alert("发生错误："+XMLHttpRequest.status+","+XMLHttpRequest.readyState+","+textStatus);
		})
	}
	var processModifyRecordResult=function(data){
		var json=eval("("+data+")");
		if(checkSession(json)){
			window.location="../../home/main/login.jsp";
		}
		if(json.result_code==0){
			window.location.reload();
		}
	}
	var clearTable=function(){
		$('.datatable').dataTable().fnClearTable();
	}
	var checkSession=function(json){
		var ok=true;
		if(json.result_code==3){
			ok=false;
		}
		return ok;
	}
	var reload=function(){
		window.location.reload();
	}
	return {
		init: function() {
			initRecordListHeader();
			initRecordList();
		},
		deleteRecord:function(id){
			deleteRecord(id);
		},
		addNew:function(){
			addNew();
		},
		modifyRecord:function(){
			modifyRecord();
		},
		showRecord:function(){
			showRecord();
		}
	};
}();
/*================================================================================*/
var Page = function() {
	var layout=2;
	var initPageStyle = function() {
		//hideFrameNav();
	};
	var hideFrameNav=function(){
		//根据需要隐藏掉一些不需要的面板
		//隐藏顶部菜单栏
		$("#page_top_div").hide();
		//隐藏旁边的菜单栏
		$("#page_sidebar_wrapper_div").hide();
		//隐藏page header
		$("#page_header_div").hide();
		//隐藏按钮工具
		$("#tool_button_div").hide();
		//隐藏掉风格板块
		$("#style_customizer_div").hide();
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
	};
	var layoutRecord=function(){
		if(layout==1)
			window.location.href="record_list.jsp";
		if(layout==2)
			window.location.href="todo_list.jsp";
	}
	var processError=function(json){
		Frame.processError(json);
	};
	var searchRecord=function(){
		window.location.href="todo_query.jsp";
	};
	return {
		init: function() {
			initPageStyle();
			handleButtonEvent();
		},
		searchRecord:function(){
			searchRecord();
		},
		layoutRecord:function(){
			layoutRecord();
		},
		processError:function(json){
			processError(json);
		}
	}
}();
/*================================================================================*/

function initLeftMenu(module){
	delMenu();
	document.title="期货交易系统";
	if(module=="home"){
		$.post("../../common_data_action?action=get_main_menu&table_name=document_main&module="+module,function(e){processMenuItemResult(e)});
	}else{
		$.post("../../common_data_action?action=get_main_menu&table_name="+module+"_tree&module="+module,function(e){processMenuItemResult(e)});
	}
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

