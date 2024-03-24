
<%@page import="fashion.dto.Member"%>
<%@page import="fashion.dao.UserDao"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>login_proc.jsp</title>
</head>
<body>

<jsp:useBean id="userDao" class="fashion.dao.UserDao" scope="session"/>
    <%
        request.setCharacterEncoding("utf-8");
    
        String username = request.getParameter("name");
        String password = request.getParameter("password");

        
        boolean loggedIn = userDao.checkLogin(username, password);
        
        
        //userdao로 이름 불러서 id값 저장해야
        
        
        
        // 로그인 성공 여부에 따라 처리
        if (loggedIn) {
        	
        	Member mem = userDao.findUserByName(username);
            // 세션에 사용자 정보 저장
            session.setAttribute("user", username);
            session.setAttribute("userId",mem.getUserId());
            
            int id = (int)session.getAttribute("userId");
            
            System.out.println("id 값 : " + id);
            
          
    %>
    		<script>
                alert("로그인 성공");
                window.location.href = "index.jsp";
            </script>
    <%
        } else {
            // 로그인 실패 시 메시지 출력 및 현재 페이지를 새로고침
    %>
            <script>
                alert("로그인에 실패했습니다. 다시 시도해주세요");
                window.location.href = document.referrer;
            </script>
    <%
        }
    %>
</body>
</html>