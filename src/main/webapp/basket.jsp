<%@ page
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib
	prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib
	prefix="fmt"
	uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set
	var="cookie_lang"
	value="${cookie['lang'].value}"
	scope="application" />
<fmt:setLocale
	value="${ empty cookie_lang ? 'EN' : cookie_lang}"
	scope="application" />
<fmt:setBundle
	basename="localization.local"
	scope="application" />
<c:set
	var="currentPage"
	value="/Controller?command=view_basket"
	scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value="Basket" /></title>
<link
	href="css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="css/site.css"
	rel="stylesheet">
<link
	href="images/favicon.ico"
	rel="shortcut icon"
	type="image/x-icon">
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/partial_pages/header.jsp"></jsp:include>
	<h3 class="center-align">
		<c:out value="Basket" />
	</h3>
	<div class="half-padding">
		<div class="container">
			<div class="basket-list card">
				<table>
					<thead>
						<tr>
							<td colspan="2">Товар</td>
							<td>Цена</td>
							<td>Кол-во</td>
							<td colspan="2">Сумма</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><img
								src=""
								alt="drinkImage"></td>
							<td>
								<div>DrinkName</div>
							</td>
							<td>Price
							<td>Amount</td>
							<td>Total
							<td>Del</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/jsp/partial_pages/footer.jsp"%>
	<script src="js/bootstrap.bundle.min.js"></script>
	<script src="js/header.js"></script>
</body>
</html>