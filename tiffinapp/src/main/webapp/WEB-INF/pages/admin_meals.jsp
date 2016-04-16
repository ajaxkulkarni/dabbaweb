<%@page import="com.rns.tiffeat.web.bo.domain.MealType"%>
<%@page import="com.rns.tiffeat.web.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="admin_header.jsp" %>
<link href="<c:url value="${resources}/css/admin_home.css"/>" rel="stylesheet">
<link href="<c:url value="${resources}/css/admin_view_orders.css"/>" rel="stylesheet">

<script>
	$(document).ready(function() {
		$('#order_table1').DataTable({
			responsive : true
		});
	});
</script>
<body>
	

	<!-- <div class="container view_order">
		<a href="allOrders.htm" class="btn btn-default view_order_btn">View
			All Orders</a>
	</div> -->
	<div class="container view_order">
	<a href="admin.htm?vendorEmail=${vendor.email}"
			class="btn btn-default back_btn1">Back</a>
	</div>
	<br/>
	<!--    meals List Div-->

	<div class="container">
		<div id="order_list_div">
			<div class="table-responsive">
				<table class="table table-bordered table-hover" id="order_table1">

					<thead>
						<tr>
							<td>ID</td>
							<td>Title</td>
							<td>Vendor</td>
							<td>Price</td>
							<td>Lunch status</td>
							<td>Dinner status</td>
							<td>View</td>
						</tr>
					</thead>

					<c:forEach items="${meals}" var="meal">
						<c:if test="${meal.vendor.status == 'OPEN' }">
						<tr>
								<td>${meal.id}</td>
								<td>${meal.title}</td>
								<td>${meal.vendor.name}</td>
								<td>${meal.price}</td>
								<td>
								<c:if
									test="${meal.mealTime == 'BOTH' || meal.mealTime == 'LUNCH' }">
									<div class="card-block">
										<div class="status">
											<strong>Status : ${meal.lunchStatus}</strong>
											<p class="card-text">Menu updated for:<fmt:formatDate pattern="yyyy-MM-dd" value="${meal.lunchMenu.date}"></fmt:formatDate></p>
										</div>
									</div>
									<br />
									<c:choose>
										<c:when test="${meal.lunchMenu == null}">
											<div class="card-block">
												<a href="addDailyContent.htm?mealId=${meal.id}&mealType=LUNCH" class="btn btn-primary" style="background-color: #4CAF50;" >Add Menu</a>
												<a href="getLastDailyContent.htm?mealId=${meal.id}&mealType=LUNCH" class="btn btn-primary" style="background-color: #4CAF50;" >Recent Menu</a>
											</div>
										</c:when>
										<c:when test="${meal.lunchStatus == 'PREPARE'}">
											<div class="card-block">
												<a
													href="updateDailyContent.htm?contentId=${meal.lunchMenu.id}&mealType='LUNCH'"
													class="btn btn-primary" style="background-color: #f44336;">Edit Content</a> <a
													href="startCooking.htm?mealId=${meal.id}&mealType=LUNCH"
													class="btn btn-primary" style="background-color: #f44336;">Start Cooking</a>
											</div>
										</c:when>
										<c:when test="${meal.lunchStatus == 'COOKING'}">
											<div class="card-block">
												<a href="startDispatch.htm?mealId=${meal.id}&mealType=LUNCH"
													class="btn btn-primary" style="background-color: #008CBA;">Dispatch</a>
											</div>
										</c:when>
									</c:choose>
								</c:if>
							</td>
								<td>
								<c:if
									test="${meal.mealTime == 'BOTH' || meal.mealTime == 'DINNER' }">
									<div class="card-block">
										<div class="status">
											<strong>Status : ${meal.dinnerStatus}</strong>
											<p class="card-text">Menu updated for:<fmt:formatDate pattern="yyyy-MM-dd" value="${meal.dinnerMenu.date}"></fmt:formatDate></p>
										</div>
									</div>
									<br />
									<c:choose>
										<c:when test="${meal.dinnerMenu == null}">
											<div class="card-block">
												<a href="addDailyContent.htm?mealId=${meal.id}&mealType=DINNER" class="btn btn-primary" style="background-color: #4CAF50;" >Add Menu</a>
												<a href="getLastDailyContent.htm?mealId=${meal.id}&mealType=DINNER" class="btn btn-primary" style="background-color: #4CAF50;" >Recent Menu</a>
											
											</div>
										</c:when>
										<c:when test="${meal.dinnerStatus == 'PREPARE'}">
											<div class="card-block">
												<a
													href="updateDailyContent.htm?contentId=${meal.dinnerMenu.id}&mealType='DINNER'"
													class="btn btn-primary" style="background-color: #f44336;">Edit Content</a> <a
													href="startCooking.htm?mealId=${meal.id}&mealType=DINNER"
													class="btn btn-primary" style="background-color: #f44336;">Start Cooking</a>
											</div>
										</c:when>
										<c:when test="${meal.dinnerStatus == 'COOKING'}">
											<div class="card-block">
												<a href="startDispatch.htm?mealId=${meal.id}&mealType=DINNER"
													class="btn btn-primary" style="background-color: #008CBA;">Dispatch</a>
											</div>
										</c:when>
									</c:choose>
								</c:if>
								</td>
								<td><a href="editMeal.htm?mealId=${meal.id}">Edit Meal</a></td>
							
						</c:if>
					</c:forEach>
					<tr>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
					<td>
							<form method="post" action="startDispatchAll">
								<input type="hidden" name="mealType" value="<%=MealType.LUNCH%>"/>
								<input type="submit" value="DISPATCH ALL"/>
							</form>
							<form method="post" action="startCookingAll">
								<input type="hidden" name="mealType" value="<%=MealType.LUNCH%>"/>
								<input type="submit" value="COOK ALL"/>
							</form>
							</td>
							<td>
							<form method="post" action="startDispatchAll">
								<input type="hidden" name="mealType" value="<%=MealType.DINNER%>"/>
								<input type="submit" value="DISPATCH ALL"/>
							</form>
							<form method="post" action="startCookingAll">
								<input type="hidden" name="mealType" value="<%=MealType.DINNER%>"/>
								<input type="submit" value="COOK ALL"/>
							</form>
							</td>
						</tr>
				</table>
			</div>
		</div>
	</div>
	<!--    End of vendor list div-->

</body>

</html>