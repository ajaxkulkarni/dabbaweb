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

	<div class="container">
		<div class=" login_box">
			<h4 class="login_form_title">Login Form</h4>
			<div class="row">
				<div class="col-md-6 login_form_card">
					<form role="form" action="customerLogin" method="post">
						<label for="username">Username</label> <input type="email"
							class="form-control" id="username" name="email"> <label
							for="password">Password</label> <input type="password"
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
							<button type="submit" class="btn btn-success btn-block">Login</button>
							<!-- <a href="" class="btn btn-default btn-block">Forgot Password</a> -->
						</div>
					</form>

					<div class="google">
						<a href="<%=Constants.GOOGLE_GET_CODE_URL%>"><img alt=""
							src="<c:url value = "${resources}/img/btn_google_signin_dark_normal_web@2x.png"/>"></a>
					</div>
				</div>
				<div class="col-md-6 register_card">
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
						<a href="registerCustomer.htm" class="btn btn-info btn-block">Register
							Now</a>
					</p>
				</div>
			</div>
		</div>
	</div>
	<%@include file="footer.jsp"%>
</body>
</html>