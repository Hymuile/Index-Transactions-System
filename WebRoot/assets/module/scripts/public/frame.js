var Frame = function() {
	//一些公共变量，框架用到的，例如状态等
	var html="";
	var userId=null;
	var userName=null;
	var currentPageUrl=null;
	var initFrameStyle = function() {
		$(".page-content-single").css("background-color","#fff");
		$(".page-content-single").css("margin-left","0px");
		$(".page-content-single").css("margin-top","0px");
		$(".page-content-single").css("min-height","600px");
		$(".page-content-single").css("padding","25px 20px 10px 20px");
	};
	var getParameter=function(param){
		var paramValue="";
		var url=window.location.search;
		if(url.indexOf("?")!=-1)
		{
			var str = url.substr(1)
			strs = str.split("&");
			var key=new Array(strs.length);
			var value=new Array(strs.length);
			for(i=0;i<strs.length;i++)
			{
				key[i]=strs[i].split("=")[0]
				value[i]=unescape(strs[i].split("=")[1]);  //key[i]和value[i]分别是上个页面或者URL传过来的参数名和参数
				if(key[i]==param){
					paramValue=value[i];
				}
			}
		}
		return paramValue;
	};
	var getSystemStatus=function(){
		var url="../../common_data_action?action=get_system_status";
		$.post(url,function(data){
			var json=eval("("+data+")");
			var list=json.aaData;
			if(list!=null){}
		});
	};
	var getUserStatus=function(){
		var url="../../user_center_data_action?action=get_user_status";
		$.post(url,function(data){
			var json=eval("("+data+")");
			var list=json.aaData;
			userName=json.user_name;
			userId=json.user_id;
			showUser(userName);
			showUserAvatar(json.user_avatar);
			showUserNoticeList(json.aaNoticeData);
			showUserModuleStatus(json.aaModuleData);
			showUserBlogStatus("0");
			if(list!=null){}
		});
	};
	var getUserResource=function(){
		var url="../../user_center_data_action?action=get_user_resource";
		$.post(url,function(data){
			//console.log(JSON.stringify(data));
			if(data.result_code==0){
				var list=data.aaData;
				if(list){showUserResourceList(list);}
			}else{
				alert(data.result_msg);
			}
		});
	};
	var showUser=function(userName){
		var obj=$("#current_user");
		if(obj){
			obj.html(userName);
		}else{}
	};
	var showUserAvatar=function(userAvatar){
		var obj=$("#current_user_avatar");
		if(obj){
			obj.attr("src",userAvatar);
		}else{}
	};
	var showUserBlogStatus=function(unreadCount){	//博客未读评论数量
		var obj=$("#unread_blog_notice");
		if(obj){
			if(parseInt(unreadCount)>0){
				obj.html(unreadCount);
				obj.show();
			}
			else{
				obj.hide();
			}
		}else{}
	};
	var showUserModuleStatus=function(list){
		var count=0;
		if(list.length>0){
			for(var i=0;i<list.length;i++){
				count=count+showUserModule(list[i]);
			}
		}
		if(count>0){
			$("#unread_notice").html(count);
		}else{
			$("#unread_notice").hide();
		}
	}
	var showUserModule=function(json){
		return json[2];
	}
	var showUserNoticeList=function(list){
		html=$("#user_notice_div").html();
		var count=0;
		if(list.length>0){
			//notice_id,notice_title,count,action_type,action
			for(var i=0;i<list.length;i++){
				count=showUserNotice(list[i]);
			}
		}
		$("#user_notice_div").html(html);
		if(count>0){
			$("#user_notice_div_count").html(count);
			$("#user_notice_div_count_tip").html(count);
		}else{
			$("#user_notice_div_count").hide();
			$("#user_notice_div_count_tip").html("0");
		}
	}
	var showUserNotice=function(json){
		html=html+"						<li>";
		html=html+"							<ul class=\"dropdown-menu-list scroller\" style=\"height: 250px;\" data-handle-color=\"#637283\">";
		html=html+"								<li onclick=\"window.location.href='"+json[5]+"?action="+json[0]+"&content="+json[3]+"'\">";
		html=html+"									<a href=\"javascript:;\">";
		html=html+"									<span class=\"time\" style=\"width:20px;height:20px;background:red;color:#FFF;border-radius:50%!important;\">"+json[2]+"</span>";
		html=html+"									<span class=\"details\">";
		html=html+"									<span class=\"label label-sm label-icon label-success\">";
		html=html+"									<i class=\"fa fa-plus\"></i>";
		html=html+"									</span>";
		html=html+"									"+json[1]+"</span>";
		html=html+"									</a>";
		html=html+"								</li>";
		html=html+"							</ul>";
		html=html+"						</li>";
		return json[2];
	}
	var showUserResourceList=function(list){
		var currentPage=window.location.href;
		for(var i=0;i<list.length;i++){
			var pageUrl=list[i][3];
			if(currentPage.indexOf(pageUrl)>0){
				showResource(list[i]);
			}else{}
		}
	};
	var showResource=function(json){
		var obj=$("#"+json[1]);
		if(obj)
			obj.show();
	};
	var returnWeiXin=function(){
		WeixinJSBridge.call('closeWindow');
	};
	var showMsg= function(msg){
		$("#portlet-config #content_div").html(msg);
		$("#portlet-config").modal("show");
	}
	var showModal= function(msg){
		$("#portlet_modal_msg #content_div").html(msg);
		$("#portlet_modal_msg").modal("show");
	}
	var showConfirm= function(msg){
		$("#portlet_modal_confirm #content_div").html(msg);
		$("#portlet_modal_confirm").modal("show");
	}
	var isWeiXin=function(){
	    var ua = window.navigator.userAgent.toLowerCase();
	    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
	        return true;
	    }else{
	        return false;
	    }
	}
	var initWeiXin=function(){
		if(isWeiXin()){
			if($("#return_weixin_button")!=null)
				$("#return_weixin_button").show();
		}else{
			if($("#return_weixin_button")!=null)
				$("#return_weixin_button").hide();
		}
	}
	var processError=function(json){
		//这里Frame仅处理result_code<10的情况，其他都给具体的js去接管
		if(json.result_code==1){}
		if(json.result_code==2){}
		if(json.result_code==3){
			//session超时了
			showMsg("长时间没有操作导致系统需要重新登陆，请您前往登陆界面登陆。");
			window.location.href="../../home/main/login.jsp";
		}
	};
	var logVisit=function(){
		var url="../../security_log_servlet_action?action=log_visit&page_url="+window.location.pathname;
		$.post(url,function(data){
			var json=eval("("+data+")");
			if(json.result_code==0){
			}else{
				processError(json);
			}
		});
	};
	return {
		init: function(module,sub) {
			initFrameStyle();
			initLeftMenu(module);
			getSystemStatus();
			//getUserStatus();
			//getUserResource();
			//initWeiXin();
			//logVisit();
			DraggableDialog.init();
		},
		processError:function(json){
			processError(json);
		},
		returnWeiXin:function(){returnWeiXin();},
		hideWaitTip:function(){},
		getUserId:function(){return userId},
		getUserName:function(){return userName},
		showWaitTip:function(){},getParameter:function(param){return getParameter(param);},
		showMsg:function(msg){showMsg(msg);},
		isWeiXin:function(){return isWeiXin();}
	};
}();
/**参数说明：
 * 根据长度截取先使用字符串，超长部分追加…
 * str 对象字符串
 * len 目标字节长度
 * 返回值： 处理结果字符串
 */
function cutString(str, len) {
	//length属性读出来的汉字长度为1
	if(str.length*2 <= len) {
		return str;
	}
	var strlen = 0;
	var s = "";
	for(var i = 0;i < str.length; i++) {
		s = s + str.charAt(i);
		if (str.charCodeAt(i) > 128) {
			strlen = strlen + 2;
			if(strlen >= len){
				return s.substring(0,s.length-1) + "...";
			}
		} else {
			strlen = strlen + 1;
			if(strlen >= len){
				return s.substring(0,s.length-2) + "...";
			}
		}
	}
	return s;
}
var DraggableDialog = function() {
	var html="";
	var draggableDialogId="draggable_dialog";
	var initDialog=function(){
		html=html+"<div class=\"modal fade draggable-modal ui-draggable in\" id=\""+draggableDialogId+"\" name=\""+draggableDialogId+"\" tabindex=\"-1\" role=\"basic\" aria-hidden=\"true\" style=\"display: none; width: auto; height: auto; margin:auto;\">";
		html=html+"	<div class=\"modal-dialog\">";
		html=html+"		<div class=\"modal-content\">";
		html=html+"			<div class=\"modal-header ui-draggable-handle\">";
		html=html+"				<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\"></button>";
		html=html+"				<h4 class=\"modal-title\">";
		html=html+"					";
		html=html+"				</h4>";
		html=html+"			</div>";
		html=html+"			<div class=\"modal-body\">";
		html=html+"				";
		html=html+"			</div>";
		html=html+"			<div class=\"modal-footer\">";
		html=html+"				<button type=\"button\" id=\"cancel_button\" class=\"btn default\" data-dismiss=\"modal\">";
		html=html+"					取消";
		html=html+"				</button>";
		html=html+"				<button type=\"button\" id=\"ok_button\" class=\"btn green\">";
		html=html+"					保存";
		html=html+"				</button>";
		html=html+"			</div>";
		html=html+"		</div>";
		html=html+"		<!-- /.modal-content -->";
		html=html+"	</div>";
		html=html+"	<!-- /.modal-dialog -->";
		html=html+"</div>";
		$(document).find("body").append(html);
		$("#"+draggableDialogId).draggable({handle: ".modal-header"});
	}
	var showMsg=function(msg,title){
		if(title==undefined) title="标题";
		$("#"+draggableDialogId+" .modal-title").html(title);
		$("#"+draggableDialogId+" .modal-body").html(msg);
		$("#"+draggableDialogId).show();
	};
	var setCancel=function(f){
		$(".modal-footer #cancel_button").click(f);
	};
	var setOk=function(f){
		$(".modal-footer #ok_button").click(f);
	};
	var close=function(){
		$("#"+draggableDialogId).hide();
	};
	var setButtonTitle=function(y,n){
		$("#cancel_button").html(n);
		$("#ok_button").html(y);
	}
	return {
		init: function() {
			initDialog();
		},
		showMsg:function(msg,title){showMsg(msg,title);},
		setId:function(id){
			this.draggableDialogId=id;
		},
		close:function(){close();},
		setButtonTitle:function(y,n){setButtonTitle(y,n);},
		onCancel:function(f){onCancel(f);},onOk:function(f){onOk(f);},
		setCancel:function(f){setCancel(f);},setOk:function(f){setOk(f);}
	};
}();
