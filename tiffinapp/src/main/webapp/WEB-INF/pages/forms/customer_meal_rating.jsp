<%@page import="com.rns.tiffeat.web.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<c:if test="${order.rating == null }"> 
 Please rate this meal!
<form action="<%=Constants.SUBMIT_MEAL_RATING%>" method="post">
<fieldset class="rating">
	<input type="hidden" name="id" value="${order.id}"/>
	<input type="hidden" name="meal.id" value="${order.meal.id}"/>
	<input type="hidden" name="mealFormat" value="${order.mealFormat}"/>
    <input type="radio" id="star5" name="rating" value="5" />
    <label class = "full" for="star5" title="Awesome - 5 stars"></label>
    <input type="radio" id="star4half" name="rating" value="4.5" />
    <label class="half" for="star4half" title="Pretty good - 4.5 stars"></label>
    <input type="radio" id="star4" name="rating" value="4" />
    <label class = "full" for="star4" title="Pretty good - 4 stars"></label>
    <input type="radio" id="star3half" name="rating" value="3.5" />
    <label class="half" for="star3half" title="Meh - 3.5 stars"></label>
    <input type="radio" id="star3" name="rating" value="3" />
    <label class = "full" for="star3" title="Meh - 3 stars"></label>
    <input type="radio" id="star2half" name="rating" value="2.5" />
    <label class="half" for="star2half" title="Kinda bad - 2.5 stars"></label>
    <input type="radio" id="star2" name="rating" value="2" />
    <label class = "full" for="star2" title="Kinda bad - 2 stars"></label>
    <input type="radio" id="star1half" name="rating" value="1.5" />
    <label class="half" for="star1half" title="Meh - 1.5 stars"></label>
    <input type="radio" id="star1" name="rating" value="1" />
    <label class = "full" for="star1" title="Sucks big time - 1 star"></label>
    <input type="radio" id="starhalf" name="rating" value="half" />
    <label class="half" for="starhalf" title="Sucks big time - 0.5 stars"></label>
	<button type="submit" class="btn order_button">Rate</button>
</fieldset>
</form>
</c:if>
<c:if test="${order.rating != null  }">
    <h4> You have rated ${order.rating}.</h4>
</c:if>
</body>
</html>