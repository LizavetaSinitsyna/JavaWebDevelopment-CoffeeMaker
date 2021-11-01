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
	value="/Controller?command=view_product_add"
	scope="application" />
<jsp:include page="/WEB-INF/jsp/partial_pages/drink_edit_error_handler.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><fmt:message key="local.add.header" /></title>
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
		<fmt:message key="local.add.header" />
	</h3>
	<p>
		<fmt:message key="local.info_request" />
	</p>
	<p>
		<span class="star">* </span>
		<fmt:message key="local.required_field_info" />
	</p>
	<div>
		<form
			action="/CoffeeMachine/Controller"
			method="post"
			enctype="multipart/form-data">
			<input
				type="hidden"
				name="command"
				value="add_product" />
			<input
				type="hidden"
				name="currentPage"
				value="${currentPage}" />
			<c:if test="${! empty errors}">
				<div class="form-floating mb-3 flex-display error">
					<fmt:message key="local.user.error.incorrect_input" />
				</div>
			</c:if>
			<div class="form-floating mb-3">
				<p class="underline">
					<fmt:message key="local.product.image" />
				</p>
				<input
					type="file"
					name="image"
					id="image"
					accept="image/png, image/jpeg">
				<span class="input-info error"><c:out value="${imageError}" /></span>
			</div>
			<div class="form-floating mb-3 flex-display">
				<input
					type="text"
					name="name"
					class="form-control width-50"
					id="name"
					placeholder="CoffeeName"
					value="${requestScope.name}"
					required>
				<label for="name">
					<fmt:message key="local.product.name" />
					<span class="star"> *</span>
				</label>
				<span class="input-info error"><c:out value="${nameError}" /></span>
			</div>
			<div class="form-floating mb-3 flex-display">
				<input
					type="number"
					step="0.01"
					min="0.01"
					max="100.00"
					name="price"
					class="form-control width-50"
					id="price"
					placeholder="0.01"
					value="${requestScope.price}"
					title="<fmt:message key="local.product.price_requirements" />"
					required>
				<label for="name">
					<fmt:message key="local.product.price" />
					<span class="star"> *</span>
				</label>
				<span class="input-info error"><c:out value="${priceError}" /></span>
			</div>
			<div class="form-floating mb-3 flex-display">
				<textarea
					name="description"
					class="form-control width-50"
					id="description"
					placeholder="Drink description"><c:out value="${requestScope.description}" /></textarea>
				<label for="description">
					<fmt:message key="local.product.description.header" />
				</label>
				<span class="input-info error"><c:out value="${descriptionError}" /></span>
			</div>
			<h5>
				<fmt:message key="local.product.ingredients" />
				<input
					id="add"
					type="button"
					class="btn btn-sm btn-outline-secondary"
					name="add-ingredient"
					value="<fmt:message key='local.product.ingredient.add' />" />
				<span class="input-info error"><c:out value="${ingredientsMessage}" /></span>
			</h5>
			<div id="ingredientsList">
				<div class="flex-display">
					<div class="form-floating mb-3 auto-flex margin-05 width-45">
						<select
							name="ingredient"
							id="ingredients-1"
							class="form-control"
							required>
							<c:forEach
								var="ingredient"
								items="${ingredients}">
								<option value="${ingredient.getId()}">${ingredient.getName()}</option>
							</c:forEach>
						</select>
						<label for="ingredients-1">
							<fmt:message key="local.product.ingredient" />
							<span class="star"> *</span>
						</label>
					</div>
					<div class="form-floating mb-3 auto-flex margin-05">
						<input
							type="number"
							name="ingredientAmount"
							min="1"
							class="form-control"
							id="amount-1"
							placeholder="1"
							required>
						<label for="amount-1}">
							<fmt:message key="local.product.amount" />
							<span class="star"> *</span>
						</label>
					</div>
					<div class="form-floating mb-3 auto-flex margin-05">
						<select
							name="optional"
							id="optional-1"
							class="form-control"
							required>
							<option
								value="false"
								selected><fmt:message key="local.product.ingredient.mandatory" /></option>
							<option value="true"><fmt:message key="local.product.ingredient.optional" /></option>
						</select>
						<label for="optional-1">
							<fmt:message key="local.product.ingredient.necessity" />
							<span class="star"> *</span>
						</label>
					</div>
				</div>
			</div>
			<input
				id="submit-add"
				type="submit"
				class="btn btn-sm btn-outline-secondary change-btn"
				name="edit-form"
				value="<fmt:message
							key="local.add.btn"/>" />
		</form>
	</div>
	<div
		id="copy"
		class="hide">
		<div class="form-floating mb-3 auto-flex margin-05 width-45">
			<select
				name="ingredient"
				id="ingr0"
				class="form-control"
				required>
				<c:forEach
					var="ingredient"
					items="${ingredients}">
					<option value="${ingredient.getId()}">${ingredient.getName()}</option>
				</c:forEach>
			</select>
			<label for="ingr0">
				<fmt:message key="local.product.ingredient" />
				<span class="star"> *</span>
			</label>
		</div>
		<div class="form-floating mb-3 auto-flex margin-05">
			<input
				type="number"
				name="ingredientAmount"
				min="1"
				class="form-control"
				id="amount0"
				placeholder="1"
				required>
			<label for="amount0">
				<fmt:message key="local.product.amount" />
				<span class="star"> *</span>
			</label>
		</div>
		<div class="form-floating mb-3 auto-flex margin-05">
			<select
				name="optional"
				id="option0"
				class="form-control"
				required>
				<c:choose>
					<c:when test="${ingredient.isOptional()}">
						<option
							value="true"
							selected><fmt:message key="local.product.ingredient.optional" /></option>
						<option value="false"><fmt:message key="local.product.ingredient.mandatory" /></option>
					</c:when>
					<c:otherwise>
						<option
							value="false"
							selected><fmt:message key="local.product.ingredient.mandatory" /></option>
						<option value="true"><fmt:message key="local.product.ingredient.optional" /></option>
					</c:otherwise>
				</c:choose>
			</select>
			<label for="option0">
				<fmt:message key="local.product.ingredient.necessity" />
				<span class="star"> *</span>
			</label>
		</div>
		<div class="auto-flex margin-05">
			<input
				type="button"
				class="btn btn-sm btn-outline-secondary"
				value="<fmt:message key='local.product.ingredient.remove' />"
				onclick="removeIngredient()" />
		</div>
	</div>
	<%@include file="partial_pages/footer.jsp"%>
	<script src="/CoffeeMachine/js/header.js"></script>
	<script src="/CoffeeMachine/js/drink_edit.js"></script>
</body>
</html>