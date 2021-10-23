package by.epamtc.coffee_machine.service;

import by.epamtc.coffee_machine.bean.transfer.OrderTransfer;

/**
 * Provides support for working with entity {@link OrderTransfer}.
 */
public interface OrderService {
	/**
	 * Removes unpaid orders.
	 * 
	 * @throws ServiceException
	 */
	void removeUnpaidOrders() throws ServiceException;

	/**
	 * Creates new Order.
	 * 
	 * @param drinksId     Array of {@code String} values which contains id of
	 *                     drinks for the order.
	 * @param drinksAmount Array of {@code String} values which contains amount of
	 *                     each drink in the order.
	 * @param userId       {@code long} value which uniquely indicates the user who
	 *                     made the order.
	 * @return {@code OrderTransfer} object representing the created Order.
	 * @throws ServiceException
	 */
	OrderTransfer placeOrder(String[] drinksId, String[] drinksAmount, long userId) throws ServiceException;

}
