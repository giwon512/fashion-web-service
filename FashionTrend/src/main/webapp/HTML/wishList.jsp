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
<!-- 스타일 시트 및 기타 설정 -->
</head>
<body>
<jsp:useBean id="wishDao" class="fashion.dao.wishListsDao" scope="session"/>
 
  <%
  List<WishLists> wishList = null; // wishList 초기화를 null로 변경
  if(session.getAttribute("userId") != null) {
      int userId = (int)session.getAttribute("userId");
      wishList = wishDao.findAllWishLists(userId); // 위시리스트 전체 조회 메소드 호출

      // 로그인은 했지만 위시리스트가 비어 있는 경우, 더미 데이터로 채움
      if(wishList == null || wishList.isEmpty()) {
          wishList = new ArrayList<WishLists>(); // 여기서 wishList를 초기화
          // 더미 데이터 추가
          wishList.add(new WishLists("/path/to/dummy/image1.jpg", "나이키", "업템포", 130000));
          wishList.add(new WishLists("/path/to/dummy/image2.jpg", "아디다스", "울트라부스트", 180000));
          wishList.add(new WishLists("/path/to/dummy/image3.jpg", "리복", "클래식", 75000));
      }
  } else {
      // 로그인하지 않은 경우 더미 데이터로 채움
      wishList = new ArrayList<WishLists>(); // 여기서 wishList를 초기화
      // 더미 데이터 추가
      wishList.add(new WishLists("/path/to/dummy/image1.jpg", "나이키", "업템포", 130000));
      wishList.add(new WishLists("/path/to/dummy/image2.jpg", "아디다스", "울트라부스트", 180000));
      wishList.add(new WishLists("/path/to/dummy/image3.jpg", "리복", "클래식", 75000));
  }
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
