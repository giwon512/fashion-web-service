<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="../CSS/news.css">
    <link rel="stylesheet" href="../CSS/news_reset.css">
<title>login.jsp</title>

<style>
/* General Styles */


body {
    font-family: 'Segoe UI', Tahoma, sans-serif;
    margin: 0;
    background-color: #f0f0f0;
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100vh;
}

.index-content {	
    background-color: #fff;
    padding: 30px;
    border-radius: 8px;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
    text-align: center;
}

h2 {
    color: #333;
    margin-bottom: 20px;
}

input[type="text"], input[type="password"] {
    width: 100%;
    padding: 12px;
    margin-bottom: 15px;
    border-radius: 5px;
    border: 1px solid #ddd;
    box-sizing: border-box;
} 

input[type="submit"] {
    background-color: #2077c5;
    color: #fff;
    padding: 12px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-weight: bold;
}

input[type="submit"]:hover {
     background-color: #005ba2;
}

label {  
    font-size: 14px;
}

a {
    color: #005ba2;
    text-decoration: none;
}



</style>


</head>
<body class="login-body">



<%@ include file="home.jsp" %>


  <div class="home-content">
    	<h2>Login</h2><br>
        <form method="post" action="login_proc.jsp" id="loginForm">
            <input type="text" name="name" placeholder="id" maxlength="10" required>
            <br>
            <input type="password" name="password" placeholder="password" maxlength="20" required>
            <label for="remember-check">
                <input type="checkbox" id="remember-check">아이디 저장하기
            </label>
            <br><br>
            <input type="submit" value="로그인"><br><br>
            <a href="signIn.jsp">회원가입이 안되어있으세요?</a>
        </form>
    </div>

</body>
</html>