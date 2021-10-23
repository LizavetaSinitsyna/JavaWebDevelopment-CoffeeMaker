package by.epamtc.coffee_machine.service.utility;

import java.util.ResourceBundle;

/**
 * Singleton-based class which supports in providing parameters specified in
 * order property file.
 * 
 * @see OrderParameter
 */
public class OrderPropertyProvider {
	private ResourceBundle bundle = ResourceBundle.getBundle("order");

	private OrderPropertyProvider() {

	}

	private static class SingletonHelper {
		private static final OrderPropertyProvider INSTANCE = new OrderPropertyProvider();
	}

	public static OrderPropertyProvider getInstance() {
		return SingletonHelper.INSTANCE;
	}

	/**
	 * Provides parameter from order property file by specified name.
	 * 
	 * @param key the name of required parameter from order property file. The
	 *            possible parameter names are stated in {@link OrderParameter}.
	 * @return {@code String} value of requested parameter.
	 */

	public String retrieveValue(String key) {
		return bundle.getString(key);
	}

}
