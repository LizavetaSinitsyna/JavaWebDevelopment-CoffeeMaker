<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:forEach var="error" items="${errors}">
	<c:choose>
		<c:when test="${error == 'ILLEGAL_EMAIL'}">
			<c:set var="email_error" scope="request">
				<fmt:message key="local.registration.email_requirements" />
			</c:set>
		</c:when>
		<c:when test="${error == 'DUBLICATE_EMAIL'}">
			<c:set var="email_error" scope="request">
				<fmt:message key="local.user.error.dublicate_email" />
			</c:set>
		</c:when>
		<c:when test="${error == 'ILLEGAL_PASSWORD'}">
			<c:set var="password_error" scope="request">
				<fmt:message key="local.registration.password_requirements" />
			</c:set>
		</c:when>
		<c:when test="${error == 'PASSWORD_NOT_CONFIRMED'}">
			<c:set var="confirm_password_error" scope="request">
				<fmt:message key="local.user.error.password_not_confirmed" />
			</c:set>
		</c:when>
		<c:when test="${error == 'ILLEGAL_USERNAME'}">
			<c:set var="username_error" scope="request">
				<fmt:message key="local.registration.username_requirements" />
			</c:set>
		</c:when>
		<c:when test="${error == 'DUBLICATE_USERNAME'}">
			<c:set var="username_error" scope="request">
				<fmt:message key="local.user.error.dublicate_username" />
			</c:set>
		</c:when>
		<c:when test="${error == 'ILLEGAL_NAME'}">
			<c:set var="name_error" scope="request">
				<fmt:message key="local.registration.name_requirements" />
			</c:set>
		</c:when>
		<c:when test="${error == 'ILLEGAL_PHONE'}">
			<c:set var="phone_error" scope="request">
				<fmt:message key="local.registration.phone_requirements" />
			</c:set>
		</c:when>
	</c:choose>
</c:forEach>