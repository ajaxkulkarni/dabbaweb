<%@page import="com.rns.tiffeat.web.bo.domain.MealFormat"%>
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
</head>
<body>

<%@include file="header.jsp" %>
<%-- <div class="container">
          You can choose from two options.
        <div class="main">
                <div class="row">
                    <div class="col-md-5 quick">
                    	<a href="checkRegistration.htm?format=<%=MealFormat.QUICK %>">
                        	<img alt="" src="<c:url value = "${resources}/img/quick.svg"/>" class = "meal_format">
                         </a>
                         <p>One time Order</p>
                         	One time order is the on demand delivery of the tiffin.
                         	Test the waters first, and then choose the vendor you like for daily dabba.
                    </div> 
                    <div class="col-md-5 quick">
                         <a href="checkRegistration.htm?format=<%=MealFormat.SCHEDULED %>">
                          	<img alt="" src="<c:url value = "${resources}/img/scheduled.svg"/>" class = "meal_format">
                         </a>
                         <p>Schedule a tiffin</p>
                         This way, you choose to receive this tiffin daily, for lunch or for dinner, or both.
                    </div> 
                </div>
        </div>
</div> --%>
<!--  <div class="container">
    <h4 class="meal_format_heading">You can choose from two options.</h4>
    <div class="meal_format_card">
        <div class="row">
            <div class="col-md-6">
                <img src="<c:url value = "${resources}/img/quick.svg"/>" alt="no-image" class="img-responsive meal_format_img" />
                <a href="checkRegistration.htm?format=<%=MealFormat.QUICK%>" class="order_url">
                <p>For One time order Click here
                </p>
                </a>
                One time order is the on demand delivery of the tiffin.
                Test the waters first, and then choose the vendor you like for daily dabba.
            </div>
            <div class="col-md-6">
                <img src="<c:url value = "${resources}/img/scheduled.svg"/>" alt="no-image" class="img-responsive meal_format_img" />
                  <a href="checkRegistration.htm?format=<%=MealFormat.SCHEDULED%>" class="order_url">
                  <p>To Schedule a Tiffin Click here
                	</p>
                </a>
                 This way, you choose to receive this tiffin daily, for lunch or for dinner, or both.
            </div>
        </div>
    </div>
</div>-->
 <h4 class="meal_format_heading">You can choose from two options.</h4>
 <div class="row">
                <div class="col-md-6">
	<div class="demo-card">
    <div class="card">
    <div class="card-block">
        <a href="checkRegistration.htm?format=<%=MealFormat.QUICK%>" class="order_url">
             <img src="<c:url value = "${resources}/img/quick.svg"/>" alt="no-image" class="img-responsive meal_format_img" />
            
			 
			 <p>For One time order Click here
                </p>
                </a>
                
        <div class="desc">
			 One time order is the on demand delivery of the tiffin.
                Test the waters first, and then choose the vendor you like for daily dabba.
            </div>
			</div>
		<br />
 </div>
</div>
</div>

<div class="row">
                <div class="col-md-6">
	<div class="demo-card1">
    <div class="card">
    <div class="card-block">
    <a href="checkRegistration.htm?format=<%=MealFormat.SCHEDULED%>" class="order_url">
             <img src="<c:url value = "${resources}/img/scheduled.svg"/>" alt="no-image" class="img-responsive meal_format_img" />
                  
			 
			 <p>To Schedule a Tiffin Click here
                </p>
                </a>
			<div class="desc">
			This way, you choose to receive this tiffin daily, for lunch or for dinner, <br />or both.
            </div>
			</div>
		<br />
 </div>
</div>
</div>



<%@include file="footer.jsp" %>
</body>   
</html>