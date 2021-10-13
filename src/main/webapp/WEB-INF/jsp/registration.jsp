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
	value="/registration"
	scope="application" />
<jsp:include page="/WEB-INF/jsp/partial_pages/registration_error_handler.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><fmt:message key="local.registration.header" /></title>
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
	<h3>
		<fmt:message key="local.registration.header" />
	</h3>
	<p>
		<fmt:message key="local.registration.info_request" />
	</p>
	<p>
		<span class="star">* </span>
		<fmt:message key="local.required_field_info" />
	</p>
	<div>
		<div class="bd-example">
			<form
				action="Controller"
				method="post">
				<input
					type="hidden"
					name="command"
					value="registration" />
				<c:if test="${! empty errors}">
					<div class="form-floating mb-3 flex-display error">
						<fmt:message key="local.user.error.incorrect_input" />
					</div>
				</c:if>
				<div class="form-floating mb-3 flex-display">
					<input
						type="email"
						name="email"
						class="form-control width-50"
						id="email"
						value="${requestScope.email}"
						placeholder="coffee@example.com"
						pattern="^(?=[^@\s]+@[^@\\s]+\.[^@\s]+).{5,255}$"
						required>
					<label for="email">
						<fmt:message key="local.registration.email" />
						<span class="star"> *</span>
					</label>
					<c:choose>
						<c:when test="${! empty email_error}">
							<span class="input-info error"><c:out value="${email_error}" /></span>
						</c:when>
						<c:otherwise>
							<span class="input-info"> <fmt:message key="local.registration.email_requirements" />
							</span>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="form-floating mb-3 flex-display">
					<input
						type="text"
						name="username"
						class="form-control width-50"
						id="username"
						placeholder="CoffeeName"
						value="${requestScope.username}"
						pattern="^[a-zA-Z0-9а-яА-Я]([\._-](?![\._-])|[a-zA-Z0-9а-яА-Я]){3,18}[a-zA-Z0-9а-яА-Я]$"
						required>
					<label for="username">
						<fmt:message key="local.registration.username" />
						<span class="star"> *</span>
					</label>
					<c:choose>
						<c:when test="${! empty username_error}">
							<span class="input-info error"><c:out value="${username_error}" /></span>
						</c:when>
						<c:otherwise>
							<span class="input-info"> <fmt:message key="local.registration.username_requirements" />
							</span>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="form-floating mb-3 flex-display">
					<input
						type="text"
						name="name"
						class="form-control width-50"
						id="name"
						placeholder="Latte"
						value="${requestScope.name}"
						pattern=".{1,100}"
						required>
					<label for="name">
						<fmt:message key="local.registration.name" />
						<span class="star"> *</span>
					</label>
					<c:choose>
						<c:when test="${! empty name_error}">
							<span class="input-info error"><c:out value="${name_error}" /></span>
						</c:when>
						<c:otherwise>
							<span class="input-info"> <fmt:message key="local.registration.name_requirements" />
							</span>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="form-floating mb-3 flex-display">
					<input
						type="text"
						name="phone"
						class="form-control width-50"
						id="phone"
						placeholder="+375XXXXXXXXX"
						value="${requestScope.phone}"
						pattern="\+375[0-9]{9}"
						required>
					<label for="phone">
						<fmt:message key="local.registration.phone" />
						<span class="star"> *</span>
					</label>
					<c:choose>
						<c:when test="${! empty phone_error}">
							<span class="input-info error"><c:out value="${phone_error}" /></span>
						</c:when>
						<c:otherwise>
							<span class="input-info"> <fmt:message key="local.registration.phone_requirements" />
							</span>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="form-floating mb-3 flex-display">
					<input
						type="password"
						name="password"
						class="form-control width-50"
						id="password"
						placeholder="xxx"
						pattern="^(?=.*?[A-ZА-Я])(?=.*?[a-zа-я])(?=.*?[0-9])(?=.*?[ !&quot;#\\$%&'\(\)\*\+,\-\./:;<=>\?@\[\]\^_`{|}~]).{8,}$"
						required>
					<label for="password">
						<fmt:message key="local.password" />
						<span class="star"> *</span>
					</label>
					<c:choose>
						<c:when test="${! empty password_error}">
							<span class="input-info error"><c:out value="${password_error}" /></span>
						</c:when>
						<c:otherwise>
							<span class="input-info"> <fmt:message key="local.registration.password_requirements" />
							</span>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="form-floating mb-3 flex-display">
					<input
						type="password"
						name="repeat-password"
						class="form-control width-50"
						id="repeatPassword"
						placeholder="xxx"
						required>
					<label for="repeatFloatingPassword">
						<fmt:message key="local.registration.repeat_password" />
						<span class="star"> *</span>
					</label>
					<span
						id="check-pass-message"
						class="input-info error"><c:out value="${confirm_password_error}" /></span>
				</div>
				<input
					id="submit-registration"
					type="submit"
					class="btn btn-sm btn-outline-secondary"
					name="registration-form"
					value="<fmt:message
							key="local.registration.sign_up"/>"
					disabled />
			</form>
		</div>
	</div>
	<p>
		<fmt:message key="local.registration.login_offer" />
		<a href="/CoffeeMachine/login"><fmt:message key="local.header.login.name" /></a>
	</p>
	<%@include file="partial_pages/footer.jsp"%>
	<jsp:include page="/WEB-INF/jsp/partial_pages/registration-js.jsp"></jsp:include>
	<script src="js/header.js"></script>
</body>
</html>