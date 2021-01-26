var module="futures";
var sub="index";
var existResultset="0";
jQuery(document).ready(function() {
	console.log("js初始化执行开始！");
	Metronic.init(); // init metronic core components
	Layout.init(); // init current layout
	ComponentsDropdowns.init();
	Demo.init(); // init demo features
	Frame.init(module,sub);
	Page.init();
	Record.init();
	console.log("js初始化执行完毕！");
});
/* ================================================================================ */

var getKLine=function(data){
	var url = "../../futures_index_servlet_action?action=get_k_line_data&index_id="+data[1];
	$.post(url,function(e){processKLine(e)});
}

var processKLine= function(e){
	var json = eval("("+e+")");
	var data = json;
//	echarts 1. 配置和数据
	var upColor = '#ec0000';
	var upBorderColor = '#8A0000';
	var downColor = '#00da3c';
	var downBorderColor = '#008F28';

	var data0 = splitData(data);

	function splitData(rawData) {
		var categoryData = [];
		var values = []
		for (var i = 0; i < rawData.length; i++) {
			categoryData.push(rawData[i].date);
			let temp=[];
			temp.push(parseFloat(rawData[i].open));
			temp.push(parseFloat(rawData[i].low));
			temp.push(parseFloat(rawData[i].high));
			temp.push(parseFloat(rawData[i].close));

			values.push(temp);
		}
		return {
			categoryData: categoryData,
			values: values
		};
	}
	function calculateMA(dayCount) {
		var result = [];
		for (var i = 0, len = data0.values.length; i < len; i++) {
			if (i < dayCount) {
				result.push('-');
				continue;
			}
			var sum = 0;
			for (var j = 0; j < dayCount; j++) {
				sum += data0.values[i - j][1];
			}
			result.push(sum / dayCount);
		}
		return result;  
	}
	option = {
			title: {
				text: "",
				left: 0
			},
			tooltip: {
				trigger: 'axis',
				axisPointer: {
					type: 'cross'
				}
			},
			legend: {
				data: ['日K', 'MA5', 'MA10', 'MA20', 'MA30']
			},
			grid: {
				left: '5%',
				right: '5%',
				bottom: '5%'
			},
			xAxis: {
				type: 'category',
				data: data0.categoryData,
				scale: true,
				boundaryGap : false,
				axisLine: {onZero: false},
				splitLine: {show: false},
				splitNumber: 20,
				min: 'dataMin',
				max: 'dataMax'
			},
			yAxis: {
				scale: true,
				splitArea: {
					show: true
				}
			},
			dataZoom: [
				{
					type: 'inside',
					start: 50,
					end: 100
				},
				{
					show: true,
					type: 'slider',
					y: '90%',
					start: 50,
					end: 100
				}
				],
				series: [
					{
						name: '日K',
						type: 'candlestick',
						data: data0.values,
						itemStyle: {
							normal: {
								color: upColor,
								color0: downColor,
								borderColor: upBorderColor,
								borderColor0: downBorderColor
							}
						},
						markPoint: {
							label: {
								normal: {
									formatter: function (param) {
										return param != null ? Math.round(param.value) : '';
									}
								}
							},
							data: [
								{
									name: 'XX标点',
									coord: ['2019/5/31', 2300],
									value: 2300,
									itemStyle: {
										normal: {color: 'rgb(41,60,85)'}
									}
								},
								{
									name: 'highest value',
									type: 'max',
									valueDim: 'highest'
								},
								{
									name: 'lowest value',
									type: 'min',
									valueDim: 'lowest'
								},
								{
									name: 'average value on close',
									type: 'average',
									valueDim: 'close'
								}
								],
								tooltip: {
									formatter: function (param) {
										return param.name + '<br>' + (param.data.coord || '');
									}
								}
						},
						markLine: {
							symbol: ['none', 'none'],
							data: [
								[
									{
										name: 'from lowest to highest',
										type: 'min',
										valueDim: 'lowest',
										symbol: 'circle',
										symbolSize: 10,
										label: {
											normal: {show: false},
											emphasis: {show: false}
										}
									},
									{
										type: 'max',
										valueDim: 'highest',
										symbol: 'circle',
										symbolSize: 10,
										label: {
											normal: {show: false},
											emphasis: {show: false}
										}
									}
									],
									{
									name: 'min line on close',
									type: 'min',
									valueDim: 'close'
									},
									{
										name: 'max line on close',
										type: 'max',
										valueDim: 'close'
									}
									]
						}
					},
					{
						name: 'MA5',
						type: 'line',
						data: calculateMA(5),
						smooth: true,
						lineStyle: {
							normal: {opacity: 0.5}
						}
					},
					{
						name: 'MA10',
						type: 'line',
						data: calculateMA(10),
						smooth: true,
						lineStyle: {
							normal: {opacity: 0.5}
						}
					},
					{
						name: 'MA20',
						type: 'line',
						data: calculateMA(20),
						smooth: true,
						lineStyle: {
							normal: {opacity: 0.5}
						}
					},
					{
						name: 'MA30',
						type: 'line',
						data: calculateMA(30),
						smooth: true,
						lineStyle: {
							normal: {opacity: 0.5}
						}
					},
					]
	};
	// 2. 创建echarts实例
	var myChart = echarts.init(document.getElementById("KLine"));
	// 3. 作用
	myChart.clear();
	myChart.setOption(option);
}

var processTrade = function(data){
	$("#order_name").val(data[2]);
	var number = $("#order_number").val();
	$("#order_confirm").click(function(){
		var type = $("#order_type").val();
		if(type != "no"){
			$.ajax({
				type:"POST",
				url:"../../futures_index_servlet_action",
				data:{
					action:"test_trade",
					order_id:$("#order_id").val(),
					order_name:$("#order_name").val(),
					order_number:$("#number").val()
				},
				success:function(e){
					data = eval("("+e+")");
					alert(data.result_msg);
				}
			});
		}else{
			alert("请选择交易类型");
		}
	});

}

var Record = function() {
	var userId;
	var userName;
	var recordCount=0;
	var html="";
	var initRecordListHeader = function() {
		var html="";
		html=html+"						<tr>";
		html=html+"							<th class=\"table-checkbox\"><input type=\"checkbox\" class=\"group-checkable\" data-set=\"#record_list .checkboxes\" /></th>";
		html=html+"							<th>期货/股票ID</th>";
		html=html+"							<th>期货/股票名称</th>";
		html=html+"							<th>涨跌额</th>";
		html=html+"							<th>最新价格</th>";
		html=html+"							<th>昨收</th>";
		html=html+"							<th>今开</th>";
		html=html+"							<th>最高价</th>";
		html=html+"							<th>最低价</th>";
		html=html+"							<th>买入</th>";
		html=html+"							<th>卖出</th>";
		html=html+"							<th>k线图</th>";
		html=html+"						</tr>";
		$("#record_list_head").html(html);
	};

	var initRecordList=function(){
		var existResultset=$("#exist_resultset").val();
		var tables=$('.datatable').dataTable({
			"destroy":true,
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
			"columnDefs": [
				{
					// targets用于指定操作的列，从第0列开始，-1为最后一列
					// return后边是我们希望在指定列填入的按钮代码
					"targets": -1,
					"orderable":false,
					"render": function ( data, type, full, meta ) {
						if(full[0] > 33){
							return "";
						}else{
							return "<button type=\"button\" class=\"btn btn-circle btn-info\">K线图</button>";
						}
					}
				}
				],
				"aoColumns": [{"mRender":function(data,type,full){
					if(full[3].toString().charAt(0)=='-') sReturn = "<div style=\"color:red;\">"+data+"</div>";
					else	sReturn ="<div style=\"color:green\">"+data+"</div>";
					return sReturn;
				},"orderable": true},
				{"mRender":function(data,type,full){
					if(full[3].toString().charAt(0)=='-') sReturn = "<div style=\"color:red;\">"+data+"</div>";
					else	sReturn ="<div style=\"color:green\">"+data+"</div>";
					return sReturn;
				},"orderable": true},
				{"mRender":function(data,type,full){
					if(full[3].toString().charAt(0)=='-') sReturn = "<div style=\"color:red;\">"+data+"</div>";
					else	sReturn ="<div style=\"color:green\">"+data+"</div>";
					return sReturn;
				},"orderable": true},
				{"mRender":function(data,type,full){
					if(full[3].toString().charAt(0)=='-') sReturn = "<div style=\"color:red;\">"+data+"</div>";
					else	sReturn ="<div style=\"color:green\">+"+data+"</div>";
					return sReturn;
				},"orderable": true},
				{"mRender":function(data,type,full){
					if(full[3].toString().charAt(0)=='-') sReturn = "<div style=\"color:red;\">"+data+"</div>";
					else	sReturn ="<div style=\"color:green\">"+data+"</div>";
					return sReturn;
				},"orderable": true},
				{"mRender":function(data,type,full){
					if(full[3].toString().charAt(0)=='-') sReturn = "<div style=\"color:red;\">"+data+"</div>";
					else	sReturn ="<div style=\"color:green\">"+data+"</div>";
					return sReturn;
				},"orderable": true},
				{"mRender":function(data,type,full){
					if(full[3].toString().charAt(0)=='-') sReturn = "<div style=\"color:red;\">"+data+"</div>";
					else	sReturn ="<div style=\"color:green\">"+data+"</div>";
					return sReturn;
				},"orderable": true},
				{"mRender":function(data,type,full){
					if(full[3].toString().charAt(0)=='-') sReturn = "<div style=\"color:red;\">"+data+"</div>";
					else	sReturn ="<div style=\"color:green\">"+data+"</div>";
					return sReturn;
				},"orderable": true},
				{"mRender":function(data,type,full){
					if(full[3].toString().charAt(0)=='-') sReturn = "<div style=\"color:red;\">"+data+"</div>";
					else	sReturn ="<div style=\"color:green\">"+data+"</div>";
					return sReturn;
				},"orderable": true},
				{"mRender":function(data,type,full){
					if(full[3].toString().charAt(0)=='-') sReturn = "<div style=\"color:red;\">"+data+"</div>";
					else	sReturn ="<div style=\"color:green\">"+data+"</div>";
					return sReturn;
				},"orderable": true},
				{"mRender":function(data,type,full){
					if(full[3].toString().charAt(0)=='-') sReturn = "<div style=\"color:red;\">"+data+"</div>";
					else	sReturn ="<div style=\"color:green\">"+data+"</div>";
					return sReturn;
				},"orderable": true},
				{}
				],
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
								var list=json.aaData;
								if(list.length>0){
									Record.recordCount=list.length;
									//这里进行数据处理显示
									fnCallback(json);

								}else{
									console.log("futures/index_data/record_list.js后台未返回任何数据");
								}
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

		$('.datatable').on('click', 'tr td:nth-child(12)',function(){
			var Row = $(this).parents('tr')[0];//通过获取该td所在的tr，即td的父级元素，取出第一列序号元素
			var Data = tables.fnGetData(Row);//得到这一行的json数据
			if(Data[0]<34){
				getKLine(Data);
			}
		});

		setInterval( function () {
			tables.api().ajax.reload(null,false);
		}, 1000);
	}



	var addNew=function(){
		var projectId=document.getElementById("project_id").value;
		var title=document.getElementById("title").value;
		var content=document.getElementById("content").value;
		var executor=document.getElementById("executor").value;
		var beginTime=document.getElementById("begin_time").value;
		var endTime=document.getElementById("end_time").value;
		var expectEndTime=document.getElementById("expect_end_time").value;
		var endTag=document.getElementById("end_tag").value;
		var status=document.getElementById("status").value;
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
		var projectId=document.getElementById("group_id").value;
		var title=document.getElementById("title").value;
		var content=document.getElementById("content").value;
		var beginTime=document.getElementById("begin_time").value;
		var endTime=document.getElementById("end_time").value;
		var expectEndTime=document.getElementById("expect_end_time").value;
		var executor=document.getElementById("executor").value;
		var endTag=document.getElementById("end_tag").value;
		var status=document.getElementById("status").value;
		var url="../../"+module+"_"+sub+"_servlet_action?action=modify_record"+
		"&id="+id+"&project_id="+projectId+"&title="+title+"&content="+content+"&begin_time="+
		beginTime+"&end_time="+endTime+"&expect_end_time="+expectEndTime+"&executor="+executor+"&end_tag="+endTag+"&status="+status;
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
		$('#query_single_index').click(function(){Page.queryRecord();});
	};

	var queryRecord = function(){
		var query_id = $("#query_id").val();
		if(query_id.length>0){
			if(query_id.length >10){
				alert("期货/股票ID过长");
			}else{
				var url = "../../futures_index_servlet_action?action=query_record&index_id="+query_id;
				$.post(url,function(e){
					console.log(e);
					var json = eval("("+e+")");
					var result= json.aaData;
					console.log(result);
					if(json.result_code == 3){
						console.log("找到了");
						let HTML = "";
						HTML += "<br>ID:"+result[0][1]+"<br>";
						HTML += "名称:"+result[0][2]+"<br>";
						
						HTML += "涨跌额:"+result[0][3]+"<br>";
						HTML += "最新价:"+result[0][4]+"<br>";
						HTML += "昨收:"+result[0][5]+"<br>";
						HTML += "今开:"+result[0][6]+"<br>";
						HTML += "最高价:"+result[0][7]+"<br>";
						HTML += "最低价:"+result[0][8]+"<br>";
						HTML += "买入价:"+result[0][9]+"<br>";
						HTML += "卖出价:"+result[0][10]+"<br>";
						$("#query-result").html(HTML);
					}else{
						$("#query-result").html("未找到ID为"+query_id+"的期货/股票");
					}
				});
			}
		}else{
			alert("请输入期货/股票ID");
		}
	}
	
	var layoutRecord=function(){
		if(layout==1)
			window.location.href="record_list.jsp";
		if(layout==2)
			window.location.href="futures_index_list.jsp";
	}
	var processError=function(json){
		Frame.processError(json);
	};
	var searchRecord=function(){
		window.location.href="futures_index_query.jsp";
	};
	return {
		init: function() {
			initPageStyle();
			handleButtonEvent();
		},
		searchRecord:function(){
			searchRecord();
		},
		queryRecord:function(){
			queryRecord();
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
