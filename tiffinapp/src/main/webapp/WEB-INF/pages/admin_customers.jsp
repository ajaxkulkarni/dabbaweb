<%@page import="com.rns.tiffeat.web.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<c:url value="${resources}/css/admin_home.css"/>" rel="stylesheet">
<script>
	$(document).ready(function() {
		$('#order_table1').DataTable({
			responsive : true
		});
	});
</script>
<body>
	<%@include file="admin_header.jsp" %>
	<div class="container view_order">
	<a href="admin.htm" class="btn btn-default view_order_btn">Back</a>
	</div>
<br/>
	<div class="container">
		<div id="order_list_div">
			<div class="table-responsive">
				<table class="table table-bordered table-hover" id="order_table1">

					<thead>
						<tr>
							<td>Email</td>
							<td>Name</td>
							<td>Phone</td>
							<td>Quick Orders</td>
							<td>Scheduled Orders</td>
							<td>Balance</td>
						</tr>
					</thead>

					<c:forEach items="${customers}" var="customer">
						<tr>
								<td>${customer.email}</td>
								<td>${customer.name}</td>
								<td>${customer.phone}</td>
								<td>${fn:length(customer.previousOrders)}</td>
								<td>${fn:length(customer.scheduledOrder)}</td>
								<td>${customer.balance}</td>
						</tr>
					</c:forEach>

				</table>
			</div>
		</div>
	</div>

</body>

</html>