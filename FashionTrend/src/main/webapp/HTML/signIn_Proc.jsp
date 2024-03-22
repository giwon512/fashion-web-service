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
 
  
	UserDao userDAO = new UserDao();
		
	
 	boolean isDuplicate = userDAO.checkDuplicateUserID(name);     //여기서 커넥 종료를 안해줘서
 	
	UserDao userDAO1 = new UserDao();
  if (!isDuplicate) {
      // 회원 정보를 데이터베이스에 저장합니다.
      boolean isSuccess = userDAO1.insertUser(member);
      if (isSuccess) {
          out.println("회원가입이 완료되었습니다.");
      } else {
          out.println("회원가입에 실패하였습니다. 다시 시도해주세요.");
      }
  } else {
      // 아이디가 중복되는 경우 메시지를 출력합니다.
      out.println("이미 사용 중인 아이디입니다. 다른 아이디를 선택해주세요.");
  }   

  
 


%> 
        	
</body>
</html>