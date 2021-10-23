package by.epamtc.coffee_machine.dao;

import java.util.List;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;

/**
 * Provides methods for working with Drinks table and entities {@link Drink},
 * {@link DrinkTransfer}
 */
public interface DrinkDAO {
	/**
	 * Obtains existed drink with specified drink id.
	 * 
	 * @param drinkId {@code long} value which uniquely indicates the drink.
	 * @return {@code Drink} with specified id.
	 * @throws DAOException
	 */
	Drink read(long drinkId) throws DAOException;

	/**
	 * Obtains specified amount of drinks starting from indicated row.
	 * 
	 * @param startIndex the begin index, inclusive.
	 * @param amount     the amount of drinks to return.
	 * @return {@code List} of {@code DrinkTransfer} objects representing specified
	 *         {@code amount} of drinks starting from {@code startIndex}
	 * @throws DAOException
	 */
	List<DrinkTransfer> obtainDrinks(int startIndex, int amount) throws DAOException;

	/**
	 * Updates existed Drink.
	 * 
	 * @param drink {@code Drink} value which contains drink id of existed drink.
	 *              The field of the already existed in database drink will be
	 *              replaced by the fields of passed drink.
	 * 
	 * @return {@code true} If the update was successful or {@code false} if update
	 *         was failed.
	 * @throws DAOException
	 */
	boolean update(Drink drink) throws DAOException;

	/**
	 * Returns general amount of drinks saved in database.
	 * 
	 * @return {@code int} value representing general amount of drinks.
	 * @throws DAOException
	 */
	int obtainGeneralDrinksAmount() throws DAOException;

}
