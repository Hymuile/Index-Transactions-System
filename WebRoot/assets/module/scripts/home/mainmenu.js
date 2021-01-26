function initLeftMenu(module){
	delMenu();
	document.title="期货交易系统";
	if(module=="home"){
		$.post("../../common_data_action?action=get_main_menu&table_name=document_main&module="+module,function(e){processMenuItemResult(e)});
	}else{
		$.post("../../common_data_action?action=get_main_menu&table_name=document_main"+"&module="+module,function(e){processMenuItemResult(e)});
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
				var html="<a href=\""+href+"\"><i class=\"icon-home\"></i><span class=\"title\">"+itemName+"</span></a>";

				li.innerHTML=html;
				ul.appendChild(li);
				
			}
		}
		var menuDiv=document.getElementById("sidebar_menu_div");
		menuDiv.appendChild(ul);
	}
}
