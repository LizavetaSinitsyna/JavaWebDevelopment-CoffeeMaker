package by.epamtc.coffee_machine.service;

import java.util.List;
import java.util.Set;

import by.epamtc.coffee_machine.bean.DrinkIngredient;
import by.epamtc.coffee_machine.bean.transfer.DrinkIngredientTransfer;

public interface DrinkIngredientService {
	List<DrinkIngredientTransfer> obtainIngredientsForSpecificDrink(long drinkId) throws ServiceException;

	Set<DrinkIngredientMessage> edit(long drinkId, List<DrinkIngredient> drinkIngredients) throws ServiceException;

}
