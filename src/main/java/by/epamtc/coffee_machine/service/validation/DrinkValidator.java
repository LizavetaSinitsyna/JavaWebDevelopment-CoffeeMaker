package by.epamtc.coffee_machine.service.validation;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import by.epamtc.coffee_machine.service.DrinkMessage;
import by.epamtc.coffee_machine.service.ServiceException;

public class DrinkValidator {
	private static final String NAME_REGEX = ".{1,150}";
	private static final String IMAGE_PATH_REGEX = ".{0,150}";
	private static final String DESCRIPTION_REGEX = ".{0,350}";
	private static final String MINIMAL_PRICE = "0.01";

	private DrinkValidator() {

	}

	public static boolean checkImagePath(String imagePath) {
		Pattern pattern = Pattern.compile(IMAGE_PATH_REGEX);

		return !(imagePath == null) && pattern.matcher(imagePath).matches();
	}

	public static boolean checkPrice(BigDecimal price) {
		return price.compareTo(new BigDecimal(MINIMAL_PRICE)) > 0;
	}

	public static boolean checkDescription(String description) {
		Pattern pattern = Pattern.compile(DESCRIPTION_REGEX);

		return !(description == null) && pattern.matcher(description).matches();
	}

	public static boolean checkName(String name) {
		Pattern pattern = Pattern.compile(NAME_REGEX);

		return !(name == null) && pattern.matcher(name).matches();
	}

	public static Set<DrinkMessage> validateFields(String imagePath, BigDecimal price, String description)
			throws ServiceException {

		Set<DrinkMessage> messages = new HashSet<>();

		if (!checkImagePath(imagePath)) {
			messages.add(DrinkMessage.ILLEGAL_IMAGE_PATH);
		}

		if (!checkPrice(price)) {
			messages.add(DrinkMessage.ILLEGAL_PRICE);
		}

		if (!checkDescription(description)) {
			messages.add(DrinkMessage.ILLEGAL_DESCRIPTION);
		}

		return messages;
	}

}
