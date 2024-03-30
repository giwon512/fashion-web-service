<%@page import="utils.JSFunction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="fashion.dao.PreferenceDao"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>설문 조사 결과</title>
</head>
<body>

<%
    // 사용자 ID를 얻기 위한 임시 코드입니다. 실제 애플리케이션에서는 로그인 세션 등으로부터 사용자 ID를 가져와야 합니다.
    Integer userId = (Integer) session.getAttribute("userId");
	
	System.out.println("userId"+userId);

    // 요청으로부터 선택된 브랜드와 카테고리 배열 가져오기
    String[] preferredBrands = request.getParameterValues("preferredBrands[]");
    String[] preferredCategories = request.getParameterValues("preferredCategories[]");

    // DAO 객체 생성
    PreferenceDao dao = new PreferenceDao();
    
    // 데이터베이스에 선호도 정보 저장
    try {
        dao.resetAndInsertUserPreferences(userId, preferredBrands, preferredCategories);
        
        JSFunction.alertLocation("정상적으로 저장되었습니다.", "surveyForm.jsp", out);
      
       
       
    } catch (Exception e) {
    	JSFunction.alertBack("선호도 정보 저장 중 오류가 발생했습니다", out);
    }

    // 저장된 선호도 정보 출력
    out.println("<h2>선택한 브랜드</h2>");
    if(preferredBrands != null) {
        for(String brand : preferredBrands) {
            out.println("<p>" + brand + "</p>");
        }
    }

    out.println("<h2>선택한 카테고리</h2>");
    if(preferredCategories != null) {
        for(String category : preferredCategories) {
            out.println("<p>" + category + "</p>");
        }
    }
%>



</body>
</html>
