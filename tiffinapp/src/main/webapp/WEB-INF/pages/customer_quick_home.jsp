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
<meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>
<body >

	<%@include file="header.jsp" %>
	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="nowDateString"></fmt:formatDate>
	<div class="container order_summary_details_div">
		<c:if test="${fn:length(customer.quickOrders) gt 0}">
        <h4 class="order_summary_heading">You have ordered ${fn:length(customer.quickOrders)} tiffin today :</h4>
        <c:forEach items="${customer.quickOrders}" var="quickOrder">
				<div class="order_details_card">
					<div class="row">
						<div class="col-md-6">
							<img alt="no_image" src="getMealImage.htm?mealId=${quickOrder.meal.id}" class="img-responsive tiffin_box_img1">
						</div>
						<fmt:formatDate pattern="yyyy-MM-dd" value="${quickOrder.date}"  var="quickOrderDate"></fmt:formatDate>
						<c:choose>
							<c:when test="${quickOrderDate ne nowDateString}">
								<h3 class="menu_list_title">You have ordered ${quickOrder.meal.title}
									for tomorrow's(${quickOrderDate}) ${quickOrder.mealType}.</h3>
							</c:when>
							<c:when test="${quickOrder.status == 'DELIVERED'}">
                				Today's order ${quickOrder.meal.title} for ${quickOrder.mealType} has been delivered. Please rate us!
                			</c:when>
                			<c:when test="${quickOrder.status == 'CANCELLED'}">
                				Today's order ${quickOrder.meal.title} for ${quickOrder.mealType} has been cancelled. Sorry for the inconvenience!
                			</c:when>
                		</c:choose>
                		<c:choose>
							<c:when test="${quickOrder.content  != null && quickOrder.status!='CANCELLED'}">
								<div class="col-md-6">
									<h3 class="menu_list_title">
											${quickOrder.mealType} menu of Tiffin
											${quickOrder.meal.title} for ${quickOrderDate} is:</h3>
									<table class="table table-bordered menu_list_table">
										<tr>
												<td>Sabji :</td>
												<td>${quickOrder.content.mainItem}</td>
											</tr>
											<tr>
												<td>Chapati/Roti :</td>
												<td>${quickOrder.content.subItem1}</td>
											</tr>
											<tr>
												<td>Rice/Dal :</td>
												<td>${quickOrder.content.subItem2}</td>
											</tr>
											<tr>
												<td>Salad :</td>
												<td>${quickOrder.content.subItem3}</td>
											</tr>
											<tr>
												<td>Extra :</td>
												<td>${quickOrder.content.subItem4}</td>
											</tr>
									</table>
								</div>
								<div class="col-md-6">
									<%-- <h3 class="order_details_title">Your meal is ${quickOrder.meal.status}</h3> --%>
									<c:choose>
										<c:when test="${quickOrder.mealStatus == 'PREPARE' }">
											<h3 class="order_details_title">Vendor got the sabji from the market and is now preparing your meal.</h3>
										</c:when>
										<c:when test="${quickOrder.mealStatus == 'COOKING' }">
											<h3 class="order_details_title">Vendor started cooking your meal. Hmmmm....smells delicious!!</h3>
										</c:when>
										<c:when test="${quickOrder.mealStatus == 'DISPATCH' && quickOrder.status != 'DELIVERED' }">
											<h3 class="order_details_title">Your meal has been dispatched. It will be delivered anytime now!</h3>
										</c:when>
									</c:choose>
									
								</div>
							</c:when>
							<c:when test="${quickOrder.content == null}">
								Menu for ${quickOrder.meal.title} for ${quickOrder.mealType} is not currently updated by the vendor.
							</c:when>
							</c:choose>
					</div>
					<form action="repeatOrder" method="post">
						<input type="hidden" name="meal.id" value="${quickOrder.meal.id}" /> 
						<input type="hidden" name="meal.title" value="${quickOrder.meal.title}" /> 
						<input type="hidden" name="meal.price" value="${quickOrder.meal.price}" /> 
						<input type="hidden" name="meal.description" value="${quickOrder.meal.description}" /> 
						<input type="hidden" name="area" value="${quickOrder.area}" /> 
						<input type="hidden" name="address" value="${quickOrder.address}" />
						<input type="hidden" name="location.address" value="${quickOrder.location.address}" />
						<input type="submit" name="" value="REPEAT?" class="btn order_button"/>
					</form>
				</div>
			</c:forEach>
        </c:if>
        <form action="customerSelectVendor" method="POST">
		<div class="submit_order">
			<button type="submit" class="btn order_button">MAKE ANOTHER ORDER</button>
		</div>
		<input type="hidden" name="pinCode"
			value="${customer.quickOrders[0].area}" class="cancel"/>
		</form>
        <h4 class="prev_order_heading">Recent orders</h4>
	<div class="row">
		<c:forEach items="${customer.previousOrders}" var="order">
		<div class="col-md-3">
			<div class="prev_order_card">
				<img src="getMealImage.htm?mealId=${order.meal.id}" class="img-responsive" id = "prevOrderImage">
				<h4 class="menu_card_title">${order.meal.title}</h4>
				<h6 class="menu_card_sub_title">${order.meal.vendor.name}</h6>
				<h6 class="menu_card_sub_title">Price : ${order.meal.price}</h6>
				<h6 class="menu_card_sub_title">Date : <fmt:formatDate value="${order.date}" pattern="yyyy-MM-dd"/></h6>
				<input id="input-2a" class="rating" min="0" max="5" step="0.5"
					data-size="sm" data-symbol="&#xf005;" data-glyphicon="false"
					data-rating-class="rating-fa">
				<form action="repeatOrder" method="post">
            	<input type="hidden" name="meal.id" value="${order.meal.id}" /> 
            	<input type="hidden" name="meal.title" value="${order.meal.title}" /> 
            	<input type="hidden" name="meal.price" value="${order.meal.price}" /> 
            	<input type="hidden" name="meal.description" value="${order.meal.description}" />
				<input type="hidden" name="area" value="${order.area}" />
				<input type="hidden" name="address" value="${order.address}" />
				<input type="hidden" name="location.address" value="${order.location.address}" />
				<button type="submit" class="btn order_button">Repeat Order</button>
				</form>
			</div>
		</div>
		</c:forEach>
	</div>
	<br/>
	</div>
	<%@include file="footer.jsp" %>
</body>
</html>