package by.epamtc.coffee_machine.dao.impl.pool;

import java.util.ResourceBundle;

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

	public String retrieveValue(String key) {
		return bundle.getString(key);
	}

}
