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
<form action="<%=Constants.QUICK_ORDER_URL_POST %>" id="quickOrderForm" method="post" onsubmit="return showModal()">
	<%-- <input type="hidden" name="customer.id" value="${customerOrder.customer.id}" />
	<input type="hidden" name="meal.price" value="${customerOrder.meal.price}" id="mealPrice" />
	<input type="hidden" name="meal.title" value="${customerOrder.meal.title}" />
	<input type="hidden" name="location.address" value="${customerOrder.location.address}" />
	<input class="form-control" type="hidden" readonly="readonly" id="name1" name="customer.name" value="${customerOrder.customer.name}" placeholder="What's your name?" required="required" maxlength="50" />
	<input class="form-control" type="hidden" readonly="readonly" id="email_id" name="customer.email" placeholder="EMAIL ID" value="${customerOrder.customer.email}" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" maxlength="30" required="required" />
	<input type="hidden" name="mealType" value="${customerOrder.mealType}" /> --%>
	<input class="form-control" type="text" maxlength="15" pattern="^(\+?\d{1,4}[\s-])?(?!0+\s+,?$)\d{10}\s*,?$" id="txtPhone" name="customer.phone" value="${customerOrder.customer.phone}" placeholder="MOBILE NUMBER" required="required" />
	<textarea class="form-control" type="text" rows="3"	id="txtAddress" name="address"	text="${customerOrder.customer.previousOrders[0].address}" placeholder="ADDRESS" maxlength=150 minlength=10 required>${customerOrder.address}</textarea>
	<div class="quantity">
	Quantity : <input type="number" size="10" min="1" max ="5" id='quantity' name="quantity" value="1" step="1" onclick="calculatePrice()"  />
	</div>
	<br/>
	<div class="payment">
	Payment method : <br /></div> 
	<label	class="radio option_radio"> 
		<input type="radio"	id="option-1" class="" name="paymentType" value="ONLINE" required="required" /> 
		<span class="">Online Payment</span>
	</label> 
	<label class="radio option_radio"> 
		<input type="radio"	id="option-1" class="" name="paymentType" value="CASH"	required="required" /> <span class="">Cash on delivery</span>
	</label> 
	<%-- <input type="hidden" name="meal.id"	value="${customerOrder.meal.id}" /> 
	<input type="hidden" name="area" value="${customerOrder.area}" />  --%>
	<label	class="checkbox chbox1"> 
		<input type="checkbox" value="agree" required>I agree to <a href="terms.htm" class="universal_link" target="_blank">terms and conditions</a>
	</label>
	<button class="btn order_button" type="submit">Order</button>
	
</form>
</body>
</html>