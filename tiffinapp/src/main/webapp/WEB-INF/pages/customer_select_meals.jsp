<%@page import="com.rns.tiffeat.web.bo.domain.MealType"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE HTML>
<html>
<head>
  <title>TiffEat | Select Meal</title>
 <meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<link
	href='https://fonts.googleapis.com/css?family=Roboto:400,300,700,500'
	rel='stylesheet' type='text/css'>
	<script type="text/javascript">
    function onViewMenu() {
    	var radios = document.getElementsByName('mealType');
		var mealType;
		for (var i = 0, length = radios.length; i < length; i++) {
		    if (radios[i].checked) {
		        mealType = radios[i].value;
		        break;
		    }
		}
    	var mealId = $("#mealId").val();
        
        $.ajax({
        	type : "POST",
            url : 'getMenu',
            dataType: 'json',
            data: "mealId=" + mealId + "&mealType=" + mealType,
            success : function(data) {
                //$('#result').html(data);
                if(data == null) {
                	$('#menuDate').text('Menu not available yet..');
                	$('#menuMainItem').text('');
                    $('#menuSubItem1').text('');
                    $('#menuSubItem2').text('');
                    $('#menuSubItem3').text('');
                    $('#menuSubItem4').text('');
                	return;
                }
                $('#menuDate').text('Menu for :' + data.date.substring(0,12));
              	$('#menuMainItem').text(data.mainItem);
                $('#menuSubItem1').text(data.subItem1);
                $('#menuSubItem2').text(data.subItem2);
                $('#menuSubItem3').text(data.subItem3);
                $('#menuSubItem4').text(data.subItem4);
                
            },
            error: function(e){
            	alert('Error: ' + e);
        	}
        });
    }
    
    function showModal(mealId) {
		$("#mealId").val(mealId);
		$('#menuDate').text('');
    	$('#menuMainItem').text('');
        $('#menuSubItem1').text('');
        $('#menuSubItem2').text('');
        $('#menuSubItem3').text('');
        $('#menuSubItem4').text('');
		$("#menuModal").modal('show');
		return false;
    }
</script>
  
</head>
<body>
<%@include file="header.jsp" %>      

      <div class="container order_details_div">
        <h4 class="order_details_heading">${vendor.name} offers:</h4>
        <c:forEach items="${vendor.meals}" var="meal">
        <div class="order_details_card">
            <div class="row">
            <form action="selectMealFormat" method="post">
            	<input type="hidden" name="title" value="${meal.title}"/>
                <input type="hidden" name="id" value="${meal.id}"/>
               	<input type="hidden" name="description" value="${meal.description}"/>
                <input type="hidden" name="price" value="${meal.price}"/>
                <div class="col-md-4">
                    <img src="getMealImage.htm?mealId=${meal.id}" alt="no-image" class="img-responsive order_card_img" />
                </div>
                <div class="col-md-6">
                    <h3 class="order_details_title">${meal.title}</h3>
                    <h4 class="order_details_sub_title">${meal.description}</h4>
                    <h4 class="order_details_sub_title">Rs. ${meal.price}</h4>
                    <h4 class="order_details_sub_title">Available for : ${meal.mealTime.description}</h4>
                    <div class="submit_order">
                        <button type="button" onclick="return showModal(${meal.id})" class="btn order_button">VIEW MENU</button>
                        <button type="submit" class="btn order_button">ORDER</button>
                    </div>
                </div>
            </form>
            </div>
        </div>
        </c:forEach>
    </div>
    
    <div class="modal fade" id="menuModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
          <div class="modal-dialog" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">View Menu</h4>
              </div>
              <div class="modal-body">
                  <div class="row">
                      <div class="col-md-6">
                          <div class="col-md-4">
                   		  <label class="radio option_radio">
                      		<input type="radio" id="option-1" class="" name="mealType" value="<%=MealType.LUNCH%>" required="required"/>
                      		<span class="">Lunch menu</span>
                      	  </label>
                      	  <label class="radio option_radio">
                      		<input type="radio" id="option-2" class="" name="mealType" value="<%=MealType.DINNER%>" required="required"/>
                      		<span class="">Dinner menu</span>
                      	  </label>
                		  </div>
                          <h6 class="order_label1"><div id= "menuDate"></div></h6>
                          <table class="table table-bordered menu_list_table">
										<tr>
												<td>Sabji</td>
												<td><h6 class="order_label1"><div id= "menuMainItem"></div></h6></td>
											</tr>
											<tr>
												<td>Chapati/Roti</td>
												<td><h6 class="order_label1"><div id= "menuSubItem1"></div></h6></td>
											</tr>
											<tr>
												<td>Rice/Dal</td>
												<td><h6 class="order_label1"><div id= "menuSubItem2"></div></h6></td>
											</tr>
											<tr>
												<td>Salad</td>
												<td><h6 class="order_label1"><div id= "menuSubItem3"></div></h6></td>
											</tr>
											<tr>
												<td>Extra</td>
												<td><h6 class="order_label1"><div id= "menuSubItem4"></div></h6></td>
											</tr>
									</table>
                          
                          <input type="hidden" id="mealId"/>
                          <h6 class="order_label1"></h6>
                      </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"> Cancel </button>
                <button type="button" class="btn btn-primary" onclick="onViewMenu()">Show Menu</button>
              </div>
            </div>
          </div>
        </div>
        </div>
    
    <%@include file="footer.jsp" %>
</body>
</html>