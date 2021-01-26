<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@page import="java.util.*"%>
<%
	Enumeration enu = request.getParameterNames();
	System.out.println(enu.toString());
	while (enu.hasMoreElements()) {
		System.out.println("-------------");
		String name = (String) enu.nextElement();
		if (name.startsWith("**")) {
			//如果是数组参数，则逐个打印
%>
<h1><%=name%>:
	<%
	String canshu[] = request.getParameterValues(name);
			int i;
			for (i = 0; i < canshu.length; i++) {
%>
	<%=canshu[i]%>
	<%
		}
	%>
</h1>
<%
	} else {
			//如果不是数组参数，直接打印
%>
<h1><%=name%>:<%=request.getParameter(name)%></h1>
<%
	}
	}
%>
<script>
wx.scanQRCode({
    needResult: 1, //为 1 时，扫码返回的结果
    scanType: ["qrCode"], //指定扫二维码
    success: function (res) {
        if (res.errMsg === "scanQRCode:ok"){
            //{"resultStr":"", "errMsg":"scanQRCode:ok"}
            if(/^[https?:\/\/|tel:|mailto:]/i.test(data)) {
                location.href = data;
                return;
            }
            // 二维码ajax验票 
            // checkPost(data);
        }
    }
});
</script>