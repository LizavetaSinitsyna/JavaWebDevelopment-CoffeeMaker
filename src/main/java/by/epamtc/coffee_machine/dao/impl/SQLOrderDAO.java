package by.epamtc.coffee_machine.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import by.epamtc.coffee_machine.bean.Order;
import by.epamtc.coffee_machine.bean.OrderDrink;
import by.epamtc.coffee_machine.bean.OrderInfo;
import by.epamtc.coffee_machine.bean.OrderStatus;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;
import by.epamtc.coffee_machine.bean.transfer.OrderTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.OrderDAO;
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.service.CommonExceptionMessage;
import by.epamtc.coffee_machine.service.utility.DecimalExchange;
import by.epamtc.coffee_machine.service.utility.OrderParameter;
import by.epamtc.coffee_machine.service.utility.OrderPropertyProvider;
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPool;

/**
 * Provides methods for working with Orders table and entities {@link Order},
 * {@link OrderTransfer}
 */
public class SQLOrderDAO implements OrderDAO {
	private static final ConnectionPool CONNECTION_POOL = ConnectionPool.retrieveConnectionPool();
	private static final String REMOVE_EXPIRED_ORDERS_QUERY = "DELETE from orders WHERE date_time <= \"%s\" AND status = \"%s\"";
	private static final String ADD_QUERY = "INSERT INTO orders (user_id, date_time, status, cost) VALUES (%s, \"%s\", \"%s\", %s)";
	private static final String ADD_ORDER_DRINK_QUERY = "INSERT INTO order_drinks (order_id, drink_id, drink_count) VALUES (%s, %s, %s)";
	private static final String SELECT_DRINK_INGREDIENTS_QUERY = "SELECT ingredient_id, SUM(ingredient_amount) * drink_count AS used_amount "
			+ "FROM drink_ingredients INNER JOIN order_drinks ON drink_ingredients.drink_id = order_drinks.drink_id "
			+ "WHERE order_id = %s GROUP BY ingredient_id";
	private static final String SELECT_ORDER_QUERY = "SELECT * FROM orders WHERE order_id = %s";
	private static final String UPDATE_INGREDIENT_AMOUNT_QUERY = "UPDATE ingredients SET current_amount = current_amount - %s WHERE ingredient_id = %s";
	private static final String UPDATE_ACCOUNT_BALANCE_QUERY = "UPDATE accounts SET balance = balance - %s WHERE account_id = "
			+ "(SELECT account_id FROM users INNER JOIN orders ON users.user_id = orders.user_id WHERE order_id = %s)";
	private static final String UPDATE_BONUS_ACCOUNT_BALANCE_QUERY = "UPDATE bonus_accounts SET balance = balance - %s "
			+ "WHERE bonus_account_id = (SELECT bonus_account_id FROM users INNER JOIN orders ON users.user_id = orders.user_id WHERE order_id = %s)";
	private static final String CHANGE_ORDER_STATUS_QUERY = "UPDATE orders SET status = \"%s\" WHERE order_id = %s";
	private static final String CASHBACK_QUERY = "UPDATE bonus_accounts SET balance = balance + %s "
			+ "WHERE bonus_account_id = (SELECT bonus_account_id FROM users INNER JOIN orders ON users.user_id = orders.user_id WHERE order_id = %s)";
	private static final String REMOVE_ORDER_QUERY = "DELETE from orders WHERE order_id = %s AND status = \"created\"";

	private static final String NO_INSERTED_DRINKS_MESSAGE = "No drinks from the order were inserted in database";

	/**
	 * Removes orders with expired time and passed status.
	 * 
	 * @param dateTime the {@code OffsetDateTime} value representing point in time
	 *                 in comparison with which older orders will be deleted.
	 * @param status   the {@code OrderStatus} value representing status with which
	 *                 old orders will be deleted.
	 * @throws DAOException If problem occurs during interaction with database.
	 */
	@Override
	public void removeExpiredOrders(OffsetDateTime dateTime, OrderStatus status) throws DAOException {
		Connection connection = null;
		Statement statement = null;

		try {
			connection = CONNECTION_POOL.retrieveConnection();
			statement = connection.createStatement();
			statement.executeUpdate(String.format(REMOVE_EXPIRED_ORDERS_QUERY, dateTime, status));

		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				CONNECTION_POOL.closeConnection(connection, statement);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}
	}

	/**
	 * Add passed order to database.
	 * 
	 * @param orderTransfer the order to be saved in database.
	 * @return {@code long} value which represents order id.
	 * @throws DAOException If problem occurs during interaction with database or
	 *                      passed parameter is invalid.
	 */
	@Override
	public long add(OrderTransfer orderTransfer) throws DAOException {
		if (orderTransfer == null) {
			throw new DAOException(CommonExceptionMessage.NULL_ARGUMENT);
		}

		Order order = orderTransfer.getOrder();
		OrderDrink orderDrink = orderTransfer.getOrderDrink();

		if (order == null || orderDrink == null) {
			throw new DAOException(CommonExceptionMessage.NULL_ARGUMENT);
		}

		OrderInfo orderInfo = order.getInfo();

		Map<DrinkTransfer, Integer> drinks = orderDrink.getDrinksAmount();

		if (orderInfo == null || drinks == null || drinks.isEmpty()) {
			throw new DAOException(CommonExceptionMessage.NULL_OR_EMPTY_ARGUMENT);
		}

		Connection connection = null;
		Statement statement = null;
		ResultSet generatedKeys = null;
		long orderId;
		long effectedRows = 0;

		try {
			connection = ConnectionPool.retrieveConnectionPool().retrieveConnection();
			if (connection == null) {
				throw new DAOException(CommonExceptionMessage.NULL_CONNECTION);
			}

			connection.setAutoCommit(false);

			statement = connection.createStatement();

			int cost = DecimalExchange.revertToInt(orderInfo.getCost());
			statement.executeUpdate(
					String.format(ADD_QUERY, order.getUserId(), orderInfo.getDateTime(), orderInfo.getStatus(), cost),
					Statement.RETURN_GENERATED_KEYS);

			generatedKeys = statement.getGeneratedKeys();

			if (generatedKeys.next()) {
				orderId = generatedKeys.getLong(1);
				orderDrink.setOrderId(orderId);
				for (Map.Entry<DrinkTransfer, Integer> element : drinks.entrySet()) {
					effectedRows += statement.executeUpdate(String.format(ADD_ORDER_DRINK_QUERY, orderId,
							element.getKey().getId(), element.getValue()));
				}

				if (effectedRows > 0) {
					connection.commit();
				} else {
					connection.rollback();
					throw new DAOException(NO_INSERTED_DRINKS_MESSAGE);
				}
			} else {
				throw new DAOException(CommonExceptionMessage.NO_GENERATED_ID);
			}

		} catch (SQLException | ConnectionPoolException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e1.getMessage(), e1);
			}
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				connection.setAutoCommit(true);
				CONNECTION_POOL.closeConnection(connection, statement, generatedKeys);
			} catch (ConnectionPoolException | SQLException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}
		return orderId;
	}

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
	 * @throws DAOException If problem occurs during interaction with database.
	 */
	@Override
	public boolean pay(long orderId, BigDecimal accountBalanceToWithdraw, BigDecimal bonusAccountBalanceToWithdraw,
			BigDecimal cashback) throws DAOException {
		if (orderId <= 0 || accountBalanceToWithdraw == null || bonusAccountBalanceToWithdraw == null
				|| cashback == null) {
			return false;
		}

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionPool.retrieveConnectionPool().retrieveConnection();
			if (connection == null) {
				throw new DAOException(CommonExceptionMessage.NULL_CONNECTION);
			}

			connection.setAutoCommit(false);

			statement = connection.createStatement();
			resultSet = statement.executeQuery(String.format(SELECT_DRINK_INGREDIENTS_QUERY, orderId));

			Map<Integer, Integer> ingredientsForUpdating = new HashMap<>();
			while (resultSet.next()) {
				ingredientsForUpdating.put(resultSet.getInt(1), resultSet.getInt(2));
			}

			for (Map.Entry<Integer, Integer> ingredient : ingredientsForUpdating.entrySet()) {
				statement.executeUpdate(
						String.format(UPDATE_INGREDIENT_AMOUNT_QUERY, ingredient.getValue(), ingredient.getKey()));
			}
			statement.executeUpdate(String.format(UPDATE_ACCOUNT_BALANCE_QUERY,
					DecimalExchange.revertToInt(accountBalanceToWithdraw), orderId));
			statement.executeUpdate(String.format(UPDATE_BONUS_ACCOUNT_BALANCE_QUERY,
					DecimalExchange.revertToInt(bonusAccountBalanceToWithdraw), orderId));

			statement.executeUpdate(String.format(CHANGE_ORDER_STATUS_QUERY, OrderStatus.PAID, orderId));

			statement.executeUpdate(String.format(CASHBACK_QUERY, DecimalExchange.revertToInt(cashback), orderId));

			connection.commit();
		} catch (SQLException | ConnectionPoolException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e1.getMessage(), e1);
			}
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				connection.setAutoCommit(true);
				CONNECTION_POOL.closeConnection(connection, statement, resultSet);
			} catch (ConnectionPoolException | SQLException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}
		return true;
	}

	/**
	 * Read existed order from database with specified order id.
	 * 
	 * @param orderId {@code long} value which uniquely indicates the order.
	 * @return{@code Order} with specified id or {@code null} if passed
	 *               {@code orderId} is invalid.
	 * @throws DAOException If problem occurs during interaction with database.
	 */
	@Override
	public Order read(long orderId) throws DAOException {
		if (orderId <= 0) {
			return null;
		}

		Order order = null;
		OrderInfo orderInfo = null;

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionPool.retrieveConnectionPool().retrieveConnection();
			if (connection == null) {
				throw new DAOException(CommonExceptionMessage.NULL_CONNECTION);
			}

			statement = connection.createStatement();
			resultSet = statement.executeQuery(String.format(SELECT_ORDER_QUERY, orderId));

			String timeZoneParam = OrderPropertyProvider.getInstance().retrieveValue(OrderParameter.DB_TIME_ZONE);
			ZoneOffset zoneId = (ZoneOffset) ZoneId.of(timeZoneParam);

			while (resultSet.next()) {
				order = new Order();
				orderInfo = new OrderInfo();

				order.setOrderId(orderId);
				order.setUserId(resultSet.getInt(2));
				orderInfo.setDateTime(OffsetDateTime.of(resultSet.getTimestamp(3).toLocalDateTime(), zoneId));
				orderInfo.setStatus(OrderStatus.valueOf(resultSet.getString(4).toUpperCase()));
				orderInfo.setCost(DecimalExchange.obtainFromInt(resultSet.getInt(5)));

				order.setInfo(orderInfo);
			}

		} catch (SQLException | ConnectionPoolException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				CONNECTION_POOL.closeConnection(connection, statement, resultSet);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}

		return order;

	}

	/**
	 * Deletes the order with specified id.
	 * 
	 * @param orderId {@code long} value which uniquely indicates the order to be
	 *                deleted.
	 * @return {@code true} If the removal was successful or {@code false}
	 *         otherwise.
	 * @throws DAOException If problem occurs during interaction with database.
	 */
	@Override
	public boolean delete(long orderId) throws DAOException {
		if (orderId <= 0) {
			return false;
		}

		Connection connection = null;
		Statement statement = null;
		int result = 0;

		try {
			connection = ConnectionPool.retrieveConnectionPool().retrieveConnection();
			if (connection == null) {
				throw new DAOException(CommonExceptionMessage.NULL_CONNECTION);
			}

			connection.setAutoCommit(false);

			statement = connection.createStatement();

			result = statement.executeUpdate(String.format(REMOVE_ORDER_QUERY, orderId));

			connection.commit();
		} catch (SQLException | ConnectionPoolException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e1.getMessage(), e1);
			}
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				connection.setAutoCommit(true);
				CONNECTION_POOL.closeConnection(connection, statement);
			} catch (ConnectionPoolException | SQLException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}
		return result > 0;
	}

}
