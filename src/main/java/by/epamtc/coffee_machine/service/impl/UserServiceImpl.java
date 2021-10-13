/**
 * 
 */
package by.epamtc.coffee_machine.service.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import by.epamtc.coffee_machine.bean.Account;
import by.epamtc.coffee_machine.bean.BonusAccount;
import by.epamtc.coffee_machine.bean.User;
import by.epamtc.coffee_machine.bean.UserInfo;
import by.epamtc.coffee_machine.bean.transfer.UserLoginPasswordTransfer;
import by.epamtc.coffee_machine.bean.transfer.UserLoginTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DAOProvider;
import by.epamtc.coffee_machine.dao.UserDAO;
import by.epamtc.coffee_machine.service.AccountService;
import by.epamtc.coffee_machine.service.BCrypt;
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
	private static final UserDAO USER_DAO = DAOProvider.getInstance().getUserDAO();
	private static final String EMAIL_REGEX = "^(?=[^@\\s]+@[^@\\s]+\\.[^@\\s]+).{5,255}$";
	private static final String PASSWORD_REGEX = "^(?=.*?[A-ZА-Я])(?=.*?[a-zа-я])(?=.*?[0-9])(?=.*?[ !\\\"#\\$%&'\\(\\)\\*\\+,\\-\\./:;<=>\\?@\\[\\]\\^_`{|}~]).{8,25}$";
	private static final String USERNAME_REGEX = "^[a-zA-Z0-9а-яА-Я]([\\._-](?![\\._-])|[a-zA-Z0-9а-яА-Я]){3,18}[a-zA-Z0-9а-яА-Я]$";
	private static final String PHONE_REGEX = "\\+375[0-9]{9}";
	private static final String NAME_REGEX = ".{1,100}";

	@Override
	public UserLoginTransfer login(String login, String password) throws ServiceException {
		UserLoginTransfer result = null;
		if (ValidationHelper.isNull(login) || ValidationHelper.isNull(password)) {
			return result;
		}
		try {
			UserLoginPasswordTransfer user = USER_DAO.login(login);
			if (!ValidationHelper.isNull(user) && BCrypt.checkpw(password, user.getPassword())) {
				result = new UserLoginTransfer();
				result.setId(user.getId());
				result.setRoleId(user.getRoleId());
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public Set<UserValidationError> registration(String email, String password, String repeatPassword, String username,
			String name, String phone) throws ServiceException {
		Set<UserValidationError> errors = null;
		ServiceProvider serviceProvider = ServiceProvider.getInstance();

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

		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		userInfo.setPassword(hashedPassword);

		userInfo.setLogin(username);
		userInfo.setName(name);
		userInfo.setPhone(phone);
		user.setInfo(userInfo);

		try {
			USER_DAO.add(user);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}

		return errors;
	}

	private Set<UserValidationError> validateFields(String email, String password, String repeatPassword,
			String username, String name, String phone) throws ServiceException {
		Pattern pattern;
		Set<UserValidationError> errors = new HashSet<>();

		try {
			pattern = Pattern.compile(EMAIL_REGEX);
			if (ValidationHelper.isNull(email) || email.isEmpty() || !pattern.matcher(email).matches()) {
				errors.add(UserValidationError.ILLEGAL_EMAIL);
			} else if (USER_DAO.containsEmail(email)) {
				errors.add(UserValidationError.DUBLICATE_EMAIL);
			}

			pattern = Pattern.compile(USERNAME_REGEX);
			if (ValidationHelper.isNull(username) || username.isEmpty() || !pattern.matcher(username).matches()) {
				errors.add(UserValidationError.ILLEGAL_USERNAME);
			} else if (USER_DAO.containsUsername(username)) {
				errors.add(UserValidationError.DUBLICATE_USERNAME);
			}

		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}

		pattern = Pattern.compile(PASSWORD_REGEX);
		if (ValidationHelper.isNull(password) || password.isEmpty() || !pattern.matcher(password).matches()) {
			errors.add(UserValidationError.ILLEGAL_PASSWORD);
		} else if (!password.equals(repeatPassword)) {
			errors.add(UserValidationError.PASSWORD_NOT_CONFIRMED);
		}

		pattern = Pattern.compile(PHONE_REGEX);
		if (ValidationHelper.isNull(phone) || phone.isEmpty() || !pattern.matcher(phone).matches()) {
			errors.add(UserValidationError.ILLEGAL_PHONE);
		}

		pattern = Pattern.compile(NAME_REGEX);
		if (ValidationHelper.isNull(name) || name.isEmpty() || !pattern.matcher(name).matches()) {
			errors.add(UserValidationError.ILLEGAL_NAME);
		}

		return errors;
	}

}
