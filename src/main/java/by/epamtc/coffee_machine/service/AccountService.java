/**
 * 
 */
package by.epamtc.coffee_machine.service;

import by.epamtc.coffee_machine.bean.Account;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface AccountService {
	Account createAccount() throws ServiceException;

}
