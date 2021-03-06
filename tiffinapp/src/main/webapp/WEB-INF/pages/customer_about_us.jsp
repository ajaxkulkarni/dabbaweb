<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
	<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<link
	href='https://fonts.googleapis.com/css?family=Roboto:400,300,700,500'
	rel='stylesheet' type='text/css'>
<link href="<c:url value = "${resources}/css/aboutUs.css"/>"
	rel="stylesheet">

<title>TiffEat | Who are we?</title>

</head>
<body class="drawer drawer-left">
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

	<div class="container">
		<h1 class="aboutUsTitle">About Us</h1>

		<div class="aboutUsCard">

			<div class="questionsDiv">
				<p class="questions">Are you a student? Or a working
					professional?</p>
				<p class="questions">Do you usually eat outside in hotels or
					mess?</p>
				<p class="questions">Do you find it tiring to cook your meals?</p>
				<p class="questions">Or do you find your existing meals boring?
				</p>

				<p class="infomercial">(Why is this sounding like a late-night
					infomercial?)</p>
			</div>

			<p class="forYou">Now that you have gone through the questions,
				we know TiffEat is for you.</p>

			<div class="whatAreWe">
				<h2>We are a tiffin delivery service.</h2>
				<p>Tiffin that is packed with delicious meal and love.</p>
			</div>

			<div class="detailText">
				<p>We know how many efforts are required to just think about
					what you are going to eat for your lunch or dinner. And then you
					have to go to that hotel or mess where you have to eat. TiffEat
					helps you with options of delicious meals and we eliminate your
					commute by delivering the food wherever you are.</p>
				<p id="itsAwesome">Isn't it awesome?</p>
				<p>Thus you save about an hour of time per meal by ordering from
					TiffEat. Think about what you can do with your brilliantly saved
					time! You can study, work, paint, write, play music, watch movies
					or whatever it is you like to do.</p>
			</div>
			<p id="orderNow">
				<a href="<%=Constants.INDEX_URL_GET%>">Order Now!</a>
			</p>
		</div>

	</div>

	<%@include file="footer.jsp"%>

</body>
</html>