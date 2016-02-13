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

<link href="<c:url value = "${resources}/css/payment_details.css"/>"
	rel="stylesheet">
</head>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
	function showModal() {
		$.ajax({
			type : "GET",
			url : '<%=Constants.SCHEDULED_ORDER_URL_GET%>',
			success : function(data) {
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});

		/* var radios = document.getElementsByName('mealType');
		var mealType;
		for (var i = 0, length = radios.length; i < length; i++) {
			if (radios[i].checked) {
				mealType = radios[i].value;
				break;
			}
		}
		//alert("Here!" + mealType);
		$("#modalMealType").text("Meal Type :" + mealType); */
		$("#payment_Modal").modal('show');
		return false;
	}

	function proceed() {

		document.getElementById("scheduledOrderForm").submit();
	}
</script>
<body>

	<%@include file="header.jsp"%>

	<div class="container payment_details_div">
		<h4 class="payment_details_heading">Schedule a Tiffin from
			${customerOrder.meal.vendor.name}</h4>
		<div class="payment_details_card">
			<c:if test="${result != null }">
				<div class="alert alert-danger">
					<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					<!-- <strong>Danger!</strong> -->${result}
				</div>
			</c:if>
			<fmt:formatDate pattern="EEE, d MMM yyyy"
				value="${customerOrder.date}" var="orderDate" />
			<div class="row">
				<div class="col-md-6">
					<form action="<%=Constants.SCHEDULED_ORDER_URL_POST %>" id="scheduledOrderForm" method="post"
						onsubmit="return showModal()">
						<input type="hidden" name="meal.id"
							value="${customerOrder.meal.id}" /> <input type="hidden"
							name="meal.price" value="${customerOrder.meal.price}" /> <input
							type="hidden" name="meal.title"
							value="${customerOrder.meal.title}" /> <input type="hidden"
							name="customer.id" value="${customerOrder.customer.id}" /> <input
							type="hidden" name="customer.name"
							value="${customerOrder.customer.name}" /> <input type="hidden"
							name="area" value="${customerOrder.area}" /> <input type="hidden"
							name="location.address" value="${customerOrder.location.address}" />
						<%-- <input type="hidden" name="date" value="${customerOrder.date}"/> --%>
						<input type="hidden" name="mealType" value="${customerOrder.mealType}"/>
						<div class="details1">
							Scheduling for : ${customerOrder.customer.name} <br /> Email :
							${customerOrder.customer.email}

							<div class="divspacing">
								Meal : ${customerOrder.meal.title}
							</div>
							<div class="divspacing">
								Vendor Name: ${customerOrder.meal.vendor.name}
							</div>
							<div class="divspacing" id="price">
								Price : ${customerOrder.meal.price}
							</div>
							Location: ${customerOrder.location.address} <br /> 
							Scheduled From : ${orderDate} <br/>
							Meal timing : ${customerOrder.mealType.description}
						</div>
						<div class="divspacing">
							<input class="form-control" type="hidden" readonly="readonly"
								id="" name="customer.email"
								value="${customerOrder.customer.email}" />
						</div>
						<div class="divspacing">
							<input class="form-control" type="text" maxlength="15"
								pattern="^(\+?\d{1,4}[\s-])?(?!0+\s+,?$)\d{10}\s*,?$"
								id="mobile_no" name="customer.phone"
								value="${customerOrder.customer.phone}"
								placeholder="MOBILE NUMBER" required />
						</div>
						<%-- <div class="option col-md-12">
							<c:forEach var="type" items="${mealType}">
								<label class="options" for="option-1"> <input
									type="radio" id="${type}" class="" name="mealType"
									value="${type}" autofocus required="required" /> <span class="">${type}</span>
								</label>
							</c:forEach>
						</div> 
						<br />--%>
						<div class="divspacing">
							<textarea class="form-control" type="text" pattern="" id=""
								name="address" placeholder="ADDRESS" required="required">${customerOrder.address}</textarea>
						</div>
						<label class="checkbox chbox1"> <input type="checkbox"
							value="agree" required>I agree to <a href="terms.htm"
							class="universal_link" target="_blank">terms and conditions</a>

						</label>
						<div class="submit_order">
							<input type="submit" name="" value="ORDER" class="btn order_button">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="payment_Modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Order Details</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-6">
							<h6 class="order_label1">Meal : ${customerOrder.meal.title}</h6>
							<h6 class="order_label1">Scheduled From : ${orderDate}</h6>
							<h6 class="order_label1">
								<div id="modalMealType">Meal timing : ${customerOrder.mealType}</div>
							</h6>
							<h6 class="order_label1">Location :
								${customerOrder.location.address}</h6>
							<h6 class="order_label1">Price:${customerOrder.meal.price}</h6>
						</div>
						<div class="col-md-6">
							<img src="getMealImage.htm?mealId=${customerOrder.meal.id}"
								alt="no_image" class="order_img1 img-responsive">
						</div>


					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							Cancel</button>
						<button type="button" class="btn btn-primary" onclick="proceed()">Proceed</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@include file="footer.jsp"%>
</body>
</html>