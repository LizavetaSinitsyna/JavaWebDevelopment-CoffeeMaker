package by.epamtc.coffee_machine.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.OffsetDateTime;
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
import by.epamtc.coffee_machine.service.utility.MenuParameter;
import by.epamtc.coffee_machine.service.utility.MenuPropertyProvider;
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPool;

public class SQLOrderDAO implements OrderDAO {
	private static final ConnectionPool CONNECTION_POOL = ConnectionPool.retrieveConnectionPool();
	private static final String REMOVE_EXPIRED_ORDERS_QUERY = "DELETE from orders WHERE date_time <= \"%s\" AND status = \"%s\"";
	private static final String ADD_QUERY = "INSERT INTO orders (user_id, date_time, status, cost) VALUES (%s, \"%s\", \"%s\", %s)";
	private static final String ADD_ORDER_DRINK_QUERY = "INSERT INTO order_drinks (order_id, drink_id, drink_count) VALUES (%s, %s, %s)";
	private static final BigDecimal PRICE_DIVISOR = new BigDecimal(
			MenuPropertyProvider.getInstance().retrieveValue(MenuParameter.DRINK_PRICE_DIVISOR));

	private static final String NO_INSERTED_DRINKS_MESSAGE = "No drinks from the order were inserted in database";

	@Override
	public Order read(long orderId) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(long orderId) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

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

	@Override
	public boolean update(long orderId, OrderInfo info) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

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
		long effectedColumns = 0;

		try {
			connection = ConnectionPool.retrieveConnectionPool().retrieveConnection();
			if (connection == null) {
				throw new DAOException(CommonExceptionMessage.NULL_CONNECTION);
			}

			connection.setAutoCommit(false);

			statement = connection.createStatement();

			int cost = orderInfo.getCost().multiply(PRICE_DIVISOR).intValue();
			statement.executeUpdate(
					String.format(ADD_QUERY, order.getUserId(), orderInfo.getDateTime(), orderInfo.getStatus(), cost),
					Statement.RETURN_GENERATED_KEYS);

			generatedKeys = statement.getGeneratedKeys();

			if (generatedKeys.next()) {
				orderId = generatedKeys.getLong(1);
				orderDrink.setOrderId(orderId);
				for (Map.Entry<DrinkTransfer, Integer> element : drinks.entrySet()) {
					effectedColumns += statement.executeUpdate(String.format(ADD_ORDER_DRINK_QUERY, orderId,
							element.getKey().getId(), element.getValue()));
				}

				if (effectedColumns > 0) {
					connection.commit();
				} else {
					connection.rollback();
					throw new DAOException(NO_INSERTED_DRINKS_MESSAGE);
				}
			} else {
				throw new DAOException(CommonExceptionMessage.NO_GENERATED_ID);
			}

		} catch (SQLException | ConnectionPoolException e) {
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

}
