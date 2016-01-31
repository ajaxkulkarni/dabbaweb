<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<link
	href='https://fonts.googleapis.com/css?family=Roboto:400,300,700,500'
	rel='stylesheet' type='text/css'>
<link href="<c:url value = "${resources}/css/customer_add_to_wallet.css"/>"
	rel="stylesheet">
<title>TiffEat | Add Money To Wallet</title>
</head>
<body>

	<%@include file="header.jsp"%>

	<div class="container add_wallet_div">
		<h4 class="add_wallet_heading">Add money to your wallet.</h4>
		<div class="add_wallet_card">
			<div class="row">
				<div class="col-md-6">
					<img alt="no_image"
						src="<c:url value = "${resources}/img/payment_del.png"/>"
						class="img-responsive wallet_img1">
				</div>
				<div class="col-md-6">
					<h3 class="add_wallet_title">Wallet Balance</h3>
					<h6 class="wallet_balance1">
						<label id="rupee_label"> Rs. </label> ${customer.balance}
					</h6>
					<form action="<%=Constants.ADD_MONEY_TO_WALLET_URL_POST%>" method="post">
						<input type="text" class="form-control" placeholder="Amount (Rs) "
							name="amount">
						<button type="submit" pattern="[0-9.]*" maxlength="4"
							name="amount" class="btn  add_money_button">Add Money</button>
					</form>
					<c:if test="${customer.balance > 100 }">
						<form action="<%=Constants.CUSTOMER_HOME_URL_GET%>" method="get">
							<button type="submit" class="btn  add_money_button">Add
								Later</button>
						</form>
					</c:if>
				</div>
			</div>

		</div>

		</form>
	</div>
	<%@include file="footer.jsp"%>
</body>
</html>