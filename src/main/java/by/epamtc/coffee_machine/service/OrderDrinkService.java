package by.epamtc.coffee_machine.service;

import java.util.List;

import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;

/**
 * Provides support for working with entity
 * {@link by.epamtc.coffee_machine.bean.OrderDrink}
 */
public interface OrderDrinkService {
	/**
	 * Select popular drinks.
	 * 
	 * @return {@code List} of {@code DrinkTransfer} objects representing popular
	 *         drinks.
	 * @throws ServiceException
	 */
	public List<DrinkTransfer> selectPopularDrinks() throws ServiceException;

}
