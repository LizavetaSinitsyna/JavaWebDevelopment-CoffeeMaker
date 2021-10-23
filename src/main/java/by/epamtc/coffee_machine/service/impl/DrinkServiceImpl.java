package by.epamtc.coffee_machine.service.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.DrinkInfo;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DAOProvider;
import by.epamtc.coffee_machine.service.DrinkService;
import by.epamtc.coffee_machine.service.CommonExceptionMessage;
import by.epamtc.coffee_machine.service.DrinkMessage;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.utility.MenuParameter;
import by.epamtc.coffee_machine.service.utility.MenuPropertyProvider;
import by.epamtc.coffee_machine.service.validation.DrinkValidator;

/**
 * Provides access to {@link by.epamtc.coffee_machine.dao.DrinkDAO} and support
 * for working with entities {@link Drink}, {@link DrinkInfo},
 * {@link DrinkTransfer}.
 */
public class DrinkServiceImpl implements DrinkService {
	private static final DAOProvider DAO_PROVIDER = DAOProvider.getInstance();
	private static final MenuPropertyProvider MENU_PROPERTY_PROVIDER = MenuPropertyProvider.getInstance();

	/**
	 * Obtains existed drink by its id.
	 * 
	 * @param drinkId {@code long} value which uniquely indicates the drink.
	 * @return {@code Drink} with specified id or {@code null} if {@code drinkId} is
	 *         invalid.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */
	@Override
	public Drink obtainDrink(long drinkId) throws ServiceException {
		Drink drink = null;
		if (drinkId <= 0) {
			return drink;
		}

		try {
			drink = DAO_PROVIDER.getDrinkDAO().read(drinkId);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return drink;
	}

	/**
	 * Obtains drinks for showing on specified page.
	 * 
	 * @param pageNumber {@code int} value which represents page number.
	 * @return {@code List} of {@code DrinkTransfer} objects representing drinks for
	 *         the specified page or {@code null} if {@code pageNumber} is invalid.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */

	@Override
	public List<DrinkTransfer> obtainMenu(int pageNumber) throws ServiceException {
		List<DrinkTransfer> drinks = null;
		if (pageNumber <= 0) {
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

	/**
	 * Calculates general amount of menu pages using drinks' amount per page
	 * provided by {@link MenuPropertyProvider}.
	 * 
	 * @return {@code int} value representing amount of pages.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer
	 *                          or drinks' amount per page is invalid.
	 */

	@Override
	public int obtainMenuPagesAmount() throws ServiceException {
		int generalDrinksAmount = 0;
		int drinksAmountPerPage = Integer
				.parseInt(MENU_PROPERTY_PROVIDER.retrieveValue(MenuParameter.DRINKS_AMOUNT_PER_PAGE));
		if (drinksAmountPerPage < 0) {
			throw new ServiceException(CommonExceptionMessage.NEGATIVE_PARAM);
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

	/**
	 * Edit existed Drink.
	 * 
	 * @param imagePath   {@code String} value representing new path to image.
	 * @param drinkId     {@code long} value which uniquely indicates the existed
	 *                    drink.
	 * @param price       {@code BigDecimal} value representing new price.
	 * @param description {@code String} value representing new description.
	 * @return {@code Set} of {@link DrinkMessage} objects.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */
	@Override
	public Set<DrinkMessage> edit(String imagePath, long drinkId, BigDecimal price, String description)
			throws ServiceException {
		Set<DrinkMessage> messages = new HashSet<>();

		if (drinkId <= 0) {
			messages.add(DrinkMessage.INVALID_DRINK_ID);
			return messages;
		}

		messages = DrinkValidator.validateFields(imagePath, price, description);

		if (messages == null || messages.isEmpty()) {
			Drink drink = new Drink();
			DrinkInfo info = new DrinkInfo();
			info.setDescription(description);
			info.setImagePath(imagePath);
			info.setPrice(price);
			drink.setId(drinkId);
			drink.setInfo(info);
			try {
				boolean result = DAOProvider.getInstance().getDrinkDAO().update(drink);
				if (!result) {
					messages.add(DrinkMessage.UNABLE_EDIT);
				}
			} catch (DAOException e) {
				throw new ServiceException(e.getMessage(), e);
			}
		}

		return messages;
	}

}
