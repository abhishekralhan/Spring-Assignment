<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<table border="1" cellpadding="10px" width="100%"
		style="border-collapse: collapse;">
		<tr>
			<th>Item</th>
			<th>Quantity</th>
			<th>Price</th>
			<th>Color</th>
			<th>Total</th>
		</tr>
		<c:forEach items="${arrList}" var="j">
			<c:forEach items="${itemList}" var="i">
				<c:if test="${j.getId()==i.key}">
					<tr>
						<td><c:out value="${j.getItems()}" /></td>
						<td><c:out value="${i.value}" /></td>
						<td><c:out value="RS. ${j.getPrice()}" /></td>
						<td><c:out value="${j.getColor()}" /></td>
						<td><c:out value="${i.value*j.getPrice()}" /></td>
					</tr>
				</c:if>
			</c:forEach>
		</c:forEach>


	</table>
	<a href="DeleteItems">Delete Items From Cart.</a>
	<!-- 	<a href="PlaceOrder">Place Order</a> -->
	<br />
	<br />
	<a href="Logout">Logout</a>
	<h3>${message}</h3>
</body>
</html>