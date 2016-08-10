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
	<form:form method="POST" modelAttribute="carts" commandName="cart"
		action="/SpringMVCDemo/AddItems">
		<table cellspacing="10">
			<tr>
				<td>Item:</td>
				<td colspan="2"><form:select path="id">
						<form:options items="${itemsList}" itemValue="id"
							itemLabel="items" />
					</form:select></td>
			</tr>
			<tr>
				<td><form:label path="quantity">Quantity</form:label></td>
				<td><form:input path="quantity" type="number"
						placeholder="Enter Quantity" required="required" min="1"/></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Add To Cart" /></td>
			</tr>

		</table>
	</form:form>
	<a href="ViewCart">View Cart</a>

	<h3>${message}</h3>
	<%-- 	<%=session.getAttribute("cart")%> --%>



</body>
</html>