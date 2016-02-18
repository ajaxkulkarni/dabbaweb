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

	<form action="<%=Constants.GET_NEARBY_VENDORS_FOR_CHANGE_ORDER_URL_POST%>" method="post">
	Location :	<input type="text" name="location.address" id="areas" placeholder="Enter Your Location" value="${customerOrder.location.address}" class="option_dropdown" required /> <br />
				<input type="hidden" name="id" value="${customerOrder.id}"/>
				<input type="hidden" name="mealType" value="${customerOrder.mealType}" /> 
				<input type="hidden" name="mealFormat" value="${customerOrder.mealFormat}" />
				<div class="divspacing">
					<textarea class="form-control" type="text" pattern="" id="txtAddress" name="address" placeholder="ADDRESS" required="required">${customerOrder.address}</textarea>
				</div>
				<div class="submit_order">
					<input type="submit" name="" value="Find Meals"	class="btn order_button">
				</div>
	</form>


</body>
</html>