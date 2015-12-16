<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.rns.tiffeat.web.util.Constants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<link href='https://fonts.googleapis.com/css?family=Roboto:400,300,700,500' rel='stylesheet' type='text/css'>
<!-- ../../resources -->
<%-- <link href="<c:url value = "/resources/css/aboutUs.css"/>" rel="stylesheet"> --%>
<link href="<c:url value = "${resources}/css/contactUs.css"/>" rel="stylesheet">  
<link href="<c:url value = "${resources}/css/home.css"/>" rel="stylesheet">
<link href="<c:url value = "${resources}/css/tiffin_order.css"/>" rel="stylesheet">
<link href="<c:url value = "${resources}/css/star-rating.min.css"/>" rel="stylesheet" media="all" rel="stylesheet" type="text/css"> 
<link href="<c:url value = "${resources}/css/quick_daily_order.css"/>" rel="stylesheet"> 
<link href="<c:url value = "${resources}/css/tiffin_summary.css"/>" rel="stylesheet"> 
<link href="<c:url value = "${resources}/css/payment_details.css"/>" rel="stylesheet"> 
<link href="<c:url value = "${resources}/css/login_page.css"/>" rel="stylesheet">
<link href="<c:url value = "${resources}/css/register_page.css"/>" rel="stylesheet"> 
<link href="<c:url value = "${resources}/css/drawer.min.css"/>" rel="stylesheet">
<link href="<c:url value = "${resources}/css/add_money.css"/>" rel="stylesheet"> 
<link href="<c:url value = "${resources}/css/FAQ.css"/>" rel="stylesheet">
<link href="<c:url value = "${resources}/css/meal_format.css"/>" rel="stylesheet">
<link href="<c:url value = "${resources}/css/style.css"/>" rel="stylesheet">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Header</title>
</head>
<body class="drawer drawer-left" >

<!--    Drawer navbar-->
<header role="banner">
    <div class="drawer-header">
        <button type="button" class="drawer-toggle drawer-hamburger" id="drawer_button1">
            <span class="sr-only">toggle navigation</span>
            <span class="drawer-hamburger-icon"></span>
        </button>
    </div>

    <div class="drawer-main drawer-default">
        <nav class="drawer-nav" role="navigation">
        <div class="drawer-brand"><a href="home.htm" id="drawer_link1"><span class="tiff">Tiff</span><span class="eat">Eat</span></a></div>

            <ul class="drawer-menu">
            	<c:if test="${customer.email == null }">
            		<li class="drawer-menu-item"><a href="customerLogin.htm" id="drawer_link1">Login</a></li>
                	<!-- <li class="drawer-menu-item"><a href="registerCustomer.htm" id="drawer_link1">Register</a></li> -->
            	</c:if>
            	<li class="drawer-menu-item"><a href="home.htm" id="drawer_link1">Make an Order</a></li>
                <li class="drawer-menu-item"><a href="terms.htm" id="drawer_link1">Privacy Policy</a></li>
                <li class="drawer-menu-item"><a href="aboutUs.htm" id="drawer_link1">About Us</a></li>
                <li class="drawer-menu-item"><a href="contactUs.htm" id="drawer_link1">Contact Us</a></li>
                <c:if test="${customer.email != null }">
                	<li class="drawer-menu-item"><a href="logout.htm" id="drawer_link1">Logout</a></li>
                	<li class="drawer-menu-item"><a href="customerHome.htm" id="drawer_link1">Home</a></li>
                	<li class="drawer-menu-item"><a href="quickOrders.htm" id="drawer_link1">Quick Orders</a></li>
                </c:if>
            </ul>

            <div class="drawer-footer"><span></span></div>
        </nav>
    </div>
</header>
    
    <div class="drawer-overlay">
    <nav class="navbar navbar-default">
        <div class="container">
            <div class="navbar-header">

                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false" id="toggle_button1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>

                <a class="navbar-brand" href="home.htm">
                    <h4 class='tiff'>Tiff<span class="eat">Eat</span></h4>
                </a>
            </div>
            <c:if test="${customer.email != null }">
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <p class="navbar-text navbar-right" id="navbar_link1">${customer.name}</p>
            </div>
            </c:if>
        </div>
    </nav>
	</div>

</body>
</html>