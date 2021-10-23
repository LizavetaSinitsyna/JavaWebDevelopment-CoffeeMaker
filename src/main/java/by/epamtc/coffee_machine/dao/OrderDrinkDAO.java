package by.epamtc.coffee_machine.dao;

import java.util.List;

import by.epamtc.coffee_machine.bean.OrderDrink;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;

/**
 * Provides methods for working with OrderDrinks table and entity
 * {@link OrderDrink}
 */
public interface OrderDrinkDAO {
	/**
	 * Select specified amount of popular drinks.
	 * 
	 * @return {@code List} of {@code DrinkTransfer} objects representing popular
	 *         drinks.
	 * @throws DAOException
	 */
	List<DrinkTransfer> selectPopularDrinks(int amount) throws DAOException;

	/**
	 * Add passed OrderDrink to database.
	 * 
	 * @param orderDrink the {@code OrderDrink} object to be saved in database.
	 * @return {@code long} value which represents OrderDrink id.
	 * @throws DAOException
	 */

	long add(OrderDrink orderDrink) throws DAOException;
}
