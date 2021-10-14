/**
 * 
 */
package by.epamtc.coffee_machine.dao;

import java.time.OffsetDateTime;
import java.util.Map;

import by.epamtc.coffee_machine.bean.Order;
import by.epamtc.coffee_machine.bean.OrderInfo;
import by.epamtc.coffee_machine.bean.OrderStatus;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface OrderDAO {
	Order read(long orderId) throws DAOException;

	boolean remove(long orderId) throws DAOException;

	boolean update(long orderId, OrderInfo info) throws DAOException;

	int add(long userId, Map<Long, Integer> drinksAmount, OffsetDateTime dateTime) throws DAOException;

	/**
	 * @param dateTime
	 * @return
	 * @throws DAOException
	 */
	void removeExpiredOrders(OffsetDateTime dateTime, OrderStatus status) throws DAOException;
}
