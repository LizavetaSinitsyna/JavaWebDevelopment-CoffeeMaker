package by.epamtc.coffee_machine.service;

import by.epamtc.coffee_machine.bean.BonusAccount;

public interface BonusAccountService {
	BonusAccount createAccount() throws ServiceException;
}
