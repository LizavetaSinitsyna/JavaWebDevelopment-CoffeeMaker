/**
 * 
 */
package by.epamtc.coffee_machine.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import by.epamtc.coffee_machine.bean.DrinkIngredient;
import by.epamtc.coffee_machine.bean.DrinkIngredientMap;
import by.epamtc.coffee_machine.bean.transfer.DrinkIngredientTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DAOProvider;
import by.epamtc.coffee_machine.service.DrinkIngredientMessage;
import by.epamtc.coffee_machine.service.DrinkIngredientService;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class DrinkIngredientServiceImpl implements DrinkIngredientService {
	private DAOProvider daoProvider = DAOProvider.getInstance();

	@Override
	public List<DrinkIngredientTransfer> obtainIngredientsForSpecificDrink(long drinkId) throws ServiceException {
		List<DrinkIngredientTransfer> result = null;
		if (!ValidationHelper.isPositive(drinkId)) {
			return result;
		}

		try {
			result = daoProvider.getDrinkIngredientDAO().readIngredientsForSpecificDrink(drinkId);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public Set<DrinkIngredientMessage> edit(long drinkId, List<DrinkIngredient> drinkIngredients)
			throws ServiceException {
		Set<DrinkIngredientMessage> messages = new HashSet<>();

		if (!ValidationHelper.isPositive(drinkId)) {
			messages.add(DrinkIngredientMessage.INVALID_DRINK_ID);
			return messages;
		}

		messages = validateFields(drinkIngredients);

		if (ValidationHelper.isNull(messages) || messages.isEmpty()) {
			DrinkIngredientMap drinkIngredientMap = new DrinkIngredientMap();
			drinkIngredientMap.setDrinkId(drinkId);
			drinkIngredientMap.setIngredients(drinkIngredients);
			try {
				int result = DAOProvider.getInstance().getDrinkIngredientDAO().update(drinkIngredientMap);
				if (!ValidationHelper.isPositive(result)) {
					messages.add(DrinkIngredientMessage.UNABLE_EDIT);
				}
			} catch (DAOException e) {
				throw new ServiceException(e.getMessage(), e);
			}
		}

		return messages;
	}

	private Set<DrinkIngredientMessage> validateFields(List<DrinkIngredient> drinkIngredients) {
		Set<DrinkIngredientMessage> messages = new HashSet<>();

		Set<Long> usedIngredients = new HashSet<>();

		if (ValidationHelper.isNull(drinkIngredients) || drinkIngredients.isEmpty()) {
			messages.add(DrinkIngredientMessage.ILLEGAL_DRINK_INGREDIENT_AMOUNT);
		} else {
			for (DrinkIngredient element : drinkIngredients) {
				long ingredientId = element.getIngredientId();
				if (!ValidationHelper.isPositive(ingredientId)) {
					messages.add(DrinkIngredientMessage.INVALID_INGREDIENT_ID);
					break;
				}
				if (!ValidationHelper.isPositive(element.getIngredientAmount())) {
					messages.add(DrinkIngredientMessage.ILLEGAL_INGREDIENT_AMOUNT);
					break;
				}
				if (usedIngredients.contains(ingredientId)) {
					messages.add(DrinkIngredientMessage.DUPLICATE_INGREDIENT);
					break;
				}
				usedIngredients.add(ingredientId);
			}
		}

		return messages;
	}

}
