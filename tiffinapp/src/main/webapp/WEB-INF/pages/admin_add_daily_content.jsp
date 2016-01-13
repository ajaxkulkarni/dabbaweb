<%@page import="com.rns.tiffeat.web.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE HTML>
<html>
<head>
	<%@include file="admin_header.jsp" %>

<title>TiffEat</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- meals-select.css -->
<link href="<c:url value = "${resources}/css/meals_select.css"/>" rel="stylesheet">
</head>

<body>

	<div class="container">

		<form action="addDailyContent" method="post">
			<div class="main">
				Menu for : ${dailyContent.date}
				<div class=" Lunch">
					<%-- <c:forEach var="type" items="${mealType}">
 					 <label class="options" for="option-1">
                      <input type="radio" id="option-1" class="" name="mealType" value="${type}" autofocus/>
                      <span class="">${type}</span>
                    </label>
			      	</c:forEach> --%>
					<div class="col-md-5 type">
						<p>${dailyContent.mealType}</p>
						<input type="hidden" name="mealId" value="${mealId}" /> <input
							type="hidden" name="mealType" value="${dailyContent.mealType}" />
						<div class="row rowdiv">
							<div class="col-md-6">
								<h5>Main Course</h5>
							</div>
							<div class="col-md-5">
								<input type="text" name="mainItem" class="form-control">
							</div>
						</div>
						<div class="row rowdiv">
							<div class="col-md-6">
								<h5>Chapati/roti/paratha</h5>
							</div>
							<div class="col-md-5">
								<input type="text" name="subItem1" class="form-control">
							</div>
						</div>
						<div class="row rowdiv">
							<div class="col-md-6">
								<h5>Rice/Dal</h5>
							</div>
							<div class="col-md-5">
								<input type="text" name="subItem2" class="form-control">
							</div>
						</div>
						<div class="row rowdiv">
							<div class="col-md-6">
								<h5>Salad</h5>
							</div>
							<div class="col-md-5">
								<input type="text" name="subItem3" class="form-control">
							</div>
						</div>
						<div class="row rowdiv">
							<div class="col-md-6">
								<h5>Extra</h5>
							</div>
							<div class="col-md-5">
								<input type="text" name="subItem4" class="form-control">
							</div>
						</div>
						${result}
						<div class="row rowdiv">
							<div class="col-md-5">
								<input type="submit" value="Add" class="submit_button">
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>