/**
 * 
 */
package by.epamtc.coffee_machine.service;

import java.util.List;
import java.util.Set;

import by.epamtc.coffee_machine.bean.DrinkIngredient;
import by.epamtc.coffee_machine.bean.transfer.DrinkIngredientTransfer;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface DrinkIngredientService {
	List<DrinkIngredientTransfer> obtainIngredientsForSpecificDrink(int drink_id) throws ServiceException;

	/**
	 * @param drinkId
	 * @param drinkIngredients
	 * @return
	 * @throws ServiceException
	 */
	Set<DrinkIngredientMessage> edit(int drinkId, List<DrinkIngredient> drinkIngredients) throws ServiceException;

}
