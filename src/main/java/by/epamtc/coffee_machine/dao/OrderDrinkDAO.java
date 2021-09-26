/**
 * 
 */
package by.epamtc.coffee_machine.dao;

import java.util.List;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.OrderDrink;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface OrderDrinkDAO extends GenericDAO<OrderDrink> {
	List<OrderDrink> findDrinksForSpecificOrder(int ingredient_id);

	List<OrderDrink> findOrdersWithSpecificDrink(int drink_id);

	@Override
	int add(OrderDrink orderDrink);

	boolean remove(OrderDrink orderDrink);

	boolean update(OrderDrink orderDrink, int amount);
	
	List<Drink> selectPopularDrinks(int amount) throws DAOException;
}
