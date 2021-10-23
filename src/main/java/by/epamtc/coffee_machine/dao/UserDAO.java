package by.epamtc.coffee_machine.dao;

import by.epamtc.coffee_machine.bean.User;
import by.epamtc.coffee_machine.bean.transfer.UserLoginPasswordTransfer;

/**
 * Provides methods for working with Users table and entities {@link User},
 * {@link UserLoginPasswordTransfer}
 */
public interface UserDAO {
	/**
	 * Checks if the user with specified email exists.
	 * 
	 * @param email the email to be checked.
	 * @return {@code true} if the user with passed email exists and {@code false}
	 *         in other case.
	 * @throws DAOException
	 */
	boolean containsEmail(String email) throws DAOException;

	/**
	 * Checks if the user with specified username exists.
	 * 
	 * @param username the username to be checked.
	 * @return {@code true} if the user with passed username exists and
	 *         {@code false} in other case.
	 * @throws DAOException
	 */
	boolean containsUsername(String username) throws DAOException;

	/**
	 * Returns User by the specified login.
	 * 
	 * @param login the login of the User
	 * @return {@code UserLoginPasswordTransfer} object representing user with
	 *         passed login.
	 * @throws DAOException
	 */
	UserLoginPasswordTransfer login(String login) throws DAOException;

	/**
	 * Add passed User to database.
	 * 
	 * @param user the user to be saved in database.
	 * @return {@code long} value which represents user id.
	 * @throws DAOException
	 */
	long add(User user) throws DAOException;

}
