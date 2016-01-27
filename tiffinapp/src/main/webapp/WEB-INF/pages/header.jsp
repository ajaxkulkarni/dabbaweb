<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.rns.tiffeat.web.util.Constants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<!-- ../../resources -->


	
<!-- Irrelevant CSS links
<link href="<c:url value = "${resources}/css/star-rating.min.css"/>"
	rel="stylesheet" media="all" rel="stylesheet" type="text/css">
<link href="<c:url value = "${resources}/css/quick_daily_order.css"/>"
	rel="stylesheet">
	-->

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
					<li><a href="customerLogin.htm">Login/Register</a></li>
				</c:if>

				<li><a href="makeNewOrder.htm">Home</a></li>
				<li><a href="terms.htm">Privacy Policy</a></li>
				<li><a href="aboutUs.htm">About Us</a></li>
				<li><a href="contactUs.htm">Contact Us</a></li>

				<c:if test="${customer.email != null }">
					<li><a href="scheduledOrders.htm">My Scheduled Orders</a></li>
					<li><a href="quickOrders.htm">My Quick Orders</a></li>
					<li><a href="logout.htm">Logout</a></li>
					<p class="navbar-text" id="navbar_link1">Welcome
						${customer.name}</p>
				</c:if>



				<!--<li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="#">Action</a></li>
          <li><a href="#">Another action</a></li>
          <li><a href="#">Something else here</a></li>
          <li role="separator" class="divider"></li>
          <li><a href="#">Separated link</a></li>
          <li role="separator" class="divider"></li>
          <li><a href="#">One more separated link</a></li>
        </ul>
      </li>
    </ul>-->
				<!-- <form class="navbar-form navbar-left" role="search">
      <div class="form-group">
        <input type="text" class="form-control" placeholder="Search">
      </div>
      <button type="submit" class="btn btn-default">Submit</button>
    </form>-->
				<!--<ul class="nav navbar-nav navbar-right">
      <li><a href="#">Link</a></li>
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="#">Action</a></li>
          <li><a href="#">Another action</a>privacy</li>
          <li><a href="#">Something else here</a></li>
          <li role="separator" class="divider"></li>
          <li><a href="#">Separated link</a></li>
        </ul>
      </li>
    </ul>-->
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid --> </nav>
</body>
</html>