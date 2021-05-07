<%@page import="com.order"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Order Management</title>

<link rel="stylesheet" href="Views/bootstrap.min.css">
<script type="text/javascript" src="Components/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="Components/order.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-7">
				<h1 class="m-3">Order Management</h1>

				<form id="formOrder" name="formOrder" method="post"
					action="order.jsp">

					Order Name: 
					<input id="orderName" name="orderName"
						type="text" class="form-control form-control-sm"> <br>
					Quantity: 
					<input id="orderQuantity"
						name="orderQuantity" type="text"
						class="form-control form-control-sm"> <br> 
					Unit: 
					<input id="orderUnit" name="orderUnit"
						type="text" class="form-control form-control-sm"> <br>
					Date: 
					<input id="orderDate" name="orderDate" type="date" value="dd/mm/yyyy"
						class="form-control form-control-sm"> <br> 
					<input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidOrderIDSave" name="hidOrderIDSave" value="">

				</form>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

			</div>
		</div>




		<br>
		<div id="divOrderGrid">

			<%
			order orderObj = new order();
			out.print(orderObj.readOrder());
			%>
		</div>
</body>
</html>