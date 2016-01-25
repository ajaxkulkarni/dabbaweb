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
<!-- <script>
	$(document).ready(function() {
		$('#order_table1').DataTable({
			responsive : true
		});
	});
</script> -->

<!-- daterangepicker.css admin_view_orders.css -->
<link href="<c:url value = "${resources}/css/daterangepicker.css" />" rel="stylesheet">
<link href="<c:url value = "${resources}/css/admin_view_orders.css"/>" rel="stylesheet">
<body>

<br/>
	
<a href="admin.htm?vendorEmail=${vendor.email}"
class="btn btn-default back_btn1">Back</a>
	<div class="demo">
	<h4>Billing for : ${invoices[0].dateRange}</h4>
		<form action="billing.htm" method="GET">
		<br/>
		<strong>Select Date:</strong> <input id="date-range0" size="40" value="" name="dateRange">
			<button type="submit" value="Filter By Date" class="btn btn-default filter_btn1">Generate</button>
		</form>
	</div>
    <br/>
	<div class="container">
		<div id="order_list_div">
			<div class="table-responsive">
				<table class="table table-bordered table-hover" id="order_table1">
					<thead>
						<tr>
							<td>Vendor</td>
							<td>Lunch Orders</td>
							<td>Dinner Orders</td>
							<td>Due Amount</td>
							<td>Profit</td>
						</tr>
					</thead>

					<c:forEach items="${invoices}" var="invoice">
						<tr>
							<td><a
								href="vendorOrders.htm?vendorId=${invoice.vendor.id}&dateRange=${invoice.dateRange}">${invoice.vendor.name}</a></td>
							<td>${fn:length(invoice.lunchOrders)}</td>
							<td>${fn:length(invoice.dinnerOrders)}</td>
							<td>${invoice.due}</td>
							<td>${invoice.profit}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<!--    End of vendor list div-->

	<!-- <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script> -->
	<script src="http://yandex.st/highlightjs/7.3/highlight.min.js"></script>
	<script type="text/javascript"
		src="<c:url value = "${resources}/js/tablesort.js"/>"></script>
	<script type="text/javascript">
            // For Demo Purposes
            $(function () {
                $('table.table-sort').tablesort();
                hljs.initHighlightingOnLoad(); // Syntax Hilighting
            });
        </script>

	<!-- for date range picker  -->
	<script type="text/javascript"
		src="<c:url value = "${resources}/js/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value = "${resources}/js/jquery.daterangepicker.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value = "${resources}/js/demo.js"/>"></script>
	<script type="text/javascript">
	$(function()
	{
		$('a.show-option').click(function(evt)
		{
			evt.preventDefault();
			$(this).siblings('.options').slideToggle();
		});
	})
	</script>

</body>

</html>