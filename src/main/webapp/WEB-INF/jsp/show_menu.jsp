<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="album half-padding">
	<div class="container">
		<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
			<c:forEach var="drink" items="${menu}">
				<div class="col">
					<div class="card shadow-sm">
						<c:if test="${sessionScope.user != null && sessionScope.user.getRoleName().equals('Admin')}"><a class="edit icon icon-image" href=""></a> <a
							class="delete icon icon-image icon-right-corner" href=""></a></c:if>
						<img alt="" src="${drink.getImagePath()}">
						<div class="card-body">
							<p class="card-text">
								<c:out value="${drink.getName()}" />
								<span class="float-right"><fmt:formatNumber type="number"
										minFractionDigits="2" value="${drink.getPrice()}" /> <fmt:message
										key="local.menu.price.currency" /></span>
							</p>
							<div class="d-flex justify-content-between align-items-center">
								<div class="btn-group">
									<form>
										<input type="hidden" name="command" value="view_product">
										<input type="hidden" name="drink_id" value="${drink.getId()}">
										<button type="submit" class="btn btn-sm btn-outline-secondary">
											<fmt:message key="local.menu.view" />
										</button>
									</form>
									<button type="button" class="btn btn-sm btn-outline-secondary">
										<fmt:message key="local.menu.add_to_basket" />
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>