/**
 * 
 */
package by.epamtc.coffee_machine.dao.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.util.Map;

import by.epamtc.coffee_machine.bean.Order;
import by.epamtc.coffee_machine.bean.OrderInfo;
import by.epamtc.coffee_machine.bean.OrderStatus;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.OrderDAO;
import by.epamtc.coffee_machine.dao.sql.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.dao.sql.pool.ConnectionPoolImpl;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class SQLOrderDAO implements OrderDAO {
	private static final ConnectionPoolImpl CONNECTION_POOL = ConnectionPoolImpl.retrieveConnectionPool();
	private static final String REMOVE_EXPIRED_ORDERS_QUERY = "DELETE from orders WHERE date_time <= \"%s\" AND status = \"%s\"";

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
	public int add(long userId, Map<Long, Integer> drinksAmount, OffsetDateTime dateTime) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

}
