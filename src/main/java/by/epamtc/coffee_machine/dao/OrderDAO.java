/**
 * 
 */
package by.epamtc.coffee_machine.dao;

import by.epamtc.coffee_machine.bean.Order;
import by.epamtc.coffee_machine.bean.OrderInfo;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface OrderDAO extends GenericDAO<Order> {
	Order read(int order_id);

	@Override
	int add(Order order);

	boolean remove(int order_id);

	boolean update(int order_id, OrderInfo info);
}
