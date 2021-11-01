package by.epamtc.coffee_machine.service.validation;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DrinkDAO;
import by.epamtc.coffee_machine.service.DrinkMessage;
import by.epamtc.coffee_machine.service.ServiceException;

/**
 * Provides support for Drink validation.
 *
 */
public class DrinkValidator {
	private static final String NAME_REGEX = ".{1,150}";
	private static final String IMAGE_PATH_REGEX = ".{0,128}";
	private static final String DESCRIPTION_REGEX = ".{0,350}";
	private static final String MIN_PRICE = "0.01";
	private static final String MAX_PRICE = "100.00";
	private static final String[] POSSIBLE_EXTENSIONS = { "jpeg", "jpg", "png" };

	private DrinkValidator() {
	}

	/**
	 * Validates drink image with extension.
	 * 
	 * @param imageName the image name with extension to validate.
	 * @return {@code true} if imageName is valid and {@code false} otherwise.
	 */
	public static boolean checkImage(String imageName) {
		if (imageName == null || imageName.isBlank()) {
			return true;
		}

		boolean result = false;
		String extension = imageName.split("\\.")[1];
		if (Arrays.asList(POSSIBLE_EXTENSIONS).contains(extension)) {
			Pattern pattern = Pattern.compile(IMAGE_PATH_REGEX);
			result = pattern.matcher(imageName).matches();
		}

		return result;
	}

	/**
	 * Validates drink price.
	 * 
	 * @param price the price to validate.
	 * @return {@code true} if price is valid and {@code false} otherwise.
	 */
	public static boolean checkPrice(BigDecimal price) {
		return price.compareTo(new BigDecimal(MIN_PRICE)) >= 0 && price.compareTo(new BigDecimal(MAX_PRICE)) <= 0;
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
	 * Checks if the drink with such name already exists.
	 * 
	 * @param name the name to check.
	 * @return {@code true} if the drink with such name already exists and
	 *         {@code false} otherwise.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */
	public static boolean checkDublicatedName(DrinkDAO drinkDao, String name) throws ServiceException {
		boolean result = false;
		try {
			result = drinkDao.containsDrinkName(name);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}

		return result;

	}

	/**
	 * Validates all drink fields.
	 * 
	 * @param drinkName   the drink name to validate
	 * @param imageName   the image name to validate
	 * @param price       the price to validate
	 * @param description the description to validate
	 * @return {@code Set} of {@code DrinkMessage}
	 */
	public static Set<DrinkMessage> validateFields(DrinkDAO drinkDao, String drinkName, String imageName, BigDecimal price,
			String description) throws ServiceException {

		Set<DrinkMessage> messages = new HashSet<>();

		if (!checkName(drinkName)) {
			messages.add(DrinkMessage.ILLEGAL_DRINK_NAME);
		} else if (checkDublicatedName(drinkDao, drinkName)) {
			messages.add(DrinkMessage.DUBLICATE_DRINK_NAME);
		}

		validateChangeableFields(messages, imageName, price, description);

		return messages;
	}

	/**
	 * Validates all editable drink fields.
	 * 
	 * @param imageName   the image name to validate
	 * @param price       the price to validate
	 * @param description the description to validate
	 * @return {@code Set} of {@code DrinkMessage}
	 */
	public static Set<DrinkMessage> validateFields(String imageName, BigDecimal price, String description) {

		Set<DrinkMessage> messages = new HashSet<>();

		validateChangeableFields(messages, imageName, price, description);

		return messages;
	}

	private static void validateChangeableFields(Set<DrinkMessage> drinkMessages, String imageName, BigDecimal price,
			String description) {
		if (!checkImage(imageName)) {
			drinkMessages.add(DrinkMessage.ILLEGAL_IMAGE_PATH);
		}

		if (!checkPrice(price)) {
			drinkMessages.add(DrinkMessage.ILLEGAL_PRICE);
		}

		if (!checkDescription(description)) {
			drinkMessages.add(DrinkMessage.ILLEGAL_DESCRIPTION);
		}
	}

}
