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
        <h4 class="payment_details_heading">Schedule a Tiffin from ${customerOrder.meal.vendor.name}</h4>
        <div class="payment_details_card">
        <c:if test="${result != null }">
			<div class="alert alert-danger">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<!-- <strong>Danger!</strong> -->${result}
			</div>
		  </c:if>
		  <fmt:formatDate pattern="EEE, d MMM yyyy" value="${customerOrder.date}" var="orderDate" />
            <div class="row">
              <div class="col-md-6">
                <form action="scheduledOrder" id="scheduledOrderForm" method="post" onsubmit="return showModal()">
                  <input type="hidden" name="meal.id" value="${customerOrder.meal.id}"/>
                  <input type="hidden" name="meal.price" value="${customerOrder.meal.price}"/>
                  <input type="hidden" name="meal.title" value="${customerOrder.meal.title}"/>
                  <input type="hidden" name="customer.id" value="${customerOrder.customer.id}"/>
                  <input type="hidden" name="customer.name" value="${customerOrder.customer.name}"/>
                  <input type="hidden" name="area" value="${customerOrder.area}"/>
                   <input type="hidden" name="location.address" value="${customerOrder.location.address}"/>
                  <%-- <input type="hidden" name="date" value="${customerOrder.date}"/> --%>
                  <div class="details1">
                  Scheduling for : ${customerOrder.customer.name} <br/>
                  Email : ${customerOrder.customer.email}
                  
                  <div class="divspacing">
                    <!-- <input class="form-control" type="text"  placeholder="My Tiffin"/> -->
                    Meal : ${customerOrder.meal.title}
                  </div>
                  <div class="divspacing">
                   <!-- <input class="form-control" type="text" pattern="" id="" name="dabbawala" placeholder="Dabbawala"/> -->
				    ${customerOrder.meal.vendor.name}                  
                  </div>
                  <div class="divspacing" id="price">
                      <!-- <input class="form-control" type="text" id="" name="" placeholder="ABC ABC"/> -->
                    Price : ${customerOrder.meal.price}
                  </div>
                 <%--  Area : ${customerOrder.area}
			      <br/> --%>
			      Location: ${customerOrder.location.address} <br/>
			      Scheduled From : ${orderDate}
			      </div>
                  <div class="divspacing">
                      <input class="form-control" type="hidden" readonly="readonly" id="" name="customer.email" value="${customerOrder.customer.email}"/>
                  </div>
                  <div class="divspacing">
                        <input class="form-control" type="text" maxlength="15" pattern="^(\+?\d{1,4}[\s-])?(?!0+\s+,?$)\d{10}\s*,?$" id="mobile_no" name="customer.phone" value="${customerOrder.customer.phone}" placeholder="MOBILE NUMBER" required/>
                  </div>
                  <!-- <div class="divspacing">
                  		<input class="form-control" type="text" pattern="" id="" name="customer.balance" placeholder="ADD MONEY TO WALLET"/>
                  </div> -->
                  <div class="option col-md-12">
                  <c:forEach var="type" items="${mealType}">
 					 <label class="options" for="option-1">
                      <input type="radio" id="${type}" class="" name="mealType" value="${type}" autofocus required="required"/>
                      <span class="">${type}</span>
                    </label>
			      </c:forEach>
			      </div>
			      <br/>
			      <div class="divspacing">
                  		<textarea class="form-control" type="text" pattern="" id="" name="address" placeholder="ADDRESS" required="required">${customerOrder.address}</textarea>
                  </div>
                  <label class="checkbox chbox1">
                  <input type="checkbox" value="agree" required>I agree to <a href = "terms.htm" class="universal_link" target="_blank">terms and conditions</a>
                           
                        </label>
                  <div class="submit_order">
                      <input type="submit" name="" value="ORDER" class="order_button">
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
                          <h6 class="order_label1">Scheduled From : ${orderDate}</h6>
                          <h6 class="order_label1"><div id= "modalMealType"></div></h6>
                          <h6 class="order_label1">Location : ${customerOrder.location.address}</h6>
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
    
    <script>
	function confirmOrder(data) {
		var array = data.split(',');
		var radios = document.getElementsByName('mealType');
		var mealType;
		for (var i = 0, length = radios.length; i < length; i++) {
		    if (radios[i].checked) {
		        mealType = radios[i].value;
		        break;
		    }
		}
		
		if(mealType == 'BOTH') {
			mealType = 'lunch and dinner';
		}
    	var r = confirm("You have scheduled " + array[2] + " meal daily for " + mealType + " for Rs." + array[3] + 
    			" starting from " + array[0] + array[1] + ".\n Are you sure?");
    	return r;
	}
	</script>
    <%@include file="footer.jsp" %>
  </body>
</html>