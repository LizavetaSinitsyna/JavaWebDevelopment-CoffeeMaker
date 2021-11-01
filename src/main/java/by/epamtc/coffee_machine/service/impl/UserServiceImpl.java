package by.epamtc.coffee_machine.service.impl;

import java.util.Set;

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
import by.epamtc.coffee_machine.service.BonusAccountService;
import by.epamtc.coffee_machine.service.CommonExceptionMessage;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;
import by.epamtc.coffee_machine.service.UserService;
import by.epamtc.coffee_machine.service.UserValidationError;
import by.epamtc.coffee_machine.service.utility.BCrypt;
import by.epamtc.coffee_machine.service.validation.UserValidator;

/**
 * Provides access to {@link UserDAO} and support for working with entities
 * {@link User}, {@link UserInfo}.
 */
public class UserServiceImpl implements UserService {
	private final UserDAO userDao = DAOProvider.getInstance().getUserDAO();
	private final AccountService accountService = ServiceProvider.getInstance().getAccountService();
	private final BonusAccountService bonusAccountService = ServiceProvider.getInstance().getBonusAccountService();

	/**
	 * Obtains User with passed login and password.
	 * 
	 * @param login    {@code String} value representing user's email of user name
	 * @param password {@code String} value representing user's password
	 * @return {@code UserLoginTransfer} object representing the authenticated user
	 *         or {@code null} if passed parameters are invalid or user with passed
	 *         parameters doesn't exist.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */

	@Override
	public UserLoginTransfer login(String login, String password) throws ServiceException {
		UserLoginTransfer result = null;
		if (login == null || password == null) {
			return result;
		}
		try {
			UserLoginPasswordTransfer user = userDao.login(login);
			if (user != null && BCrypt.checkpw(password, user.getPassword())) {
				result = new UserLoginTransfer();
				result.setId(user.getId());
				result.setRoleId(user.getRoleId());
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * Creates user with passed parameters.
	 * 
	 * @param email          {@code String} value representing user's email
	 * @param password       {@code String} value representing user's password. If
	 *                       all passed parameters are valid, the password will be
	 *                       encrypted by
	 *                       {@link by.epamtc.coffee_machine.service.utility.BCrypt}
	 * @param repeatPassword {@code String} value representing user's repeat
	 *                       password
	 * @param username       {@code String} value representing user's username
	 * @param name           {@code String} value representing user's name
	 * @param phone          {@code String} value representing user's phone
	 * @return {@code Set} of {@link UserValidationError} objects.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */

	@Override
	public Set<UserValidationError> registration(String email, String password, String repeatPassword, String username,
			String name, String phone) throws ServiceException {
		Set<UserValidationError> errors = null;

		errors = UserValidator.validateFields(userDao, email, password, repeatPassword, username, name, phone);

		if (errors != null && errors.size() > 0) {
			return errors;
		}

		User user = new User();
		Account account = accountService.createAccount();
		BonusAccount bonusAccount = bonusAccountService.createAccount();
		if (account == null || bonusAccount == null) {
			throw new ServiceException(CommonExceptionMessage.NULL_ACCOUNT);
		}
		user.setAccountId(account.getId());
		user.setBonusAccountId(bonusAccount.getId());

		UserInfo userInfo = new UserInfo();
		userInfo.setEmail(email);

		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		userInfo.setPassword(hashedPassword);

		userInfo.setLogin(username);
		userInfo.setName(name);
		userInfo.setPhone(phone);
		user.setInfo(userInfo);

		try {
			userDao.add(user);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}

		return errors;
	}

}
