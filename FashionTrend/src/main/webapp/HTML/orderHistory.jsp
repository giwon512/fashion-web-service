<%@page import="java.time.LocalDate"%>
<%@page import="fashion.dto.purChaseInfo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
    <h2>구매 내역</h2>
    <%
        // 더미 데이터 생성
   List<purChaseInfo> dummyPurchaseList = new ArrayList<>();
    dummyPurchaseList.add(new purChaseInfo("https://image.msscdn.net/images/goods_img/20220111/2301177/2301177_1_big.jpg", "나이키 반팔티", LocalDate.parse("2023-03-24"), 12345, 1, 30000));
    dummyPurchaseList.add(new purChaseInfo("https://image.msscdn.net/images/goods_img/20220111/2301178/2301178_1_big.jpg", "아디다스 운동화", LocalDate.parse("2023-03-22"), 12346, 2, 90000));
    dummyPurchaseList.add(new purChaseInfo("https://image.msscdn.net/images/goods_img/20220111/2301179/2301179_1_big.jpg", "리복 크로스핏", LocalDate.parse("2023-03-20"), 12347, 1, 80000));
        for(purChaseInfo pur : dummyPurchaseList) {
    %>
    <div class="purchase-card">
        <img src="<%= pur.getImageUrl() %>" alt="Product Image" class="purchase-img">
        <div class="purchase-info">
            <div>상품명: <%= pur.getProductName() %></div>
            <div>주문일자: <%= pur.getPurchaseDate() %></div>
            <div>주문번호: <%= pur.getPurchaseId() %></div>
            <div>수량: <%= pur.getQuantity() %></div>
            <div>주문금액: <%= pur.getAmount() %>원</div>
        </div>
    </div>
    <%
        }
    %>
</div>
</body>
</html>
