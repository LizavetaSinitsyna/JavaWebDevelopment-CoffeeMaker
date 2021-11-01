<%@ taglib
	prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib
	prefix="fmt"
	uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="album half-padding">
	<div class="container">
		<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
			<c:forEach
				var="drink"
				items="${menu}">
				<c:set
					var="drinkId"
					value="${drink.getId()}" />
				<c:set
					var="image"
					value="${drink.getImagePath()}" />
				<c:set
					var="name"
					value="${drink.getName()}" />
				<c:set
					var="price"
					value="${drink.getPrice()}" />
				<div class="col">
					<div class="card shadow-sm">
						<c:if test="${sessionScope.user != null && sessionScope.user.getRoleId() == 1}">
							<a
								class="edit icon icon-image"
								href="/CoffeeMachine/Controller?command=view_product_edit&drink_id=${drinkId}"></a>
							<a
								class="delete icon icon-image icon-right-corner"
								href=""></a>
						</c:if>
						<img
							class="drink-menu"
							alt="${drink.getName()}"
							src="${image}">
						<div class="card-body">
							<p class="card-text">
								<c:out value="${name}" />
								<span class="float-right"><fmt:formatNumber
										type="number"
										minFractionDigits="2"
										value="${price}" /> <fmt:message key="local.menu.price.currency" /></span>
							</p>
							<div class="d-flex justify-content-between align-items-center">
								<div class="btn-group">
									<form action="Controller">
										<input
											type="hidden"
											name="command"
											value="view_product">
										<input
											type="hidden"
											name="drink_id"
											value="${drinkId}">
										<button
											type="submit"
											class="btn btn-sm btn-outline-secondary">
											<fmt:message key="local.menu.view" />
										</button>
									</form>
									<div>
										<input
											type="hidden"
											name="image"
											value="${image}">
										<input
											type="hidden"
											name="drink_id"
											value="${drinkId}">
										<input
											type="hidden"
											name="name"
											value="${name}">
										<c:set
											var="intValue"
											value="${price.intValue()}" />
										<input
											type="hidden"
											name="priceInt"
											value="${intValue}">
										<input
											type="hidden"
											name="priceFractional"
											value="${((price - intValue) * 100).intValue()}">
										<input
											type="button"
											class="btn btn-sm btn-outline-secondary"
											value="<fmt:message key="local.menu.add_to_basket" />"
											onclick="addToBasket(); increaseBasket()" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>