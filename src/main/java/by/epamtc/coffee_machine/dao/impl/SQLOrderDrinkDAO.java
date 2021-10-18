package by.epamtc.coffee_machine.dao.impl;

import java.math.BigDecimal;
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
import by.epamtc.coffee_machine.service.utility.MenuParameter;
import by.epamtc.coffee_machine.service.utility.MenuPropertyProvider;

public class SQLOrderDrinkDAO implements OrderDrinkDAO {
	private static final ConnectionPool CONNECTION_POOL = ConnectionPool.retrieveConnectionPool();
	private static final MenuPropertyProvider MENU_PROPERTY_PROVIDER = MenuPropertyProvider.getInstance();
	private static final String SELECT_POPULAR_DRINKS_QUERY = "SELECT drink_id, name, image_path, price "
			+ "FROM drinks WHERE drink_id IN " + "(SELECT drink_id FROM (SELECT drink_id, SUM(drink_count) "
			+ "FROM order_drinks GROUP BY drink_id ORDER BY drink_count DESC LIMIT ?) sub_query)";
	private static final String ADD_QUERY = "INSERT INTO order_drinks (order_id, drink_id, drink_count) VALUES (%s, %s, %s)";

	@Override
	public List<OrderDrink> findDrinksForSpecificOrder(int ingredient_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDrink> findOrdersWithSpecificDrink(int drink_id) {
		// TODO Auto-generated method stub
		return null;
	}

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

	@Override
	public boolean remove(OrderDrink orderDrink) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(OrderDrink orderDrink, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

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

				BigDecimal priceDB = new BigDecimal(resultSet.getInt(4));
				BigDecimal priceDivisor = new BigDecimal(
						MENU_PROPERTY_PROVIDER.retrieveValue(MenuParameter.DRINK_PRICE_DIVISOR));
				BigDecimal price = priceDB.divide(priceDivisor);
				drink.setPrice(price);

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
