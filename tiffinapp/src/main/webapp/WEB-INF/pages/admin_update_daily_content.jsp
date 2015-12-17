<%@page import="com.rns.tiffeat.web.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE HTML>
<html>
<head>
  <title>TiffEat</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    
  <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:300,400,500,700" type="text/css">
<!--  <link rel="stylesheet" href="css/meals_select.css">-->
  <link href="<c:url value = "${resources}/css/meals_select.css"/>" rel="stylesheet">  
</head>

<body id="page-top" class="index">
<nav class="navbar navbar-default">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand" href="">
                    <h4 class='tiff'>Tiff<span class="eat">Eat</span></h4>
                </a>
            </div>
        </div>
    </nav>

    
    <br />
    <br />

    
<div class="container">
	<form action="updateDailyContent" method="post">         
        <div class="main">
                <div class="row Lunch">
                    <%-- <c:forEach var="type" items="${mealType}">
 					 <label class="options" for="option-1">
                      <input type="radio" <c:if test="${dailyContent.mealType eq type}">checked</c:if> id="option-1" class="" name="mealType" value="${type}" autofocus/>
                      <span class="">${type}</span>
                    </label>
			      	</c:forEach> --%>
                    <div class="col-md-5 type">
                         <p>${mealType}</p>
                         	<input type="hidden" name="id" value="${dailyContent.id}"/>
                         	<%-- <input type="hidden" name="date" value="${dailyContent.date}"/> --%>
                            <div class="row rowdiv">
                                <div class="col-md-6">
                                    <h5>Main Course</h5>
                                </div>
                                <div class="col-md-5">
                                    <input type="text" name="mainItem" value="${dailyContent.mainItem}" class="form-control">
                                </div>
                            </div>
                            <div class="row rowdiv">
                                <div class="col-md-6">
                                    <h5>Chapati/roti/paratha</h5>
                                </div>
                                <div class="col-md-5">
                                    <input type="text" name="subItem1" value="${dailyContent.subItem1}" class="form-control">
                                </div>
                           </div>
                            <div class="row rowdiv">
                                <div class="col-md-6">
                                    <h5>Rice/Dal</h5>
                                </div>
                                <div class="col-md-5">
                                    <input type="text" name="subItem2" value="${dailyContent.subItem2}" class="form-control">
                                </div>
                            </div>
                            <div class="row rowdiv">
                                <div class="col-md-6">
                                    <h5>Salad</h5>
                                </div>
                                <div class="col-md-5">
                                    <input type="text" name="subItem3" value="${dailyContent.subItem3}" class="form-control">
                                </div>
                            </div>
                            <div class="row rowdiv">
                                <div class="col-md-6">
                                    <h5>Extra</h5>
                                </div>
                                <div class="col-md-5">
                                    <input type="text" name="subItem4" value="${dailyContent.subItem4}" class="form-control">
                                </div>
                            </div>
                            ${result}
                            <div class="row rowdiv">
                                <div class="col-md-5">
                                    <input type="submit" value="Add" class="form-control">
                                </div>
                            </div>
                    </div> 
                </div>    
        </div>
        </form>
</div>
</body>   
</html>