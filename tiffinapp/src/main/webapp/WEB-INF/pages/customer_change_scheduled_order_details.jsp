<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>TiffEat | Schedule a Tiffin</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
</head>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
function showModal() {
	$.ajax({
    	type : "GET",
        url : 'scheduledOrder.htm',
        success : function(data) {
        },
        error: function(e){
        	alert('Error: ' + e);
    	}
    });
	
	var radios = document.getElementsByName('mealType');
	var mealType;
	for (var i = 0, length = radios.length; i < length; i++) {
	    if (radios[i].checked) {
	        mealType = radios[i].value;
	        break;
	    }
	}
	//alert("Here!" + mealType);
	$("#modalMealType").text("Meal Type :" +mealType);
	$("#modalAddress").text("Address : " + $("#txtAddress").val());
	$("#payment_Modal").modal('show');
	return false;
}

function proceed() {
	
	document.getElementById("scheduledOrderForm").submit();
}
</script>
<body>
	
	<%@include file="header.jsp" %>

      <div class="container payment_details_div">
        <h4 class="payment_details_heading">Change Tiffin</h4>
        <div class="payment_details_card">
        <c:if test="${result != null }">
			<div class="alert alert-danger">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<!-- <strong>Danger!</strong> -->${result}
			</div>
		  </c:if>
            <div class="row">
              <div class="col-md-6">
                <form action="changeOrder" id="scheduledOrderForm" method="post" onsubmit="return showModal()">
                  <input type="hidden" name="customer.id" value="${customerOrder.customer.id}"/>
                  <input type="hidden" name="content.id" value="${customerOrder.content.id}">
                  <input type="hidden" name="meal.id" value="${customerOrder.meal.id}"/>
                  <input type="hidden" name="meal.title" value="${customerOrder.meal.title}"/>
                  <input type="hidden" name="id" value="${customerOrder.id}"/>
                  <input type="hidden" name="location.address" value="${customerOrder.location.address}"/>
                  Meal : ${customerOrder.meal.title} <br/>
                  Price : ${customerOrder.meal.price} <br/>
			      Location: ${customerOrder.location.address} <br/>
			      Timing : ${customerOrder.mealType} <br/>
			      <div class="divspacing">
                  		<textarea class="form-control" type="text" pattern="" id="txtAddress" name="address" value="${customerOrder.address}" placeholder="ADDRESS" required="required">${customerOrder.address}</textarea>
                  </div>
                  <div class="submit_order">
                      <input type="submit" name="" value="CHANGE ORDER" class="order_button">
                  </div>
            </form>
            </div>
        </div>
      </div>
    </div>
    
     <div class="modal fade" id="payment_Modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
          <div class="modal-dialog" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel"> Order Details</h4>
              </div>
              <div class="modal-body">
                  <div class="row">
                      <div class="col-md-6">
                          <h6 class="order_label1">Meal : ${customerOrder.meal.title}</h6>
                          <h6 class="order_label1"><div id= "modalMealType"></div></h6>
                          <h6 class="order_label1">Location : ${customerOrder.location.address}</h6>
                          <h6 class="order_label1"><div id= "modalAddress"></div></h6>
                          <h6 class="order_label1">Price:${customerOrder.meal.price}</h6>
                      </div>
                      <div class="col-md-6">
                  		<img src="getMealImage.htm?mealId=${customerOrder.meal.id}" alt="no_image" class="order_img1 img-responsive">
                      </div>
                 
            
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"> Cancel </button>
                  <button type="button" class="btn btn-primary" onclick="proceed()">Proceed</button>
              </div>
            </div>
          </div>
        </div>
        </div>
    
    <%@include file="footer.jsp" %>
  </body>
</html>