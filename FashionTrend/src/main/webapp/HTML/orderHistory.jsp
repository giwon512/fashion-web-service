<%@page import="java.time.LocalDate"%>
<%@page import="fashion.dto.purChaseInfo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./IsLoggedIn.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>주문내역 조회</title>
<style>
.home-content {
    padding: 20px;
}

.purchase-card {
    border: 1px solid #ddd;
    margin-bottom: 20px;
    padding: 10px;
    display: flex;
    align-items: center;
}

.purchase-img {
    width: 100px;
    height: auto;
    margin-right: 20px;
}

.purchase-info {
    text-align: left;
}
</style>
</head>
<body>
<%@ include file="home.jsp" %>
<div class="home-content">
    <h2>저장된 페이지</h2>
    <%
        // 더미 데이터 생성
   List<purChaseInfo> dummyPurchaseList = new ArrayList<>();
    dummyPurchaseList.add(new purChaseInfo("/FashionTrend/resources/wishListsPicture/savepage1.png", "2024 F/W 파리 패션위크 빅 트렌드 키워드11 ", LocalDate.parse("2023-03-12"), 12342, 1, 1818000));
    dummyPurchaseList.add(new purChaseInfo("/FashionTrend/resources/wishListsPicture/fashion02.png", "2024 F/W 루이비통 2024 F/W 여성복 컬렉션 ", LocalDate.parse("2023-02-13"), 12341, 1, 1818000));
    dummyPurchaseList.add(new purChaseInfo("/FashionTrend/resources/wishListsPicture/yak.jpeg", "블랙야크 해발 1,100m 아웃도어 복합문화공간 '베이스캠프' 지리산점 오픈 ", LocalDate.parse("2023-03-22"), 12332, 1, 1818000));
    dummyPurchaseList.add(new purChaseInfo("/FashionTrend/resources/wishListsPicture/cam.jpeg", "캠브리지 멤버스, 예복 슈트 스타일링 팁까지 시그니처 슈트 컬렉션 출시 ", LocalDate.parse("2023-03-25"), 12233, 1, 1818000));
    dummyPurchaseList.add(new purChaseInfo("/FashionTrend/resources/wishListsPicture/bacity.jpeg", "화사한 봄날 다시 뜨는 유행템 '바시티 재킷 vs 청청 패션' 뭐 살까? ", LocalDate.parse("2023-03-23"), 12211, 1, 1818000));
        for(purChaseInfo pur : dummyPurchaseList) {
    %>
    <div class="purchase-card">
        <img src="<%= pur.getImageUrl() %>" alt="Product Image" class="purchase-img">
        <div class="purchase-info">
            <div>기사명: <%= pur.getProductName() %></div>
            <div>기사일자: <%= pur.getPurchaseDate() %></div>
            <div>기사번호: <%= pur.getProductId()%></div>
        </div>
    </div>
    <%
        }
    %>
</div>
</body>
</html>
