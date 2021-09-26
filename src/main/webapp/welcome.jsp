<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="cookie_lang" value="${cookie['lang'].value}" scope="application" />
<fmt:setLocale value="${cookie_lang == null ? 'EN' : cookie_lang}" scope="application" />
<fmt:setBundle basename="localization.local" scope="application" />
<c:set var="currentPage" value="/CoffeeMachine/index.jsp" scope="application"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CoffeeMaker</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/site.css" rel="stylesheet">
<link href="images/favicon.ico" rel="shortcut icon" type="image/x-icon">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<c:if test="${!empty popular_drinks}">
		<h1>The most popular drinks</h1>
		<div class="album py-5">
			<div class="container">
				<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
					<c:forEach var="drink" items="${popular_drinks}">
						<div class="col">
							<div class="card shadow-sm">
								<img alt="" src="${drink.getInfo().getImagePath()}">
								<div class="card-body">
									<p class="card-text">
										<c:out value="${drink.getInfo().getName()}" />
									</p>
									<div class="d-flex justify-content-between align-items-center">
										<div class="btn-group">
											<button type="button"
												class="btn btn-sm btn-outline-secondary">View</button>
											<button type="button"
												class="btn btn-sm btn-outline-secondary">Add to
												basket</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</c:if>
	<jsp:include page="footer.jsp"></jsp:include>
	<script src="js/bootstrap.bundle.min.js"></script>
	<script src="js/header.js"></script>
</body>
</html>