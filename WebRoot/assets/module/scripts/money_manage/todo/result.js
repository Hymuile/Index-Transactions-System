jQuery(document).ready(function() {
	console.log("js初始化执行开始！");
	Metronic.init(); // init metronic core components
	Layout.init(); // init current layout
	QuickSidebar.init(); // init quick sidebar
	Demo.init(); // init demo features
	Frame.init(module,sub);
	Page.init();
	console.log("js初始化执行完毕！");
});
var module="weather";
var sub="list";
/*================================================================================*/
//关于页面的控件生成等操作都放在Page里，和Record独立，Record主要是和记录集交互
var Page = function() {
	var handleReturnEvent=function(){
		$('#return_button').click(function() {
			var redirectUrl=$("#redirect_url").val();
			var redirectParam=$("#redirect_param").val();
			var redirectParamName=$("#redirect_param_name").val();
			if(redirectUrl!=null){
				if(redirectParamName!=undefined &&redirectParamName!=null){
					window.location.href=redirectUrl+"?"+redirectParamName+"="+redirectParam;
				}else{
					window.location.href=redirectUrl;
				}
			}
		});
	}
	return {
		init: function() {
			handleReturnEvent();
		}
	}
}();//Page
/*================================================================================*/
