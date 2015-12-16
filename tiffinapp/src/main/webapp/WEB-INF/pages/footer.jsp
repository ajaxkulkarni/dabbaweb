<%@page import="com.rns.tiffeat.web.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Footer</title>

</head>
<body>
          <!--  Start of Footer-->
    	<!-- <div class="container-fluid footer">
        <p>Designed by <a href="http://resoneuronance.com/">Resoneuronance</a></p>
   		</div> -->
    <!--  End Of Footer-->

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    
    <script src="//cdnjs.cloudflare.com/ajax/libs/iScroll/5.1.1/iscroll-min.js"></script>
    <script src="//cdn.rawgit.com/ungki/bootstrap.dropdown/3.3.1/dropdown.min.js"></script>
    <script src="js/star-rating.js" type="text/javascript"></script>
        <!-- drawer js -->
    <script src="<c:url value = "${resources}/js/star-rating.js"/>" type="text/javascript"></script>
    <script src="<c:url value="${resources}/js/wow.min.js"/>"></script>     
    <script src="<c:url value="${resources}/js/custom.js"/>"></script>   
    <script src="<c:url value="${resources}/js/jquery.drawer.min.js"/>"></script>
    <script>
        $(document).ready(function() {
  $(".drawer").drawer();
});
    </script>  
</body>
</html>