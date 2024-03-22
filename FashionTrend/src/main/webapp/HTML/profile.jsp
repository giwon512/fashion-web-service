<%@page import="fashion.dto.Member"%>
<%@page import="fashion.dao.UserDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="home.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원정보 수정</title>
<style>
.profile-body {
    font-family: 'Noto Sans KR', sans-serif;
    background-color: #f5f5f5;
    margin: 0;
    padding: 20px;
}

.container {
    max-width: 600px;
    margin: auto;
    background-color: #fff;
    padding: 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.header h1 {
    color: #333;
    text-align: center;
}

.input-group {
    margin-bottom: 15px;
}

.input-group label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
}

.input-group input, .input-group select {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

.button-group {
    text-align: right;
}

.button-group button {
    padding: 10px 20px;
    background-color: #5f3e63;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.button-group button:hover {
    background-color: #50314f;
}

</style>
</head>
<body class="profile-body">
  <div class="container">
    <div class="header">
      <h1>회원정보 수정</h1>
    </div>
    
  <!--
  	유저 정보를 어떻게 가져오냐?
  	로그인이 성공하면, 세션값에 
  
    -->
  

     <%
     	int userId = 2; // session.getAttribute로 해당 세션의 id값을 얻어옴. 지금은 하드코딩함.
        UserDao userDao = new UserDao();
        Member member = userDao.findUserById(userId); // 예시로 1번 ID를 사용
        member.setUserId(userId);
        
 
    %>
     <div class="index-content">
    <form action="profileUpdate.jsp" method="post">
        <input type="hidden" name="userId" value="<%= member.getUserId() %>"> <!-- 수정된 부분 -->
        <div class="input-group">
          <label for="name">이름</label>
          <input type="text" id="name" name="name" value="<%= member.getName() %>"> <!-- 수정된 부분 -->
        </div>
        <div class="input-group">
          <label for="password">비밀번호</label>
          <input type="password" id="password" name="password" value="<%= member.getPassword() %>"> <!-- 수정된 부분 -->
        </div>
        <div class="input-group">
          <label for="email">이메일</label>
          <input type="email" id="email" name="email" value="<%= member.getEmail() %>"> <!-- 수정된 부분 -->
        </div>
        <div class="input-group">
          <label for="gender">성별</label>
          <select id="gender" name="gender">
            <option value="M" <% if(member.getGender().equals("M")) out.print("selected"); %>>남자</option>
            <option value="F" <% if(member.getGender().equals("F")) out.print("selected"); %>>여자</option>
          </select>
        </div>
        <div class="input-group">
          <label for="address">주소</label>
          <input type="text" id="address" name="address" value="<%= member.getAddress() %>"> <!-- 수정된 부분 -->
        </div>
        <div class="button-group">
          <button type="submit">수정</button>
        </div>
      </form>
      </div>

  </div>
</body>
</html>