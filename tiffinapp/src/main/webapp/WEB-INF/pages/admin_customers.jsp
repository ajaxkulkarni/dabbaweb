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
<%@include file="admin_header.jsp"%>
<link href="<c:url value="${resources}/css/admin_home.css"/>"
	rel="stylesheet">
<link href="<c:url value="${resources}/css/tablesort.css"/>"
rel="stylesheet">

<script>
	$(document).ready(function() {
		$('#order_table1').DataTable({
			responsive : true
		});
	});
</script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script> 
<script src="http://yandex.st/highlightjs/7.3/highlight.min.js"></script> 
<script type="text/javascript"
src="<c:url value = "${resources}/js/tablesort.js"/>"></script>
<script>
            $(function () {
                $('table.table-sort').tablesort();
                hljs.initHighlightingOnLoad(); // Syntax Hilighting
            });
        </script>
<body>
    <div class="container view_order">
		<a href="admin.htm" class="btn btn-default view_order_btn">Back</a>
	</div>
	<br />
	<div class="container">
		<div id="order_list_div">
			<div class="table-responsive">
				<!--<table class="table table-bordered table-hover" id="order_table1">-->
				<table class="table-sort table-sort-search">
					<thead>
					
						<tr>
						<th class="table-sort">Email</th>
						<th class="table-sort">Name</th>
						<th class="table-sort">Phone</th>
						<th class="table-sort">Quick Order</th>
						<th class="table-sort">Schdule Order</th>
						<th class="table-sort">Balance</th>
							<!--<th>Email</th>
							<th>Name</th>
							<th>Phone</th>
							<th>Quick Orders</th>
							<th>Scheduled Orders</th>
							<th>Balance</th>-->
						</tr>
					</thead>
					<tbody>
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
</tbody>
				</table>
				
			</div>
		</div>
	</div>

</body>

</html>