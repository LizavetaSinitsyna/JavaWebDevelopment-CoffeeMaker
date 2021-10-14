/**
 * 
 */
package by.epamtc.coffee_machine.service.utility;

import java.util.ResourceBundle;

/**
 * @author Lizaveta Sinitsyna
 *
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

	public String retrieveValue(String key) {
		return bundle.getString(key);
	}

}
