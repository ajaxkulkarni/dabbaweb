<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>

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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Account activation</title>
<style>
.payment_details_card{
	margin: 16px 70px;
	box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.26);
	background-color: white;
	padding: 20px;
	}
	
.details p{
font-size:16px;
font-family: 'Roboto', sans-serif;
margin: center;
}
</style>
</head>
<body>

<%@include file="header.jsp"%>
<br/>
<br/>
<div class="payment_details_card">
			<div class="row">
				<div class="col-md-6">
					
						<div class="details">
							<p>We have sent an activation link to your Email: ${email}</p><br/>
<p>Please click on that link to activate your account and Enjoy <strong>TiffEating!</strong></p>
							
							
						</div>
						
						
						
					</form>
				</div>
		</div>


</body>
</html>