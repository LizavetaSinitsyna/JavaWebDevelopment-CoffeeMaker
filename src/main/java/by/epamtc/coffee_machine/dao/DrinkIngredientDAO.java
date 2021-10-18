package by.epamtc.coffee_machine.dao;

import java.util.List;

import by.epamtc.coffee_machine.bean.DrinkIngredient;
import by.epamtc.coffee_machine.bean.DrinkIngredientMap;
import by.epamtc.coffee_machine.bean.transfer.DrinkIngredientTransfer;


public interface DrinkIngredientDAO {
	List<DrinkIngredient> findDrinksWithSpecificIngredient(long ingredientId) throws DAOException;

	List<DrinkIngredientTransfer> readIngredientsForSpecificDrink(long drinkId) throws DAOException;

	boolean remove(DrinkIngredient drinkIngredient) throws DAOException;

	
	boolean update(DrinkIngredientMap drinkIngredientMap) throws DAOException;

	long add(DrinkIngredient drinkIngredient);
}
