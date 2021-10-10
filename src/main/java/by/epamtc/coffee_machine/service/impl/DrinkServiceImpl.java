/**
 * 
 */
package by.epamtc.coffee_machine.service.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.DrinkInfo;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DAOProvider;
import by.epamtc.coffee_machine.service.DrinkService;
import by.epamtc.coffee_machine.service.DrinkMessage;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.utility.MenuParameter;
import by.epamtc.coffee_machine.utility.MenuPropertyProvider;
import by.epamtc.coffee_machine.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class DrinkServiceImpl implements DrinkService {
	private static final DAOProvider DAO_PROVIDER = DAOProvider.getInstance();
	private static final MenuPropertyProvider MENU_PROPERTY_PROVIDER = MenuPropertyProvider.getInstance();
	private static final String DRINK_NAME_REGEX = ".{1,150}";
	private static final String IMAGE_PATH_REGEX = ".{0,150}";
	private static final String DESCRIPTION_REGEX = ".{0,350}";
	private static final String MINIMAL_PRICE = "0.01";

	@Override
	public Drink obtainDrink(int drink_id) throws ServiceException {
		Drink drink = null;
		if (!ValidationHelper.isPositive(drink_id)) {
			return drink;
		}

		try {
			drink = DAO_PROVIDER.getDrinkDAO().read(drink_id);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return drink;
	}

	@Override
	public List<DrinkTransfer> obtainMenu(int pageNumber) throws ServiceException {
		List<DrinkTransfer> drinks = null;
		if (ValidationHelper.isNegative(pageNumber)) {
			return drinks;
		}

		int amount = Integer.parseInt(MENU_PROPERTY_PROVIDER.retrieveValue(MenuParameter.DRINKS_AMOUNT_PER_PAGE));
		int startIndex = 0;
		if (pageNumber > 1) {
			startIndex += (pageNumber - 1) * amount;
		}

		try {
			drinks = DAO_PROVIDER.getDrinkDAO().obtainDrinks(startIndex, amount);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return drinks;
	}

	@Override
	public int obtainMenuPagesAmount() throws ServiceException {
		int generalDrinksAmount = 0;
		int drinksAmountPerPage = Integer
				.parseInt(MENU_PROPERTY_PROVIDER.retrieveValue(MenuParameter.DRINKS_AMOUNT_PER_PAGE));
		if (!ValidationHelper.isPositive(drinksAmountPerPage)) {
			throw new ServiceException(ValidationHelper.NEGATIVE_PARAM_EXCEPTION);
		}
		try {
			generalDrinksAmount = DAO_PROVIDER.getDrinkDAO().obtainGeneralDrinksAmount();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		int addPage = generalDrinksAmount % drinksAmountPerPage > 0 ? 1 : 0;
		int result = generalDrinksAmount / drinksAmountPerPage + addPage;
		return result;
	}

	@Override
	public Set<DrinkMessage> edit(String imagePath, int drinkId, BigDecimal price, String description)
			throws ServiceException {
		Set<DrinkMessage> messages = new HashSet<>();

		if (!ValidationHelper.isPositive(drinkId)) {
			messages.add(DrinkMessage.INVALID_DRINK_ID);
			return messages;
		}

		messages = validateFields(imagePath, price, description);

		if (ValidationHelper.isNull(messages) || messages.isEmpty()) {
			Drink drink = new Drink();
			DrinkInfo info = new DrinkInfo();
			info.setDescription(description);
			info.setImagePath(imagePath);
			info.setPrice(price);
			drink.setId(drinkId);
			drink.setInfo(info);
			try {
				int result = DAOProvider.getInstance().getDrinkDAO().update(drink);
				if (!ValidationHelper.isPositive(result)) {
					messages.add(DrinkMessage.UNABLE_EDIT);
				}
			} catch (DAOException e) {
				throw new ServiceException(e.getMessage(), e);
			}
		}

		return messages;
	}

	private Set<DrinkMessage> validateFields(String imagePath, BigDecimal price, String description)
			throws ServiceException {

		Set<DrinkMessage> messages = new HashSet<>();
		Pattern pattern;
		Matcher matcher;

		if (!ValidationHelper.isNull(imagePath) || !imagePath.isEmpty()) {
			pattern = Pattern.compile(IMAGE_PATH_REGEX);
			matcher = pattern.matcher(imagePath);
			if (!matcher.matches()) {
				messages.add(DrinkMessage.ILLEGAL_IMAGE_PATH);
			}
		}

		if (price.compareTo(new BigDecimal(MINIMAL_PRICE)) < 0) {
			messages.add(DrinkMessage.ILLEGAL_PRICE);
		}

		if (!ValidationHelper.isNull(description) || !description.isEmpty()) {
			pattern = Pattern.compile(DESCRIPTION_REGEX);
			matcher = pattern.matcher(description);
			if (!matcher.matches()) {
				messages.add(DrinkMessage.ILLEGAL_DESCRIPTION);
			}
		}

		return messages;
	}

}
