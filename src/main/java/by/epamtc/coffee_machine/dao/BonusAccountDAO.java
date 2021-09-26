/**
 * 
 */
package by.epamtc.coffee_machine.dao;

import by.epamtc.coffee_machine.bean.BonusAccount;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface BonusAccountDAO extends GenericDAO<BonusAccount> {
	BonusAccount read(int account_id) throws DAOException;

	@Override
	int add(BonusAccount account) throws DAOException;

	boolean update(int account_id, int amount) throws DAOException;
}
