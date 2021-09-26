/**
 * 
 */
package by.epamtc.coffee_machine.service;

import java.util.Set;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface UserService {
	Set<UserValidationError> registration(String email, String password, String repeatPassword, String username, String name,
			String phone) throws ServiceException;

}
