/**
 * 
 */
package by.epamtc.coffee_machine.service.impl;

import by.epamtc.coffee_machine.bean.BonusAccount;
import by.epamtc.coffee_machine.dao.BonusAccountDAO;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DAOProvider;
import by.epamtc.coffee_machine.service.BonusAccountService;
import by.epamtc.coffee_machine.service.ServiceException;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class BonusAccountServiceImpl implements BonusAccountService {
	private final BonusAccountDAO accountDAO = DAOProvider.getInstance().getBonusAccountDAO();

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

}
