<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

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
                          <h4 class="order_label1"><div id= "menuDate"></div></h4>
                          <table class="table table-bordered menu_list_table">
										<tr>
												<td>Sabji</td>
												<td><h5 class="order_label1"><div id= "menuMainItem"></div></h5></td>
											</tr>
											<tr>
												<td>Chapati/Roti</td>
												<td><h5 class="order_label1"><div id= "menuSubItem1"></div></h5></td>
											</tr>
											<tr>
												<td>Rice/Dal</td>
												<td><h5 class="order_label1"><div id= "menuSubItem2"></div></h5></td>
											</tr>
											<tr>
												<td>Salad</td>
												<td><h5 class="order_label1"><div id= "menuSubItem3"></div></h5></td>
											</tr>
											<tr>
												<td>Extra</td>
												<td><h5 class="order_label1"><div id= "menuSubItem4"></div></h5></td>
											</tr>
									</table>
                          
                          <input type="hidden" id="mealId"/>
                          <h6 class="order_label1"></h6>
                      </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"> Cancel </button>
              </div>
            </div>
          </div>
        </div>
        </div>

</body>
</html>