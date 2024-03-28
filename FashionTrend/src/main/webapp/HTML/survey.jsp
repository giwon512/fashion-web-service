<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="home.jsp" %>
<%@ include file="./IsLoggedIn.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>패션 선호도 설문조사</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        .survey-container {
            max-width: 600px;
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin: 0 auto;
        }
        h1, h2 {
            color: #333;
        }
        select {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #f8f8f8;
        }
        input[type="submit"] {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<div class="survey-container">
    <h1>패션 선호도 설문조사</h1>
    
    <form action="submitSurvey.jsp" method="post">
        <h2>브랜드 선호도</h2>
        <select name="preferredBrands[]" multiple>
            <option value="Nike">Nike</option>
            <option value="Adidas">Adidas</option>
            <option value="Zara">Zara</option>
            <option value="HM">H&M</option>
            <option value="gucci">GUCCI</option>
            <option value="chanel">샤넬</option>
            <option value="dior">디올</option>
            <option value="hermes">에르메스</option>
        </select>
        
        <h2>카테고리 선호도</h2>
        <select name="preferredCategories[]" multiple>
            <option value="minimal">미니멀</option>
            <option value="street">스트릿</option>
            <option value="casual">캐쥬얼</option>
            <option value="americaji">아메카지</option>
            <option value="vintage">빈티지</option>
            <option value="classic">클래식</option>
            <option value="retro">레트로</option>
            <option value="gopcore">고프코어</option>
        </select>
        
        <input type="submit" value="설문조사 제출">
    </form>
</div>

</body>
</html>