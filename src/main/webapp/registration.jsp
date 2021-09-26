<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="cookie_lang" value="${cookie['lang'].value}"
	scope="application" />
<fmt:setLocale value="${cookie_lang == null ? 'EN' : cookie_lang}"
	scope="application" />
<fmt:setBundle basename="localization.local" scope="application" />
<c:set var="currentPage" value="/CoffeeMachine/registration.jsp"
	scope="application" />
<c:if test="${! empty registration_errors}">
	<c:forEach var="error" items="${registration_errors}">
		<c:choose>
			<c:when test="${error == 'EMPTY_EMAIL'}">
				<c:set var="email_error">
					<fmt:message key="local.user.error.empty_field" />
				</c:set>
			</c:when>
			<c:when test="${error == 'ILLEGAL_EMAIL'}">
				<c:set var="email_error">
					<fmt:message key="local.user.error.illegal_email" />
				</c:set>
			</c:when>
			<c:when test="${error == 'DUBLICATE_EMAIL'}">
				<c:set var="email_error">
					<fmt:message key="local.user.error.dublicate_email" />
				</c:set>
			</c:when>
			<c:when test="${error == 'EMPTY_PASSWORD'}">
				<c:set var="password_error">
					<fmt:message key="local.user.error.empty_field" />
				</c:set>
			</c:when>
			<c:when test="${error == 'EMPTY_REPEAT_PASSWORD'}">
				<c:set var="confirm_password_error">
					<fmt:message key="local.user.error.empty_field" />
				</c:set>
			</c:when>
			<c:when test="${error == 'ILLEGAL_PASSWORD'}">
				<c:set var="password_error">
					<fmt:message key="local.user.error.illegal_password" />
				</c:set>
			</c:when>
			<c:when test="${error == 'PASSWORD_NOT_CONFIRMED'}">
				<c:set var="confirm_password_error">
					<fmt:message key="local.user.error.password_not_confirmed" />
				</c:set>
			</c:when>
			<c:when test="${error == 'EMPTY_USERNAME'}">
				<c:set var="username_error">
					<fmt:message key="local.user.error.empty_field" />
				</c:set>
			</c:when>
			<c:when test="${error == 'ILLEGAL_USERNAME'}">
				<c:set var="username_error">
					<fmt:message key="local.user.error.illegal_username" />
				</c:set>
			</c:when>
			<c:when test="${error == 'DUBLICATE_USERNAME'}">
				<c:set var="username_error">
					<fmt:message key="local.user.error.dublicate_username" />
				</c:set>
			</c:when>
			<c:when test="${error == 'EMPTY_NAME'}">
				<c:set var="name_error">
					<fmt:message key="local.user.error.empty_field" />
				</c:set>
			</c:when>
			<c:when test="${error == 'EMPTY_PHONE'}">
				<c:set var="phone_error">
					<fmt:message key="local.user.error.empty_field" />
				</c:set>
			</c:when>
			<c:when test="${error == 'ILLEGAL_PHONE'}">
				<c:set var="phone_error">
					<fmt:message key="local.user.error.illegal_phone" />
				</c:set>
			</c:when>
		</c:choose>
	</c:forEach>
	<c:remove var="registration_errors" scope="session" />
</c:if>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><fmt:message key="local.registration.header" /></title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/site.css" rel="stylesheet">
<link href="images/favicon.ico" rel="shortcut icon" type="image/x-icon">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<h1>
		<fmt:message key="local.registration.header" />
	</h1>
	<p>
		<fmt:message key="local.registration.info_request" />
	</p>
	<p>
		<span class="star">* </span>
		<fmt:message key="local.registration.required_field_info" />
	</p>
	<div>
		<div class="bd-example">
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="registration" />
				<div class="form-floating mb-3 flex-display">
					<input type="email" name="email" class="form-control"
						id="floatingInput" value="${email}"
						placeholder="coffee@example.com" required> <label
						for="floatingInput"><fmt:message
							key="local.registration.email" /><span class="star"> *</span> </label> <span
						class="error-margin">${email_error}</span>
				</div>
				<div class="form-floating mb-3 flex-display">
					<input type="text" name="login" class="form-control" id="Username"
						placeholder="CoffeeName" value="${login}" required> <label
						for="Username"><fmt:message
							key="local.registration.username" /><span class="star"> *</span>
					</label><span class="error-margin"><c:out value="${username_error}" /></span>
				</div>
				<div class="form-floating mb-3 flex-display">
					<input type="text" name="name" class="form-control" id="name"
						placeholder="Latte" value="${name}" required> <label
						for="name"><fmt:message key="local.registration.name" /><span
						class="star"> *</span></label><span class="error-margin"><c:out
							value="${name_error}" /></span>
				</div>
				<div class="form-floating mb-3 flex-display">
					<input type="text" name="phone" class="form-control" id="phone"
						placeholder="+375(XX)XXX-XX-XX" value="${phone}" required>
					<label for="phone"><fmt:message
							key="local.registration.phone" /><span class="star"> *</span></label><span
						class="error-margin"><c:out value="${phone_error}" /></span>
				</div>
				<div class="form-floating mb-3 flex-display">
					<input type="password" name="password" class="form-control"
						id="password" placeholder="xxx" required> <label
						for="floatingPassword"><fmt:message
							key="local.registration.password" /><span class="star"> *</span></label><span
						class="error-margin">${password_error}</span>
				</div>
				<div class="form-floating mb-3 flex-display">
					<input type="password" name="repeat-password" class="form-control"
						id="repeatPassword" placeholder="xxx" required> <label
						for="repeatFloatingPassword"><fmt:message
							key="local.registration.repeat_password" /><span class="star">
							*</span></label><span id="check_pass_message" class="error-margin"></span><span
						class="error-margin">${confirm_password_error}</span>
				</div>
				<input id="submit-registration" type="submit" class="btn"
					name="registration-form"
					value="<fmt:message
							key="local.registration.sign_up"/>"
					disabled />
			</form>
		</div>
	</div>
	<p>
		<fmt:message key="local.registration.login_offer" />
		<a href="#"><fmt:message key="local.header.login.name" /></a>
	</p>
	<jsp:include page="footer.jsp"></jsp:include>
	<jsp:include page="registration-js.jsp"></jsp:include>
	<script src="js/header.js"></script>
</body>
</html>