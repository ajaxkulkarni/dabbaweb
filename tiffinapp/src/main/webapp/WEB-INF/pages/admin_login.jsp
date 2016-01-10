<%@page import="com.rns.tiffeat.web.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <html>
<head>

<!-- admin_login.css -->    
<link href="<c:url value="${resources}/css/admin_login.css"/>" rel="stylesheet">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>

<%@include file="admin_header.jsp" %>

<div class="main">
		<form action="adminLogin" method="post">
    		<h1><lable>Admin Login </lable> </h1>
  			<div class="inset">
	  			<p>
	    		 <label for="email">Username</label>
   	 			<input type="text" placeholder="Username" name="username" required/>
				</p>
  				<p>
				    <label for="password">Password</label>
				    <input type="password" placeholder="Password" name="password" required/>
  				</p>
  				<input type="submit" value="Login"/>
 			 </div>
		</form>
	</div>  

	
	
	
</body>
</html>