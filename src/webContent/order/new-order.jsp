<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Product Management Application</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>

	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: teal">
			<div>
				<a href="http://localhost:8080/products" class="navbar-brand"> Product Management App </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/products"
					class="nav-link">Products</a></li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${product != null}">
					<form action="update" method="post">
				</c:if>
				<c:if test="${product == null}">
					<form action="insert" method="post">
				</c:if>

				<caption>
					<h2>
						<c:if test="${product != null}">
            			Edit User
            		</c:if>
						<c:if test="${product == null}">
            			Add New Product
            		</c:if>
					</h2>
				</caption>

				<c:if test="${product != null}">
					<input type="hidden" name="id" value="<c:out value='${product.id}' />" />
				</c:if>

				<fieldset class="form-group">
					<label>Customer Name</label> <input type="text"
						value="<c:out value='${order.customer_name}' />" class="form-control"
						name="customer_name" required="required">
				</fieldset>
                <fieldset class="form-group">
                    <label>Product Name</label> <input type="text"
                        value="<c:out value='${order.product}' />" class="form-control"
                        name="product" required="required">
                </fieldset>
                <fieldset class="form-group">
                    <label>Total Transaction</label> <input type="text"
                        value="<c:out value='${order.transaction}' />" class="form-control"
                        name="transaction" required="required">
                </fieldset>


				<fieldset class="form-group">
					<label>Address</label> <input type="text"
						value="<c:out value='${order.address}' />" class="form-control"
						name="address">
				</fieldset>

				<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>