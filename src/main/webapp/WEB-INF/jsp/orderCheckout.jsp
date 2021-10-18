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
	value="/order"
	scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><fmt:message key="local.order.header" /></title>
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
		<fmt:message key="local.order.header" />
	</h3>
	<div class="half-padding">
		<div class="container">
			<form action="Controller">
				<input
					type="hidden"
					name="command"
					value="pay">
				<div class="basket-list">
					<table>
						<thead>
							<tr class="basket-list-header">
								<td
									class="basket-list-td"
									colspan="2"><fmt:message key="local.basket.table.drink" /></td>
								<td class="basket-list-td"><fmt:message key="local.product.price" /></td>
								<td class="basket-list-td"><fmt:message key="local.product.amount" /></td>
								<td><fmt:message key="local.basket.table.sum" /></td>
							</tr>
						</thead>
						<tbody>
							<c:forEach
								var="drink"
								items="${order.getOrderDrink().getDrinksAmount().entrySet()}">
								<tr class="basket-list-tr">
									<td><img
										class="basket-list-image"
										src="${drink.getKey().getImagePath()}"
										alt="drinkImage"></td>
									<td class="basket-list-td">${drink.getKey().getName()}</td>
									<td class="basket-list-td"><fmt:formatNumber
											type="number"
											minFractionDigits="2"
											value="${drink.getKey().getPrice()}" /></td>
									<td class="basket-list-td">${drink.getValue()}</td>
									<td><fmt:formatNumber
											type="number"
											minFractionDigits="2"
											value="${drink.getKey().getPrice().doubleValue() * drink.getValue()}" /></td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr class="basket-list-header basket-list-tr">
								<td
									class="basket-list-td"
									colspan="4"><fmt:message key="local.basket.table.total" /></td>
								<td
									id="total"
									colspan="2"><fmt:formatNumber
										type="number"
										minFractionDigits="2"
										value="${order.getOrder().getInfo().getCost()}" /></td>
							</tr>
							<tr class="basket-list-header basket-list-tr">
								<td colspan="3">Бонусный счет<input type="number">
								</td>
								<td colspan="3">Денежный счет<input type="number">
								</td>
							</tr>
							<tr class="basket-list-header basket-list-tr">
								<td colspan="6"><button
										type="submit"
										class="do-btn btn btn-sm btn-outline-secondary float-right">
										<fmt:message key="local.order.pay" />
									</button></td>
							</tr>
						</tfoot>
					</table>
				</div>
			</form>
		</div>
	</div>
	<%@include file="/WEB-INF/jsp/partial_pages/footer.jsp"%>
	<script src="js/bootstrap.bundle.min.js"></script>
	<script src="js/header.js"></script>
</body>
</html>