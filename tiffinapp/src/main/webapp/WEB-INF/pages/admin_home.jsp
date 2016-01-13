<%@page import="com.rns.tiffeat.web.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="admin_header.jsp" %>
<link href="<c:url value="${resources}/css/admin_home.css"/>" rel="stylesheet">
<script>
	$(document).ready(function() {
		$('#order_table1').DataTable({
			responsive : true
		});
	});
</script>
<body>

	

	<div class="container view_order">
		<a href="allOrders.htm" class="btn btn-default view_order_btn">View All Orders</a>
		<a href="meals.htm" class="btn btn-default view_order_btn">View Meals</a>
		<a href="customers.htm" class="btn btn-default view_order_btn">View Customers</a>
		<a href="billing.htm" class="btn btn-default view_order_btn">Billing</a>
	</div>
	<br/>
	<!--    vendor List Div-->

	<div class="container">
		<div id="order_list_div">
			<div class="table-responsive">
				<table class="table table-bordered table-hover" id="order_table1">

					<thead>
						<tr>
							<td>ID</td>
							<td>Name</td>
							<td>Address</td>
							<td>Phone_No</td>
							<td>Rating</td>
							<td>Status</td>
							<td>View</td>
						</tr>
					</thead>

					<c:forEach items="${vendors}" var="vendor">
						<tr>
							<form action="vendorDetails" method="post">
								<td>${vendor.id}</td>
								<td>${vendor.name}</td>
								<td>${vendor.address}</td>
								<td>${vendor.phone}</td>
								<td>${vendor.rating}</td>
								<td>${vendor.status}</td> <input type="hidden" name="email"
									value="${vendor.email}" />
								</td>
								<td><input type="submit"
									class="btn btn-default view_order1_btn" value="View" /></td>
							</form>
						</tr>
					</c:forEach>

				</table>
			</div>
		</div>
	</div>
	<!--    End of vendor list div-->

	<div class="container add_vendor">
		<a href="addVendor.htm" class="btn btn-default add_vendor_btn">Add
			Vendors</a>
	</div>

	<!--    menu card div-->
	<div class="container">
		<div id="card_menu1">

			<h4>Quick Orders:${admin.quickOrderCount}</h4>
			<h4>Schedule orders:${admin.scheduledOrderCount}</h4>
			<h4>Lunch Orders: ${fn:length(admin.lunchOrders)}</h4>
			<h4>Dinner Orders:${fn:length(admin.dinnerOrders)}</h4>

		</div>
	</div>

</body>

</html>