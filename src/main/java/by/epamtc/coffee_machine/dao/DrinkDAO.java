/**
 * 
 */
package by.epamtc.coffee_machine.dao;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.DrinkInfo;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface DrinkDAO extends GenericDAO<Drink> {
	Drink read(int drink_id);

	@Override
	int add(Drink drink);

	boolean remove(int drink_id);

	boolean update(int drink_id, DrinkInfo info);
}
