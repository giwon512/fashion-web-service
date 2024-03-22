<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그아웃</title>
</head>
<body>
<%
    // 세션에서 user 값을 가져와서 확인
    String user = (String) session.getAttribute("user");
    if (user != null) {
        // 세션에 user 값이 있을 경우 세션을 종료
        session.invalidate();
        out.println("로그아웃되었습니다.");
        response.sendRedirect("index.jsp");
    } else {
        out.println("이미 로그아웃되었거나 로그인 상태가 아닙니다.");
    }
%>
</body>
</html>
```