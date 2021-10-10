<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>
	const email = document.getElementById('email');
	const username = document.getElementById('username');
	const name = document.getElementById('name');
	const phone = document.getElementById('phone');
	const password = document.getElementById('password');
	const repeatPassword = document.getElementById('repeatPassword');

	function checkPassword() {
		var message = document.getElementById('check-pass-message');
		if (password.value == repeatPassword.value) {
			message.innerHTML = '';
			message.parentNode.getElementsByTagName('input')[0].classList.remove("error-input");
			document.getElementById('submit-registration').disabled = false;

		} else {
			message.innerHTML = '<fmt:message key="local.user.error.password_not_confirmed"/>';
			message.parentNode.getElementsByTagName('INPUT')[0].classList.add("error-input");
			document.getElementById('submit-registration').disabled = true;
		}
	}
	
	function removeErrorClass(event) {
		event.target.classList.remove("error-input");
	}
	
	email.onkeyup = removeErrorClass;
	username.onkeyup = removeErrorClass;
	name.onkeyup = removeErrorClass;
	phone.onkeyup = removeErrorClass;
	
	password.addEventListener('keyup', checkPassword);
	password.addEventListener('keyup', removeErrorClass);
	repeatPassword.addEventListener('keyup', checkPassword);
	
</script>
<script>
	(function markErrorFields() {
		var errorFields = document.getElementsByClassName('error');
		for (var i = 0; i < errorFields.length; i++) {
			if (errorFields[i].innerHTML != '') {
				var errorFieldParent = errorFields[i].parentNode;
				var input = errorFieldParent.getElementsByTagName('INPUT')[0];
				input.classList.add("error-input");
			}
		}
	})();
</script>