<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
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
<link href="<c:url value = "${resources}/css/login_page.css"/>"
	rel="stylesheet">


<title>TiffEat | Login</title>
</head>
<body>

	<%@include file="header.jsp"%>

	<div class="">

		<div class="login_box">

			<h4 class="login_form_title">Login Form</h4>

			<div class="">
				<div class=" login_form_card">
					<form role="form" action="customerLogin" method="post">
						<label for="username">Username</label> <input type="email"
							class="form-control" id="username" name="email" autofocus>
						<label for="password">Password</label> <input type="password"
							class="form-control" id="password" name="password">
						<div class="checkbox">
							<!--  <label>
                                      <input type="checkbox" name="remember" id="remember"> Remember login
                                  </label> -->
							<!--<p class="">(if this is a private computer)</p>-->
							<c:if test="${result != null }">
								<div class="alert alert-danger">
									<a href="#" class="close" data-dismiss="alert"
										aria-label="close">&times;</a>
									<!-- <strong>Danger!</strong> -->${result}
								</div>
							</c:if>
							<div class="login_button">
								<button type="submit" class="btn btn-success btn-block">Login</button>

							</div>
						</div>
					</form>

					<p>OR</p>

					<div class="google">
						<a href="<%=Constants.GOOGLE_GET_CODE_URL%>"><img alt=""
							src="<c:url value = "${resources}/img/btn_google_signin_dark_normal_web@2x.png"/>"></a>
					</div>


					<a href="#registration_form" id="register_now">New User? Register Now.</a>
				</div>
				<!--<div class="col-md-6 register_card">
					<p>
						Register now for <span class="text-success">FREE</span>
					</p>
					<ul class="list-unstyled reg_list">
						<li><span class="fa fa-check text-success"></span> See all
							your orders</li>
						<li><span class="fa fa-check text-success"></span> Fast
							re-order</li>
						<li><span class="fa fa-check text-success"></span> Save your
							favorites</li>
						<li><span class="fa fa-check text-success"></span> Fast
							checkout</li>
					</ul>
					<p>
						<a href="#register_box" class="btn btn-info btn-block">Register
							Now</a>-->
				</p>
			</div>
		</div>
	</div>
	</div>


	<div id="customerEmail">${customer.email}</div>
	<div class="container">
		<div id="registration_form">
			<form action="registerCustomer" method="post"
				onsubmit="return checkPasswordMatch()">
				<div class="register_box" id="register_box">
					<h4 class="register_form_title">Registration Form</h4>
					<!--<a href="customerLogin.htm" style="color: black;"><strong>Already
							Registered? Login</strong></a>-->
					<c:if test="${result != null }">
						<div class="alert alert-danger">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							<!-- <strong>Danger!</strong> -->${result}
						</div>
					</c:if>
					<div class="register_form">
						<form role="form">
							<div class=" ">
								<label for="firstName" class="">Full Name</label> <input
									type="text" id="firstName" name="name" placeholder="Full Name"
									class="form-control" required>
							</div>
							<div class=" ">
								<label for="email" class="">Email</label> <input type="email"
									id="email" name="email" placeholder="Email"
									class="form-control" required>
							</div>
							<div class=" ">
								<label for="password" class="">Password</label> <input
									type="password" id="password" name="password"
									placeholder="Password" class="form-control" required>
							</div>

							<div class=" ">
								<label class="" for="password_confirm">Confirm Password
								</label> <input type="password" id="password_confirm"
									placeholder="Re-type Password" class="form-control" required>
								<p class="error_alert" id="password_error"></p>
							</div>

							<div class=" ">
								<input type="checkbox" name="remember" id="remember" required>
								I accept all <a href="terms.htm" class="universal_link"
									target="_blank"><strong>terms and conditions. </strong></a>

							</div>
							<div class="register_button">
								<button type="submit" class="btn btn-success btn-block ">Register</button>
							</div>
						</form>
					</div>
				</div>
			</form>
		</div>
	</div>


</body>
</html>