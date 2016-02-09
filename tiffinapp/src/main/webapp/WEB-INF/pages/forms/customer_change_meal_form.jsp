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

	<form action="<%=Constants.CHANGE_MEAL_URL_POST%>" method="post">
		<input type="hidden" name="meal.title" value="${order.meal.title}" />
		<input type="hidden" name="meal.vendor.name" value="${order.meal.vendor.name}" /> 
		<input type="hidden" name="id" value="${order.customerOrderId}" /> 
		<input type="hidden" name="mealType" value="${order.mealType}" /> 
		<input type="hidden" name="meal.id" value="${order.meal.id}" /> 
		<input type="hidden" name="mealFormat" value="${order.mealFormat}" /> 
		<input type="hidden" name="address" value="${order.address}" /> 
		<input type="hidden" name="content.id" value="${order.content.id}" /> 
		<input type="hidden" name="location.address" value="${order.location.address}" /> 
		<input type="hidden" name="menuDate" value="${order.content.date}" /> 
		<input type="hidden" name="meal.vendor.status" value="${order.meal.vendor.status}"> 
		<input type="submit" value="CHANGE THIS MEAL" class="btn order_button">
	</form>

</body>
</html>