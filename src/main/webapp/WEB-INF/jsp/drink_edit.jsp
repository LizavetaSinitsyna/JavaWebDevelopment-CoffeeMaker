<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="cookie_lang" value="${cookie['lang'].value}" scope="application" />
<fmt:setLocale value="${ empty cookie_lang ? 'EN' : cookie_lang}" scope="application" />
<fmt:setBundle basename="localization.local" scope="application" />
<c:set var="drinkId" value="${requestScope.drink.getId()}" />
<c:set var="drinkInfo" value="${requestScope.drink.getInfo()}" />
<c:set var="currentPage"
	value="/CoffeeMachine/Controller?command=view_product_edit&drink_id=${drinkId}" scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value="${drinkInfo.getName()}" /></title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/site.css" rel="stylesheet">
<link href="images/favicon.ico" rel="shortcut icon" type="image/x-icon">
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/partial_pages/header.jsp"></jsp:include>

	<h3 class="center-align">
		<c:out value="${drinkInfo.getName()}" />
	</h3>
	<p>
		<fmt:message key="local.edit.change_info_request" />
	</p>
	<p>
		<span class="star">* </span>
		<fmt:message key="local.required_field_info" />
	</p>
	<div>
		<div class="bd-example">
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="view_product_edit" />
				<c:if test="${! empty errors}">
					<div class="form-floating mb-3 flex-display error">
						<fmt:message key="local.user.error.incorrect_input" />
					</div>
				</c:if>
				<div class="form-floating mb-3 flex-display">
					<input type="text" name="imagePath" class="form-control" id="imagePath"
						value="${drinkInfo.getImagePath()}" placeholder="/CoffeeMachine/images/image_name.png"
						required> <label for="imagePath"><fmt:message key="local.product.image" /><span
						class="star"> *</span> </label><span class="input-info error"><c:out value="${image_error}" /></span>
				</div>
				<div class="form-floating mb-3 flex-display">
					<input type="text" name="name" class="form-control" id="name" placeholder="CoffeeName"
						value="${drinkInfo.getName()}" required> <label for="name"><fmt:message
							key="local.product.name" /><span class="star"> *</span> </label> <span class="input-info error"><c:out
							value="${name_error}" /></span>
				</div>
				<div class="form-floating mb-3 flex-display">
					<input type="number" step="0.01" min="0.01" name="price" class="form-control" id="price"
						placeholder="0.01" value="${drinkInfo.getPrice()}" required> <label for="name"><fmt:message
							key="local.product.price" /><span class="star"> *</span></label><span class="input-info error"><c:out
							value="${price_error}" /></span>
				</div>
				<div class="form-floating mb-3 flex-display">
					<textarea name="description" class="form-control" id="description"
						placeholder="Drink description"><c:out value="${drinkInfo.getDescription()}" /></textarea>
					<label for="description"><fmt:message key="local.product.description.header" /></label>
				</div>
				<p>
					<fmt:message key="local.product.ingredients" />
				</p>
				<c:forEach var="ingredient" items="${ingredients}">
					<div class="form-floating mb-3 flex-display">
						<label><input type="text" name="ingredient" class="form-control" placeholder="xxx"
							value="${ingredient.getIngredientName()}" required> <fmt:message
								key="local.product.ingredient" /><span class="star"> *</span></label> <label><input
							type="number" name="ingredientAmount" min="1" class="form-control" placeholder="1"
							value="${ingredient.getIngredientAmount()}" required> <fmt:message
								key="local.product.ingredient.amount" /><span class="star"> *</span></label>
						<c:choose>
							<c:when test="${ingredient.isOptional() ==}">
								<input type="checkbox" name="<fmt:message key="local.product.ingredient.optional" />"
									class="form-control" placeholder="x" required checked>
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="<fmt:message key="local.product.ingredient.optional" />"
									class="form-control" placeholder="x" required>
							</c:otherwise>
						</c:choose>
						<span class="input-info error"><c:out value="${ingredient_error}" /></span>
					</div>
				</c:forEach>
				<input id="submit-edit" type="submit" class="btn btn-sm btn-outline-secondary" name="edit-form"
					value="<fmt:message
							key="local.edit.submit"/>" disabled />
			</form>
		</div>
	</div>
	<p>
		<fmt:message key="local.registration.login_offer" />
		<a href="/CoffeeMachine/login"><fmt:message key="local.header.login.name" /></a>
	</p>
	<%@include file="partial_pages/footer.jsp"%>
	<script src="js/header.js"></script>
</body>
</html>