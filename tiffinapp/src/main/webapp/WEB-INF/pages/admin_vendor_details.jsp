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
 	<link href="<c:url value="${resources}/css/bootstrap.min.css"/>" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="<c:url value="${resources}/css/freelancer.css"/>" rel="stylesheet">
    
    
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic" rel="stylesheet" type="text/css">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Vendor Details</title>
	<script type="text/javascript">
    
    function showLunch() {
    	document.getElementById("dinnerMeals").style.display = 'none';           // Hide
    	document.getElementById("lunchMeals").style.display = 'block';
    }
    
    function showDinner() {
    	document.getElementById("lunchMeals").style.display = 'none';           // Hide
    	document.getElementById("dinnerMeals").style.display = 'block';
    }
    
   </script>
	
</head>
<body>


<!--    Start of Navbar -->
<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="">
                <h4 class='tiff'>Tiff<span class="eat">Eat</span></h4>
            </a>
            <div class="username">
			<a href="editVendor.htm?vendor=${vendor.email}">${vendor.name}</a>
			<!--  </div>
			</div>-->
			
    </div>
        </div>
    </div>
    </div>
</nav>
<!--        End of Navbar part-->

		
		<br />
		<br />
	
		
	<div class="row">
	<div class="col-lg-12">
	<div class="wrapper">

<div class="container add_meal">
 <a href="addNewMeal.htm" class="btn btn-default add_meal_btn">Add Meal</a>
 <button value="LUNCH" class="btn btn-default add_meal_btn" onclick="showLunch()">LUNCH MEALS</button>
 <button value="DINNER" class="btn btn-default add_meal_btn" onclick="showDinner()">DINNER MEALS</button>
 </div>
</div>
 </div>

<div class="wrapper1">
 <div class="container back">
 <a href="admin.htm" class="btn btn-default back_btn">Back</a>
 </div>
	
</div>
</div>

<br />
<br />
<br />	
	 
	 <div class="row" id = "lunchMeals">
	 	LUNCH MEALS
	 	<c:forEach items="${vendor.meals}" var="meal">
	 	<c:if test="${meal.mealTime == 'BOTH' || meal.mealTime == 'LUNCH' }">
        <div class="col-md-4">
		<div class="demo-card">
    	<div class="card">
        <img class="card-img-top" src="getMealImage.htm?mealId=${meal.id}">
        <div class="card-block">
            <h4 class="card-title">
			${meal.title}
			<!--  <a href="welcome.html">
			<img class="img-top" src="edit45.png">--></a></h4>
			
			<div class="price">
			<h4>${meal.price}</h4>
			</div>
            <strong>LUNCH</strong>
            <p class="card-text">${meal.description}</p>
            <div class="status">
			<strong>Status:${meal.lunchStatus}</strong>
			<p class="card-text">Menu updated for:${meal.lunchMenu.date}</p>
			 </div>
		</div>
		<br />
		<c:choose>
				<c:when test="${meal.lunchMenu == null}">
					<div class="card-block">
            			<a href="addDailyContent.htm?mealId=${meal.id}&mealType=LUNCH" class="btn btn-primary">Add Menu</a>
        			</div>
				</c:when>
				<c:when test="${meal.lunchStatus == 'PREPARE'}">
					<div class="card-block">
            			<a href="updateDailyContent.htm?contentId=${meal.lunchMenu.id}&mealType='LUNCH'" class="btn btn-primary">Edit Content</a>
						<a href="startCooking.htm?mealId=${meal.id}&mealType=LUNCH" class="btn btn-primary">Start Cooking</a>
        			</div>
				</c:when>
				<c:when test="${meal.lunchStatus == 'COOKING'}">
					<div class="card-block">
            			<a href="startDispatch.htm?mealId=${meal.id}&mealType=LUNCH" class="btn btn-primary">Dispatch</a>
        			</div>
				</c:when>
				<%-- <c:when test="${meal.status == 'DISPATCH' && meal.lunchMenu.date}">
					<div class="card-block">
            			<a href="startDispatch.htm?mealId=${meal.id}" class="btn btn-primary">Dispatch</a>
        			</div>
				</c:when> --%>
				<%-- <c:otherwise>
					<div class="card-block">
            			<a href="addDailyContent.htm?mealId=${meal.id}" class="btn btn-primary">Add Menu</a>
        			</div>
				</c:otherwise> --%>
				</c:choose>
				<%-- <div class="card-block">
            			<a href="addDailyContent.htm?mealId=${meal.id}&mealType='LUNCH'" class="btn btn-primary">Add Menu</a>
            			<a href="updateDailyContent.htm?contentId=${meal.lunchMenu.id}" class="btn btn-primary">Edit Menu</a>
        		</div> --%>
    	</div>
	</div>
	<div class="row">
	<div class="view_orders">
	<a href="getMealOrders.htm?mealId=${meal.id}&type=LUNCH">View Orders</a>
	</div>
	
<div class="edit_meal">
	<a href="editMeal.htm?mealId=${meal.id}">Edit Meal</a>
	
	</div>
	</div>
	</div>
	</c:if>
	</c:forEach>
	</div>
	
	<div class="row" id = "dinnerMeals" style="display:none">
		DINNER MEALS
	 	<c:forEach items="${vendor.meals}" var="meal">
	 	<c:if test="${meal.mealTime == 'BOTH' || meal.mealTime == 'DINNER' }">
        <div class="col-md-4">
		<div class="demo-card">
    	<div class="card">
        <img class="card-img-top" src="getMealImage.htm?mealId=${meal.id}">
        <div class="card-block">
            <h4 class="card-title">
			${meal.title}
			<!--  <a href="welcome.html">
			<img class="img-top" src="edit45.png">--></a></h4>
			
			<div class="price">
			<h4>${meal.price}</h4>
			</div>
            <strong>DINNER</strong>
            <p class="card-text">${meal.description}</p>
            <div class="status">
			<strong>Status:${meal.dinnerStatus}</strong>
			<p class="card-text">Menu updated for:${meal.dinnerMenu.date}</p>
			 </div>
		</div>
		<br />
		<c:choose>
				<c:when test="${meal.dinnerMenu == null}">
					<div class="card-block">
            			<a href="addDailyContent.htm?mealId=${meal.id}&mealType=DINNER" class="btn btn-primary">Add Menu</a>
        			</div>
				</c:when>
				<c:when test="${meal.dinnerStatus == 'PREPARE'}">
					<div class="card-block">
            			<a href="updateDailyContent.htm?contentId=${meal.dinnerMenu.id}&mealType=DINNER" class="btn btn-primary">Edit Content</a>
						<a href="startCooking.htm?mealId=${meal.id}&mealType=DINNER" class="btn btn-primary">Start Cooking</a>
        			</div>
				</c:when>
				<c:when test="${meal.dinnerStatus == 'COOKING'}">
					<div class="card-block">
            			<a href="startDispatch.htm?mealId=${meal.id}&mealType=DINNER" class="btn btn-primary">Dispatch</a>
        			</div>
				</c:when>
				<%-- <c:otherwise>
					<div class="card-block">
            			<a href="addDailyContent.htm?mealId=${meal.id}" class="btn btn-primary">Add Menu</a>
        			</div>
				</c:otherwise> --%>
				</c:choose>
				<%-- <div class="card-block">
            			<a href="addDailyContent.htm?mealId=${meal.id}&mealType='DINNER'" class="btn btn-primary">Add Menu</a>
            			<a href="updateDailyContent.htm?contentId=${meal.dinnerMenu.id}" class="btn btn-primary">Edit Menu</a>
        		</div> --%>
    	</div>
	</div>
	<div class="row">
	<div class="view_orders">
	<a href="getMealOrders.htm?mealId=${meal.id}&type=DINNER">View Orders</a>
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
<!-- <footer>
	<div class="footer-below">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                      Copyright &copy; 2015 tiffEat.com
                    </div>
                </div>
            </div>
        </div>
    </footer> -->	
</body>
</html>