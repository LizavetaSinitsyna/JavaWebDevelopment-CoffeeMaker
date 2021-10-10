/**
 * 
 */
package by.epamtc.coffee_machine.dao;

import java.util.List;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface DrinkDAO extends GenericDAO<Drink> {
	Drink read(int drink_id) throws DAOException;

	List<DrinkTransfer> obtainDrinks(int startIndex, int amount) throws DAOException;

	boolean remove(int drink_id);

	int update(Drink drink) throws DAOException;

	/**
	 * @return
	 * @throws DAOException
	 */
	int obtainGeneralDrinksAmount() throws DAOException;

	/**
	 * @param drinkId
	 * @return
	 * @throws DAOException
	 */
	String obtainDrinkName(int drinkId) throws DAOException;
}
