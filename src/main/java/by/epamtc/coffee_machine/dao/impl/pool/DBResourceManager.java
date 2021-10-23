package by.epamtc.coffee_machine.dao.impl.pool;

import java.util.ResourceBundle;

/**
 * Singleton-based class which supports in providing parameters specified in
 * database property file.
 * 
 * @see DBParameter
 */
public class DBResourceManager {
	private ResourceBundle bundle = ResourceBundle.getBundle("database");

	private DBResourceManager() {

	}

	private static class SingletonHelper {
		private static final DBResourceManager INSTANCE = new DBResourceManager();
	}

	public static DBResourceManager getInstance() {
		return SingletonHelper.INSTANCE;
	}

	/**
	 * Provides parameter from database property file by specified name.
	 * 
	 * @param key the name of required parameter from database property file. The
	 *            possible parameter names are stated in {@link DBParameter}.
	 * @return {@code String} value of requested parameter.
	 */
	public String retrieveValue(String key) {
		return bundle.getString(key);
	}

}
