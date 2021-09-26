/**
 * 
 */
package by.epamtc.coffee_machine.service.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.epamtc.coffee_machine.bean.Account;
import by.epamtc.coffee_machine.bean.BonusAccount;
import by.epamtc.coffee_machine.bean.User;
import by.epamtc.coffee_machine.bean.UserInfo;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DAOProvider;
import by.epamtc.coffee_machine.dao.UserDAO;
import by.epamtc.coffee_machine.service.AccountService;
import by.epamtc.coffee_machine.service.BonusAccountService;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;
import by.epamtc.coffee_machine.service.UserService;
import by.epamtc.coffee_machine.validation.ValidationHelper;
import by.epamtc.coffee_machine.service.UserValidationError;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class UserServiceImpl implements UserService {
	private final UserDAO userDAO = DAOProvider.getInstance().getUserDAO();
	private final String emailRegex = "[^@\\s]+@[^@\\s]+\\.[^@\\s]+";
	private final String passwordRegex = ".*";// ?
	private final String usernameRegex = ".*";// ?
	private final String phoneRegex = "\\+375[0-9]{9}";

	@Override
	public Set<UserValidationError> registration(String email, String password, String repeatPassword, String username,
			String name, String phone) throws ServiceException {
		Set<UserValidationError> errors = null;
		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		errors = checkRequiredFields(email, password, repeatPassword, username, name, phone);
		if (errors.size() > 0) {
			return errors;
		}

		errors = validateFields(email, password, repeatPassword, username, name, phone);
		if (errors.size() > 0) {
			return errors;
		}

		AccountService accountService = serviceProvider.getAccountService();
		BonusAccountService bonusAccountService = serviceProvider.getBonusAccountService();

		User user = new User();
		Account account = accountService.createAccount();
		BonusAccount bonusAccount = bonusAccountService.createAccount();
		if (ValidationHelper.isNull(account) || ValidationHelper.isNull(bonusAccount)) {
			throw new ServiceException(ValidationHelper.NULL_ACCOUNT_EXCEPTION);
		}
		user.setAccount(account);
		user.setBonusAccount(bonusAccount);

		UserInfo userInfo = new UserInfo();
		userInfo.setEmail(email);
		userInfo.setPassword(password);
		userInfo.setLogin(username);
		userInfo.setName(name);
		userInfo.setPhone(phone);
		user.setInfo(userInfo);

		try {
			userDAO.add(user);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}

		return errors;
	}

	private Set<UserValidationError> checkRequiredFields(String email, String password, String repeatPassword,
			String username, String name, String phone) {
		Set<UserValidationError> errors = new HashSet<>();
		if (ValidationHelper.isNull(email)) {
			errors.add(UserValidationError.EMPTY_EMAIL);
		}

		if (ValidationHelper.isNull(password)) {
			errors.add(UserValidationError.EMPTY_PASSWORD);
		}

		if (ValidationHelper.isNull(repeatPassword)) {
			errors.add(UserValidationError.EMPTY_REPEAT_PASSWORD);
		}

		if (ValidationHelper.isNull(username)) {
			errors.add(UserValidationError.EMPTY_USERNAME);
		}

		if (ValidationHelper.isNull(name)) {
			errors.add(UserValidationError.EMPTY_NAME);
		}

		if (ValidationHelper.isNull(phone)) {
			errors.add(UserValidationError.EMPTY_PHONE);
		}

		return errors;
	}

	private Set<UserValidationError> validateFields(String email, String password, String repeatPassword,
			String username, String name, String phone) throws ServiceException {
		Pattern pattern;
		Matcher matcher;
		Set<UserValidationError> errors = new HashSet<>();

		try {
			pattern = Pattern.compile(emailRegex);
			matcher = pattern.matcher(email);
			if (!matcher.matches()) {
				errors.add(UserValidationError.ILLEGAL_EMAIL);
			}

			if (!errors.contains(UserValidationError.ILLEGAL_EMAIL) && userDAO.containsEmail(email)) {
				errors.add(UserValidationError.DUBLICATE_EMAIL);
			}

			pattern = Pattern.compile(usernameRegex);
			matcher = pattern.matcher(username);
			if (!matcher.matches()) {
				errors.add(UserValidationError.ILLEGAL_USERNAME);
			}

			if (!errors.contains(UserValidationError.ILLEGAL_USERNAME) && userDAO.containsUsername(username)) {
				errors.add(UserValidationError.DUBLICATE_USERNAME);
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}

		pattern = Pattern.compile(passwordRegex);
		matcher = pattern.matcher(password);
		if (!matcher.matches()) {
			errors.add(UserValidationError.ILLEGAL_PASSWORD);
		}

		if (!password.equals(repeatPassword)) {
			errors.add(UserValidationError.PASSWORD_NOT_CONFIRMED);
		}

		pattern = Pattern.compile(phoneRegex);
		matcher = pattern.matcher(phone);
		if (!matcher.matches()) {
			errors.add(UserValidationError.ILLEGAL_PHONE);
		}

		return errors;
	}

}
