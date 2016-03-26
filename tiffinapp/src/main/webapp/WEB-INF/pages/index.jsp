<%@page import="com.rns.tiffeat.web.bo.domain.MealType"%>
<%@page import="com.rns.tiffeat.web.bo.domain.MealFormat"%>
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
<title> Order tiffin online, Order meal online, Order homemade food online, Order food online, Order online Pune,online food delevery,
tiffins for online,online food service,tiffin service in pune - tiffeat.com </title>
<meta name="description" content=" The tiffeat.com is Searching Destination for delevery of  Healthy Home made food in your pune .
order your food tiffins requirment and we will deliver the same at your door step we are provide Monthly Tiffin Service in Pune from 
tiffeat.com"/>
<meta name="robots" content="noodp"/>
<meta name="keywords" content="online tiffin provide in pune,mess tiffins,home made online tiffins,tiffin providers in Pune,tiffin 
services in pune ,lunch box providers in pune ,online lunch box provider,tiffin delevery in pune,lunch delevery in pune,healthy home 
made tiffins,healthy home made food,healthy home made lunch, office food service,online,food,tiffins,lunch,khana,online khana,online 
home made khana,meal tiffin service,meal tiffin delevery,meal food delevery,track your meal,meal for group,order food for group,online 
lunch for group,get food online,get lunch online,get tiifins online,get khana online" />

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<link
	href='https://fonts.googleapis.com/css?family=Roboto:400,300,700,500'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Roboto+Slab' rel='stylesheet' type='text/css'>
<link href="<c:url value = "${resources}/css/home.css"/>"
	rel="stylesheet">
<link href="<c:url value = "${resources}/css/faq.css"/>" rel="stylesheet">
<link href="<c:url value = "${resources}/css/star_rating_static.css"/>" rel="stylesheet">
	<link href="<c:url value = "${resources}/js/cycle.js"/>"
	rel="stylesheet">
	<link href="<c:url value = "${resources}/js/json_parse.js"/>"
	rel="stylesheet">
	<link href="<c:url value = "${resources}/js/json_parse_state.js"/>"
	rel="stylesheet">
<link href="<c:url value = "${resources}/js/json2.js"/>"
	rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="<c:url value = "${resources}/js/getMenu.js"/>"></script>


	
<script type="text/javascript">
$(document).ready(function(){
	selected();
	$("#resultText").hide();
});
	function selected() {
		if($("#mealFormat").val() == "SCHEDULED") {
			$("#orderDay").hide();
			$("#bothType").show();
		}
		if($("#mealFormat").val() == "QUICK") {
			$("#orderDay").show();
			$("#bothType").hide();
			if($("#mealType").val() == "BOTH") {
				$("#mealType").val("LUNCH")
			}
		}
	}
	
	function scrollDown() {
	    $('html, body').animate({
	        scrollTop: $("#vendorsList").offset().top
	    }, 1000);
	}
	
	function showError() {
	   $("#loader").modal('hide');
  	   $("#resultText").show();
       $("#resultText").html("No tiffins currently available in your area for " + $("#orderDay").val() + "'s " + $("#mealType").val());
	}
	
	function starRating(rating) {
		if(rating == null) {
			return "<strong>Not Rated</strong>";
		}
		
		string = "<div class='rating_bar'><div  class='rating' style='width:" + rating*20 + "%;'></div></div>";
		return string;
	}
	
	function getMeals() {
		var order = {date:"",mealType:$("#mealType").val(),mealFormat:$("#mealFormat").val(),location:{address:$("#areas").val()}};
		var json = JSON.stringify(order);
		$("#loader").modal('show');
		$.ajax({
       	type : "POST",
           url : 'getMeals',
           dataType: 'json',
           data: "order="+ json + "&orderDate=" + $("#orderDay").val(),
           success : function(meals) {
               //$('#result').html(data);
               scrollDown();
               if(meals == null || meals.length == 0) {
            	   showError();
            	   $("#rows").html("");
                   return;
               }
               var appendString = "";
               var i;
               for (i = 0; i < meals.length; i++) {
            	   var desc;
                   if(order.mealFormat == "QUICK") {
                	  desc = "";
                   }
                   else {
                	  desc = "Starts from :" + meals[i].startsFromDay;
                   }
            	   appendString = appendString + "<div class=\"col-md-4\"><div class=\"menu_card\">" +
              		"<img src=\"getMealImage.htm?mealId=" +meals[i].id + "\" class=\"menu_card_image img-responsive\">" +
              		"<h4 class=\"menu_meal_title\">" +meals[i].title +"</h4>" +
					"<h4 class=\"menu_vendor_name\">" +meals[i].vendor.name + "</h4>" +
					"<h4 class=\"menu_price\">" + "&#8377;" + meals[i].price + "</h4>" +
					"<p>" + desc + "</p>" +
					"<p>" + starRating(meals[i].rating) + "</p>" +
					"<button type=\"button\" class=\"btn order_button\" onclick=\"getMenu(" + meals[i].id + ",'" + meals[i].title + "','"+ $("#mealType").val() +"','" + $("#orderDay").val() +"')\" >MENU</button>" +
					"  <button type=\"button\" class=\"btn order_button\" onclick=\"chooseMeal(" + meals[i].id +")\" >ORDER</button>" +
					"</div></div>";
				}
               $("#rows").html(appendString);
               $("#loader").modal('hide');
               $("#resultText").show();
               $("#resultText").html("Tiffins in Your Area ");
           },
           error: function(e){
           	alert("Error: " + e);
           	showError();
       	}
       }); 
   }
	
   function chooseMeal(mealId) {
	  $.ajax({
       	type : "POST",
           url : 'setMeal',
           data: "mealId=" + mealId,
           success : function(response) {
        	   window.location.href = "checkRegistration.htm";
           },
           error: function(e){
           		alert("Error: " + e);
       		}
       }); 
   } 
   
</script>

</head>

<body>
<!-- Google Tag Manager -->
<noscript><iframe src="//www.googletagmanager.com/ns.html?id=GTM-KBKF2N"
height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
'//www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
})(window,document,'script','dataLayer','GTM-KBKF2N');</script>
<!-- End Google Tag Manager -->
	<%@include file="header.jsp"%>

	<!--        Banner part-->
	<div class="container-fluid tiffin_banner">
	<div class="container">
		<h3 class="banner_heading">
			Order supremely healthy homemade meals online!
		</h3>
		<div class="container googlePlayBlock">
			<a href="https://play.google.com/store/apps/details?id=com.rns.tiffeat.mobile&hl=en" target="_blank">
				<img src="<c:url value = "${resources}/img/google-play-badge.png"/>" >
			</a>
		</div>
		
		<p class="banner_timing_text">Order your Lunch before 12 pm</p>
		<p class="banner_timing_text">And Dinner before 7 pm</p>
		<h4 class="sub_div_heading">We currently serve in Pune</h4>
		<div class="container banner_sub_div" id="top">
			
			<div class="row">
				<form action="<%=Constants.GET_NEARBY_VENDORS_URL_POST%>" id="searchByArea"
					onsubmit="onSearchByVendors()" method="post">
					<div class="location_div">
					<p>Save 20% per tiffin by starting daily tiffin service!</p>
						<p>I want to  
						<select name="mealFormat" id="mealFormat" class="option_dropdown" onchange="selected()" id="format">
							<option value="<%=MealFormat.QUICK%>">Try a meal</option>
							<option value="<%=MealFormat.SCHEDULED%>">Start daily tiffin service</option>
						</select>
						for  <select name="orderDate" class="option_dropdown" id = "orderDay">
							<option value="today">Today's</option>
							<option value="tomorrow">Tomorrow's</option>
						</select>	
						<select name="mealType" class="option_dropdown" id="mealType">
							<option value="<%=MealType.LUNCH%>">Lunch</option>
							<option value="<%=MealType.DINNER%>">Dinner</option>
							<option value="<%=MealType.BOTH%>" id="bothType">Both</option>
						</select>
						</p>
						<p>I live in <input type="text" name="location.address" id="areas"
							placeholder="Enter Your Area" value="${location}"
							class="option_dropdown" />
						</p>
						<!--  --> 
						<button type="button" onclick="getMeals()" class="btn loc_button" title="I want healthy food!">Find mealsss</button>
					</div>
				</form>
			</div>
			<%--  </c:if> --%>
		</div>
		</div>
	</div>
	<!--        End of Banner part-->


	<!--        Start of menu list div

	<div class="container" id="vendorsList">
		<h4 class="menu_card_heading" id = "resultText">Tiffins in Your Area </h4>
		<div class="row" id="rows">
		</div>
	</div>
	
	<div class="container">
		<img class="inShortContentImages"
			src="<c:url value = "${resources}/img/hourglass.svg"/>">
		<p class="inShortContent">Order a meal in seconds</p>

		<img class="inShortContentImages"
			src="<c:url value = "${resources}/img/shipping.svg"/>">
		<p class="inShortContent">Get it delivered wherever you want</p>

		<img class="inShortContentImages"
			src="<c:url value = "${resources}/img/man_eating.svg"/>">
		<p class="inShortContent">Stuff your tummy with our delicious
			healthy meal</p>

		<img class="inShortContentImages"
			src="<c:url value = "${resources}/img/jumping_man.svg"/>">
		<p class="inShortContent">Enjoy your free time!</p>-->

<div class="container">
		<h1 class="FAQTitle">FAQs</h1>

		<div class="FAQCard">

			<h3 class="faq_question">What is TiffEat?</h3>
			<p class="faq_answer">TiffEat is a tiffin delivery service that
				brings you a meal from your chosen kitchen.</p>

			<h3 class="faq_question">When do I get my meal?</h3>
			<p class="faq_answer">We deliver your tiffin(s) for LUNCH between
				12pm - 2pm and for DINNER between 8pm - 10pm.</p>

			<h3 class="faq_question">Why do I need TiffEat?</h3>
			<p class="faq_answer">Well, that's a subjective question. If you
				prefer affordable home cooked meals that remind you of your mother's
				cooking then we are glad to serve you. You eat at home usually, you
				eat in restaurants occasionally. Right?</p>

			<h3 class="faq_question">I can't find my area in your list.</h3>
			<p class="faq_answer">
				In that case, we are not yet available in your area. But be assured
				that we are tucking our shirts in, folding our cuffs and tying back
				our hair to come serve you in your area. If you want to suggest us
				your area, please shoot us a mail at <a
					href="mailto:contact@tiffeat.com" class="universal_link">contact@tiffeat.com</a>
			</p>

			<h3 class="faq_question">What is your refund policy?</h3>
			<p class="faq_answer">In any case where you were transacting on
				TiffEat using a credit/debit/net banking/any wallets and the
				transaction failed but you were debited, you do not need to transact
				again. We will either follow the money and successfully place your
				order or if you want refund, we will initiate it within 7 business
				days and return it to you in your bank. Note that the bank may take
				extra time to process from its end.</p>

			<h3 class="faq_question">What are all these kitchens/vendors in
				my area?</h3>
			<p class="faq_answer">They are kitchens run by hard working
				people that prefer to cook food as you get it at home. These
				kitchens are selected by us based on their quality of food. Thus, by
				TiffEating, you get home cooked meal and the local kitchens are
				supported. A win-win.</p>

			<h3 class="faq_question">What if I did not like your meal?</h3>
			<p class="faq_answer" id="final_answer">
				We are sad to hear that. We are sorry to displease your taste buds
				and your healthy muscles. Please contact us at <a
					href="mailto:contact@tiffeat.com" class="universal_link">contact@tiffeat.com</a>
				and help us so that this wont happen again.
			</p>
		</div>
		<p class="weAreFlattered">Thank you for checking us out. We are
			flattered!</p>
	</div>
	
	<div class="footer">
		<ul >
			<li><a href="<%=Constants.TERMS_URL_GET%>">Privacy Policy</a></li>
			<li><a href="<%=Constants.ABOUT_US_URL_GET%>">About Us</a></li>
		</ul>
	</div>
	<div class="modal fade" id="loader" tabindex="-1" role="dialog" aria-labelledby="myLabel">
          <div class="modal-dialog" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myLabel">Getting nearby tiffins ..</h4>
              </div>
              <div class="modal-body">
              </div>
             </div>
          </div>
    </div>
          
	<%@include file= "forms/customer_menu_modal.jsp" %>

	<%@include file="footer.jsp"%>
	<script
		src="http://maps.googleapis.com/maps/api/js?sensor=false&amp;libraries=places"></script>
	<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script> -->
	<script src="<c:url value="${resources}/js/jquery.geocomplete.js"/>"></script>
	<script>
		$(document).ready(function() {
			$("#areas").geocomplete({
				types : [ "geocode", "establishment" ],
			});
		});
	</script>
</body>
</html>