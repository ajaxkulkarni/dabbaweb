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

	<form action="<%=Constants.ADD_SCHEDULED_ORDER_URL_POST%>"
		method="post">
		<input type="hidden" name="mealType" value="${customer.scheduledOrder[0].mealType}" />
		<input type="hidden" name="area" value="${customer.scheduledOrder[0].area}">
		<input type="submit" value="ADD DINNER" class="btn order_button" />
	</form>

</body>
</html>