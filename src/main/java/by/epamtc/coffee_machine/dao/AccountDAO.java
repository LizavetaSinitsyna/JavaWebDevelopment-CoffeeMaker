package by.epamtc.coffee_machine.dao;

import java.math.BigDecimal;

import by.epamtc.coffee_machine.bean.Account;

/**
 * Provides methods for working with Accounts table and {@link Account} entity
 */
public interface AccountDAO {
	/**
	 * Updates the balance of already existed account.
	 * 
	 * @param accounId   {@code long} value which uniquely indicates the existed
	 *                   account.
	 * @param newBalance {@code BigDecimal} value representing new account
	 *                   balance.
	 * @return {@code true} If the update was successful or {@code false} if update
	 *         was failed.
	 * @throws DAOException
	 */

	boolean update(long accounId, BigDecimal newBalance) throws DAOException;

	/**
	 * Obtains existed account with specified account id.
	 * 
	 * @param accountId {@code long} value which uniquely indicates the account.
	 * @return {@code Account} with specified id.
	 * @throws DAOException
	 */
	Account read(long accountId) throws DAOException;

	/**
	 * Add passed account to database.
	 * 
	 * @param account the account to be saved in database.
	 * @return {@code long} value which represents account id.
	 * @throws DAOException
	 */
	long add(Account account) throws DAOException;

	/**
	 * Read account from database for specified user by user's id.
	 * 
	 * @param userId {@code long} value which uniquely indicates the user.
	 * @return {@code Account} for specified user.
	 * @throws DAOException
	 */
	Account readByUserId(long userId) throws DAOException;

}
