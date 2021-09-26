<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
const password = document.getElementById('password');
const repeat_password = document.getElementById('repeatPassword');

function check_password() {
	if(password.value == repeat_password.value) {
		document.getElementById('submit-registration').disabled = false;
		document.getElementById('check_pass_message').innerHTML = '';
		
	} else {
		document.getElementById('submit-registration').disabled = true;
		document.getElementById('check_pass_message').innerHTML = '<fmt:message key="local.user.error.password_not_confirmed"/>';
	}
}
password.onkeyup = check_password;
repeat_password.onkeyup = check_password;
</script>