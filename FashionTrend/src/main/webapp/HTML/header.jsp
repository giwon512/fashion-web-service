<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String login  = (String)session.getAttribute("user") != null ? "logout"  : "login";
	String register =(String)session.getAttribute("user") != null ? "" : "register";
	
	

%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Header and Footer Example</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <style>
    	.searchButton {
    		border : 1.25px solid rgb(199, 31, 31);
    		padding : 6px 12px;
    		color : rgb(199, 31, 31);
    		display : block;
    		border-radius : 5px;
    		background-color : #343a40;
    	}
    	
    	.searchButton:hover {
    		color : white;
    		background-color : rgb(199, 31, 31);
    	}
        
    </style>
</head>
<body>

    <!-- Header -->
    <header>
       <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
  <!-- navbar-brand의 content 변경 -->
  <a class="navbar-brand" href="index.jsp" style="color: rgb(199, 31, 31);"><b>DOTORI</b></a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="index.jsp" style="color: rgb(199, 31, 31);">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="<%=login+".jsp"%>"><%=login %></a>
      </li>
       <li class="nav-item">
        <a class="nav-link" href="signIn.jsp"><%=register%></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="profile.jsp">myPage</a>
      </li>
      

    </ul>
    <!--  나중 순위  --> 
    <form class="form-inline my-2 my-lg-0">
      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" />
      <button class="searchButton" type="submit">Search</button>
    </form>
  </div>
</nav>
       
    </header>



</body>
</html>