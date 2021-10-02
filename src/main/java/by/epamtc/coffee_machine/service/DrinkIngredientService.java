/**
 * 
 */
package by.epamtc.coffee_machine.service;

import java.util.List;

import by.epamtc.coffee_machine.bean.transfer.DrinkIngredientTransfer;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface DrinkIngredientService {
	List<DrinkIngredientTransfer> obtainIngredientsForSpecificDrink(int drink_id) throws ServiceException;

}
