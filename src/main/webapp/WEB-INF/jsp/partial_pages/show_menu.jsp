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
							alt=""
							src="${drink.getImagePath()}">
						<div class="card-body">
							<p class="card-text">
								<c:out value="${drink.getName()}" />
								<span class="float-right"><fmt:formatNumber
										type="number"
										minFractionDigits="2"
										value="${drink.getPrice()}" /> <fmt:message key="local.menu.price.currency" /></span>
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
									<form action="Controller">
										<input
											type="hidden"
											name="command"
											value="add_to_basket">
										<input
											type="hidden"
											name="drink_id"
											value="${drinkId}">
										<input
											type="hidden"
											name="currentPage"
											value="${currentPage}">
										<button
											type="submit"
											class="btn btn-sm btn-outline-secondary">
											<fmt:message key="local.menu.add_to_basket" />
										</button>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>