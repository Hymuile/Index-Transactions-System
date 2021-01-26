var module="project";
var sub="statistic";
jQuery(document).ready(function() {
	console.log("js初始化执行开始！");
	Metronic.init(); // init metronic core components
	Layout.init(); // init current layout
	QuickSidebar.init(); // init quick sidebar
	Demo.init(); // init demo features
	ComponentsDropdowns.init();
	Frame.init(module,sub);
	Page.init();
	Record.init();
	ChartsFlotcharts.init();
	ChartsFlotcharts.initBarCharts();
	console.log("js初始化执行完毕！");
});
/*================================================================================*/
var Record = function() {
	var userId;
	var userName;
	var recordCount=0;
	var initRecordStyle = function() {
	};
	var initRecordList = function(){
		initRecordListHeader();
		getStatisticResult();
		updateTableList();
	}
	var initRecordListHeader = function() {
		var html="";
		html=html+"						<tr>";
		html=html+"							<th class=\"table-checkbox\"><input type=\"checkbox\" class=\"group-checkable\" data-set=\"#record_list .checkboxes\" /></th>";
		html=html+"							<th>时间段</th>";
		html=html+"							<th>数量</th>";
		html=html+"							<th>颜色类型</th>";
		html=html+"							<th>颜色说明</th>";
		html=html+"						</tr>";
		$("#record_list_head").html(html);
	};
	var updateTableList=function(json){
		var action="get_statistic_record";
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
						sReturn = '<input type="checkbox" class="checkboxes" value="'+data+'"/>';
						return sReturn;
					},"orderable": false
				},{},{},{},{}],
			"aLengthMenu": [[10,15,20,25,40,50,-1],[10,15,20,25,40,50,"所有记录"]],
			"fnDrawCallback": function(){$(".checkboxes").uniform();$(".group-checkable").uniform();},
			"fnServerData":function (sSource, aoData, fnCallback, oSettings) {
				$.ajax({
					"dataType": 'json',
					"type": "POST",
					"url": sSource,
					"data": aoData,
					"success": function(json){
						Metronic.stopPageLoading();	//停止等待动画
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
			"sAjaxSource":"../../"+module+"_"+sub+"_servlet_action?action="+action+"&exist_resultset=1" 
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
	var getStatisticResult=function(){
		var action="get_statistic_record";
		var url="../../"+module+"_"+sub+"_servlet_action?action="+action+"&exist_resultset=1";
		$.post(url,function(json){
			console.log(JSON.stringify(json));
			if(json.result_code==0){
				var list=json.aaData;
				var image=json.result_image;
				if(list!=null){
					showStatisticList(json);
					showStatisticImage(image);
				}
			}else{
				if(Page!=null){
					Page.processError(json);
				}
			}
		});
	}
	var showStatisticList=function(json){
		return;
		updateTableList(json);
		var list=json.aaData;
		var html="";
		html=html+"<ul>";
		html=html+"序号----时间段----总数";
		for(var i=0;i<list.length;i++){
			html=html+"<li>"+list[i][0]+"----"+list[i][1]+","+list[i][2]+"，牌照颜色："+list[i][3]+"</li>";
		}
		html=html+"</ul>";
		$("#record_list_div").html(html);
	}
	var showStatisticImage=function(imageFile){
		$("#result_image").attr("src","../../../"+imageFile+"?"+Math.random());
	}
	var getRecord = function(){
		Metronic.startPageLoading({message: '正在查询中，请稍候...'});	//开始等待动画
		var existResultset=$("#exist_resultset").val();
		var url="../../"+module+"_"+sub+"_servlet_action?action=get_statistic_record&exist_resultset="+existResultset;
		$.post(url,function(json){
			console.log(JSON.stringify(json));
			if(json.result_code==0){
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
		window.location.href="record_view.jsp?id="+id;
	};
	var deleteRecord = function(id){
		if(confirm("您确定要删除这条记录吗？")){
			if(id>-1){
				$.post("../../"+module+"_"+sub+"_servlet_action?action=delete_record&id="+id,function(json){
					if(json.result_code==0){
						var count=json.count;
						var amount=json.amount;
						window.location.reload();
						alert("已经从数据表删除该记录！");
					}
				})
			}
		}
	};
	var exportRecord=function(exportType){
		if(Record.recordCount>0){
			if(Record.recordCount<2000 || exportType==1){
				var url="../../"+module+"_export_servlet_action?action=get_export_record&exist_resultset=1";
				$.post(url,function(json){
					console.log(JSON.stringify(json));
					if(json.result_code==0){
						var list=json.aaData;
						if(list!=null && list.length>0){
							if(confirm("您当前有 "+list.length+" 项未完成导出工作，继续进行新的导出吗？")){
								window.location.href="../../"+module+"_"+sub+"_servlet_action?action=export_statistic_resultset&export_type="+exportType+"&exist_resultset=1";
							}else{
								if(confirm("您要查看当前已经在进行的导出吗？")){
									window.location.href="../../user/center/export_list.jsp";
								}
							}
						}else{
							window.location.href="../../"+module+"_"+sub+"_servlet_action?action=export_statistic_resultset&export_type="+exportType+"&exist_resultset=1";
						}
					}else{
						if(Page!=null){
							Page.processError(json);
						}
					}
				});
			}else{
				Frame.showMsg("当前要导出的记录太多，目前限制2000条以内才能导出，请您重新缩小范围查询！");
			}
		}else{
			Frame.showMsg("您当前的记录结果为空，请您设置条件查询后再导出！");
		}
	};
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
		exportRecord:function(exportType){
			exportRecord(exportType);
		},
		viewRecord:function(id){
			viewRecord(id);
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
	};
	var processError=function(json){
		if(json.result_code>=10){
			showMsg(json.result_msg);
		}else{
			if(Frame!=null)
				Frame.processError(json);
		}
	};
	var handleButtonEvent=function(){
		$('#return_button').click(function() {Page.confirmBack();});
		$('#search_button').click(function() {Page.searchRecord();});
		$('#delete_button').click(function() {Page.deleteRecord();});
		$('#add_button').click(function() {Page.addRecord();});
		$('#submit_button').click(function() {Page.submitRecord();});
		$('#tools_menu_reload').click(function() {Page.reload();});
		$('#help_button').click(function() {Page.help();});
	};
	var addRecord=function(){
		window.location.href=sub+"_add.jsp";
	}
	var showMsg=function(msg){
		showMessage(msg);
	}
	var data=new Array();
	var showResult=function(json){
		var list=json.aaData;
		if(list!=null){
			showRecordList(list);
		}
		ChartsFlotcharts.drawChart(data);
	};
	var showRecordList=function(list){
		var title="管理";
		var tip="当前查询到了 "+list.length+" 条记录";
		html="													<div><span id=\"tip_div\"></span>";
		var l="";
		for(var i=0;i<list.length;i++){
			showRecord(list[i]);
			data[i]=new Array();
			data[i][0]=i;
			data[i][1]=list[i][1];
		}
		html=html+"													</div>";
		$("#record_list_div").html(html);
		if($("#title_div")) $("#title_div").html(title);
		if($("#tip_div")) $("#tip_div").html(tip);
	};
	var showRecord = function(json){
		var image="../../assets/module/img/public/blog.jpg";
		html=html+"														<div style=\"clear:both;width:100%;margin-top:5px;border:0px solid blue;\">";
		html=html+"															<div style=\"float:left;border:0px solid green;\">";
		html=html+"																<img src=\""+image+"\" style=\"width:100px;height:auto;border-radius:50%!important;border:0px solid red;\"></img>";
		html=html+"															</div>";
		html=html+"															<div style=\"display:table-cell;margin-left:10px;margin-right:10px;margin-top:10px;margin-bottom:10px;border:0px solid blue;\"><p>";
		html=html+"																<span>标题："+json[1]+"</span><p>";
		html=html+"																<span>内容："+json[2]+"</span><p>";
		html=html+"																<span>时间："+json[4]+"</span><p>";
		html=html+"																<span>状态："+json[6]+"</span><p>";
		html=html+"																<button  type=\"button\" class=\"btn green-haze btn-circle btn-sm\" onclick=\"Record.deleteRecord("+json[0]+");\">删除</button>";
		html=html+"																<button  type=\"button\" class=\"btn green-haze btn-circle btn-sm\" onclick=\"Record.viewRecord("+json[0]+");\">详细信息</button>";
		html=html+"															</div>";
		html=html+"														</div>";
	};
	var returnWeiXin=function(){
		WeixinJSBridge.call('closeWindow');
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
	var mapRecord=function(){
		window.location.href="statistic_map.jsp?exist_resultset=1";
	};
	var printRecord=function(){
		var url="statistic_print.jsp?exist_resultset=1";
		window.open(url);
	};
	var statisticQuery=function(){
		window.location.href="todo_statistic_query.jsp";
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
		searchRecord:function(){
			searchRecord();
		},
		reload:function(){
			window.location.reload();
		},
		returnWeiXin:function(){
			returnWeiXin();
		},
		printRecord:function(){
			printRecord();
		},
		mapRecord:function(){
			mapRecord();
		},
		statisticQuery:function(){
			statisticQuery();
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
var ChartsFlotcharts = function() {
    // bar chart:
    var data = GenerateSeries(0);
    function GenerateSeries(added) {
        var data = [];
        var start = 100 + added;
        var end = 200 + added;

        for (i = 1; i <= 20; i++) {
            var d = Math.floor(Math.random() * (end - start + 1) + start);
            data.push([i, d]);
            start++;
            end++;
        }
        return data;
    }

    var options = {
        series: {
            bars: {
                show: true
        	}
    	},
        bars: {
            barWidth: 0.8,
            lineWidth: 0, // in pixels
            shadowSize: 0,
            align: 'left'
        },
        grid: {
            tickColor: "#eee",
            borderColor: "#eee",
            borderWidth: 1,
            hoverable: true
        },
        colors:["##3598dc", "##3128dc"]//这里就是颜色自定义的地方。
    };
	var drawChart=function(data){
        if ($('#result_vertical_bar_chart').size() !== 0) {
            $.plot($("#result_vertical_bar_chart"), [{
                data: data,
                lines: {
                    lineWidth: 1,
                },
                shadowSize: 0
            }], options);
        }
	}
    return {
        //main function to initiate the module
        init: function() {
            Metronic.addResizeHandler(function() {
                //Charts.initPieCharts();
            });
        },
        initBarCharts: function() {
            if ($('#result_vertical_bar_chart').size() !== 0) {
                $.plot($("#result_vertical_bar_chart"), [{
                    data: [[1,2],[2,5],[3,3],[4,9],[8,4]],
                    lines: {
                        lineWidth: 1,
                    },
                    shadowSize: 0
                }], options);
            }
            $("#result_vertical_bar_chart").bind("plothover", function (event, pos, item) {    
                if (item) {    
                    if (previousPoint != item.dataIndex) {    
                        previousPoint = item.dataIndex;    
                        $("#tooltip").remove();
                        var x = item.datapoint[0].toFixed(0);
                        var y = item.datapoint[1].toFixed(0);    
        
                        var tip = "当前值："+x+","+y;
                        showTooltip(item.pageX, item.pageY,tip);    
                    }
                }    
                else {    
                    $("#tooltip").remove();    
                    previousPoint = null;    
                }    
            });
            // 节点提示    
            var showTooltip=function(x, y, contents) {    
                $('<div id="tooltip">' + contents + '</div>').css( {    
                    position: 'absolute',    
                    display: 'none',    
                    top: y + 10,    
                    left: x + 10,    
                    border: '1px solid #fdd',    
                    padding: '2px',
                    'background-color': '#dfeffc',    
                    opacity: 0.80    
                }).appendTo("body").fadeIn(200);    
            }
        },
        drawChart:function(data){
        	drawChart(data);
        }
    };
}();
