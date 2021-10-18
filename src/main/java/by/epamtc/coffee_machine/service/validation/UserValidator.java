package by.epamtc.coffee_machine.service.validation;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DAOProvider;
import by.epamtc.coffee_machine.dao.UserDAO;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.UserValidationError;

public class UserValidator {
	private static final UserDAO USER_DAO = DAOProvider.getInstance().getUserDAO();

	private static final String EMAIL_REGEX = "^(?=[^@\\s]+@[^@\\s]+\\.[^@\\s]+).{5,255}$";
	private static final String PASSWORD_REGEX = "^(?=.*?[A-ZА-Я])(?=.*?[a-zа-я])(?=.*?[0-9])(?=.*?[ !\\\"#\\$%&'\\(\\)\\*\\+,\\-\\./:;<=>\\?@\\[\\]\\^_`{|}~]).{8,25}$";
	private static final String USERNAME_REGEX = "^[a-zA-Z0-9а-яА-Я]([\\._-](?![\\._-])|[a-zA-Z0-9а-яА-Я]){3,18}[a-zA-Z0-9а-яА-Я]$";
	private static final String PHONE_REGEX = "\\+375[0-9]{9}";
	private static final String NAME_REGEX = ".{1,100}";

	private UserValidator() {

	}

	public static boolean checkEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		return !(email == null) && pattern.matcher(email).matches();

	}

	public static boolean checkDublicatedEmail(String email) throws ServiceException {
		boolean result = false;
		try {
			result = USER_DAO.containsEmail(email);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}

		return result;

	}

	public static boolean checkUsername(String username) {
		Pattern pattern = Pattern.compile(USERNAME_REGEX);
		return !(username == null) && pattern.matcher(username).matches();

	}

	public static boolean checkDublicatedUsername(String username) throws ServiceException {
		boolean result = false;
		try {
			result = USER_DAO.containsUsername(username);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}

		return result;

	}

	public static boolean checkPassword(String password) {
		Pattern pattern = Pattern.compile(PASSWORD_REGEX);
		return !(password == null) && pattern.matcher(password).matches();

	}

	public static boolean checkRepeatPassword(String password, String repeatPassword) {
		boolean result = false;
		if (password != null && repeatPassword != null) {
			result = password.equals(repeatPassword);
		}
		return result;

	}

	public static boolean checkPhone(String phone) {
		Pattern pattern = Pattern.compile(PHONE_REGEX);
		return !(phone == null) && pattern.matcher(phone).matches();

	}

	public static boolean checkName(String name) {
		Pattern pattern = Pattern.compile(NAME_REGEX);
		return !(name == null) && pattern.matcher(name).matches();

	}

	public static Set<UserValidationError> validateFields(String email, String password, String repeatPassword,
			String username, String name, String phone) throws ServiceException {
		Set<UserValidationError> errors = new HashSet<>();

		if (!checkEmail(email)) {
			errors.add(UserValidationError.ILLEGAL_EMAIL);
		} else if (checkDublicatedEmail(email)) {
			errors.add(UserValidationError.DUBLICATE_EMAIL);
		}

		if (!checkUsername(username)) {
			errors.add(UserValidationError.ILLEGAL_USERNAME);
		} else if (checkDublicatedUsername(username)) {
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
