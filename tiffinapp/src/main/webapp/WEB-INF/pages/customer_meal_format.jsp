<%@page import="com.rns.tiffeat.web.bo.domain.MealFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE HTML>
<html>
<head>
<title>TiffEat</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<link
	href='https://fonts.googleapis.com/css?family=Roboto:400,300,700,500'
	rel='stylesheet' type='text/css'>
<link href="<c:url value = "${resources}/css/customer_meal_format.css"/>"
	rel="stylesheet">
</head>
<body>

	<%@include file="header.jsp"%>
	<div class="container">
		<h4 class="meal_format_heading">You can choose from two options:</h4>
		<div class="row">
			<div class="col-md-6">
				<div class="demo-card">
					<a href="checkRegistration.htm?format=<%=MealFormat.QUICK%>"
						class="order_url"> <img
						src="<c:url value = "${resources}/img/quick.svg"/>" alt="no-image"
						class="img-responsive meal_format_img" />


						<p>For One time order Click here</p>
					</a>

					<div class="desc">This is the on demand delivery of the
						tiffin. Test the waters first, and then choose the vendor you like
						for daily dabba.</div>
				</div>
			</div>

			<div class="col-md-6">
				<div class="demo-card">
					<a href="checkRegistration.htm?format=<%=MealFormat.SCHEDULED%>"
						class="order_url"> <img
						src="<c:url value = "${resources}/img/scheduled.svg"/>"
						alt="no-image" class="img-responsive meal_format_img" />


						<p>To Schedule a Tiffin Click here</p>
					</a>
					<div class="desc">
						This way, you choose to receive this tiffin daily, for lunch or
						for dinner,<br> or both.
					</div>
				</div>
			</div>
		</div>


	</div>
	<%@include file="footer.jsp"%>
</body>
</html>