/**
 * 
 */
package by.epamtc.coffee_machine.service.impl;

import java.util.List;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DAOProvider;
import by.epamtc.coffee_machine.service.DrinkService;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.utility.MenuParameter;
import by.epamtc.coffee_machine.utility.MenuPropertyProvider;
import by.epamtc.coffee_machine.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class DrinkServiceImpl implements DrinkService {
	private DAOProvider daoProvider = DAOProvider.getInstance();
	private MenuPropertyProvider menuPropertyProvider = MenuPropertyProvider.getInstance();

	@Override
	public Drink obtainDrink(int drink_id) throws ServiceException {
		Drink drink = null;
		if (!ValidationHelper.isPositive(drink_id)) {
			return drink;
		}

		try {
			drink = daoProvider.getDrinkDAO().read(drink_id);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return drink;
	}

	@Override
	public List<DrinkTransfer> obtainMenu(int pageNumber) throws ServiceException {
		List<DrinkTransfer> drinks = null;
		if (ValidationHelper.isNegative(pageNumber)) {
			return drinks;
		}

		int amount = Integer.parseInt(menuPropertyProvider.retrieveValue(MenuParameter.DRINKS_AMOUNT_PER_PAGE));
		int startIndex = 0;
		if (pageNumber > 1) {
			startIndex += (pageNumber - 1) * amount;
		}

		try {
			drinks = daoProvider.getDrinkDAO().obtainDrinks(startIndex, amount);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return drinks;
	}

	@Override
	public int obtainMenuPagesAmount() throws ServiceException {
		int generalDrinksAmount = 0;
		int drinksAmountPerPage = Integer
				.parseInt(menuPropertyProvider.retrieveValue(MenuParameter.DRINKS_AMOUNT_PER_PAGE));
		if (!ValidationHelper.isPositive(drinksAmountPerPage)) {
			throw new ServiceException(ValidationHelper.NEGATIVE_PARAM_EXCEPTION);
		}
		try {
			generalDrinksAmount = daoProvider.getDrinkDAO().obtainGeneralDrinksAmount();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		int addPage = generalDrinksAmount % drinksAmountPerPage > 0 ? 1 : 0;
		int result = generalDrinksAmount / drinksAmountPerPage + addPage;
		return result;
	}

}
