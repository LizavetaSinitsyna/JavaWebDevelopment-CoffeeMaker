/**
 * 
 */
package by.epamtc.coffee_machine.service.impl;

import java.util.List;

import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DAOProvider;
import by.epamtc.coffee_machine.service.OrderDrinkService;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.utility.MenuParameter;
import by.epamtc.coffee_machine.utility.MenuPropertyProvider;
import by.epamtc.coffee_machine.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class OrderDrinkServiceImpl implements OrderDrinkService {
	private DAOProvider daoProvider = DAOProvider.getInstance();
	private MenuPropertyProvider menuPropertyProvider = MenuPropertyProvider.getInstance();

	@Override
	public List<DrinkTransfer> selectPopularDrinks() throws ServiceException {
		List<DrinkTransfer> drinks = null;
		int amount;
		try {
			amount = Integer.parseInt(menuPropertyProvider.retrieveValue(MenuParameter.POPULAR_DRINKS_AMOUNT));
			if (ValidationHelper.isNegative(amount)) {
				throw new ServiceException(ValidationHelper.NEGATIVE_PARAM_EXCEPTION);
			}
			drinks = daoProvider.getOrderDrinkDAO().selectPopularDrinks(amount);
		} catch (DAOException | NumberFormatException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return drinks;
	}

}
