<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="fashion.dao.wishListsDao"%>
<%@ page import="fashion.dto.WishLists"%>
<%@ page import="java.util.List"%>




<%@ include file="home.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>위시리스트 전체 보기</title>
<style>
    .wish-list-container {
    	margin : 50px;
        display: flex;
        flex-wrap: wrap;
        gap: 20px;
        justify-content: center;
    }
    .wish-item {
        border: 1px solid #ddd;
        width: 200px;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        margin-bottom: 20px;
        text-align: center;
    }
    .wish-item img {
        width: 100%;
        height: auto;
    }
    .wish-item-info {
        padding: 10px;
    }
    .brand-name, .product-name, .product-price {
        margin: 5px 0;
    }
    .brand-name {
        font-weight: bold;
        color: #333;
    }
    .product-name {
        color: #666;
    }
    .product-price {
        color: #999;
    }
</style>
</head>
<body>
<jsp:useBean id="wishDao" class="fashion.dao.wishListsDao" scope="session"/>
 
  <%
    	int userId = (int)session.getAttribute("userId");
    
  
   		List<WishLists> wishList = wishDao.findAllWishLists(userId); // 위시리스트 전체 조회 메소드 호출
   	
   	
  %>
  
  <div class="home-content">
  <div class="wish-list-container">
      <% for(WishLists list : wishList) { %>
          <div class="wish-item">
              <img src="<%= list.getImageUrl() %>" alt="Product Image">
              <div class="wish-item-info">
                  <div class="brand-name"><%= list.getBrandName() %></div>
                  <div class="product-name"><%= list.getProductName() %></div>
                  <div class="product-price"><%= list.getProductPrice() %>원</div>
              </div>
          </div>
      <% } %>
  </div>
  </div>
</body>
</html>