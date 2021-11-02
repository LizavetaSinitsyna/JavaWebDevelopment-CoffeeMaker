package by.epamtc.coffee_machine.service.validation;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.UserDAO;
import by.epamtc.coffee_machine.service.CommonExceptionMessage;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.UserValidationError;

/**
 * Provides support for User validation.
 *
 */
public class UserValidator {

	private static final String EMAIL_REGEX = "^(?=[^@\\s]+@[^@\\s]+\\.[^@\\s]+).{5,255}$";
	private static final String PASSWORD_REGEX = "^(?=.*?[A-ZА-Я])(?=.*?[a-zа-я])(?=.*?[0-9])(?=.*?[ !\\\"#\\$%&'\\(\\)\\*\\+,\\-\\./:;<=>\\?@\\[\\]\\^_`{|}~]).{8,25}$";
	private static final String USERNAME_REGEX = "^[a-zA-Z0-9а-яА-Я]([\\._-](?![\\._-])|[a-zA-Z0-9а-яА-Я]){3,18}[a-zA-Z0-9а-яА-Я]$";
	private static final String PHONE_REGEX = "\\+375[0-9]{9}";
	private static final String NAME_REGEX = ".{1,100}";

	private UserValidator() {

	}

	/**
	 * Validates user's email.
	 * 
	 * @param email the email to validate.
	 * @return {@code true} if email is valid and {@code false} otherwise.
	 */
	public static boolean checkEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		return !(email == null) && pattern.matcher(email).matches();

	}

	/**
	 * Checks if the user with such email already exists.
	 * 
	 * @param email the email to check.
	 * @return {@code true} if the user with such email already exists and
	 *         {@code false} otherwise.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */
	public static boolean checkDublicatedEmail(UserDAO userDao, String email) throws ServiceException {
		if (userDao == null) {
			throw new ServiceException(CommonExceptionMessage.NULL_ARGUMENT);
		}

		if (email == null) {
			return false;
		}
		
		boolean result = false;
		
		try {
			result = userDao.containsEmail(email);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}

		return result;

	}

	/**
	 * Validates username.
	 * 
	 * @param username the username to validate.
	 * @return {@code true} if username is valid and {@code false} otherwise.
	 */
	public static boolean checkUsername(String username) {
		Pattern pattern = Pattern.compile(USERNAME_REGEX);
		return !(username == null) && pattern.matcher(username).matches();

	}

	/**
	 * Checks if the user with such username already exists.
	 * 
	 * @param username the username to check.
	 * @return {@code true} if the user with such username already exists and
	 *         {@code false} otherwise.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */
	public static boolean checkDublicatedUsername(UserDAO userDao, String username) throws ServiceException {
		if (userDao == null) {
			throw new ServiceException(CommonExceptionMessage.NULL_ARGUMENT);
		}

		boolean result = false;

		try {
			result = userDao.containsUsername(username);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}

		return result;

	}

	/**
	 * Validates password.
	 * 
	 * @param password the password to validate.
	 * @return {@code true} if password is valid and {@code false} otherwise.
	 */

	public static boolean checkPassword(String password) {
		Pattern pattern = Pattern.compile(PASSWORD_REGEX);
		return !(password == null) && pattern.matcher(password).matches();

	}

	/**
	 * Validates password repeat.
	 * 
	 * @param password       the password.
	 * @param repeatPassword the repeat of password.
	 * @return {@code true} if password and repeatPassword are equal and
	 *         {@code false} otherwise.
	 */
	public static boolean checkRepeatPassword(String password, String repeatPassword) {
		boolean result = false;
		if (password != null && repeatPassword != null) {
			result = password.equals(repeatPassword);
		}
		return result;

	}

	/**
	 * Validates phone.
	 * 
	 * @param phone the phone to validate.
	 * @return {@code true} if phone is valid and {@code false} otherwise.
	 */
	public static boolean checkPhone(String phone) {
		Pattern pattern = Pattern.compile(PHONE_REGEX);
		return !(phone == null) && pattern.matcher(phone).matches();

	}

	/**
	 * Validates name.
	 * 
	 * @param name the name to validate.
	 * @return {@code true} if name is valid and {@code false} otherwise.
	 */
	public static boolean checkName(String name) {
		Pattern pattern = Pattern.compile(NAME_REGEX);
		return !(name == null) && pattern.matcher(name).matches();

	}

	/**
	 * Validates all {@code User} fields.
	 * 
	 * @param email          the email to validate.
	 * @param password       the password to validate.
	 * @param repeatPassword the repeat of password to validate.
	 * @param username       the username to validate.
	 * @param name           the name to validate.
	 * @param phone          the phone to validate.
	 * @return {@code Set} of {@code UserValidationError}.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */
	public static Set<UserValidationError> validateFields(UserDAO userDao, String email, String password,
			String repeatPassword, String username, String name, String phone) throws ServiceException {
		Set<UserValidationError> errors = new HashSet<>();

		if (!checkEmail(email)) {
			errors.add(UserValidationError.ILLEGAL_EMAIL);
		} else if (checkDublicatedEmail(userDao, email)) {
			errors.add(UserValidationError.DUBLICATE_EMAIL);
		}

		if (!checkUsername(username)) {
			errors.add(UserValidationError.ILLEGAL_USERNAME);
		} else if (checkDublicatedUsername(userDao, username)) {
			errors.add(UserValidationError.DUBLICATE_USERNAME);
		}

		if (!checkPassword(password)) {
			errors.add(UserValidationError.ILLEGAL_PASSWORD);
		} else if (!checkRepeatPassword(password, repeatPassword)) {
			errors.add(UserValidationError.PASSWORD_NOT_CONFIRMED);
		}

		if (!checkPhone(phone)) {
			errors.add(UserValidationError.ILLEGAL_PHONE);
		}

		if (!checkName(name)) {
			errors.add(UserValidationError.ILLEGAL_NAME);
		}

		return errors;
	}

}
