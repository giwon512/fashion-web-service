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
            font-family: 'Arial', sans-serif;
            background-color: #333;
            color: #f4f4f4;
            margin: 0;
            padding: 0px;
        }
        .survey-container {
            max-width: 600px;
            background: #424242;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
            margin: 0 auto;
        }
        h1, h2 {
            color: #f4f4f4;
        }
        .checkbox-group label {
            display: block;
            margin: 10px 0;
            cursor: pointer;
        }
        .checkbox-group input[type="checkbox"] {
            margin-right: 10px;
        }
        input[type="submit"] {
            background-color: #000;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }
        input[type="submit"]:hover {
            background-color: #757575;
        }
        select, input[type="checkbox"] {
            background-color: #616161;
            color: #fff;
            border: 1px solid #757575;
        }
        select {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 5px;
        }
        select option {
            background-color: #424242;
            color: #fff;
        }
    </style>
</head>
<body>

<div class="survey-container">
    <h1>패션 선호도 설문조사</h1>
    
    <form action="submitSurvey.jsp" method="post">
        <h2>브랜드 선호도</h2>
        <div class="checkbox-group">
            <label><input type="checkbox" name="preferredBrands[]" value="Nike">Nike</label>
            <label><input type="checkbox" name="preferredBrands[]" value="Adidas">Adidas</label>
            <label><input type="checkbox" name="preferredBrands[]" value="Zara">Zara</label>
            <label><input type="checkbox" name="preferredBrands[]" value="HM">H&M</label>
            <label><input type="checkbox" name="preferredBrands[]" value="gucci">GUCCI</label>
            <label><input type="checkbox" name="preferredBrands[]" value="chanel">샤넬</label>
            <label><input type="checkbox" name="preferredBrands[]" value="dior">디올</label>
            <label><input type="checkbox" name="preferredBrands[]" value="hermes">에르메스</label>
        </div>
        
        <h2>카테고리 선호도</h2>
        <div class="checkbox-group">
            <label><input type="checkbox" name="preferredCategories[]" value="minimal">미니멀</label>
            <label><input type="checkbox" name="preferredCategories[]" value="street">스트릿</label>
            <label><input type="checkbox" name="preferredCategories[]" value="casual">캐쥬얼</label>
            <label><input type="checkbox" name="preferredCategories[]" value="americaji">아메카지</label>
            <label><input type="checkbox" name="preferredCategories[]" value="vintage">빈티지</label>
            <label><input type="checkbox" name="preferredCategories[]" value="classic">클래식</label>
            <label><input type="checkbox" name="preferredCategories[]" value="retro">레트로</label>
            <label><input type="checkbox" name="preferredCategories[]" value="gopcore">고프코어</label>
        </div>
        
        <input type="submit" value="설문조사 제출">
    </form>
</div>

</body>
</html>
