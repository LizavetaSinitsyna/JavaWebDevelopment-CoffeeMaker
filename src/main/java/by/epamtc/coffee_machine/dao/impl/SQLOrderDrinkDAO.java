package by.epamtc.coffee_machine.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.epamtc.coffee_machine.bean.OrderDrink;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.OrderDrinkDAO;
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPool;
import by.epamtc.coffee_machine.service.utility.DecimalExchange;

/**
 * Provides methods for working with OrderDrinks table and entity
 * {@link OrderDrink}
 */
public class SQLOrderDrinkDAO implements OrderDrinkDAO {
	private static final ConnectionPool CONNECTION_POOL = ConnectionPool.retrieveConnectionPool();
	private static final String SELECT_POPULAR_DRINKS_QUERY = "SELECT drink_id, name, image_path, price "
			+ "FROM drinks WHERE drink_id IN " + "(SELECT drink_id FROM (SELECT drink_id, SUM(drink_count) "
			+ "FROM order_drinks GROUP BY drink_id ORDER BY SUM(drink_count) DESC LIMIT ?) sub_query)";
	private static final String ADD_QUERY = "INSERT INTO order_drinks (order_id, drink_id, drink_count) VALUES (%s, %s, %s)";

	/**
	 * Add passed OrderDrink to database.
	 * 
	 * @param orderDrink the {@code OrderDrink} object to be saved in database.
	 * @return {@code long} value which represents OrderDrink id.
	 * @throws DAOException If problem occurs during interaction with database.
	 */
	@Override
	public long add(OrderDrink orderDrink) throws DAOException {
		int effectedColumns = 0;

		if (orderDrink == null) {
			return effectedColumns;
		}

		Map<DrinkTransfer, Integer> drinks = orderDrink.getDrinksAmount();

		if (drinks == null || drinks.isEmpty()) {
			return effectedColumns;
		}

		Connection connection = null;
		Statement statement = null;

		long orderId = orderDrink.getOrderId();
		if (orderId <= 0) {
			return effectedColumns;
		}

		try {
			connection = CONNECTION_POOL.retrieveConnection();
			statement = connection.createStatement();

			for (Map.Entry<DrinkTransfer, Integer> element : drinks.entrySet()) {
				effectedColumns += statement
						.executeUpdate(String.format(ADD_QUERY, orderId, element.getKey().getId(), element.getValue()));
			}

		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				CONNECTION_POOL.closeConnection(connection, statement);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}
		return effectedColumns;
	}

	/**
	 * Select specified amount of popular drinks.
	 * 
	 * @return {@code List} of {@code DrinkTransfer} objects representing popular
	 *         drinks or {@code null} if passed parameter is invalid.
	 * @throws DAOException If problem occurs during interaction with database.
	 */
	@Override
	public List<DrinkTransfer> selectPopularDrinks(int amount) throws DAOException {
		List<DrinkTransfer> drinks = null;

		if (amount < 0) {
			return drinks;
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		DrinkTransfer drink;
		drinks = new ArrayList<>();

		try {
			connection = CONNECTION_POOL.retrieveConnection();
			preparedStatement = connection.prepareStatement(SELECT_POPULAR_DRINKS_QUERY);
			preparedStatement.setInt(1, amount);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				drink = new DrinkTransfer();
				drink.setId(resultSet.getLong(1));
				drink.setName(resultSet.getString(2));
				drink.setImagePath(resultSet.getString(3));
				drink.setPrice(DecimalExchange.obtainFromInt(resultSet.getInt(4)));

				drinks.add(drink);
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				CONNECTION_POOL.closeConnection(connection, preparedStatement, resultSet);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}
		return drinks;
	}

}
