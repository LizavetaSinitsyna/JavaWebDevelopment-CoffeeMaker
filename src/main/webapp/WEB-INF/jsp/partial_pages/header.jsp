<%@ taglib
	prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib
	prefix="fmt"
	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib
	uri="http://java.sun.com/jsp/jstl/functions"
	prefix="fn"%>
<c:set
	var="basket"
	value="${cookie['basket'].value}"
	scope="application" />
<header
	class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between mb-4 border-bottom">
	<a
		href="/CoffeeMachine/index.jsp"
		class="d-flex align-items-center col-md-3 mb-2 mb-md-0 text-dark text-decoration-none"> <img
		class="logo me-2"
		src="images/coffee_maker.png"
		alt="CoffeeMaker">
	</a>
	<ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
		<li><a
			href="/CoffeeMachine/index.jsp"
			class="nav-link link-dark text-uppercase"><fmt:message key="local.header.home.name" /></a></li>
		<li><form action="Controller">
				<input
					type="hidden"
					name="command"
					value="view_menu">
				<input
					id="menu"
					type="submit"
					class="btn text-uppercase empty-shadow"
					name="menu"
					value="<fmt:message
					key="local.header.menu.name" />" />
			</form></li>
		<li><a
			href="#"
			class="nav-link link-dark text-uppercase center-align"><fmt:message
					key="local.header.bonus_program.name" /></a></li>
		<li><a
			href="#"
			class="nav-link link-dark text-uppercase"><fmt:message key="local.header.about.name" /></a></li>
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
			</c:choose> <input
				id="language-change"
				type="button"
				class="btn text-uppercase"
				value="${btn_lang}" />
			<div
				id="dropdown-content"
				class="dropdown-content text-uppercase">
				<form action="Controller">
					<input
						type="hidden"
						name="command"
						value="change_language">
					<input
						type="hidden"
						name="currentPage"
						value="${currentPage}">
					<input
						id="lang1"
						type="submit"
						class="btn text-uppercase"
						name="new_lang"
						value="${sub_btn_lang}" />
				</form>
			</div></li>
	</ul>
	<ul class="nav col-12 col-md-auto mb-2 mb-md-0 text-end">
		<li><form action="basket">
				<input
					type="hidden"
					name="nextPage"
					value="/basket">
				<button
					id="basket-area"
					type="submit"
					class="btn empty-shadow revert-padding"
					style="text-align: -webkit-center;">
					<div class="basket icon-image"></div>
					<fmt:message key="local.header.basket.name" />
					(<span id="basketSize">0</span>)
				</button>
			</form></li>
		<c:choose>
			<c:when test="${user != null}">
				<li class="header-link-border header-link-border-stat">
					<form action="Controller">
						<input
							type="hidden"
							name="command"
							value="log_out">
						<input
							type="hidden"
							name="currentPage"
							value="${currentPage}">
						<input
							id="log_out"
							type="submit"
							class="btn empty-shadow"
							name="log_out"
							value="<fmt:message
							key="local.header.log_out.name"/>" />
					</form>
				</li>
				<li><button
						id="log_out"
						type="submit"
						class="btn"
						name="log_out"
						style="text-align: -webkit-center;">
						<img
							src="/CoffeeMachine/images/user_icon.png"
							style="display: block;">
						<fmt:message key="local.header.user.profile" />
					</button></li>
			</c:when>
			<c:otherwise>
				<li class="header-link-border header-link-border-stat"><a
					href="/CoffeeMachine/login"
					class="nav-link link-dark"><fmt:message key="local.header.login.name" /></a></li>
				<li class="header-link-border header-link-border-stat"><a
					href="/CoffeeMachine/registration"
					class="nav-link link-dark"><fmt:message key="local.header.sign_up.name" /></a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</header>
