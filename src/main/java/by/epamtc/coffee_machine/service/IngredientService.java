package by.epamtc.coffee_machine.service;

import java.util.List;

import by.epamtc.coffee_machine.bean.Ingredient;
import by.epamtc.coffee_machine.bean.transfer.IngredientTransfer;

/**
 * Provides support for working with entities {@link Ingredient},
 * {@link IngredientTransfer}
 */
public interface IngredientService {
	/**
	 * Obtains all existed ingredients.
	 * 
	 * @return {@code List} of {@code IngredientTransfer} objects representing all
	 *         existed ingredients
	 * @throws ServiceException
	 */
	List<IngredientTransfer> obtainAllIngredients() throws ServiceException;
}
