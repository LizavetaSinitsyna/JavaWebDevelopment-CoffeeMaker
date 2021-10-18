package by.epamtc.coffee_machine.service.utility;

import java.util.ResourceBundle;

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
