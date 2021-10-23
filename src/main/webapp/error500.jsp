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
	value="/CoffeeMachine/error500"
	scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><fmt:message key="local.error.500" /></title>
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
	<h1>
		<fmt:message key="local.error.500" />
	</h1>
	<%@include file="/WEB-INF/jsp/partial_pages/footer.jsp"%>
	<script src="/CoffeeMachine/js/bootstrap.bundle.min.js"></script>
	<script src="/CoffeeMachine/js/header.js"></script>
</body>
</html>