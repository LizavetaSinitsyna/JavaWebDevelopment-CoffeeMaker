package by.epamtc.coffee_machine.dao;

import java.math.BigDecimal;
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

	/**
	 * Read existed order from database with specified order id.
	 * 
	 * @param orderId {@code long} value which uniquely indicates the order.
	 * @return{@code Order} with specified id or {@code null} if passed
	 *               {@code orderId} is invalid.
	 * @throws DAOException
	 */
	Order read(long orderId) throws DAOException;

	/**
	 * Performs payment operation and saves cashback.
	 * 
	 * @param orderId                       {@code long} value which uniquely
	 *                                      indicates the order under payment.
	 * @param accountBalanceToWithdraw      {@code BigDecimal} value which specifies
	 *                                      the amount to withdraw from user
	 *                                      account.
	 * @param bonusAccountBalanceToWithdraw {@code BigDecimal} value which specifies
	 *                                      the amount to withdraw from user bonus
	 *                                      account.
	 * @param cashback                      {@code BigDecimal} value which specifies
	 *                                      the amount of cashback to be saved on
	 *                                      bonus account.
	 * @return {@code true} If the payment was successful or {@code false}
	 *         otherwise.
	 * @throws DAOException
	 */
	boolean pay(long orderId, BigDecimal accountBalanceToWithdraw, BigDecimal bonusAccountBalanceToWithdraw,
			BigDecimal cashback) throws DAOException;

	/**
	 * Deletes the order with specified id.
	 * 
	 * @param orderId {@code long} value which uniquely indicates the order to be
	 *                deleted.
	 * @return {@code true} If the removal was successful or {@code false}
	 *         otherwise.
	 * @throws DAOException
	 */
	boolean delete(long orderId) throws DAOException;
}
