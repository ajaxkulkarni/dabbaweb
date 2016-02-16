<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.rns.tiffeat.web.util.Constants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>


<link href="<c:url value = "${resources}/css/style.css"/>"
	rel="stylesheet">


<title>Header</title>
</head>
<body>
	<nav class="navbar navbar-default">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="home.htm">
				<h4 class='tiff'>
					Tiff<span class="eat">Eat</span>
				</h4>
			</a>

		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<!--<li class="active"><a href="customerLogin.htm">Login <span class="sr-only">(current)</span></a></li>-->
				<c:if test="${customer.email == null }">
					<li><a href="<%=Constants.CUSTOMER_LOGIN_URL_GET%>">Login/Register</a></li>
					
				</c:if>

				<li><a href="<%=Constants.TERMS_URL_GET%>">Privacy Policy</a></li>
				<li><a href="<%=Constants.ABOUT_US_URL_GET%>">About Us</a></li>
				<li><a href="<%=Constants.CONTACT_US_URL_GET%>">Contact Us</a></li>
				
				
				
				<c:if test="${customer.email != null }">
					<li><a href="<%=Constants.SCHEDULED_ORDERS_URL_GET%>">My Daily Tiffin</a></li>
					<li><a href="<%=Constants.QUICK_ORDERS_HOME_URL_GET%>">My Quick Orders</a></li>
					<li><a href="<%=Constants.LOGOUT_URL_GET%>">Logout</a></li>
					<p class="navbar-text" id="navbar_link1">Welcome
						${customer.name}</p>
						
				</c:if>
				
				</div>
				
	</div>
	
	<!-- /.container-fluid --> </nav>
</body>
</html>