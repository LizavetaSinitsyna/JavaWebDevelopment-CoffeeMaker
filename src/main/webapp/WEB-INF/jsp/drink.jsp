<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="cookie_lang" value="${cookie['lang'].value}"
	scope="application" />
<fmt:setLocale value="${ empty cookie_lang ? 'EN' : cookie_lang}"
	scope="application" />
<fmt:setBundle basename="localization.local" scope="application" />
<c:set var="drink" value="${requestScope.drink}" />
<c:set var="drinkId" value="${drink.getId()}" />
<c:set var="drinkInfo" value="${drink.getInfo()}" />
<c:set var="currentPage"
	value="/CoffeeMachine/Controller?command=view_product&drink_id=${drinkId}"
	scope="application" />
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
	<div class="album half-padding">
		<div class="container">
			<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
				<div class="col">
					<div class="card shadow-sm">
						<c:if
							test="${sessionScope.user != null && sessionScope.user.getRoleName().equals('Admin')}">
							<a class="edit icon icon-image"
								href="/CoffeeMachine/Controller?command=view_product_edit&drink=${drink}"></a>
							<a class="delete icon icon-image icon-right-corner" href=""></a>
						</c:if>
						<img alt="" src="${drinkInfo.getImagePath()}">
						<div class="card-body">
							<p class="card-text">
								<fmt:formatNumber type="number" minFractionDigits="2"
									value="${drinkInfo.getPrice()}" />
								<fmt:message key="local.menu.price.currency" />
							</p>
							<div class="d-flex justify-content-between align-items-center">
								<div class="btn-group">
									<button type="button" class="btn btn-sm btn-outline-secondary">
										<fmt:message key="local.menu.add_to_basket" />
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col description">
					<div class="card description-row">
						<p>
							<fmt:message key="local.product.description.header" />
						</p>
						<p>
							<c:out value="${drinkInfo.getDescription()}" />
						</p>
					</div>
					<div class="card description-row">
						<p>
							<span><fmt:message key="local.product.feedback.header" /></span>
							<span class="rating float-right"> <input type="radio"
								name="rating" value="5" id="5"><label for="5">☆</label>
								<input type="radio" name="rating" value="4" id="4"><label
								for="4">☆</label> <input type="radio" name="rating" value="3"
								id="3"><label for="3">☆</label> <input type="radio"
								name="rating" value="2" id="2"><label for="2">☆</label>
								<input type="radio" name="rating" value="1" id="1"><label
								for="1">☆</label>
							</span>
						</p>
						<p>feedback</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="partial_pages/footer.jsp"%>
	<script src="js/bootstrap.bundle.min.js"></script>
	<script src="js/header.js"></script>
</body>
</html>