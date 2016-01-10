<%@page import="com.rns.tiffeat.web.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title></title>
	<!-- animate.css admin_edit_meal.css jquery.min.js -->
	<link href="<c:url value = "${resources}/css/animate.css"/>" rel="stylesheet">  
	<link href="<c:url value = "${resources}/css/admin_edit_meal.css"/>" rel="stylesheet">
</head>
<body>
	<%@include file="admin_header.jsp" %>
	<br />
	<br />

	<form action="editMeal" method="post" enctype="multipart/form-data">
		<div class="container">
			<div class="main">
				<div class="row sub">

					<h4>Add New Meal</h4>
				</div>
				<div class="row sub">
					<div class="col-md-6">
						<p>Meal title:</p>
					</div>
					<div class="col-md-6">
						<input type="text" name="title" class="form-control"
							value="${meal.title}" required>
					</div>
				</div>
				<input type="hidden" name="id" value="${meal.id}" />
				<div class="row sub">
					<div class="col-md-6">
						<p>Price:</p>
					</div>
					<div class="col-md-6">
						<input type="text" name="price" class="form-control"
							value="${meal.price}" required>
					</div>
				</div>
				<div class="row sub">
					<div class="col-md-6">
						<p>Description:</p>
					</div>
					<div class="col-md-6">
						<textarea class="form-control" name="description" cols="10"
							rows="5" maxlength="200" value="${meal.description}" required>${meal.description}</textarea>
					</div>
				</div>
				<div class="row sub">
					<div class="col-md-6">
						<p>Timings:</p>
					</div>
					<div class="col-md-6">
						<select name="mealTime" class="form-control option_dropdown">
							<option value="">Select what time is meal available</option>
							<c:forEach items="${mealType}" var="time">
								<option value="${time}">${time}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="row sub">
					<div class="col-md-6">
						<p>Image:</p>
					</div>
					<div class="col-md-6">
						<input type="file" name="imageFile" />
					</div>
				</div>
				${result}
				<div class="row sub">
					<input type="submit" name="" class="submit_button"
						value="Edit Meal">
				</div>
			</div>
		</div>
	</form>

	<script src="<c:url value="/resources/js/wow.min.js"/>"></script>
	<script src="<c:url value="/resources/js/custom.js"/>"></script>
</body>
</html>