/**
 * 
 */
package by.epamtc.coffee_machine.service.impl;

import java.util.List;

import by.epamtc.coffee_machine.bean.transfer.IngredientTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DAOProvider;
import by.epamtc.coffee_machine.service.IngredientService;
import by.epamtc.coffee_machine.service.ServiceException;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class IngredientServiceImpl implements IngredientService {
	private DAOProvider daoProvider = DAOProvider.getInstance();
	//private static final String INGREDIENT_NAME_REGEX = ".{1,150}";

	@Override
	public List<IngredientTransfer> obtainAllIngredients() throws ServiceException {
		List<IngredientTransfer> ingredients = null;
		try {
		ingredients = daoProvider.getIngredientDAO().readAll();
		} catch(DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return ingredients;
	}

}
