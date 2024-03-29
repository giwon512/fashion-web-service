<%@page import="utils.JSFunction"%>
<%@page import="fashion.dao.UserDao"%>
<%@page import="fashion.dto.Member"%>
<%@ page import="java.sql.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>signIn_proc.jsp</title>
</head>
<body>
<jsp:useBean id="userDao" class="fashion.dao.UserDao" scope="session"/>
<%
	request.setCharacterEncoding("utf-8");



String name = request.getParameter("name");
String password = request.getParameter("password");
String email = request.getParameter("email");
String gender = request.getParameter("gender");
String userAddress1 = request.getParameter("userAddress1");
String userAddress2 = request.getParameter("userAddress2");
String address = userAddress1 + " " + userAddress2;


	Member member = new Member();
	member.setName(name);
	member.setPassword(password);
	member.setEmail(email);
	member.setGender(gender);
	member.setAddress(address);
 
		
	
 	boolean isDuplicate = userDao.checkDuplicateUserID(name);     
 	

  if (!isDuplicate) {
      // 회원 정보를 데이터베이스에 저장합니다.
      boolean isSuccess = userDao.insertUser(member);
      if (isSuccess) {
    	  
    	  session.setAttribute("user", name);
    	  session.setAttribute("userId",member.getUserId());
    	  
    	  JSFunction.alertLocation("회원가입 성공", "survey.jsp", out);
         
      } else {
	

    	  JSFunction.alertBack("회원가입 실패", out);

      }
  } else {
	  
	  JSFunction.alertBack("이미 존재하는 아이디입니다.", out);
 	
     
   } 
  
  
  
  %>  
  


  
        	
</body>
</html>