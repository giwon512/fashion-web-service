<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="fashion.dao.wishListsDao"%>
<%@ page import="fashion.dto.WishLists"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<%@ include file="./IsLoggedIn.jsp" %>
<%@ include file="home.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>위시리스트 전체 보기</title>
<style>
    body {
        font-family: Arial, sans-serif;
    }
    .wish-list-container {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
        gap: 20px;
        padding: 20px;
    }
    .wish-item {
        border: 1px solid #ddd;
        border-radius: 8px;
        overflow: hidden;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }
    .wish-item img {
        width: 100%;
        height: 200px;
        object-fit: cover;
    }
    .wish-item-info {
        padding: 15px;
        background-color: #f9f9f9;
    }
    .brand-name {
        font-size: 18px;
        margin-bottom: 5px;
        color: #333;
    }
    .product-name {
        font-size: 16px;
        margin-bottom: 10px;
        color: #666;
    }
    .product-price {
        font-size: 20px;
        font-weight: bold;
        color: #000;
    }
</style>
</head>
<body>
<jsp:useBean id="wishDao" class="fashion.dao.wishListsDao" scope="session"/>
 
  <%
  List<WishLists> wishList = null;
  if(session.getAttribute("userId") != null) {
      int userId = (int)session.getAttribute("userId");
      wishList = wishDao.findAllWishLists(userId);

      if(wishList == null || wishList.isEmpty()) {
          wishList = new ArrayList<WishLists>();
          wishList.add(new WishLists("/FashionTrend/resources/wishListsPicture/up.jpeg", "나이키", "업템포"));
          wishList.add(new WishLists("/FashionTrend/resources/wishListsPicture/dior.jpeg", "Dior", "디올 워크앤디올 스니커즈"));
          wishList.add(new WishLists("/FashionTrend/resources/wishListsPicture/lv.jpeg", "루이비통", "남성 슬렌더 월릿 [M62294]"));
          wishList.add(new WishLists("/FashionTrend/resources/wishListsPicture/vest.jpeg", "나이키", "SS24 나이키 ACG ARCTIC WOLF VEST BAROQUE_BROWN 24PFN2448SP_237"));
      }
  } else {
	  wishList = new ArrayList<WishLists>();
      wishList.add(new WishLists("/FashionTrend/resources/wishListsPicture/up.jpeg", "나이키", "업템포"));
      wishList.add(new WishLists("/FashionTrend/resources/wishListsPicture/dior.jpeg", "Dior", "디올 워크앤디올 스니커즈"));
      wishList.add(new WishLists("/FashionTrend/resources/wishListsPicture/lv.jpeg", "루이비통", "남성 슬렌더 월릿 [M62294]"));
      
      
      //SS24 나이키 ACG ARCTIC WOLF VEST BAROQUE_BROWN 24PFN2448SP_237
  }
  %>
  
  v
<div class="home-content">
<div class="wish-list-container">
    <% for(WishLists list : wishList) { %>
        <div class="wish-item">
            <img src="<%= list.getImageUrl() %>" alt="Product Image">
            <div class="wish-item-info">
                <div class="brand-name"><%= list.getBrandName() %></div>
                <div class="product-name"><%= list.getProductName() %></div>
                
            </div>
        </div>
    <% } %>
</div>
</div>



</body>
</html>
