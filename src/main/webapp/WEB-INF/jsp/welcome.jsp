<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="cookie_lang" value="${cookie['lang'].value}"
	scope="application" />
<fmt:setLocale value="${cookie_lang == null ? 'EN' : cookie_lang}"
	scope="application" />
<fmt:setBundle basename="localization.local" scope="application" />
<c:set var="currentPage" value="/CoffeeMachine/index.jsp"
	scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><fmt:message key="local.welcome.header" /></title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/site.css" rel="stylesheet">
<link href="images/favicon.ico" rel="shortcut icon" type="image/x-icon">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<c:if test="${!empty menu}">
		<h3 class="center-align">
			<fmt:message key="local.welcome.popular_drinks" />
		</h3>
		<jsp:include page="/WEB-INF/jsp/show_menu.jsp"></jsp:include>
	</c:if>
	<%@include file="footer.jsp"%>
	<script src="js/bootstrap.bundle.min.js"></script>
	<script src="js/header.js"></script>
</body>
</html>