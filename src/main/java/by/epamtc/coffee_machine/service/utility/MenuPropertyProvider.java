package by.epamtc.coffee_machine.service.utility;

import java.util.ResourceBundle;

/**
 * Singleton-based class which supports in providing parameters specified in
 * menu property file.
 * 
 * @see MenuParameter
 */
public class MenuPropertyProvider {
	private ResourceBundle bundle = ResourceBundle.getBundle("menu");

	private MenuPropertyProvider() {

	}

	private static class SingletonHelper {
		private static final MenuPropertyProvider INSTANCE = new MenuPropertyProvider();
	}

	public static MenuPropertyProvider getInstance() {
		return SingletonHelper.INSTANCE;
	}

	/**
	 * Provides parameter from menu property file by specified name.
	 * 
	 * @param key the name of required parameter from menu property file. The
	 *            possible parameter names are stated in {@link MenuParameter}.
	 * @return {@code String} value of requested parameter.
	 */
	public String retrieveValue(String key) {
		return bundle.getString(key);
	}

}
