package by.epamtc.coffee_machine.dao;

import java.util.List;

import by.epamtc.coffee_machine.bean.OrderDrink;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;

public interface OrderDrinkDAO {
	List<OrderDrink> findDrinksForSpecificOrder(int ingredient_id);

	List<OrderDrink> findOrdersWithSpecificDrink(int drink_id);

	boolean remove(OrderDrink orderDrink);

	boolean update(OrderDrink orderDrink, int amount);
	
	List<DrinkTransfer> selectPopularDrinks(int amount) throws DAOException;

	long add(OrderDrink orderDrink) throws DAOException;
}
