<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="admin_header.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<script type="text/javascript">
    function cancel(orderId) {
        $.ajax({
        	type : "POST",
            url : 'cancelCustomerOrder',
            data: "order=" + orderId,
            success : function(data) {
                //$('#result').html(data);
                alert("Cancelled!!");
            },
            error: function(e){
            	alert('Error in canceling!' + e);
        	}
        });
    }
</script>

<!-- daterangepicker.css admin_view_orders.css -->
<link href="<c:url value = "${resources}/css/daterangepicker.css" />"
	rel="stylesheet">
<link href="<c:url value = "${resources}/css/admin_view_orders.css"/>"
	rel="stylesheet">
<link href="<c:url value="${resources}/css/tablesort.css"/>"
rel="stylesheet">
<body>


	<div class="container" id="view_order_div">
		<a href="admin.htm?vendorEmail=${vendor.email}"
			class="btn btn-default back_btn1">Back</a>
		<form action="orders.htm">
			<div class="row">
				<div class="col-md-4">
					<select name="status" class="form-control option_dropdown">

						<c:forEach items="${orderStatus}" var="status">
							<option value="${status}">${status}</option>
						</c:forEach>
					</select>

				</div>
				<div class="col-md-4">
					<button type="submit" value="Filter"
						class="btn btn-default filter_btn1">Filter</button>
				</div>
			</div>
		</form>
<br/>
		<div class="demo">
			<form action="orders.htm" method="GET">
				<strong>Select Date:</strong> <input id="date-range0" size="40" value=""
					name="dateRange">
				<button type="submit" value="Filter By Date"
					class="btn btn-default filter_btn1">Filter By Date</button>
			</form>
		</div>
<br/>

		<div class="hscroll">

			<form action="delilverTiffins" method="post">
				<table class="table-sort table-sort-search" border="1">
					
				<thead>	
			     <tr>
					
						<th class="table-sort" style="padding: 0 18px"><strong>ID</strong></th>

						<th class="table-sort" style="padding: 0 35px"><strong>Name</strong></th>

						<th class="table-sort" style="padding: 0 35px"><strong>Phone</strong></th>

						<th class="table-sort" style="padding: 0 35px"><strong>Vendor</strong></th>

						<th class="table-sort" style="padding: 0 35px"><strong>Location</strong></th>

						<th class="table-sort" style="padding: 0 35px"><strong>Address</strong></th>

						<th class="table-sort" style="padding: 0 35px"><strong>Order
								date</strong></th>

						<th class="table-sort" style="padding: 0 35px"><strong>Order
								time</strong></th>

						<th class="table-sort" style="padding: 0 35px"><strong>Meal
								title</strong></th>

						<th class="table-sort" style="padding: 0 35px"><strong>Order
								format</strong></th>

						<th class="table-sort" style="padding: 0 35px"><strong>Order
								price</strong></th>

						<th class="table-sort" style="padding: 0 35px"><strong>Meal
								code</strong></th>

						<th class="table-sort" style="padding: 0 35px"><strong>Payment
								Type</strong></th>

						<th class="table-sort" style="padding: 0 35px"><strong>Order
								status</th>
						
						

						<th class="table-sort" style="padding: 0 35px"><strong>Area</strong></th>

						<th class="table-sort" style="padding: 0 35px"><strong>Email</strong></th>

					</tr>

					<c:forEach items="${orders}" var="order">
						<c:if test="${status == null || status == order.status}">

							<tr>
							</thead>
								<div class="table-sort">
									<td style="padding: 0 18px">${order.customerOrderId}</td>

									<td style="padding: 0 35px">${order.customer.name}</td>

									<td style="padding: 0 35px">${order.customer.phone}</td>

									<td style="padding: 0 35px">${order.meal.vendor.name}</td>

									<td style="padding: 0 35px">${order.location.address}</td>

									<td style="padding: 0 35px">${order.address}</td>

									<td style="padding: 0 35px"><fmt:formatDate
											value="${order.date}" pattern="yyyy-MM-dd" /></td>

									<td style="padding: 0 35px">${order.mealType}</td>

									<td style="padding: 0 35px">${order.meal.title}</td>

									<td style="padding: 0 35px">${order.mealFormat}</td>

									<td style="padding: 0 35px">${order.price}</td>

									<td style="padding: 0 35px">${order.meal.id}</td>

									<td style="padding: 0 35px">${order.paymentType}</td>

									<td style="padding: 0 35px">${order.status}</td>
									
									<td style="padding: 0 35px">${order.meal.vendor.pinCode}</td>

									<td style="padding: 0 35px">${order.customer.email}</td>
									
								</div>
								<c:if
									test="${order.mealStatus == 'DISPATCH' && order.status == 'ORDERED'}">
									<td><input type="checkbox" name="orders"
										value="${order.id}" />Deliver</td>
								</c:if>
								<c:if
									test="${order.status == 'ORDERED' && order.mealStatus == 'PREPARE'}">
									<%-- <form action="cancelCustomerOrder" method="post"> --%>
									<%-- <input type="hidden" name="id" value="${order.id}"/> --%>
									<td><input type="button" value="Cancel"
										onclick="cancel(${order.id})"></td>
									<%-- </form> --%>
								</c:if>

								
							</tr>

						</c:if>
					</c:forEach>
					</div>
				</table>

				<input type="submit" value="Deliver Tiffins"
					class="btn btn-default deliver_tiffin_btn1" />
			</form>
			<!--<form action="downloadExcel" method="post">
				<input type="hidden" name="status" value="${status}" /> 
					<input type="submit" value="Generate report" />
				</form>-->



			<form action="downloadExcel" method="post">
				<div class="row">
					<input type="hidden" name="status" value="${status}"
						class="btn btn-default status_btn1" /> <input type="submit"
						value="Generate report"
						class="btn btn-default generate_report_btn1" />
			</form>
		</div>
		${result}

	</div>

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