<%@page import="com.rns.tiffeat.web.bo.domain.MealType"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>TiffEat | Home</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<link
	href='https://fonts.googleapis.com/css?family=Roboto:400,300,700,500'
	rel='stylesheet' type='text/css'>


<link href="<c:url value = "${resources}/css/tiffin_summary.css"/>"
	rel="stylesheet">
<link href="<c:url value = "${resources}/css/prev_order.css"/>"
	rel="stylesheet">
<link href="<c:url value = "${resources}/css/star_rating.css"/>" 
    rel="stylesheet">

</head>
<body>

	<%@include file="header.jsp"%>
	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="nowDateString"></fmt:formatDate>
	<div class="container order_summary_details_div">
		<c:if test="${fn:length(customer.quickOrders) gt 0}">
		<h4 class="order_summary_heading1"><p>Save 20% per tiffin by starting daily tiffin service!</p></h4>
			<h4 class="order_summary_heading">Your Quick Orders:</h4>
			<c:forEach items="${customer.quickOrders}" var="order">
				<div class="order_details_card">
					<div class="row">
						<div class="col-md-6">
							<img alt="no_image"
								src="getMealImage.htm?mealId=${order.meal.id}"
								class="img-responsive tiffin_box_img1">
						</div>
						<fmt:formatDate pattern="yyyy-MM-dd" value="${order.date}"
							var="orderDate"></fmt:formatDate>
						<div class="delivered">
						<c:choose>
							<c:when test="${orderDate ne nowDateString}">
								<h3 class="menu_list_title">You have ordered
									${order.meal.title} for tomorrow's(${orderDate})
									${order.mealType}.</h3>
							</c:when>
							<c:when test="${order.status == 'DELIVERED'}">
                				Today's order ${order.meal.title} for ${order.mealType} has been delivered.
                				<br/>
                				<%@include file="forms/customer_meal_rating.jsp" %>
                			</c:when>
							<c:when test="${order.status == 'CANCELLED'}">
                				Today's order ${order.meal.title} for ${order.mealType} has been cancelled. Sorry for the inconvenience!
                			</c:when>
						</c:choose>
						</div>
						<c:choose>
							<c:when
								test="${order.content  != null && order.status!='CANCELLED' && order.status != 'DELIVERED'}">
								<div class="col-md-6">
								<h3 class="menu_list_title">Vendor Name: ${order.meal.vendor.name}</h3>
									<h3 class="menu_list_title">${order.mealType} menu of
										Tiffin ${order.meal.title} for ${orderDate} is:</h3>
									<table class="table table-bordered menu_list_table">
										<tr>
											<td>Sabji :</td>
											<td>${order.content.mainItem}</td>
										</tr>
										<tr>
											<td>Chapati/Roti :</td>
											<td>${order.content.subItem1}</td>
										</tr>
										<tr>
											<td>Rice/Dal :</td>
											<td>${order.content.subItem2}</td>
										</tr>
										<tr>
											<td>Salad :</td>
											<td>${order.content.subItem3}</td>
										</tr>
										<tr>
											<td>Extra :</td>
											<td>${order.content.subItem4}</td>
										</tr>

									</table>
								</div>
								<br/>
								<br/>
								<div class="col-md-6">
									<%-- <h3 class="order_details_title">Your meal is ${order.meal.status}</h3> --%>
									
									<c:choose>
									
										<c:when test="${order.mealStatus == 'PREPARE' }">
											<h3 class="order_details_title">Vendor got the sabji
												from the market and is now preparing your meal.</h3>
										</c:when>
										<c:when test="${order.mealStatus == 'COOKING' }">
											<h3 class="order_details_title">Vendor started cooking
												your meal. Hmmmm....smells delicious!!</h3>
										</c:when>
										<c:when
											test="${order.mealStatus == 'DISPATCH' && order.status != 'DELIVERED' }">
											<h3 class="order_details_title">Your meal has been
												dispatched. It will be delivered anytime now!</h3>
										</c:when>
									</c:choose>

								</div>
							</c:when>
							<c:when test="${order.content == null}">
								Menu for ${order.meal.title} for ${order.mealType} is not currently updated by the vendor.
							</c:when>
						</c:choose>
					</div>
					<br/>
					<div class="price">Date: ${orderDate}</div><br/>
					<div class="price">Number of Tiffins: ${order.quantity}</div><br/>
					<div class="price">Total Price: ${order.price}</div><br/>
					

					<%-- <form action="<%=Constants.REPEAT_ORDER_URL_POST %>" method="post">
						<input type="hidden" name="meal.id" value="${quickOrder.meal.id}" />
						<input type="hidden" name="meal.title"
							value="${quickOrder.meal.title}" /> <input type="hidden"
							name="meal.price" value="${quickOrder.meal.price}" /> <input
							type="hidden" name="meal.description"
							value="${quickOrder.meal.description}" /> <input type="hidden"
							name="area" value="${quickOrder.area}" /> <input type="hidden"
							name="address" value="${quickOrder.address}" /> <input
							type="hidden" name="location.address"
							value="${quickOrder.location.address}" /> <br /> <input
							type="submit" name="" value="REPEAT?" class="btn order_button" />

					</form> --%>
				</div>
			</c:forEach>
		</c:if>
		<form action="<%=Constants.CUSTOMER_SELECT_VENDOR_URL_POST%>" method="POST">
		<br/>
			<div class="submit_order">
				<button type="submit" class="btn order_button">MAKE New
					ORDER</button>
			</div>
			<input type="hidden" name="pinCode"
				value="${customer.quickOrders[0].area}" class="cancel" />
		</form>
		<h4 class="prev_order_heading">Recent orders</h4>
		<div class="row">
			<c:forEach items="${customer.previousOrders}" var="order">
				<c:if
					test="${order.meal.vendor.status != 'NA' && order.meal.vendor.status != 'CLOSED'  }">
					<div class="col-md-3">
						<div class="prev_order_card">
							<img src="getMealImage.htm?mealId=${order.meal.id}"
								class="img-responsive" id="prevOrderImage">
							<h4 class="menu_card_title">${order.meal.title}</h4>
							<h6 class="menu_card_sub_title">${order.meal.vendor.name}</h6>
							<h6 class="menu_card_sub_title">Price : ${order.meal.price}</h6>
							<h6 class="menu_card_sub_title">
								Date :
								<fmt:formatDate value="${order.date}" pattern="yyyy-MM-dd" />
							</h6>
							<%-- <form action="<%=Constants.REPEAT_ORDER_URL_POST%>" method="post">
								<input type="hidden" name="meal.id" value="${order.meal.id}" />
								<input type="hidden" name="meal.title"
									value="${order.meal.title}" /> <input type="hidden"
									name="meal.price" value="${order.meal.price}" /> <input
									type="hidden" name="meal.description"
									value="${order.meal.description}" /> <input type="hidden"
									name="area" value="${order.area}" /> <input type="hidden"
									name="address" value="${order.address}" /> <input
									type="hidden" name="location.address"
									value="${order.location.address}" />
								<button type="submit" class="btn order_button">Repeat
									Order</button>
							</form> --%>
						</div>
					</div>
				</c:if>
			</c:forEach>
		</div>
		<br />
	</div>
	
	<%@include file="footer.jsp"%>
</body>
</html>