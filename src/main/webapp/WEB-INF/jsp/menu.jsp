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
<%@ taglib
	prefix="ctg"
	uri="customtags"%>
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
	var="currentMenuPage"
	value="${empty param.page ? 1 : param.page}" />
<c:set
	var="currentPage"
	value="/Controller?command=view_menu&page=${currentMenuPage}"
	scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><fmt:message key="local.header.menu.name" /></title>
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
	<c:choose>
		<c:when test="${sessionScope.user != null && sessionScope.user.getRoleId() == 1}">
			<form action="/CoffeeMachine/Controller">
				<h3 class="center-align">
					<fmt:message key="local.header.menu.name" />
					<input
						type="hidden"
						name="command"
						value="view_product_add">
					<button
						type="submit"
						class="btn btn-sm btn-outline-secondary">
						<fmt:message key="local.add.btn" />
					</button>
				</h3>
			</form>
		</c:when>
		<c:otherwise>
			<h3 class="center-align">
				<fmt:message key="local.header.menu.name" />
			</h3>
		</c:otherwise>
	</c:choose>
	<jsp:include page="/WEB-INF/jsp/partial_pages/show_menu.jsp"></jsp:include>
	<div class="btn-group page-list">
		<form action="/CoffeeMachine/Controller">
			<input
				type="hidden"
				name="command"
				value="view_menu">
			<ctg:pagination
				current="${currentMenuPage}"
				total="${requestScope.pages_amount}"
				generalClass="btn btn-sm btn-outline-secondary"
				currentPageClass="btn btn-sm btn-outline-secondary current" />
		</form>
	</div>
	<%@include file="partial_pages/footer.jsp"%>
	<script src="/CoffeeMachine/js/bootstrap.bundle.min.js"></script>
	<script src="/CoffeeMachine/js/header.js"></script>
	<script src="/CoffeeMachine/js/addToBasket.js"></script>
</body>
</html>