<%@page import="com.rns.tiffeat.web.bo.domain.MealType"%>
<%@page import="com.rns.tiffeat.web.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Vendor Details</title>
<script type="text/javascript">
	function showLunch() {
		document.getElementById("dinnerMeals").style.display = 'none'; // Hide
		document.getElementById("lunchMeals").style.display = 'block';
	}

	function showDinner() {
		document.getElementById("lunchMeals").style.display = 'none'; // Hide
		document.getElementById("dinnerMeals").style.display = 'block';
	}
</script>

	<!-- freelancer.css -->
<link href="<c:url value="${resources}/css/freelancer.css"/>" rel="stylesheet">
</head>
<body>

	<%@include file="admin_header.jsp" %>
	
	<div class="container">
		<div class="wrapper1">
			<div class="back">
				<a href="admin.htm" class="btn btn-default back_btn">Back</a>
			</div>

		</div>
		<div class="wrapper">
			<div class="add_meal">
				<a href="addNewMeal.htm?vendorEmail=${vendor.email}"
					class="btn btn-default add_meal_btn">Add Meal</a>
				<button value="LUNCH" class="btn btn-default add_meal_btn"
					onclick="showLunch()">LUNCH MEALS</button>
				<button value="DINNER" class="btn btn-default add_meal_btn"
					onclick="showDinner()">DINNER MEALS</button>
			</div>
		</div>
	</div>
	<div class="container" id="lunchMeals">
		<h4>LUNCH MEALS</h4>
		<c:forEach items="${vendor.meals}" var="meal">
			<c:if test="${meal.mealTime == 'BOTH' || meal.mealTime == 'LUNCH' }">
				<div class="col-md-4">
					<div class="meal-card">
						<img class="card-img-top" src="getMealImage.htm?mealId=${meal.id}">
						<div class="card-block">
							<h4 class="card-title">
								${meal.title}
								<!--  <a href="welcome.html">
			<img class="img-top" src="edit45.png">-->
								</a>
							</h4>

							<div class="price">${meal.price}</div>
							<strong>LUNCH</strong>
							<p class="card-text">${meal.description}</p>
							<div class="status">
								<strong>Status : ${meal.lunchStatus}</strong>
								<p class="card-text">Menu updated for:${meal.lunchMenu.date}</p>
							</div>
						</div>
						<br />
						<c:choose>
							<c:when test="${meal.lunchMenu == null}">
								<div class="card-block">
									<a href="addDailyContent.htm?mealId=${meal.id}&mealType=LUNCH"
										class="btn btn-primary">Add Menu</a>
								</div>
							</c:when>
							<c:when test="${meal.lunchStatus == 'PREPARE'}">
								<div class="card-block">
									<a
										href="updateDailyContent.htm?contentId=${meal.lunchMenu.id}&mealType='LUNCH'"
										class="btn btn-primary">Edit Content</a> <a
										href="startCooking.htm?mealId=${meal.id}&mealType=LUNCH"
										class="btn btn-primary">Start Cooking</a>
								</div>
							</c:when>
							<c:when test="${meal.lunchStatus == 'COOKING'}">
								<div class="card-block">
									<a href="startDispatch.htm?mealId=${meal.id}&mealType=LUNCH"
										class="btn btn-primary">Dispatch</a>
								</div>
							</c:when>
						</c:choose>
					</div>
					<div class="row">
						<div class="view_orders">
							<a href="getMealOrders.htm?mealId=${meal.id}&type=LUNCH">View
								Orders</a>
						</div>

						<div class="edit_meal">
							<a href="editMeal.htm?mealId=${meal.id}">Edit Meal</a>

						</div>
					</div>
				</div>
			</c:if>
		</c:forEach>
	</div>

	<div class="container" id="dinnerMeals" style="display: none">
		<h4>DINNER MEALS</h4>
		<c:forEach items="${vendor.meals}" var="meal">
			<c:if test="${meal.mealTime == 'BOTH' || meal.mealTime == 'DINNER' }">
				<div class="col-md-4">
					<div class="meal-card">
						<img class="card-img-top" src="getMealImage.htm?mealId=${meal.id}">
						<div class="card-block">
							<h4 class="card-title">
								${meal.title}
								</a>
							</h4>

							<div class="price">
								<h4>${meal.price}</h4>
							</div>
							<strong>DINNER</strong>
							<p class="card-text">${meal.description}</p>
							<div class="status">
								<strong>Status : ${meal.dinnerStatus}</strong>
								<p class="card-text">Menu updated
									for:${meal.dinnerMenu.date}</p>
							</div>
						</div>
						<br />
						<c:choose>
							<c:when test="${meal.dinnerMenu == null}">
								<div class="card-block">
									<a href="addDailyContent.htm?mealId=${meal.id}&mealType=DINNER"
										class="btn btn-primary">Add Menu</a>
								</div>
							</c:when>
							<c:when test="${meal.dinnerStatus == 'PREPARE'}">
								<div class="card-block">
									<a
										href="updateDailyContent.htm?contentId=${meal.dinnerMenu.id}&mealType=DINNER"
										class="btn btn-primary">Edit Content</a> <a
										href="startCooking.htm?mealId=${meal.id}&mealType=DINNER"
										class="btn btn-primary">Start Cooking</a>
								</div>
							</c:when>
							<c:when test="${meal.dinnerStatus == 'COOKING'}">
								<div class="card-block">
									<a href="startDispatch.htm?mealId=${meal.id}&mealType=DINNER"
										class="btn btn-primary">Dispatch</a>
								</div>
							</c:when>
						</c:choose>
					</div>
					<div class="">
						<div class="view_orders">
							<a href="getMealOrders.htm?mealId=${meal.id}&type=DINNER">View
								Orders</a>
						</div>

						<div class="edit_meal">
							<a href="editMeal.htm?mealId=${meal.id}">Edit Meal</a>

						</div>
					</div>
				</div>
			</c:if>
		</c:forEach>
	</div>

	<br />
	<br />
</body>
</html>