package by.epamtc.coffee_machine.service;

import java.util.List;
import java.util.Set;

import by.epamtc.coffee_machine.bean.DrinkIngredient;
import by.epamtc.coffee_machine.bean.transfer.DrinkIngredientTransfer;

/**
 * Provides support for working with entities {@link DrinkIngredient},
 * {@link DrinkIngredientTransfer}
 */
public interface DrinkIngredientService {
	/**
	 * Obtains all ingredients for specified drink.
	 * 
	 * @param drinkId {@code long} value which uniquely indicates the drink.
	 * @return {@code List} of {@code DrinkIngredientTransfer} objects representing
	 *         ingredients for the specified drink.
	 * @throws ServiceException
	 */
	List<DrinkIngredientTransfer> obtainIngredientsForSpecificDrink(long drinkId) throws ServiceException;

	/**
	 * Edit ingredients for specified drink.
	 * 
	 * @param drinkId          {@code long} value which uniquely indicates the
	 *                         drink.
	 * @param drinkIngredients {@code List} of {@code DrinkIngredient} objects which
	 *                         represent new drink's composition.
	 * @return {@code Set} of {@link DrinkIngredientMessage} objects.
	 * @throws ServiceException
	 */

	Set<DrinkIngredientMessage> edit(long drinkId, List<DrinkIngredient> drinkIngredients) throws ServiceException;

}
