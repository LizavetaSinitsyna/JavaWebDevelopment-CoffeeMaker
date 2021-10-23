package by.epamtc.coffee_machine.service;

import by.epamtc.coffee_machine.bean.BonusAccount;

/**
 * Provides support for working with entity {@link BonusAccount}
 */
public interface BonusAccountService {
	/**
	 * Creates new bonus account.
	 * 
	 * @return Newly created {@code BonusAccount}.
	 * @throws ServiceException If problem occurs during account creating.
	 */
	BonusAccount createAccount() throws ServiceException;

	/**
	 * Obtains existed bonus account for specified user by user's id
	 * 
	 * @param userId {@code long} value which uniquely indicates the user.
	 * @return {@code BonusAccount} for specified user.
	 * @throws ServiceException
	 */

	BonusAccount obtainAccountByUserId(long userId) throws ServiceException;
}
