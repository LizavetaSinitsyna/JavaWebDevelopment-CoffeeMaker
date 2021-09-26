/**
 * 
 */
package by.epamtc.coffee_machine.dao.sql;

import java.sql.ResultSet;

import by.epamtc.coffee_machine.bean.User;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class SQLUserDAOHelper {
	private SQLUserDAOHelper() {

	}

	private static final class SingletonHelper {
		private static final SQLUserDAOHelper INSTANCE = new SQLUserDAOHelper();
	}

	public static SQLUserDAOHelper getInstance() {
		return SingletonHelper.INSTANCE;
	}
	
	public User retrieveUser(ResultSet resultSet) {
		return null;
	}

}
