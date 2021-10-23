package by.epamtc.coffee_machine.service.validation;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import by.epamtc.coffee_machine.service.DrinkMessage;
import by.epamtc.coffee_machine.service.ServiceException;

/**
 * Provides support for Drink validation.
 *
 */
public class DrinkValidator {
	private static final String NAME_REGEX = ".{1,150}";
	private static final String IMAGE_PATH_REGEX = ".{0,150}";
	private static final String DESCRIPTION_REGEX = ".{0,350}";
	private static final String MINIMAL_PRICE = "0.01";

	private DrinkValidator() {

	}

	/**
	 * Validates drink image path.
	 * 
	 * @param imagePath the image path to validate.
	 * @return {@code true} if imagePath is valid and {@code false} otherwise.
	 */
	public static boolean checkImagePath(String imagePath) {
		Pattern pattern = Pattern.compile(IMAGE_PATH_REGEX);

		return !(imagePath == null) && pattern.matcher(imagePath).matches();
	}

	/**
	 * Validates drink price.
	 * 
	 * @param price the price to validate.
	 * @return {@code true} if price is valid and {@code false} otherwise.
	 */
	public static boolean checkPrice(BigDecimal price) {
		return price.compareTo(new BigDecimal(MINIMAL_PRICE)) > 0;
	}

	/**
	 * Validates drink description.
	 * 
	 * @param description the description to validate.
	 * @return {@code true} if description is valid and {@code false} otherwise.
	 */
	public static boolean checkDescription(String description) {
		Pattern pattern = Pattern.compile(DESCRIPTION_REGEX);

		return !(description == null) && pattern.matcher(description).matches();
	}

	/**
	 * Validates drink name.
	 * 
	 * @param name the name to validate.
	 * @return {@code true} if name is valid and {@code false} otherwise.
	 */
	public static boolean checkName(String name) {
		Pattern pattern = Pattern.compile(NAME_REGEX);

		return !(name == null) && pattern.matcher(name).matches();
	}

	/**
	 * Validates all editable drink fields.
	 * 
	 * @param imagePath   the image path to validate
	 * @param price       the price to validate
	 * @param description the description to validate
	 * @return {@code Set} of {@code DrinkMessage}
	 * @throws ServiceException
	 */
	public static Set<DrinkMessage> validateFields(String imagePath, BigDecimal price, String description) {

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
