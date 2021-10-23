package by.epamtc.coffee_machine.service;

import java.util.Set;

import by.epamtc.coffee_machine.bean.transfer.UserLoginTransfer;

/**
 * Provides support for working with entity {@link UserLoginTransfer}.
 */
public interface UserService {
	/**
	 * Creates user with passed parameters.
	 * 
	 * @param email          {@code String} value representing user's email
	 * @param password       {@code String} value representing user's password
	 * @param repeatPassword {@code String} value representing user's repeat
	 *                       password
	 * @param username       {@code String} value representing user's username
	 * @param name           {@code String} value representing user's name
	 * @param phone          {@code String} value representing user's phone
	 * @return {@code Set} of {@link UserValidationError} objects.
	 * @throws ServiceException
	 */
	Set<UserValidationError> registration(String email, String password, String repeatPassword, String username,
			String name, String phone) throws ServiceException;

	/**
	 * Obtains User with passed login and password.
	 * 
	 * @param login    {@code String} value representing user's email of user name
	 * @param password {@code String} value representing user's password
	 * @return {@code UserLoginTransfer} object representing the authenticated user.
	 * @throws ServiceException
	 */
	UserLoginTransfer login(String login, String password) throws ServiceException;

}
