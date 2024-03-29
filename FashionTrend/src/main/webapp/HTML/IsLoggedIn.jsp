<%@page import="utils.JSFunction"%>
<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
if(session.getAttribute("user") == null){
	JSFunction.alertLocation("로그인 후 이용해주십시오","login.jsp",out);
	return;
}



%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>