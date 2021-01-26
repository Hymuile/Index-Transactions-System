var ComponentsPickers = function () {
	//格式化日期
	var formatDate = function (date,format) {
	    var y = date.getFullYear();
	    var m = date.getMonth() + 1;
	    m = m < 10 ? ('0' + m) : m;
	    var d = date.getDate();
	    d = d < 10 ? ('0' + d) : d;
	    return y + '-' + m + '-' + d;
	};
	var formatDateTime = function (date,format) {
	    var y = date.getFullYear();
	    var m = date.getMonth() + 1;
	    m = m < 10 ? ('0' + m) : m;
	    var d = date.getDate();
	    d = d < 10 ? ('0' + d) : d;
	    var h = date.getHours();
	    var minute = date.getMinutes();
	    var second = date.getSeconds();
	    minute = minute < 10 ? ('0' + minute) : minute;
	    second = second < 10 ? ('0' + second) : second;
	    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
	};
	var parseDate = function (date) {
	    var t = Date.parse(date);
	    if (!isNaN(t)) {
	        return new Date(t);
	    } else {
	        return new Date();
	    }
	};
	var handleDatetimePicker = function () {
        if (!jQuery().datetimepicker) {
            return;
        }
        $(".form_datetime").datetimepicker({
            autoclose: true,
            isRTL: Metronic.isRTL(),
            format: "yyyy-mm-dd hh:ii:ss",
            pickerPosition: (Metronic.isRTL() ? "bottom-right" : "bottom-left")
        });
        $('body').removeClass("modal-open"); // fix bug when inline picker is used in modal
    }
    return {
        //main function to initiate the module
        init: function () {
            handleDatetimePicker();
        },
        parseDate:function(date){
        	return parseDate(date);
        },
        formatDateTime:function(date,format){
        	return formatDateTime(date,format);        	
        },
        formatDate:function(date,format){
        	return formatDate(date,format);
        }
    };
}();
/*================================================================================*/
/**
 * 时间对象的格式化;
 */
Date.prototype.format = function(format) {
	/*
	* eg:format="YYYY-MM-dd hh:mm:ss";
	*/
	var o = {
		"M+" :this.getMonth() + 1, // month
		"d+" :this.getDate(), // day
		"h+" :this.getHours(), // hour
		"m+" :this.getMinutes(), // minute
		"s+" :this.getSeconds(), // second
		"q+" :Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" :this.getMilliseconds()
		// millisecond
	}
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}
