package by.epamtc.coffee_machine.dao;

import java.util.List;

import by.epamtc.coffee_machine.bean.DrinkIngredientMap;
import by.epamtc.coffee_machine.bean.transfer.DrinkIngredientTransfer;

/**
 * Provides methods for working with DrinkIngredients table and entities
 * {@link DrinkIngredientMap}, {@link DrinkIngredientTransfer},
 * {@link by.epamtc.coffee_machine.bean.DrinkIngredient}
 */
public interface DrinkIngredientDAO {
	/**
	 * Read ingredients from database for specified drink by drink id.
	 * 
	 * @param drinkId {@code long} value which uniquely indicates the drink.
	 * @return {@code List} of {@code DrinkIngredientTransfer} objects representing
	 *         all ingredients for specified drink.
	 * @throws DAOException
	 */
	List<DrinkIngredientTransfer> readIngredientsForSpecificDrink(long drinkId) throws DAOException;

	/**
	 * Updates ingredients for specified id taking drink id from passed
	 * {@code DrinkIngredientMap}.
	 * 
	 * @param drinkIngredientMap the {@code DrinkIngredientMap} object. Ingredients
	 *                           of the already existed in database drink will be
	 *                           replaced by the ingredients of passed
	 *                           drinkIngredientMap.
	 * @return {@code true} If the update was successful or {@code false} if update
	 *         was failed.
	 * @throws DAOException
	 */
	boolean update(DrinkIngredientMap drinkIngredientMap) throws DAOException;
}
