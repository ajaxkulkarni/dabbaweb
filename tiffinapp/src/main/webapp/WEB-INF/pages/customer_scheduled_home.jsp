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

</head>
<body>

	<%@include file="header.jsp"%>
	<div class="container order_summary_details_div">

		<form action="<%=Constants.ADD_MONEY_TO_WALLET_URL_GET %>" method="get">
			<div class="balance">
				<input type="submit" value="ADD TO WALLET" class="btn order_button" />
		</form>
		Balance : ${customer.balance}
	</div>
	<c:if test="${result != null && result!='OK' }">
		<div class="alert alert-danger">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<!-- <strong>Danger!</strong> -->${result}
		</div>
	</c:if>
	<c:if test="${fn:length(customer.scheduledOrder) == 0}">
		<h4 class="order_summary_heading">You don't have any
			Daily Tiffins right now. Start a Dinner or Lunch tiffin to get a daily
			meal and get 20% of on each meal.</h4>
		<form action="<%=Constants.ADD_SCHEDULED_ORDER_URL_POST %>" method="post">
			<input type="hidden" name="mealType" value="LUNCH" /> <input
				type="hidden" name="area" value=""> <input type="submit"
				value="ADD DINNER" class="btn order_button" />
		</form>
		<br/>
		<form action="<%=Constants.ADD_SCHEDULED_ORDER_URL_POST %>" method="post">
			<input type="hidden" name="mealType" value="DINNER" /> <input
				type="hidden" name="area" value=""> <input type="submit"
				value="ADD LUNCH" class="btn order_button" />
		</form>
	</c:if>
	<c:forEach items="${customer.scheduledOrder}" var="order">
		<fmt:formatDate pattern="EEE, d MMM yyyy"
			value="${order.content.date}" var="orderDate" />
		<h4 class="order_summary_heading">You have scheduled to receive
			${order.mealType} tiffin from ${order.meal.vendor.name} Tiffins.</h4>
		<div class="order_details_card">
			<div class="row">
				<div class="col-md-6">
					<img alt="no_image" src="getMealImage.htm?mealId=${order.meal.id}"
						class="img-responsive tiffin_box_img1">
				</div>
				<c:choose>
					<c:when test="${order.status == 'NA'}">
                		Today's order ${order.meal.title} for ${order.mealType} is not yet available.
                	</c:when>
					<c:when test="${order.status == 'PAYABLE'}">
						<h4 class="today">
							You won't receive ${order.mealType} meal for ${orderDate}.
							<c:if
								test="${customer.balance < order.price || customer.balance == null}">
								<br />
                		You are low on cash.
                		<div class="add_wallet">
									Please <a href="<%=Constants.ADD_MONEY_TO_WALLET_URL_GET%>">add some money</a> into
									your wallet to continue ${order.meal.title} meal.
								</div>
							</c:if>
						</h4>
					</c:when>
					<c:when test="${order.status == 'CANCELLED'}">
						<h4 class="today">Your order ${order.meal.title} of
							${order.mealType} for ${orderDate} has been cancelled.</h4>
					</c:when>
					<c:when test="${order.status == 'DELIVERED'}">
						<h4 class="today">Your order ${order.meal.title} of
							${order.mealType} for ${orderDate} has been delivered. Please
							rate us!</h4>
					</c:when>
					<c:when test="${order.status == 'INVALID'}">
						<h4 class="today">Your order ${order.meal.title} of
							${order.mealType} is no longer available. Please select a different meal..</h4>
							<br />
							<%@include file = "forms/customer_change_meal_form.jsp" %>
					</c:when>
					<c:when test="${order.content!=null}">
						<div class="col-md-6">
							<h4 class="today">${order.mealType}
								menu of ${order.meal.title} For :
								<fmt:formatDate pattern="yyyy-MM-dd"
									value="${order.content.date}"></fmt:formatDate>
							</h4>
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
						<div class="col-md-6">
							<%-- <h3 class="order_details_title">Your meal is ${order.meal.status}</h3> --%>
							<c:choose>
								<c:when test="${order.mealStatus == 'PREPARE' }">
									<h3 class="order_details_title">Vendor got the sabji from
										the market and is now preparing your meal.</h3>
									<%@include file = "forms/customer_cancel_meal_form.jsp" %>
									<br />
									<%@include file = "forms/customer_change_meal_form.jsp" %>
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
					<c:otherwise>
						<h4 class="today">Menu of ${order.meal.title} for
							${order.mealType} is yet to be updated..</h4>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="order_bottom_div">
				<p class="order_bottom_label">
					Price :<span> ${order.meal.price}</span>
				</p>
				<p class="order_bottom_label">
					Scheduled from:<span> <fmt:formatDate pattern="yyyy-MM-dd"
							value="${order.date}" /></span>
				</p>
				<p class="order_bottom_label">
					Tiffins remaining:${customer.noOfTiffinsRemaining }<span></span>
				</p>
			</div>
		</div>
	</c:forEach>
	<c:if test="${fn:length(customer.scheduledOrder) == 1}">
		<%@include file = "forms/customer_add_scheduled_order_form.jsp" %>
	</c:if>

	<%@include file="footer.jsp"%>
</body>
</html>