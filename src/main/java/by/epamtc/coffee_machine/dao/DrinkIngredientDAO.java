/**
 * 
 */
package by.epamtc.coffee_machine.dao;

import java.util.List;

import by.epamtc.coffee_machine.bean.DrinkIngredient;
import by.epamtc.coffee_machine.bean.DrinkIngredientMap;
import by.epamtc.coffee_machine.bean.transfer.DrinkIngredientTransfer;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface DrinkIngredientDAO extends GenericDAO<DrinkIngredient> {
	List<DrinkIngredient> findDrinksWithSpecificIngredient(long ingredientId) throws DAOException;

	List<DrinkIngredientTransfer> readIngredientsForSpecificDrink(long drinkId) throws DAOException;

	boolean remove(DrinkIngredient drinkIngredient) throws DAOException;

	
	int update(DrinkIngredientMap drinkIngredientMap) throws DAOException;
}
