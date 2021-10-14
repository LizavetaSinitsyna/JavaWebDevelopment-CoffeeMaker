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

	void update(long accounId, int amount) throws DAOException;

	Account read(long accountId) throws DAOException;

}
