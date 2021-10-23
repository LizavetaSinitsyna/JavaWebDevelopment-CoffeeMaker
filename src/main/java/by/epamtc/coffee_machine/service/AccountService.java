package by.epamtc.coffee_machine.service;

import by.epamtc.coffee_machine.bean.Account;

/**
 * Provides support for working with entity {@link Account}
 */
public interface AccountService {
	/**
	 * Creates new account.
	 * 
	 * @return Newly created {@code Account}.
	 * @throws ServiceException
	 */
	Account createAccount() throws ServiceException;

	/**
	 * Obtains existed account for specified user by user's id
	 * 
	 * @param userId {@code long} value which uniquely indicates the user.
	 * @return {@code Account} for specified user.
	 * @throws ServiceException
	 */

	Account obtainAccountByUserId(long userId) throws ServiceException;

}
