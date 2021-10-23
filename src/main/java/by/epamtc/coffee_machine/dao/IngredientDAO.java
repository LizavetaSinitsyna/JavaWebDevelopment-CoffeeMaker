package by.epamtc.coffee_machine.dao;

import java.util.List;

import by.epamtc.coffee_machine.bean.Ingredient;
import by.epamtc.coffee_machine.bean.transfer.IngredientTransfer;

/**
 * Provides methods for working with Ingredients table and entities
 * {@link Ingredient}, {@link IngredientTransfer}
 */
public interface IngredientDAO {
	/**
	 * Obtains existed ingredient with specified ingredient id.
	 * 
	 * @param ingredientId {@code long} value which uniquely indicates the
	 *                     ingredient.
	 * @return {@code Ingredient} with specified id.
	 * @throws DAOException
	 */
	Ingredient read(long ingredientId) throws DAOException;

	/**
	 * Returns all ingredients from database.
	 *
	 * @return {@code List} of {@code IngredientTransfer} objects representing all
	 *         ingredients from database.
	 * @throws DAOException
	 */
	List<IngredientTransfer> readAll() throws DAOException;

	/**
	 * Returns Ingredient from database with available for new order amount.
	 *
	 * @return {@code Ingredient} representing available amount.
	 * @throws DAOException
	 */
	Ingredient readAvailable(long ingredientId) throws DAOException;

}
