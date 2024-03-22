<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Header and Footer Example</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <style>
        /* Add your custom styles here */
        
        
        
    </style>
</head>
<body>

    <!-- Header -->
    <header>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container">
                <a class="navbar-brand" href="#">Your Logo</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="#"><span class="sr-only">home</span></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="profile.jsp">mypage</a>
                        </li>
                        
                       
                        <li class="nav-item">
                            <a href="login.jsp" class="btn btn-primary rounded-0 py-2 px-lg-4 d-none d-lg-block">login<i class="fa fa-arrow-right ms-3"></i></a>
                        </li>
                         <li class="nav-item">
                            <a class="nav-link" href="signIn.jsp">register</a>
                        </li>
                    </ul>
                </div>
            </div>
            
        </nav>
    </header>



</body>
</html>