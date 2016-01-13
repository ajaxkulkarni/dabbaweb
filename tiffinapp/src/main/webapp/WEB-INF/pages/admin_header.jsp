<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin</title>
<link href="<c:url value="${resources}/css/bootstrap.min.css"/>" rel="stylesheet">

<!-- Custom Fonts -->
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="http://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
<link href="http://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic" rel="stylesheet" type="text/css">

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="<c:url value="${resources}/js/jquery.min.js"/>"></script>    
<!--      DataTables javascript -->

<script type="text/javascript" src="<c:url value = "${resources}/js/jquery-1.11.2.min.js"/>"></script>
<script src="${resources}/js/jquery-1.11.3.min.js"></script>
<script src="${resources}/js/dataTables.bootstrap.min.js"></script>
<script src="${resources}/js/jquery.dataTables.min.js"></script>


<!-- Custom CSS -->
<%-- <link href="<c:url value="${resources}/css/admin_home.css"/>" rel="stylesheet"> --%>
<%-- <link href="<c:url value = "${resources}/css/meals_select.css"/>" rel="stylesheet"> --%>
<%-- <link href="<c:url value = "${resources}/css/animate.css"/>" rel="stylesheet">  
<link href="<c:url value = "${resources}/css/admin_add_meal.css"/>" rel="stylesheet"> --%> 
<%-- <link href="<c:url value = "${resources}/css/signup.css"/>" rel="stylesheet"> --%>
<%-- <link href="<c:url value = "${resources}/css/admin_edit_meal.css"/>" rel="stylesheet"> --%>
<%-- <link href="<c:url value="${resources}/css/admin_login.css"/>" rel="stylesheet"> --%>
<%-- <link href="<c:url value="${resources}/css/freelancer.css"/>" rel="stylesheet"> --%>
<%-- <link rel="stylesheet" type="text/css" href="${resources}/css/tablesort.css">
<link rel="stylesheet" type="text/css" href="${resources}/css/styles.css"> --%>
<%-- <link href="<c:url value = "${resources}/css/daterangepicker.css" />" rel="stylesheet">
<link href="<c:url value = "${resources}/css/admin_view_orders.css"/>" rel="stylesheet"> --%>


</head>
<body>

<!--    Start of Navbar -->
	<nav class="navbar navbar-default">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="admin.htm">
				<h4 class='tiff'>
					Tiff<span class="eat">Eat</span>- Vendors
				</h4>
			</a>
		</div>
		<div class="username">
			<a href="editVendor.htm?vendor=${vendor.email}">${vendor.name}</a>
		</div>
	</div>
	</nav>
	<!--        End of Navbar part-->

</body>
</html>