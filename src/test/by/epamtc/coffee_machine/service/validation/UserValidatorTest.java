package by.epamtc.coffee_machine.service.validation;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.UserDAO;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.UserValidationError;

public class UserValidatorTest {
	private UserDAO userDAO;

	@Before
	public void initMock() {
		userDAO = Mockito.mock(UserDAO.class);
	}

	@Test
	public void testCheckEmailWithValidEmail() {
		String email = "x@y.z";
		Assert.assertTrue(UserValidator.checkEmail(email));
	}

	@Test
	public void testCheckEmailWithInvalidEmail() {
		String email = "x@yz";
		Assert.assertFalse(UserValidator.checkEmail(email));
	}

	@Test
	public void testCheckDublicatedEmailWithDublicatedEmail() throws DAOException, ServiceException {
		String email = "x@y.z";
		Mockito.when(userDAO.containsEmail(Mockito.any())).thenReturn(true);
		Assert.assertTrue(UserValidator.checkDublicatedEmail(userDAO, email));
	}

	@Test
	public void testCheckDublicatedEmailWithUniqueEmail() throws DAOException, ServiceException {
		String email = "x@y.z";
		Mockito.when(userDAO.containsEmail(Mockito.any())).thenReturn(false);
		Assert.assertFalse(UserValidator.checkDublicatedEmail(userDAO, email));
	}

	@Test
	public void testCheckDublicatedEmailWithNullEmail() throws DAOException, ServiceException {
		String email = null;
		Mockito.when(userDAO.containsEmail(Mockito.any())).thenReturn(false);
		Assert.assertFalse(UserValidator.checkDublicatedEmail(userDAO, email));
	}

	@Test(expected = ServiceException.class)
	public void testCheckDublicatedEmailWithDAOException() throws DAOException, ServiceException {
		String email = "";
		Mockito.when(userDAO.containsEmail(Mockito.any())).thenThrow(new DAOException());
		UserValidator.checkDublicatedEmail(userDAO, email);
	}

	@Test(expected = ServiceException.class)
	public void testCheckDublicatedEmailWithNullUserDAO() throws DAOException, ServiceException {
		String email = null;
		UserValidator.checkDublicatedEmail(null, email);
	}

	@Test
	public void testCheckUsernameWithValidUsername() {
		String username = "54ty21";
		Assert.assertTrue(UserValidator.checkUsername(username));
	}

	@Test
	public void testCheckUsernameWithTooShortUsername() {
		String username = "4ty2";
		Assert.assertFalse(UserValidator.checkUsername(username));
	}

	@Test
	public void testCheckUsernameWithNullUsername() {
		String username = null;
		Assert.assertFalse(UserValidator.checkUsername(username));
	}

	@Test
	public void testCheckDublicatedUsernameWithDublicatedUsername() throws DAOException, ServiceException {
		String username = "username";
		Mockito.when(userDAO.containsUsername(Mockito.any())).thenReturn(true);
		Assert.assertTrue(UserValidator.checkDublicatedUsername(userDAO, username));
	}

	@Test
	public void testCheckDublicatedUsernameWithUniqueUsername() throws DAOException, ServiceException {
		String username = "username";
		Mockito.when(userDAO.containsUsername(Mockito.any())).thenReturn(false);
		Assert.assertFalse(UserValidator.checkDublicatedUsername(userDAO, username));
	}

	@Test(expected = ServiceException.class)
	public void testCheckDublicatedUsernameWithDAOException() throws DAOException, ServiceException {
		String username = "username";
		Mockito.when(userDAO.containsUsername(Mockito.any())).thenThrow(new DAOException());
		UserValidator.checkDublicatedUsername(userDAO, username);
	}

	@Test(expected = ServiceException.class)
	public void testCheckDublicatedUsernameWithNullUserDAO() throws DAOException, ServiceException {
		String username = "username";
		UserValidator.checkDublicatedUsername(null, username);
	}

	@Test
	public void testCheckPasswordWithValidPassword() {
		String password = "Password1!";
		Assert.assertTrue(UserValidator.checkPassword(password));
	}

	@Test
	public void testCheckPasswordWithInvalidPassword() {
		String password = "pass";
		Assert.assertFalse(UserValidator.checkPassword(password));
	}

	@Test
	public void testCheckPasswordWithNull() {
		String password = null;
		Assert.assertFalse(UserValidator.checkPassword(password));
	}

	@Test
	public void testCheckRepeatPasswordWithEqualPasswords() {
		String password = "pass";
		String repeatPassword = "pass";
		Assert.assertTrue(UserValidator.checkRepeatPassword(password, repeatPassword));
	}

	@Test
	public void testCheckRepeatPasswordWithDifferentPasswords() {
		String password = "pass";
		String repeatPassword = "pass1";
		Assert.assertFalse(UserValidator.checkRepeatPassword(password, repeatPassword));
	}

	@Test
	public void testCheckRepeatPasswordWithNullFirstPassword() {
		String password = null;
		String repeatPassword = "pass1";
		Assert.assertFalse(UserValidator.checkRepeatPassword(password, repeatPassword));
	}

	@Test
	public void testCheckRepeatPasswordWithNullSecondPassword() {
		String password = "pass";
		String repeatPassword = null;
		Assert.assertFalse(UserValidator.checkRepeatPassword(password, repeatPassword));
	}

	@Test
	public void testCheckPhoneWithValidPhone() {
		String phone = "+375331234569";
		Assert.assertTrue(UserValidator.checkPhone(phone));
	}

	@Test
	public void testCheckPhoneWithTooShortPhone() {
		String phone = "331234569";
		Assert.assertFalse(UserValidator.checkPhone(phone));
	}

	@Test
	public void testCheckNameWithValidName() {
		String name = "Mia";
		Assert.assertTrue(UserValidator.checkName(name));
	}

	@Test
	public void testCheckNameWithEmptyName() {
		String name = "";
		Assert.assertFalse(UserValidator.checkName(name));
	}

	@Test
	public void testValidateFieldsWithIllegalPasswordAndDublicateUsername() throws DAOException, ServiceException {
		Set<UserValidationError> expected = new HashSet<>();
		expected.add(UserValidationError.ILLEGAL_PASSWORD);
		expected.add(UserValidationError.DUBLICATE_USERNAME);
		String email = "x@y.z";
		String password = "pass";
		String repeatPassword = "pass";
		String username = "user_name";
		String name = "name";
		String phone = "+375257865174";
		Mockito.when(userDAO.containsUsername(Mockito.any())).thenReturn(true);
		Set<UserValidationError> actual = UserValidator.validateFields(userDAO, email, password, repeatPassword,
				username, name, phone);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testValidateFieldsWithIllegalEmailAndNotEqualPasswordsAndIllegalPhone()
			throws DAOException, ServiceException {
		Set<UserValidationError> expected = new HashSet<>();
		expected.add(UserValidationError.ILLEGAL_EMAIL);
		expected.add(UserValidationError.PASSWORD_NOT_CONFIRMED);
		expected.add(UserValidationError.ILLEGAL_PHONE);
		String email = "x@yz";
		String password = "Password1!";
		String repeatPassword = "Password2!";
		String username = "user_name";
		String name = "name";
		String phone = "257865174";
		Mockito.when(userDAO.containsUsername(Mockito.any())).thenReturn(false);
		Set<UserValidationError> actual = UserValidator.validateFields(userDAO, email, password, repeatPassword,
				username, name, phone);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testValidateFieldsWithDublicatedEmailAndIllegalName() throws DAOException, ServiceException {
		Set<UserValidationError> expected = new HashSet<>();
		expected.add(UserValidationError.DUBLICATE_EMAIL);
		expected.add(UserValidationError.ILLEGAL_NAME);
		String email = "x@y.z";
		String password = "Password1!";
		String repeatPassword = "Password1!";
		String username = "user_name";
		String name = "";
		String phone = "+375257865174";
		Mockito.when(userDAO.containsEmail(Mockito.any())).thenReturn(true);
		Set<UserValidationError> actual = UserValidator.validateFields(userDAO, email, password, repeatPassword,
				username, name, phone);
		Assert.assertEquals(expected, actual);
	}

}
