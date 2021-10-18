package by.epamtc.coffee_machine.dao;

import by.epamtc.coffee_machine.bean.Account;

public interface AccountDAO {

	boolean update(long accounId, int amount) throws DAOException;

	Account read(long accountId) throws DAOException;

	long add(Account account) throws DAOException;

	Account readByUserId(long accountId) throws DAOException;

}
