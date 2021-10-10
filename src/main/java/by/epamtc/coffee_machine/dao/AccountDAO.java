/**
 * 
 */
package by.epamtc.coffee_machine.dao;

import by.epamtc.coffee_machine.bean.Account;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface AccountDAO extends GenericDAO<Account> {
	Account read(int account_id) throws DAOException;

	void update(int account_id, int amount) throws DAOException;

}
