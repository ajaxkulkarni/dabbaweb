<%@page import="com.rns.tiffeat.web.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<form action="<%=Constants.CANCEL_ORDER_URL_POST%>" method="post" commandName="customerOrder" onsubmit="return confirmCancel()">
		<input type="hidden" name="id" value="${order.customerOrderId}" /> 
		<input type="hidden" name="mealType" value="${order.mealType}" /> 
		<input type="submit" value="CANCEL THIS MEAL" class="btn order_button">
	</form>

	<script>
		function confirmCancel() {
			var r = confirm("Are you sure you want to cancel this meal?");
			return r;
		}
	</script>

</body>
</html>