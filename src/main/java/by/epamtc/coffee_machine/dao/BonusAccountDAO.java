package by.epamtc.coffee_machine.dao;

import java.math.BigDecimal;

import by.epamtc.coffee_machine.bean.BonusAccount;

/**
 * Provides methods for working with BonusAccounts table and
 * {@link BonusAccount} entity
 */
public interface BonusAccountDAO {

	/**
	 * Updates the balance of already existed account.
	 * 
	 * @param accounId   {@code long} value which uniquely indicates the existed
	 *                   account.
	 * @param newBalance {@code BigDecimal} value representing new account balance.
	 * @return {@code true} If the update was successful or {@code false} if update
	 *         was failed.
	 * @throws DAOException
	 */
	boolean update(long accountId, BigDecimal newBalance) throws DAOException;

	/**
	 * Add passed bonus account to database.
	 * 
	 * @param account the account to be saved in database.
	 * @return {@code long} value which represents bonus account id.
	 * @throws DAOException
	 */

	long add(BonusAccount account) throws DAOException;

	/**
	 * Obtains existed bonus account with specified bonus account id.
	 * 
	 * @param accountId {@code long} value which uniquely indicates the bonus
	 *                  account.
	 * @return {@code BonusAccount} with specified id.
	 * @throws DAOException
	 */
	BonusAccount read(long accountId) throws DAOException;

	/**
	 * Read bonus account from database for specified user by user's id.
	 * 
	 * @param userId {@code long} value which uniquely indicates the user.
	 * @return {@code BonusAccount} for specified user.
	 * @throws DAOException
	 */
	BonusAccount readByUserId(long userId) throws DAOException;
}
