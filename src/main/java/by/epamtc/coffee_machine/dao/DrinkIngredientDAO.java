/**
 * 
 */
package by.epamtc.coffee_machine.dao;

import java.util.List;

import by.epamtc.coffee_machine.bean.DrinkIngredient;
import by.epamtc.coffee_machine.bean.transfer.DrinkIngredientTransfer;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface DrinkIngredientDAO extends GenericDAO<DrinkIngredient> {
	List<DrinkIngredient> findDrinksWithSpecificIngredient(int ingredient_id);

	List<DrinkIngredientTransfer> readIngredientsForSpecificDrink(int drink_id) throws DAOException;

	@Override
	int add(DrinkIngredient drinkIngredient);

	DrinkIngredient read(int drink_id, int ingredient_id);

	boolean remove(DrinkIngredient drinkIngredient);

	boolean update(DrinkIngredient drinkIngredient, int amount);
}
