/**
 * 
 */
package by.epamtc.coffee_machine.dao;

import java.util.Map;

import by.epamtc.coffee_machine.bean.Order;
import by.epamtc.coffee_machine.bean.OrderInfo;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface OrderDAO {
	Order read(int order_id);

	boolean remove(int order_id);

	boolean update(int order_id, OrderInfo info);

	/**
	 * @param userId
	 * @param drinksAmount
	 * @return
	 */
	int add(int userId, Map<Integer, Integer> drinksAmount);
}
