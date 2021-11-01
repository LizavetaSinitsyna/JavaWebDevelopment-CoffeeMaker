package by.epamtc.coffee_machine.service;

import java.util.List;

import by.epamtc.coffee_machine.bean.Order;
import by.epamtc.coffee_machine.bean.transfer.DrinkIngredientTransfer;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;
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

	/**
	 * Performs payment for the order operation.
	 * 
	 * @param orderId {@code long} value which uniquely indicates the order.
	 * @param userId  {@code long} value which uniquely indicates the user who made
	 *                the order.
	 * @return {@code true} if payment was successful or {@code false} otherwise.
	 * @throws ServiceException
	 */
	boolean pay(long orderId, long userId) throws ServiceException;

	/**
	 * 
	 * Obtains order by specified id.
	 * 
	 * @param orderId {@code long} value which uniquely indicates the order.
	 * @return {@code Order} with specified id.
	 * @throws ServiceException
	 */
	Order read(long orderId) throws ServiceException;

	/**
	 * 
	 * Deletes order with specified id.
	 * 
	 * @param orderId {@code long} value which uniquely indicates the order.
	 * @return {@code true} if removal was successful or {@code false} otherwise.
	 * @throws ServiceException
	 */
	boolean cancel(long orderId) throws ServiceException;

	/**
	 * Obtains all ingredients for specified drink.
	 * 
	 * @param drinkId {@code long} value which uniquely indicates the drink.
	 * @return {@code List} of {@code DrinkIngredientTransfer} objects representing
	 *         ingredients for the specified drink or {@code null} if drinkId is
	 *         invalid.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */
	List<DrinkIngredientTransfer> obtainIngredientsForSpecificDrink(long drinkId) throws ServiceException;

	/**
	 * Select popular drinks using amount of drinks to be selected provided by
	 * {@link MenuPropertyProvider}.
	 * 
	 * @return {@code List} of {@code DrinkTransfer} objects representing popular
	 *         drinks.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer
	 *                          or popular drinks' amount is invalid.
	 */
	List<DrinkTransfer> selectPopularDrinks() throws ServiceException;

}
