<%@page import="fashion.dto.Member"%>
<%@page import="fashion.dao.UserDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원정보 업데이트</title>
</head>
<body>
<div class="container">
    <h1>회원정보 업데이트</h1>

    <%
        // 요청으로부터 파라미터 받기
        
        request.setCharacterEncoding("utf-8");
    
    	if(request.getParameter("userId") != null){
    			String numberStr = request.getParameter("userId");
    	}
       
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        


        // userId를 정수로 파싱
        int userId;
        
        
        try {
            userId = Integer.parseInt(request.getParameter("userId"));
            System.out.println(userId);
        } catch (NumberFormatException e) {
            %>
            <p>올바른 사용자 ID를 입력해주세요.</p> <%
            return; 
        }

        // DAO 객체 생성 및 회원 정보 업데이트 메소드 호출
        UserDao userDao = new UserDao();
       
    
        Member member = new Member(userId, name, password,gender,email,address);
        int updateResult = userDao.updateUser(member);

        // 업데이트 결과에 따라 메시지 출력
        if (updateResult > 0) {
            %>
            <p>회원 정보가 성공적으로 업데이트되었습니다.</p>
            <a href="profile.jsp?userId=<%=userId%>">회원 정보 보기</a>
            <%
        } else {
            %>
            <p>회원 정보 업데이트에 실패하였습니다. 다시 시도해주세요.</p>
            <%
        }


    %> 
</div>
</body>
</html>