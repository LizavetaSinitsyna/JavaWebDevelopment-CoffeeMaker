/**
 * 
 */
package by.epamtc.coffee_machine.service.impl;

import java.util.List;

import by.epamtc.coffee_machine.bean.transfer.DrinkIngredientTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DAOProvider;
import by.epamtc.coffee_machine.service.DrinkIngredientService;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class DrinkIngredientServiceImpl implements DrinkIngredientService {
	private DAOProvider daoProvider = DAOProvider.getInstance();

	@Override
	public List<DrinkIngredientTransfer> obtainIngredientsForSpecificDrink(int drink_id) throws ServiceException {
		List<DrinkIngredientTransfer> result = null;
		if (!ValidationHelper.isPositive(drink_id)) {
			return result;
		}
		
		try {
			result = daoProvider.getDrinkIngredientDAO().readIngredientsForSpecificDrink(drink_id);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return result;
	}

}
