package by.epamtc.coffee_machine.dao;

import java.time.OffsetDateTime;

import by.epamtc.coffee_machine.bean.Order;
import by.epamtc.coffee_machine.bean.OrderStatus;
import by.epamtc.coffee_machine.bean.transfer.OrderTransfer;

/**
 * Provides methods for working with Orders table and entities {@link Order},
 * {@link OrderTransfer}
 */
public interface OrderDAO {
	/**
	 * Removes orders with expired time and passed status.
	 * 
	 * @param dateTime the {@code OffsetDateTime} value representing point in time
	 *                 in comparison with which older orders will be deleted.
	 * @param status   the {@code OrderStatus} value representing status with which
	 *                 old orders will be deleted.
	 * @throws DAOException If problem occurs during interaction with database.
	 */
	void removeExpiredOrders(OffsetDateTime dateTime, OrderStatus status) throws DAOException;

	/**
	 * Add passed order to database.
	 * 
	 * @param order the order to be saved in database.
	 * @return {@code long} value which represents order id.
	 * @throws DAOException
	 */
	long add(OrderTransfer order) throws DAOException;
}
