<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 로그인 된 사용자만 접근가능

 if (session.getAttribute("user") == null) {
    response.sendRedirect("/login");
    return;
}
 -->


<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>마이페이지</title>
    <style>
        /* 전체 페이지 스타일링 */
        .home-body {
            font-family: 'Arial', sans-serif;	
            margin: 0;
            padding: 0;
            display: flex;
        }

        /* 메뉴 스타일링 */
        .home-menu {
            background-color: #f3f3f3; /* 메뉴 배경 색상 */
            width: 200px; /* 메뉴 너비 */
            height: 100vh; /* 전체 화면 높이 */
            padding-top: 20px; /* 상단 여백 */
        }

        .home-menu ul {
            list-style: none; /* 리스트 스타일 제거 */
            padding: 0;
        }

        .home-menu ul li {
            padding: 10px; /* 메뉴 항목 패딩 */
        }

        .home-menu ul li a {
            text-decoration: none; /* 링크 밑줄 제거 */
            color: #333; /* 링크 색상 */
            display: block;
        }

        .home-menu ul li a:hover {
            background-color: #ddd; /* 호버 시 배경 색상 변경 */
            color: #000; /* 호버 시 텍스트 색상 변경 */
        }

        /* 내용 부분 스타일링 */
        .home-content {
            flex-grow: 1; /* 나머지 공간 차지 */
            margin : 0;
            
        }
    </style>
</head>
<body class="home-body">
    
    <div class="home-menu">
        <ul>
            <% if (session.getAttribute("user") != null) { %>
                <!-- 로그인된 사용자에게 보여줄 메뉴 -->
                <li><a href="logout.jsp">로그아웃</a>
                <li><a href="fashionNews.jsp">패션뉴스</a></li>
                <li><a href="wishList.jsp">위시리스트</a></li>
                <li><a href="profile.jsp">회원정보 수정</a></li>
                <li><a href="orderHistory.jsp">구매내역</a></li>
            <% } else { %>
                <!-- 비로그인 사용자에게 보여줄 메뉴 -->
                
                
                <li><a href="login.jsp">로그인</a>
                <li><a href="index.jsp">홈</a></li>
                <li><a href="fashionNews.jsp">패션뉴스</a></li>
       
            <% } %>
        </ul>
    </div>

</body>
</html>
