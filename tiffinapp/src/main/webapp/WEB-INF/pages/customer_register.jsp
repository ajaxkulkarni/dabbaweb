<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>TiffEat | Register</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<!-- <script type="text/javascript">

$(function(){
    alert("Email is:" + $("#customerEmail").text());
    window.history.forward()
});
</script>  -->   
    
</head>
<body>

	<%@include file="header.jsp" %>
	<div id = "customerEmail">${customer.email}</div>
   <div class="container-fluid">
        <div class="row">
        	<form action="registerCustomer" method="post" onsubmit="return checkPasswordMatch()">
                <div class="col-md-8 col-md-offset-2 register_box">
                    <h4 class="register_form_title">Registration Form</h4> 
                    <a href="customerLogin.htm" style="color:black;"><strong>Already Registered? Login</strong></a>
                     <c:if test="${result != null }">
								<div class="alert alert-danger">
									<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
									<!-- <strong>Danger!</strong> -->${result}
								</div>
							</c:if>
                    <div class="col-md-12 register_form">
                    <form  role="form">
                    <div class="col-md-9">
                        <label for="firstName" class="">Full Name</label>
                        <input type="text" id="firstName" name="name" placeholder="Full Name" class="form-control" autofocus required>
                    </div>
                    <div class="col-md-9">
                        <label for="email" class="">Email</label>
                        <input type="email" id="email" name="email" placeholder="Email" class="form-control" required>
                    </div>
                        <div class="col-md-9">
                            <label for="password" class="">Password</label>
                            <input type="password" id="password" name="password" placeholder="Password" class="form-control" required>
                        </div>
                        
                        <div class="col-md-9">
                            <label class="" for="password_confirm">Password (Confirm)</label>
                            <input type="password" id="password_confirm" placeholder="Re-type Password" class="form-control" required>
                            <p class="error_alert" id="password_error"></p>
                        </div>
                        
                        <div class="col-md-9">
                         <label class="checkbox">
                             <input type="checkbox" name="remember" id="remember" required><strong>I accept all <a href = "terms.htm" class="universal_link" target="_blank">terms and conditions.</strong></a>
                        </label>
                        </div>
						<button type="submit" class="btn btn-success col-md-4">Register</button>
                    </form>
                    <div class="google">
                    <a href="<%=Constants.GOOGLE_GET_CODE_URL%>"><img alt="" src="<c:url value = "${resources}/img/sign-in-with-google.png"/>" style="height:40px;width:300px;" ></a>
            		</div>
                    </div>
                </div>
        	</form>
        </div>
	</div>
   <%@include file="footer.jsp" %>
</body>

<script>
        function checkPasswordMatch() {
    var password = $("#password").val();
    var confirmPassword = $("#password_confirm").val();

    if (password != confirmPassword)
    {
        $("#password_error").html("Passwords do not match!");
//        document.getElementById("password_confirm").style.borderColor = "#E34234";
        document.getElementById("password_confirm").style.borderColor = "#E34234";
        return false;
    }else
    {
        $("#password_error").html("Passwords match.");
        document.getElementById("password_confirm").style.borderColor = "#99e334";
        return true;
    }
}

$(document).ready(function () {
   $("#password_confirm").keyup(checkPasswordMatch);
});
    </script>


</html>