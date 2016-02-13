<%@page import="com.rns.tiffeat.web.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>TiffEat | Schedule_Dinner/Lunch</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">

<link
	href='https://fonts.googleapis.com/css?family=Roboto:400,300,700,500'
	rel='stylesheet' type='text/css'>

<link rel="stylesheet" href="css/star-rating.min.css" media="all"
	rel="stylesheet" type="text/css" />


<link href="<c:url value = "${resources}/css/add lunch_dinner.css"/>" rel="stylesheet">
</head>



<body>
<%@include file="header.jsp" %>
	<div class="container payment_details_div">
		<h4 class="payment_details_heading">Schedule ${customerOrder.mealType.description}</h4>
		<div class="payment_details_card">
			<div class="row">
				<div class="col-md-6">
					<form action="<%=Constants.GET_NEARBY_VENDORS_FOR_ADD_ORDER_URL_POST%>" method="post">
					<div class="details">
						Location: <input type="text" name="location.address" id="areas" placeholder="Enter Your Location" value="" class="option_dropdown" /><br />
					</div><br/>
					<div class="divspacing">
						<textarea class="form-control" type="text" pattern=""
							id="txtAddress" name="address" value="" placeholder="ADDRESS"
							required="required"></textarea>
					</div>
					<div class="submit_order">
						<input type="submit" name="" value="Find Meals"
							class="btn order_button">
					</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="container menu_cards_div" id="vendorsList">
		<c:choose>
			<c:when test="${fn:length(meals) gt 0}">
				<h4 class="menu_card_heading">Tiffins in Your Area</h4>
				<div class="row">
					<c:forEach items="${meals}" var="meal">
					<form action="<%=Constants.SCHEDULED_ORDER_URL_POST %>" method="post">
					<div class="col-md-4">
						<div class="menu_card">
							<img src="getMealImage.htm?mealId=${meal.id}" class="menu_card_image img-responsive">
							<h4 class="menu_card_price">${meal.price}Rs.</h4>
							<h4 class="menu_card_title">${meal.title}</h4>
							<h4 class="menu_card_title">${meal.vendor.name}</h4>
							<input type="hidden" name="meal.title" value="${meal.title}" /> 
							<input type="hidden" name="meal.id" value="${meal.id}" />
							<input type="hidden" name="meal.price" value="${meal.price}" />
							<button type="submit" class="btn order_button">ORDER</button>
						</div>
					</div>
					</form>
					</c:forEach>
				</div>
			</c:when>
			<c:otherwise>
				${result}
			</c:otherwise>
		</c:choose>
	</div>
	
	<%@include file="footer.jsp" %>
	<script
		src="http://maps.googleapis.com/maps/api/js?sensor=false&amp;libraries=places"></script>
	<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script> -->
	<script src="<c:url value="${resources}/js/jquery.geocomplete.js"/>"></script>
	<script>
		$(document).ready(function() {
			$("#areas").geocomplete({
				types : [ "geocode", "establishment" ],
			});

		});
	</script>


	
<body>
</html>

