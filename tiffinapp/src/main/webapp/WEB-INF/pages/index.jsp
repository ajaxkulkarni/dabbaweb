<%@page import="com.rns.tiffeat.web.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>TiffEat | Order tiffin online </title>


</head>

<body>
    <%@include file="header.jsp" %>
    
    <!--        Banner part-->
    <div class="container-fluid tiffin_banner">
        <h3 class="banner_heading">Order tiffins Online from our vendors<br/>
                <span class="banner_sub_heading">and have a homemade meal wherever you are!</span></h3>
        <div class="container banner_sub_div">
            <h4 class="sub_div_heading">We currently serve in Pune</h4>
            <%-- <c:if test="${customer.orderInProcess.id == 0 }"> --%>
            <div class="row">
            	<form action="getNearbyVendors" id = "searchByArea" onsubmit="onSearchByVendors()" method="post">
                <div class="col-md-4">
                    <%-- <select name="pinCode" class="form-control option_dropdown">
                        <option value="">Where Do you Want Tiffin</option>
                        <c:forEach items="${areas}" var="area">
                        	<option value="${area.key}">${area.value}</option>
                        </c:forEach> 
                    </select> --%>
                    <input type="text" name="address" id="areas" placeholder="Your Area " value="${location}" class="form-control option_dropdown" />
                </div>
                <!-- <div class="col-md-4">
                    <select name="" class="form-control option_dropdown">
                        <option value="">Show Me Every Thing</option>
                    </select>
                </div> -->
                <div class="col-md-4">
                    <button type="submit" class="btn loc_button">Find my tiffins</button>
                </div>
                </form>
            </div>
           <%--  </c:if> --%>
        </div>
    </div>
    <!--        End of Banner part-->
    
            
            <!--        Start of menu list div-->

    <div class="container menu_cards_div" id="vendorsList">
    <c:choose>
    <c:when test="${fn:length(vendors) gt 0}">
    <h4 class="menu_card_heading">Tiffins in Your Area</h4>
    	<div class="row">
        <c:forEach items="${vendors}" var="vendor">
        	<a href="getVendorMeals.htm?vendorEmail=${vendor.email}">
            	<div class="col-md-4">
                	<div class="menu_card">
                    	<img src="getVendorProfilePic.htm?vendorEmail=${vendor.email}" class="menu_card_image img-responsive">
                    	<h4 class="menu_card_title">${vendor.name}</h4>
                    	<!-- <h6 class="menu_card_sub_title">Veg, Non-Veg, Jain, Fast-Food</h6>
                    	<input id="input-2a" value="4" class="rating" min="0" max="5" step="0.5" data-size="sm" data-symbol="&#xf005;" data-glyphicon="false" data-rating-class="rating-fa"> -->
                	</div>
            	</div>
         	</a>
         </c:forEach>
        </div>
      </c:when>
      <c:otherwise>
      	<h4 class="menu_card_heading">${result}</h4>
      </c:otherwise>
      </c:choose>
      </div>
      
      <div class="container">
            
            <img class="inShortContentImages" src="<c:url value = "${resources}/img/hourglass.svg"/>">
            <p class="inShortContent">Order a tiffin in seconds</p>
            
            <img class="inShortContentImages" src="<c:url value = "${resources}/img/shipping.svg"/>">
            <p class="inShortContent">Get it delivered wherever you want</p>
            
            <img  class="inShortContentImages" src="<c:url value = "${resources}/img/man_eating.svg"/>"> 
            <p class="inShortContent">Stuff your tummy with our delicious meals</p>
            
            <img class="inShortContentImages" src="<c:url value = "${resources}/img/jumping_man.svg"/>">
            <p class="inShortContent">Enjoy your free time!</p>

            <h1 class="FAQTitle">FAQs</h1>

            <div class="FAQCard">

                <h3 class="faq_question">What is TiffEat?</h3>
                <p class="faq_answer">TiffEat is a tiffin delivery service that brings you a meal from your chosen kitchen.</p>

                <h3 class="faq_question">When do I get my meal?</h3>
                <p class="faq_answer">We deliver your tiffin(s) for lunch between 12pm - 2pm and for dinner between 8pm - 10pm.</p>

                <h3 class="faq_question">Why do I need TiffEat?</h3>
                <p class="faq_answer">Well, that's a subjective question. If you prefer affordable home cooked meals that remind you of your mother's cooking then we are glad to serve you. You eat at home usually, you eat in restaurants occasionally. Right?</p>

                <h3 class="faq_question">I can't find my area in your list.</h3>
                <p class="faq_answer">In that case, we are not yet available in your area. But be assured that we are tucking our shirts in, folding our cuffs and tying back our hair to come serve you in your area. If you want to suggest us your area, please shoot us a mail at <a href="mailto:contact@tiffeat.com" class="universal_link">contact@tiffeat.com</a></p>

                <h3 class="faq_question">What is your refund policy?</h3>
                <p class="faq_answer">In any case where you were transacting on TiffEat using a credit/debit/net banking/any wallets and the transaction failed but you were debited, you do not need to transact again. We will either follow the money and successfully place your order or if you want refund, we will initiate it within 7 business days and return it to you in your bank. Note that the bank may take extra time to process from its end.</p>

                <h3 class="faq_question">What are all these kitchens/vendors in my area?</h3>
                <p class="faq_answer">They are kitchens run by hard working people that prefer to cook food as you get it at home. These kitchens are selected by us based on their quality of food. Thus, by TiffEating, you get home cooked meal and the local kitchens are supported. A win-win.</p>

                <h3 class="faq_question">What if I did not like your meal?</h3>
                <p class="faq_answer" id="final_answer">We are sad to hear that. We are sorry to displease your taste buds and your healthy muscles. Please contact us at <a href="mailto:contact@tiffeat.com" class="universal_link">contact@tiffeat.com</a> and help us so that this wont happen again. </p>

            </div>
            <p class="weAreFlattered">Thank you for checking us out. We are flattered!</p>
        </div>

            
    <%@include file="footer.jsp" %>  
    <script src="http://maps.googleapis.com/maps/api/js?sensor=false&amp;libraries=places"></script>
    <!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script> -->
	<script src="<c:url value="${resources}/js/jquery.geocomplete.js"/>"></script>
    <script>
  $(document).ready(function() {
		$("#areas").geocomplete({
	          types: ["geocode", "establishment"],
	        });
		
 		});
  
  </script> 
    </body>
</html>