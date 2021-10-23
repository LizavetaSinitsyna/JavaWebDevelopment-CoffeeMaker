package by.epamtc.coffee_machine.service.impl;

import by.epamtc.coffee_machine.bean.BonusAccount;
import by.epamtc.coffee_machine.dao.BonusAccountDAO;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DAOProvider;
import by.epamtc.coffee_machine.service.BonusAccountService;
import by.epamtc.coffee_machine.service.ServiceException;

/**
 * Provides access to {@link BonusAccountDAO} and support for working with
 * entity {@link BonusAccount}
 */
public class BonusAccountServiceImpl implements BonusAccountService {
	private final BonusAccountDAO accountDAO = DAOProvider.getInstance().getBonusAccountDAO();

	/**
	 * Creates new bonus account through interaction with DAO-layer.
	 * 
	 * @return Newly created {@code BonusAccount}.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */
	@Override
	public BonusAccount createAccount() throws ServiceException {
		long id;
		BonusAccount account = null;

		try {
			account = new BonusAccount();
			id = accountDAO.add(account);
			account.setId(id);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return account;
	}

	/**
	 * Obtains existed bonus account for specified user by user's id
	 * 
	 * @param userId {@code long} value which uniquely indicates the user.
	 * @return {@code BonusAccount} for specified user or {@code null} if
	 *         {@code userId} is invalid.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */
	@Override
	public BonusAccount obtainAccountByUserId(long userId) throws ServiceException {
		BonusAccount account = null;

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
