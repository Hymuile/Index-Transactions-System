<%@page contentType="text/html; charset=UTF-8"%>
<meta charset="utf-8" />
<%
	session.removeAttribute("user_role");
	session.removeAttribute("user_id");
	session.removeAttribute("user_name");
	session.removeAttribute("user_authority");
	session.removeAttribute("user_email");
	session.removeAttribute("user_margin");
	session.removeAttribute("user_balance");
	session.removeAttribute("user_city");
%>
<script>
window.location="login.jsp";
</script>
