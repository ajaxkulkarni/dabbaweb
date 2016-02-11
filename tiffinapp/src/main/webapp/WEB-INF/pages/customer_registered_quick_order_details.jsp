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
<title>TiffEat | Payment Details</title>
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

<link href="<c:url value = "${resources}/css/payment_details.css"/>"
	rel="stylesheet">
<script type="text/javascript">
	function showModal() {
		
		$("#modalPrice").text("Total Payable :" + calculatePrice());
		$("#modalAddress").text("Address : " + $("#txtAddress").val());
		$("#modalPhone").text("Phone :" + $("#txtPhone").val());
		$("#payment_Modal").modal('show');
		return false;
	}
	
	function calculatePrice() {
		var quantity = parseInt($("#quantity").val());
		var mealPrice = parseInt($("#mealPrice").val());
		var i = 0;
		var price = 0;
		for(i=0; i<quantity; i++) {
			price = price + mealPrice;
		}
		$("#totalPrice").text(price);
		return price;
	}
</script>
</head>
<body onload="calculatePrice()">

	<%@include file="header.jsp"%>

	<div class="container payment_details_div">
		<h4 class="payment_details_heading">Just one step away!</h4>
		<div class="payment_details_card">
			<c:if test="${result != 'OK' && result!=null }">
				<div class="alert alert-danger">
					<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					<!-- <strong>Danger!</strong> -->${result}
				</div>
			</c:if>
			<fmt:formatDate pattern="yyyy-MM-dd" value="${customerOrder.date}"
				var="orderDate" />
			<%--  Order Date : ${orderDate} <br/> --%>


			<div class="row">
				<div class="col-md-6">
						<div class="details">
							Meal : ${customerOrder.meal.title}<br /> Order for :
							${customerOrder.customer.name} <br /> Email :
							${customerOrder.customer.email} <br />
							Date : <fmt:formatDate pattern="yyyy-MM-dd" value="${customerOrder.date}"/> <br/>
							Location : ${customerOrder.location.address} <br /> Price :
							${customerOrder.meal.price} <br/>
							Total price :<div id="totalPrice"></div>
						</div>
						<strong>Meal timing : ${customerOrder.mealType}</strong>
						<%@include file ="forms/customer_quick_order_form.jsp" %>
				</div>
				<div class="col-md-6">
					<img
						src="<c:url value="${resources}/img/Payment-Methods-Icons.jpg"/>"
						class="img-responsive payment_method_icon"
						alt="payment_method_icon">

				</div>
			</div>

		</div>

	</div>

	<div class="modal fade" id="payment_Modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Order Details</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-6">
							<h6 class="order_label1">Meal : ${customerOrder.meal.title}</h6>
							<h6 class="order_label1">
								<div id="modalDate">Date :${orderDate}</div>
							</h6>
							<h6 class="order_label1">
								<div id="modalMealType">Timing :${customerOrder.mealType.description}</div>
							</h6>
							<h6 class="order_label1">Location :
								${customerOrder.location.address}</h6>
							<h6 class="order_label1">
								<div id="modalAddress"></div>
							</h6>
							<h6 class="order_label1">
								<div id="modalPhone"></div>
							</h6>
							<h6 class="order_label1" id = "modalPrice"></h6>
						</div>
						<div class="col-md-6">
							<img src="getMealImage.htm?mealId=${customerOrder.meal.id}"
								alt="no_image" class="order_img1 img-responsive">
						</div>


					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							Cancel</button>
						<button type="button" class="btn btn-primary" onclick="proceed()">Proceed</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!--        End of Order Details div-->
	<script type="text/javascript">
		function proceed() {

			document.getElementById("quickOrderForm").submit();
		}
	</script>
	<%@include file="footer.jsp"%>
</body>
</html>