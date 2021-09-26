<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<header
	class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
	<a href="/"
		class="d-flex align-items-center col-md-3 mb-2 mb-md-0 text-dark text-decoration-none">
		<img class="logo me-2" src="images/coffee_maker.png" alt="CoffeeMaker">
	</a>

	<ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
		<li><a href="/CoffeeMachine/index.jsp"
			class="nav-link link-dark text-uppercase"><fmt:message
					key="local.header.home.name" /></a></li>
		<li><a href="#" class="nav-link link-dark text-uppercase"><fmt:message
					key="local.header.menu.name" /></a></li>
		<li><a href="#"
			class="nav-link link-dark text-uppercase center-align"><fmt:message
					key="local.header.bonus_program.name" /></a></li>
		<li><a href="#" class="nav-link link-dark text-uppercase"><fmt:message
					key="local.header.about.name" /></a></li>

		<li><c:choose>
				<c:when test="${cookie_lang == 'EN'}">
					<c:set var="btn_lang">
						<fmt:message key="local.header.langbtn.name.en" />
					</c:set>
					<c:set var="sub_btn_lang">
						<fmt:message key="local.header.langbtn.name.ru" />
					</c:set>
				</c:when>
				<c:when test="${cookie_lang == 'RU'}">
					<c:set var="btn_lang">
						<fmt:message key="local.header.langbtn.name.ru" />
					</c:set>
					<c:set var="sub_btn_lang">
						<fmt:message key="local.header.langbtn.name.en" />
					</c:set>
				</c:when>
				<c:otherwise>
					<c:set var="btn_lang">
						<fmt:message key="local.header.langbtn.name.en" />
					</c:set>
					<c:set var="sub_btn_lang">
						<fmt:message key="local.header.langbtn.name.ru" />
					</c:set>
				</c:otherwise>
			</c:choose> <input id="language-change" type="button" class="btn text-uppercase"
			value="${btn_lang}" onclick="showDropdownContent()" />
			<div id="dropdown-content" class="dropdown-content text-uppercase">
				<form action="Controller">
					<input type="hidden" name="command" value="change_language">
					<input type="hidden" name="currentPage" value="${currentPage}">
					<input id="lang1" type="submit" class="btn text-uppercase"
						name="new_lang" value="${sub_btn_lang}" />
				</form>
			</div></li>
	</ul>

	<ul class="nav col-12 col-md-auto mb-2 mb-md-0 text-end">
		<li id="basket-area"><a href="#" class="basket basket-image">&nbsp;</a><a
			href="#" class="nav-link basket-float-right link-dark"><fmt:message
					key="local.header.basket.name" /></a></li>
		<li class="header-link-border header-link-border-stat"><a
			href="#" class="nav-link link-dark"><fmt:message
					key="local.header.login.name" /></a></li>
		<li class="header-link-border header-link-border-stat"><a
			href="/CoffeeMachine/registration.jsp" class="nav-link link-dark"><fmt:message
					key="local.header.sign_up.name" /></a></li>
	</ul>
</header>
