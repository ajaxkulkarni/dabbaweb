<%@page import="com.rns.tiffeat.web.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Payment</title>
</head>
<body>

Redirecting to payment gateway.. Please do not close or refresh..
<!-- https://test.payu.in/_payment -->
<form id = "payment_form" action='<%=Constants.PAYMENT_URL%>' method='post'>
<!-- <form id = "payment_form" action='https://secure.payu.in/_payment' method='post'> -->
<input type="hidden" name="firstname" value="${payUDetails.name}" />
<input type="hidden" name="surl" value="${payUDetails.successUrl}" />
<input type="hidden" name="phone" value="${payUDetails.phone}" />
<input type="hidden" name="key" value="${payUDetails.merchantKey}" />
<input type="hidden" name="hash" value = "${payUDetails.hashKey}" />
<%-- <input type="hidden" name="curl" value="${payUDetails.cancel}" /> --%>
<input type="hidden" name="furl" value="${payUDetails.failureUrl}" />
<input type="hidden" name="txnid" value="${payUDetails.id}" />
<input type="hidden" name="productinfo" value="${payUDetails.productInfo}" />
<input type="hidden" name="amount" value="${payUDetails.amount}" />
<input type="hidden" name="email" value="${payUDetails.email}" />
<input type="hidden" name="drop_category" value="COD,CASH,EMI" />

<input type= "submit" value="submit" style="display:none" />

</form>

<script type="text/javascript">
	document.getElementById("payment_form").submit();
</script>

</body>
</html>