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
	<h3 class="center-align">
		<fmt:message key="local.header.menu.name" />
	</h3>
	<jsp:include page="/WEB-INF/jsp/partial_pages/show_menu.jsp"></jsp:include>
	<div class="btn-group page-list">
		<c:if test="${requestScope.pages_amount != null && requestScope.pages_amount > 1}">
			<c:forEach
				var="page"
				begin="1"
				end="${requestScope.pages_amount}"
				varStatus="pageNumber">
				<form action="/CoffeeMachine/Controller">
					<input
						type="hidden"
						name="command"
						value="view_menu">
					<c:choose>
						<c:when test="${currentMenuPage == page}">
							<button
								class="btn btn-sm btn-outline-secondary current"
								type="submit"
								name="page"
								value="${pageNumber.index}">
								<c:out value="${pageNumber.index} " />
							</button>
						</c:when>
						<c:otherwise>
							<button
								class="btn btn-sm btn-outline-secondary"
								type="submit"
								name="page"
								value="${pageNumber.index}">
								<c:out value="${pageNumber.index} " />
							</button>
						</c:otherwise>
					</c:choose>
				</form>
			</c:forEach>
		</c:if>
	</div>
	<%@include file="partial_pages/footer.jsp"%>
	<script src="/CoffeeMachine/js/bootstrap.bundle.min.js"></script>
	<script src="/CoffeeMachine/js/header.js"></script>
	<script src="/CoffeeMachine/js/addToBasket.js"></script>
</body>
</html>