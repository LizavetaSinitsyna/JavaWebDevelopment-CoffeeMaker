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
	value="/basket"
	scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><fmt:message key="local.header.basket.name" /></title>
<link
	href="/CoffeeMachine/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="/CoffeeMachine/css/site.css"
	rel="stylesheet">
<link
	href="/CoffeeMachine/images/favicon.ico"
	rel="shortcut icon"
	type="image/x-icon">
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/partial_pages/header.jsp"></jsp:include>
	<h3 class="center-align">
		<fmt:message key="local.header.basket.name" />
	</h3>
	<div class="half-padding">
		<div class="container">
			<c:if test="${! empty unavailableIngredient}">
				<div class="error">
					<input
						type="hidden"
						id="unavailableDrinkId"
						value="${unavailableIngredient.getDrinkId()}">
					<input
						type="hidden"
						id="availableDrinkAmount"
						value="${unavailableIngredient.getAvailableDrinkAmount()}">
					<div>
						<fmt:message key="local.basket.error.unavailable_ingredient" />
						<span><c:out value="${unavailableIngredient.getIngredientName()}. " /></span>
					</div>
					<div>
						<fmt:message key="local.basket.error.availableDrinkAmount" />
						<span><c:out value="${unavailableIngredient.getAvailableDrinkAmount()}." /></span> <span><fmt:message
								key="local.basket.error.change_order_invitation" /></span>
					</div>
				</div>
			</c:if>
			<c:if test="${! empty failedPayment}">
				<div class="error">
					<fmt:message key="local.pay.failed_payment" />
				</div>
			</c:if>
			<c:if test="${! empty failedOrderCancel}">
				<div class="error">
					<fmt:message key="local.order.failed_order_cancel" />
				</div>
			</c:if>
			<form
				action="/CoffeeMachine/Controller"
				method="post">
				<input
					type="hidden"
					name="command"
					value="make_order">
				<div class="basket-list">
					<h6
						class="hide"
						id="emptyBasket">
						<fmt:message key="local.basket.empty.message" />
					</h6>
					<table class="basket-table">
						<thead>
							<tr class="basket-list-header">
								<td colspan="2"><fmt:message key="local.basket.table.drink" /></td>
								<td><fmt:message key="local.product.price" /></td>
								<td><fmt:message key="local.product.amount" /></td>
								<td colspan="2"><fmt:message key="local.basket.table.sum" /></td>
							</tr>
						</thead>
						<tbody>
						</tbody>
						<tfoot>
							<tr class="basket-list-header basket-list-tr">
								<td colspan="4"><fmt:message key="local.basket.table.total" /></td>
								<td
									id="total"
									colspan="2"><fmt:message key="local.basket.table.sum" /></td>
							</tr>
							<tr class="basket-list-header basket-list-tr">
								<td colspan="6"><button
										type="submit"
										class="do-btn btn btn-sm btn-outline-secondary float-right">
										<fmt:message key="local.basket.order.make" />
									</button></td>
							</tr>
						</tfoot>
					</table>
				</div>
			</form>
		</div>
	</div>
	<%@include file="/WEB-INF/jsp/partial_pages/footer.jsp"%>
	<script src="/CoffeeMachine/js/bootstrap.bundle.min.js"></script>
	<script src="/CoffeeMachine/js/header.js"></script>
	<script src="/CoffeeMachine/js/basket.js"></script>
</body>
</html>