/**
 * 
 */
package by.epamtc.coffee_machine.utility;

import java.util.ResourceBundle;

/**
 * @author Lizaveta Sinitsyna
 *
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

	public String retrieveValue(String key) {
		return bundle.getString(key);
	}

}
