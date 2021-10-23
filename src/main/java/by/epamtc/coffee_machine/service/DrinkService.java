package by.epamtc.coffee_machine.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;

/**
 * Provides support for working with entities {@link Drink},
 * {@link DrinkTransfer}.
 */
public interface DrinkService {
	/**
	 * Obtains drinks for showing on specified page.
	 * 
	 * @param pageNumber {@code int} value which represents page number.
	 * @return {@code List} of {@code DrinkTransfer} objects representing drinks for
	 *         the specified page.
	 * @throws ServiceException
	 */
	List<DrinkTransfer> obtainMenu(int pageNumber) throws ServiceException;

	/**
	 * Obtains existed drink by its id.
	 * 
	 * @param drinkId {@code long} value which uniquely indicates the drink.
	 * @return {@code Drink} with specified id.
	 * @throws ServiceException
	 */
	Drink obtainDrink(long drinkId) throws ServiceException;

	/**
	 * Calculates general amount of menu pages.
	 * 
	 * @return {@code int} value representing amount of pages.
	 * @throws ServiceException
	 */
	int obtainMenuPagesAmount() throws ServiceException;

	/**
	 * Edit existed Drink.
	 * 
	 * @param imagePath   {@code String} value representing new path to image.
	 * @param drinkId     {@code long} value which uniquely indicates the existed
	 *                    drink.
	 * @param price       {@code BigDecimal} value representing new price.
	 * @param description {@code String} value representing new description.
	 * @return {@code Set} of {@link DrinkMessage} objects.
	 * @throws ServiceException
	 */
	Set<DrinkMessage> edit(String imagePath, long drinkId, BigDecimal price, String description)
			throws ServiceException;

}
