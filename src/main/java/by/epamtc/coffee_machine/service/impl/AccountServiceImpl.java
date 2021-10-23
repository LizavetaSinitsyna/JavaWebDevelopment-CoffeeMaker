package by.epamtc.coffee_machine.service.impl;

import by.epamtc.coffee_machine.bean.Account;
import by.epamtc.coffee_machine.dao.AccountDAO;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DAOProvider;
import by.epamtc.coffee_machine.service.AccountService;
import by.epamtc.coffee_machine.service.ServiceException;

/**
 * Provides access to {@link AccountDAO} and support for working with entity
 * {@link Account}
 */
public class AccountServiceImpl implements AccountService {
	private final AccountDAO accountDAO = DAOProvider.getInstance().getAccountDAO();

	/**
	 * Creates new account through interaction with DAO-layer.
	 * 
	 * @return Newly created {@code Account}.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */

	@Override
	public Account createAccount() throws ServiceException {
		long id;
		Account account = null;

		try {
			account = new Account();
			id = accountDAO.add(account);
			account.setId(id);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return account;
	}

	/**
	 * Obtains existed account for specified user by user's id
	 * 
	 * @param userId {@code long} value which uniquely indicates the user.
	 * @return {@code Account} for specified user or {@code null} if {@code userId}
	 *         is invalid.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */

	@Override
	public Account obtainAccountByUserId(long userId) throws ServiceException {
		Account account = null;

		if (userId <= 0) {
			return account;
		}

		try {
			account = accountDAO.readByUserId(userId);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return account;
	}

}
