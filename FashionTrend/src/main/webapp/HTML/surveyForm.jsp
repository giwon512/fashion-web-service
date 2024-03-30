<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="fashion.dto.Preference"%>
<%@ page import="fashion.dao.PreferenceDao"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>패션 선호도 설문 결과</title>
<style>
    body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }
    .container { max-width: 600px; background: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); margin: 0 auto; }
    h2 { color: #333; }
    ul { list-style-type: none; padding: 0; }
    li { padding: 8px; margin-bottom: 2px; background-color: #f8f8f8; border: 1px solid #ddd; }
</style>
</head>
<body>

<%@ include file="home.jsp" %>
<div class="home-content">
<div class="container">
    <h1><%=session.getAttribute("user") %>님의 패션 선호도</h1>
    <%
        Integer userId = (Integer) session.getAttribute("userId");
        if(userId == null){
            out.println("<p>로그인이 필요한 서비스입니다.</p>");
        } else {
            PreferenceDao dao = new PreferenceDao();
            List<Preference> preferences = dao.selectUserPreferences(userId);

            if(preferences.isEmpty()){
                out.println("<p>선택한 선호도 정보가 없습니다.</p>");
            } else {
                out.println("<h2>선택한 브랜드</h2>");
                out.println("<ul>");
                for(Preference pref : preferences){
                    if(pref.getBrand() != null && !pref.getBrand().isEmpty()){
                        out.println("<li>" + pref.getBrand() + "</li>");
                    }
                }
                out.println("</ul>");

                out.println("<h2>선택한 카테고리</h2>");
                out.println("<ul>");
                for(Preference pref : preferences){
                    if(pref.getCategory() != null && !pref.getCategory().isEmpty()){
                        out.println("<li>" + pref.getCategory() + "</li>");
                    }
                }
                out.println("</ul>");
            }
        }
    %>
    <a href="survey.jsp">설문조사 페이지로 돌아가기</a>
</div>
</div>
</body>
</html>
