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
	var="drinkId"
	value="${requestScope.drink.getId()}" />
<c:set
	var="drinkInfo"
	value="${requestScope.drink.getInfo()}" />
<c:set
	var="currentPage"
	value="/Controller?command=view_product_edit&drink_id=${drinkId}"
	scope="application" />
<jsp:include page="/WEB-INF/jsp/partial_pages/drink_edit_error_handler.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value="${drinkInfo.getName()}" /></title>
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
		<c:out value="${drinkInfo.getName()}" />
	</h3>
	<h5>
		<c:if test="${! empty successDrinkUpdate }">
			<span class="input-info success"><fmt:message key="local.product.fields.successful_update" /></span>
		</c:if>
	</h5>
	<p>
		<fmt:message key="local.edit.change_info_request" />
	</p>
	<p>
		<span class="star">* </span>
		<fmt:message key="local.required_field_info" />
	</p>
	<div>
		<form
			action="Controller"
			method="post">
			<input
				type="hidden"
				name="command"
				value="edit_product" />
			<input
				type="hidden"
				name="currentPage"
				value="${currentPage}" />
			<input
				type="hidden"
				name="drink_id"
				value="${drinkId}" />
			<c:if test="${! empty errors}">
				<div class="form-floating mb-3 flex-display error">
					<fmt:message key="local.user.error.incorrect_input" />
				</div>
			</c:if>
			<div class="form-floating mb-3 flex-display">
				<input
					type="text"
					name="imagePath"
					class="form-control width-50"
					id="imagePath"
					value="${drinkInfo.getImagePath()}"
					placeholder="/CoffeeMachine/images/image_name.png">
				<label for="imagePath">
					<fmt:message key="local.product.image" />
				</label>
				<span class="input-info error"><c:out value="${imageError}" /></span>
			</div>
			<div class="form-floating mb-3 flex-display">
				<input
					type="number"
					step="0.01"
					min="0.01"
					name="price"
					class="form-control width-50"
					id="price"
					placeholder="0.01"
					value="${drinkInfo.getPrice()}"
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
					placeholder="Drink description"><c:out value="${drinkInfo.getDescription()}" /></textarea>
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
				<c:if test="${! empty successIngredientsUpdate }">
					<span class="input-info success"><fmt:message
							key="local.product.ingredient.successful_update" /></span>
				</c:if>
			</h5>
			<div id="ingredientsList">
				<c:forEach
					var="ingredient"
					items="${drink_ingredients}"
					varStatus="ingredientNumber">
					<div class="flex-display">
						<div class="form-floating mb-3 auto-flex margin-05 width-45">
							<select
								name="ingredient"
								id="ingredients-${ingredientNumber.index}"
								class="form-control"
								required>
								<c:forEach
									var="sub_ingredient"
									items="${ingredients}">
									<c:choose>
										<c:when test="${sub_ingredient.getName() == ingredient.getIngredientName()}">
											<option
												value="${sub_ingredient.getId()}"
												selected>${sub_ingredient.getName()}</option>
										</c:when>
										<c:otherwise>
											<option value="${sub_ingredient.getId()}">${sub_ingredient.getName()}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
							<label for="ingredients-${ingredientNumber.index}">
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
								id="amount-${ingredientNumber.index}"
								placeholder="1"
								value="${ingredient.getIngredientAmount()}"
								required>
							<label for="amount-${ingredientNumber.index}">
								<fmt:message key="local.product.amount" />
								<span class="star"> *</span>
							</label>
						</div>
						<div class="form-floating mb-3 auto-flex margin-05">
							<select
								name="optional"
								id="optional-${ingredientNumber.index}"
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
							<label for="optional-${ingredientNumber.index}">
								<fmt:message key="local.product.ingredient.necessity" />
								<span class="star"> *</span>
							</label>
						</div>
						<div class="auto-flex margin-05">
							<c:if test="${ingredientNumber.index > 0}">
								<input
									type="button"
									class="btn btn-sm btn-outline-secondary"
									value="<fmt:message key='local.product.ingredient.remove' />"
									onclick="removeIngredient()" />
							</c:if>
						</div>
					</div>
				</c:forEach>
			</div>
			<input
				id="submit-edit"
				type="submit"
				class="btn btn-sm btn-outline-secondary"
				name="edit-form"
				value="<fmt:message
							key="local.edit.submit"/>" />
		</form>
	</div>
	<p>
		<fmt:message key="local.registration.login_offer" />
		<a href="/CoffeeMachine/login"><fmt:message key="local.header.login.name" /></a>
	</p>
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
					var="sub_ingredient"
					items="${ingredients}">
					<option value="${sub_ingredient.getId()}">${sub_ingredient.getName()}</option>
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
	<script src="js/header.js"></script>
	<script src="js/drink_edit.js"></script>
</body>
</html>