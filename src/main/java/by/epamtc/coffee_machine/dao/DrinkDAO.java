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
	Drink read(long drinkId) throws DAOException;

	List<DrinkTransfer> obtainDrinks(int startIndex, int amount) throws DAOException;

	boolean remove(long drinkId) throws DAOException;

	int update(Drink drink) throws DAOException;

	int obtainGeneralDrinksAmount() throws DAOException;

}
