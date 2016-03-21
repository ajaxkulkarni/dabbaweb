<%@page import="com.rns.tiffeat.web.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>TiffEat | Change Order</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
	<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<link
	href='https://fonts.googleapis.com/css?family=Roboto:400,300,700,500'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="<c:url value = "${resources}/css/star-rating.min.css"/>" media="all"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<c:url value = "${resources}/css/change_order.css"/>">
<link href="<c:url value = "${resources}/css/star_rating_static.css"/>" rel="stylesheet">
<script src="<c:url value = "${resources}/js/getMenu.js"/>"></script>
</head>



<body>
	<%@include file="header.jsp"%>
	<div class="container payment_details_div">
		<h4 class="payment_details_heading">Change Tiffin</h4>
		<div class="payment_details_card">
		<c:if test="${result != 'OK' && result!=null }">
				<div class="alert alert-danger">
					<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					<!-- <strong>Danger!</strong> -->${result}
				</div>
			</c:if>
			<div class="row">
				<div class="col-md-6">
					<div class="details">
						Meal Type: ${customerOrder.mealType} <br /> <br /> Current Meal:
						${customerOrder.meal.title} <br /> <br /> Vendor Name:
						${customerOrder.meal.vendor.name} <br /> <br /> 
					<%@include file = "forms/customer_change_order_form.jsp" %>
					
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
					<form action="<%=Constants.CHANGE_ORDER_URL_POST %>" method="post">
					<div class="col-md-4">
						<div class="menu_card">
							<img src="getMealImage.htm?mealId=${meal.id}" class="menu_card_image img-responsive">
							<h4 class="menu_card_price">${meal.price}Rs.</h4>
							<h4 class="menu_card_title">${meal.title}</h4>
							<h4 class="menu_card_title">${meal.vendor.name}</h4>
							<div class='rating_bar'>
								<c:if test="${meal.rating != null}">
									<div class='rating' style='width:${meal.rating*20}%'></div>
								</c:if>
							</div>
							<input type="hidden" name="meal.title" value="${meal.title}" /> 
							<input type="hidden" name="meal.id" value="${meal.id}" /><br/>
							<button type="button" class="btn order_button" onclick="getMenu(${meal.id},'${meal.title}','${customerOrder.mealType}')">MENU</button>
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
	<%@include file="forms/customer_menu_modal.jsp" %>
	
	<%@include file="footer.jsp"%>
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