<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="cookie_lang" value="${cookie['lang'].value}"
	scope="application" />
<fmt:setLocale value="${cookie_lang == null ? 'EN' : cookie_lang}"
	scope="application" />
<fmt:setBundle basename="localization.local" scope="application" />
<c:set var="currentPage" value="/CoffeeMachine/login"
	scope="application" />
<c:set var="registration_errors" value="${registration_errors}"
	scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><fmt:message key="local.header.login.name" /></title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/site.css" rel="stylesheet">
<link href="images/favicon.ico" rel="shortcut icon" type="image/x-icon">
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	<h3>
		<fmt:message key="local.header.login.name" />
	</h3>
	<p>
		<fmt:message key="local.login.info_request" />
	</p>
	<p>
		<span class="star">* </span>
		<fmt:message key="local.registration.required_field_info" />
	</p>
	<div>
		<div class="bd-example">
			<form action="Controller">
				<input type="hidden" name="command" value="login" />
				<c:if test="${incorrect_credentials}">
					<div class="form-floating mb-3 flex-display error">
						<fmt:message key="local.user.error.incorrect_credentials" />
					</div>
				</c:if>
				<div class="form-floating mb-3 flex-display">
					<input type="text" name="login" class="form-control" id="login"
						placeholder="coffee@example.com" required> <label
						for="login"><fmt:message key="local.login.login_label" /><span
						class="star"> *</span> </label>
				</div>
				<div class="form-floating mb-3 flex-display">
					<input type="password" name="password" class="form-control"
						id="password" placeholder="xxx" required> <label
						for="password"><fmt:message key="local.password" /><span
						class="star"> *</span></label>
				</div>
				<input id="submit-registration" type="submit"
					class="btn btn-sm btn-outline-secondary" name="registration-form"
					value="<fmt:message
							key="local.header.login.name"/>" />
			</form>
		</div>
	</div>
	<p>
		<fmt:message key="local.login.registration_offer" />
		<a href="/CoffeeMachine/registration"><fmt:message
				key="local.registration.header" /></a>
	</p>
	<%@include file="footer.jsp"%>
	<script src="js/header.js"></script>
</body>
</html>