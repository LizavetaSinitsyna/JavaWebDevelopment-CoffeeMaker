package by.epamtc.coffee_machine.service.impl;

import java.util.List;

import by.epamtc.coffee_machine.bean.transfer.IngredientTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DAOProvider;
import by.epamtc.coffee_machine.service.IngredientService;
import by.epamtc.coffee_machine.service.ServiceException;

/**
 * Provides access to {@link by.epamtc.coffee_machine.dao.IngredientDAO} and
 * support for working with entities {@link Ingredient},
 * {@link IngredientTransfer}
 */
public class IngredientServiceImpl implements IngredientService {
	private DAOProvider daoProvider = DAOProvider.getInstance();

	/**
	 * Obtains all existed ingredients.
	 * 
	 * @return {@code List} of {@code IngredientTransfer} objects representing all
	 *         existed ingredients
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */
	@Override
	public List<IngredientTransfer> obtainAllIngredients() throws ServiceException {
		List<IngredientTransfer> ingredients = null;
		try {
			ingredients = daoProvider.getIngredientDAO().readAll();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return ingredients;
	}

}
