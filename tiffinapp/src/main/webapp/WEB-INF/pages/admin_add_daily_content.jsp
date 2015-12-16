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

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header page-scroll">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
				<div class="col-lg-12">
                <a class="navbar-brand" href="#page-top">tiffEat</a>
				
				</div>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            
        </div>
        <!-- /.container-fluid -->
    </nav>

<br />
<br />
<br />
<br />
<div class="tiffin_name>">
<h2>Mahalaxmi tiffin service</h2>
</div>

<div class="wrapper1">
<div class="container back">
 <a href="admin.htm" class="btn btn-default back_btn">Back</a>
 </div>
</div>

<br />
<div class="container">
	<form action="addDailyContent" method="post">         
        <div class="main">
        		Menu for : ${dailyContent.date}
                <div class="row Lunch">
                    <%-- <c:forEach var="type" items="${mealType}">
 					 <label class="options" for="option-1">
                      <input type="radio" id="option-1" class="" name="mealType" value="${type}" autofocus/>
                      <span class="">${type}</span>
                    </label>
			      	</c:forEach> --%>
                    <div class="col-md-5 type">
                         <p>${dailyContent.mealType}</p>
                         	<input type="hidden" name="mealId" value="${mealId}"/>
                         	<input type="hidden" name="mealType" value="${dailyContent.mealType}"/>
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