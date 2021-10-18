package by.epamtc.coffee_machine.dao;

import by.epamtc.coffee_machine.bean.User;
import by.epamtc.coffee_machine.bean.UserInfo;
import by.epamtc.coffee_machine.bean.transfer.UserLoginPasswordTransfer;

public interface UserDAO {
	
	boolean containsEmail(String email) throws DAOException;

	boolean containsUsername(String username) throws DAOException;

	boolean remove(int user_id) throws DAOException;

	boolean update(int user_id, UserInfo userInfo) throws DAOException;

	UserLoginPasswordTransfer login(String login) throws DAOException;

	long add(User user) throws DAOException;

}
