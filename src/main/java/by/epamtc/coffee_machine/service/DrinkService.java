package by.epamtc.coffee_machine.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.DrinkIngredient;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;
import by.epamtc.coffee_machine.bean.transfer.DrinkMessageTransfer;

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
	 * @param realPathForImage {@code String} value representing path to image
	 *                         folder.
	 * @param imageContent     {@code InputStream} value representing stream with
	 *                         image content.
	 * @param imageName        {@code String} value representing image name with
	 *                         extension.
	 * @param drinkId          {@code long} value which uniquely indicates the
	 *                         existed drink.
	 * @param price            {@code BigDecimal} value representing new price.
	 * @param description      {@code String} value representing new description.
	 * @return {@code Set} of {@link DrinkMessage} objects.
	 * @throws ServiceException
	 */
	Set<DrinkMessage> editDrink(String realPathForImage, InputStream imageContent, String imageName, long drinkId,
			BigDecimal price, String description) throws ServiceException;

	/**
	 * Creates drink with passed parameters.
	 * 
	 * @param realPathForImage {@code String} value representing path to image
	 *                         folder.
	 * @param imageContent     {@code InputStream} value representing stream with
	 *                         image content.
	 * @param imageName        {@code String} value representing image name with
	 *                         extension.
	 * @param drinkName        {@code String} value representing drink name.
	 * @param price            {@code BigDecimal} value representing new price.
	 * @param description      {@code String} value representing new description.
	 *                         drinkIngredients {@code List} of
	 * @param drinkIngredients {@code DrinkIngredient} objects which represent drink
	 *                         composition.
	 * @return {@code DrinkMessageTransfer} object.
	 * @throws ServiceException
	 */
	DrinkMessageTransfer add(String realPathForImage, InputStream imageContent, String imageName, String drinkName,
			BigDecimal price, String description, List<DrinkIngredient> drinkIngredients) throws ServiceException;

	/**
	 * Edit ingredients for specified drink.
	 * 
	 * @param drinkId          {@code long} value which uniquely indicates the
	 *                         drink.
	 * @param drinkIngredients {@code List} of {@code DrinkIngredient} objects which
	 *                         represent new drink composition.
	 * @return {@code Set} of {@link DrinkIngredientMessage} objects.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */
	Set<DrinkIngredientMessage> editDrinkComposition(long drinkId, List<DrinkIngredient> drinkIngredients)
			throws ServiceException;

}
