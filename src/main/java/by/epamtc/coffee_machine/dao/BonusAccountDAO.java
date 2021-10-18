package by.epamtc.coffee_machine.dao;

import by.epamtc.coffee_machine.bean.BonusAccount;

public interface BonusAccountDAO {

	boolean update(int account_id, int amount) throws DAOException;

	long add(BonusAccount account) throws DAOException;

	BonusAccount read(long accountId) throws DAOException;

	BonusAccount readByUserId(long userId) throws DAOException;
}
