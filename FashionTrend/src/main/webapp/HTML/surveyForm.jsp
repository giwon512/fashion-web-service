<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="fashion.dto.Preference"%>
<%@ page import="fashion.dao.PreferenceDao"%>

<%@ include file="home.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>패션 선호도 설문 결과</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0px;
        }
        .survey-container {
            max-width: 600px;
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            margin: 20px auto;
        }
        .survey-container h1, .survey-container h2 {
            color: #333;
        }
        .survey-list {
            list-style-type: none;
            padding: 0;
        }
        .survey-item {
            padding: 8px;
            margin-bottom: 5px;
            background-color: #f8f8f8;
            border: 1px solid #ddd;
        }
    </style>
</head>
<body>

<div class="home-content">
    <div class="survey-container">
        <h1>패션 선호도 설문 결과</h1>
        <%
            String user = (String) session.getAttribute("user");
            Integer userId = (Integer) session.getAttribute("userId");
            if(user == null || userId == null){
        %>
                <p>로그인이 필요한 서비스입니다.</p>
        <%
            } else {
                out.print("<h1>" + user + "님의 패션 선호도</h1>");
                
                PreferenceDao dao = new PreferenceDao();
                List<Preference> preferences = dao.selectUserPreferences(userId);
                if(preferences.isEmpty()){
        %>
                    <p>선택한 선호도 정보가 없습니다.</p>
        <%
                } else {
        %>
                    <h2>선택한 브랜드</h2>
                    <ul class="survey-list">
        <%
                        for(Preference pref : preferences){
                            if(pref.getBrand() != null && !pref.getBrand().isEmpty()){
        %>
                                <li class="survey-item"><%= pref.getBrand() %></li>
        <%
                            }
                        }
        %>
                    </ul>
                    <h2>선택한 카테고리</h2>
                    <ul class="survey-list">
        <%
                        for(Preference pref : preferences){
                            if(pref.getCategory() != null && !pref.getCategory().isEmpty()){
        %>
                                <li class="survey-item"><%= pref.getCategory() %></li>
        <%
                            }
                        }
                    }
                }
        %>
                    </ul>
        <a href="survey.jsp">설문조사 페이지로 돌아가기</a>
    </div>
</div>

</body>
</html>
