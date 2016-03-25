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
<link href="<c:url value = "${resources}/css/FAQ.css"/>" rel="stylesheet">
<link href="<c:url value = "${resources}/css/star_rating_static.css"/>" rel="stylesheet">
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