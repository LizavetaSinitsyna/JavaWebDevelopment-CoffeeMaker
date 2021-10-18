package by.epamtc.coffee_machine.service.impl;

import by.epamtc.coffee_machine.bean.Account;
import by.epamtc.coffee_machine.dao.AccountDAO;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DAOProvider;
import by.epamtc.coffee_machine.service.AccountService;
import by.epamtc.coffee_machine.service.ServiceException;

public class AccountServiceImpl implements AccountService {
	private final AccountDAO accountDAO = DAOProvider.getInstance().getAccountDAO();

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

}
