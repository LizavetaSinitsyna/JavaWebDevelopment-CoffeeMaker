/**
 * 
 */
package by.epamtc.coffee_machine.dao;

import by.epamtc.coffee_machine.bean.UserInfo;
import by.epamtc.coffee_machine.bean.User;

/**
 * @author Dell
 *
 */
public interface UserDAO extends GenericDAO<User> {
	boolean authorization(String login, String password) throws DAOException;

	boolean registration(UserInfo userInfo) throws DAOException;

	User searchByLogin(String login) throws DAOException;
	
	boolean containsEmail(String email) throws DAOException;
	
	boolean containsUsername(String username) throws DAOException;

	@Override
	int add(User user) throws DAOException;

	boolean remove(int user_id) throws DAOException;

	boolean update(int user_id, UserInfo userInfo) throws DAOException;

}
