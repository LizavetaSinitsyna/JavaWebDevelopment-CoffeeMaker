package by.epamtc.coffee_machine.service.impl;

import java.util.List;

import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DAOProvider;
import by.epamtc.coffee_machine.service.CommonExceptionMessage;
import by.epamtc.coffee_machine.service.OrderDrinkService;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.utility.MenuParameter;
import by.epamtc.coffee_machine.service.utility.MenuPropertyProvider;

/**
 * Provides access to {@link by.epamtc.coffee_machine.dao.OrderDrinkDAO} and
 * support for working with entity {@link by.epamtc.coffee_machine.bean.OrderDrink}
 */
public class OrderDrinkServiceImpl implements OrderDrinkService {
	private DAOProvider daoProvider = DAOProvider.getInstance();
	private MenuPropertyProvider menuPropertyProvider = MenuPropertyProvider.getInstance();

	/**
	 * Select popular drinks using amount of drinks to be selected provided by
	 * {@link MenuPropertyProvider}.
	 * 
	 * @return {@code List} of {@code DrinkTransfer} objects representing popular
	 *         drinks.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer
	 *                          or popular drinks' amount is invalid.
	 */
	@Override
	public List<DrinkTransfer> selectPopularDrinks() throws ServiceException {
		List<DrinkTransfer> drinks = null;
		int amount;
		try {
			amount = Integer.parseInt(menuPropertyProvider.retrieveValue(MenuParameter.POPULAR_DRINKS_AMOUNT));
			if (amount < 0) {
				throw new ServiceException(CommonExceptionMessage.NEGATIVE_PARAM);
			}
			drinks = daoProvider.getOrderDrinkDAO().selectPopularDrinks(amount);
		} catch (DAOException | NumberFormatException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return drinks;
	}

}
