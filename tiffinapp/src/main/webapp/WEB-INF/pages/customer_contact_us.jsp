<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
	<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<link
	href='https://fonts.googleapis.com/css?family=Roboto:400,300,700,500'
	rel='stylesheet' type='text/css'>
<link href="<c:url value = "${resources}/css/contactUs.css"/>"
	rel="stylesheet">
<title>TiffEat | Contact Us</title>
</head>
<body class="drawer drawer-left">

	<%@include file="header.jsp"%>

	<div class="container">
		<h1 class="contactUsTitle">Contact Us</h1>

		<div class="contactUsCard">
			<h3>You can contact us on:</h3>
			<p>
				<img src="<c:url value="${resources}/img/ic_email_24px.svg"/>">
				<a href="mailto:contact@tiffeat.com">contact@tiffeat.com</a>
			</p>
			<p>
				<img src="<c:url value="${resources}/img/WhatsApp_Logo_3.svg"/>">
				<a href="tel:8087538194">8087538194</a>, <a href="tel:7350182285">7350182285</a>
			</p>
			<p>
				<img src="<c:url value="${resources}/img/ic_location_on_24px.svg"/>">
				Resoneuronance Software solutions Pvt Ltd. <br /> Sr.No. 60/3, Shiv
				Nagari, Bijali Nagar <br /> Chinchwad - 411030 <br /> Maharashtra,
				India <br />
			</p>


		</div>

		<p id="byResoneuronance">
			TiffEat is a service registered under <a
				href="http://resoneuronance.com" target="_blank">Resoneuronance</a>
		</p>
	</div>



	<%@include file="footer.jsp"%>

</body>
</html>