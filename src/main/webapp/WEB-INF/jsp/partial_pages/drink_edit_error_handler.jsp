<%@ taglib
	prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib
	prefix="fmt"
	uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:forEach
	var="error"
	items="${errors}">
	<c:choose>
		<c:when test="${error == 'ILLEGAL_IMAGE_PATH'}">
			<c:set
				var="imageError"
				scope="request">
				<fmt:message key="local.product.image_requirements" />
			</c:set>
		</c:when>
		<c:when test="${error == 'ILLEGAL_PRICE'}">
			<c:set
				var="priceError"
				scope="request">
				<fmt:message key="local.product.price_requirements" />
			</c:set>
		</c:when>
		<c:when test="${error == 'ILLEGAL_DESCRIPTION'}">
			<c:set
				var="descriptionError"
				scope="request">
				<fmt:message key="local.registration.username_requirements" />
			</c:set>
		</c:when>
	</c:choose>
</c:forEach>
<c:forEach
	var="ingredientError"
	items="${ingredientErrors}">
	<c:choose>
		<c:when test="${ingredientError == 'ILLEGAL_DRINK_INGREDIENT_AMOUNT'}">
			<c:set
				var="ingredientsMessage"
				scope="request">
				<fmt:message key="local.product.ingredients.amount.error" />
			</c:set>
		</c:when>
		<c:when test="${ingredientError == 'ILLEGAL_INGREDIENT_AMOUNT'}">
			<c:set
				var="ingredientsMessage"
				scope="request">
				<fmt:message key="local.product.ingredient.amount.error" />
			</c:set>
		</c:when>
		<c:when test="${ingredientError == 'DUPLICATE_INGREDIENT'}">
			<c:set
				var="ingredientsMessage"
				scope="request">
				<fmt:message key="local.product.ingredient.duplicate.error" />
			</c:set>
		</c:when>
	</c:choose>
</c:forEach>