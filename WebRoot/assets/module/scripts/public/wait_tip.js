function showWaitTip(msg){
	document.all('LoadProcess_head').style.visibility ="visible";
	document.all('ifra_head').style.visibility = "visible";
	if(msg!=null) document.all('waitTip').innerHTML=msg;
}
function hideWaitTip(){
	document.all('LoadProcess_head').style.visibility ="hidden";
	document.all('ifra_head').style.visibility = "hidden";
}
