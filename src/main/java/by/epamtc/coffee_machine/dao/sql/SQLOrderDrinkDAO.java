/**
 * 
 */
package by.epamtc.coffee_machine.dao.sql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.coffee_machine.bean.OrderDrink;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.OrderDrinkDAO;
import by.epamtc.coffee_machine.dao.sql.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.dao.sql.pool.ConnectionPoolImpl;
import by.epamtc.coffee_machine.utility.MenuParameter;
import by.epamtc.coffee_machine.utility.MenuPropertyProvider;
import by.epamtc.coffee_machine.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class SQLOrderDrinkDAO implements OrderDrinkDAO {
	private static final String SELECT_POPULAR_DRINKS_QUERY = "SELECT drink_id, name, image_path, price "
			+ "FROM drinks WHERE drink_id IN " + "(SELECT drink_id FROM (SELECT drink_id, SUM(drink_count) "
			+ "FROM order_drinks GROUP BY drink_id ORDER BY drink_count DESC LIMIT ?) sub_query)";
	private MenuPropertyProvider menuPropertyProvider = MenuPropertyProvider.getInstance();

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
	public int add(OrderDrink orderDrink) {
		// TODO Auto-generated method stub
		return 0;

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

		if (ValidationHelper.isNegative(amount)) {
			return drinks;
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		DrinkTransfer drink;
		drinks = new ArrayList<>();
		ConnectionPoolImpl connectionPool = ConnectionPoolImpl.retrieveConnectionPool();

		try {
			connection = connectionPool.retrieveConnection();
			preparedStatement = connection.prepareStatement(SELECT_POPULAR_DRINKS_QUERY);
			preparedStatement.setInt(1, amount);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				drink = new DrinkTransfer();
				drink.setId(resultSet.getInt(1));
				drink.setName(resultSet.getString(2));
				drink.setImagePath(resultSet.getString(3));

				BigDecimal priceDB = new BigDecimal(resultSet.getInt(4));
				BigDecimal priceDivisor = new BigDecimal(
						menuPropertyProvider.retrieveValue(MenuParameter.DRINK_PRICE_DIVISOR));
				BigDecimal price = priceDB.divide(priceDivisor);
				drink.setPrice(price);

				drinks.add(drink);
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				connectionPool.closeConnection(connection, preparedStatement, resultSet);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}
		return drinks;
	}

}
