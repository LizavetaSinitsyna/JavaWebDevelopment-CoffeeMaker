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
	value="/login"
	scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><fmt:message key="local.header.login.name" /></title>
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
	<h3>
		<fmt:message key="local.header.login.name" />
	</h3>
	<p>
		<fmt:message key="local.login.info_request" />
	</p>
	<p>
		<span class="star">* </span>
		<fmt:message key="local.required_field_info" />
	</p>
	<div>
		<div class="bd-example">
			<form
				action="/CoffeeMachine/Controller"
				method="post">
				<input
					type="hidden"
					name="command"
					value="login" />
					<input
					type="hidden"
					name="nextPage"
					value="${param.nextPage}" />
				<c:if test="${incorrect_credentials}">
					<div class="form-floating mb-3 flex-display error">
						<fmt:message key="local.user.error.incorrect_credentials" />
					</div>
				</c:if>
				<div class="form-floating mb-3 flex-display">
					<input
						type="text"
						name="login"
						class="form-control width-50"
						id="login"
						placeholder="coffee@example.com"
						required>
					<label for="login">
						<fmt:message key="local.login.login_label" />
						<span class="star"> *</span>
					</label>
				</div>
				<div class="form-floating mb-3 flex-display">
					<input
						type="password"
						name="password"
						class="form-control width-50"
						id="password"
						placeholder="xxx"
						required>
					<label for="password">
						<fmt:message key="local.password" />
						<span class="star"> *</span>
					</label>
				</div>
				<input
					id="submit-registration"
					type="submit"
					class="btn btn-sm btn-outline-secondary"
					name="registration-form"
					value="<fmt:message
							key="local.header.login.name"/>" />
			</form>
		</div>
	</div>
	<p>
		<fmt:message key="local.login.registration_offer" />
		<a href="/CoffeeMachine/registration"><fmt:message key="local.header.sign_up.name" /></a>
	</p>
	<%@include file="partial_pages/footer.jsp"%>
	<script src="/CoffeeMachine/js/header.js"></script>
</body>
</html>