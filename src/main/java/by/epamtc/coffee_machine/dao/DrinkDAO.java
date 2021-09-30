/**
 * 
 */
package by.epamtc.coffee_machine.dao;

import java.util.List;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.DrinkInfo;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface DrinkDAO extends GenericDAO<Drink> {
	Drink read(int drink_id) throws DAOException;
	
	List<DrinkTransfer> obtainDrinks(int startIndex, int amount) throws DAOException;

	@Override
	int add(Drink drink);

	boolean remove(int drink_id);

	boolean update(int drink_id, DrinkInfo info);

	/**
	 * @return
	 * @throws DAOException 
	 */
	int obtainGeneralDrinksAmount() throws DAOException;
}
