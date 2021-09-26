/**
 * 
 */
package by.epamtc.coffee_machine.service;

import by.epamtc.coffee_machine.bean.BonusAccount;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface BonusAccountService {
	BonusAccount createAccount() throws ServiceException;
}
