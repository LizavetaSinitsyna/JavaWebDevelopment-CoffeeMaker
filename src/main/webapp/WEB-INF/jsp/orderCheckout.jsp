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
	<form
		action="/CoffeeMachine/Controller"
		method="post">
		<h3 class="center-align">
			<fmt:message key="local.order.header" />
			<input
				type="hidden"
				name="command"
				value="cancel_order">
			<input
				type="hidden"
				name="orderId"
				value="${order.getOrder().getOrderId()}">
			<button
				type="submit"
				class="btn btn-sm btn-outline-secondary">
				<fmt:message key="local.edit.cancel" />
			</button>
		</h3>
	</form>
	<div class="half-padding">
		<div class="container">
			<form
				action="/CoffeeMachine/Controller"
				method="post">
				<input
					type="hidden"
					name="command"
					value="pay_for_order">
				<input
					type="hidden"
					name="orderId"
					value="${order.getOrder().getOrderId()}">
				<div class="basket-list">
					<table class="basket-table">
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
								<td colspan="3"><fmt:message key="local.pay.cash_balance" />: <fmt:formatNumber
										type="number"
										minFractionDigits="2"
										value="${account.getBalance()}" /> <fmt:message key="local.menu.price.currency" /></td>
								<td colspan="3"><fmt:message key="local.pay.bonus_balance" />: <fmt:formatNumber
										type="number"
										minFractionDigits="2"
										value="${bonusAccount.getBalance()}" /> <fmt:message key="local.menu.price.currency" /></td>
							</tr>
							<tr class="basket-list-header">
								<td colspan="6"><button
										type="submit"
										class="do-btn btn btn-sm btn-outline-secondary">
										<fmt:message key="local.order.pay" />
									</button></td>
							</tr>
							<tr class="basket-list-header">
								<td colspan="6"><fmt:message key="local.order.creation_time" />
									<fmt:parseDate
										value="${order.getOrder().getInfo().getDateTime()}"
										pattern="yyyy-MM-dd'T'HH:mm"
										var="parsedDateTime"
										type="both" /> <fmt:formatDate
										pattern="dd.MM.yyyy HH:mm"
										value="${ parsedDateTime }" /></td>
							</tr>
							<tr class="basket-list-header">
								<td colspan="6"><fmt:message key="local.pay.time_warn" /></td>
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
</body>
</html>