<%-- <%@page import="fashion.dto.purChaseInfo"%>
<%@page import="fashion.dao.purChaseInfoDao"%>
<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>



<%@page import="java.util.List"%>

<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>주문내역 조회</title>
<style>
table {
    width: 100%;
    border-collapse: collapse;
}

table, th, td {
    border: 1px solid black;
}

th, td {
    padding: 8px;
    text-align:center;
}

th {
    background-color: #f2f2f2;
}
</style>
</head>
<body>
    <h2>구매 내역</h2>
    <table>
        <tr>
            <th>상품정보</th>
            <th>주문일자</th>
            <th>주문번호</th>
            <th>수량</th>
            <th>주문금액</th>
        </tr>
        <%
        
        //아이디를 어떻게 가져올
            purChaseInfoDao dao = new purChaseInfoDao();
        	purChaseInfo info = dao.getPurchaseInfoByUserId(userId);
            for(purchase_info pur : purchaseList) {
        %>
        <tr>
            <td>
            	<img src="https://image.msscdn.net/images/goods_img/20220111/2301177/2301177_1_big.jpg" alt="반팔티" style="width:100px; height:auto; float:left;">
            	 <span style="display:block; text-align:left;"><%=pur.getItem_name()%></span>
            </td>
            <td><%=pur.getPurchase_date()%></td>
            <td><%=pur.getPurchase_id()%></td>
            <td><%=pur.getQuantity()%></td>
            <td><%=pur.getAmount()%></td>
        </tr>
        <%
            }
        %>
    </table>
</body>
</html>


</body>
</html> --%>