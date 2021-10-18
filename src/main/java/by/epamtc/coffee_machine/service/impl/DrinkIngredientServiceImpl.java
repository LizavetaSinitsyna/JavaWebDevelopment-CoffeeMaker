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
import by.epamtc.coffee_machine.service.validation.IngredientValidator;

public class DrinkIngredientServiceImpl implements DrinkIngredientService {
	private DAOProvider daoProvider = DAOProvider.getInstance();

	@Override
	public List<DrinkIngredientTransfer> obtainIngredientsForSpecificDrink(long drinkId) throws ServiceException {
		List<DrinkIngredientTransfer> result = null;
		if (drinkId <= 0) {
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

		if (drinkId <= 0) {
			messages.add(DrinkIngredientMessage.INVALID_DRINK_ID);
			return messages;
		}

		messages = IngredientValidator.validateFields(drinkIngredients);

		if (messages == null || messages.isEmpty()) {
			DrinkIngredientMap drinkIngredientMap = new DrinkIngredientMap();
			drinkIngredientMap.setDrinkId(drinkId);
			drinkIngredientMap.setIngredients(drinkIngredients);
			try {
				boolean result = DAOProvider.getInstance().getDrinkIngredientDAO().update(drinkIngredientMap);
				if (!result) {
					messages.add(DrinkIngredientMessage.UNABLE_EDIT);
				}
			} catch (DAOException e) {
				throw new ServiceException(e.getMessage(), e);
			}
		}

		return messages;
	}
}
