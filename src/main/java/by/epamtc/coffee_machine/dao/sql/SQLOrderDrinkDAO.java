/**
 * 
 */
package by.epamtc.coffee_machine.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.DrinkInfo;
import by.epamtc.coffee_machine.bean.OrderDrink;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.OrderDrinkDAO;
import by.epamtc.coffee_machine.dao.sql.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.dao.sql.pool.ConnectionPoolImpl;
import by.epamtc.coffee_machine.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class SQLOrderDrinkDAO implements OrderDrinkDAO {
	private final String SELECT_POPULAR_DRINKS_QUERY = "SELECT * FROM drinks WHERE drink_id IN "
			+ "(SELECT drink_id FROM (SELECT drink_id, SUM(drink_count) "
			+ "FROM order_drinks GROUP BY drink_id ORDER BY drink_count DESC LIMIT ?) sub_query)";

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
	public List<Drink> selectPopularDrinks(int amount) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Drink drink;
		DrinkInfo drinkInfo;
		List<Drink> drinks = new ArrayList<>();
		ConnectionPoolImpl connectionPool = ConnectionPoolImpl.retrieveConnectionPool();
		if (ValidationHelper.isNegative(amount)) {
			throw new DAOException(ValidationHelper.NEGATIVE_PARAM_EXCEPTION);
		}
		try {
			connection = connectionPool.retrieveConnection();
			preparedStatement = connection.prepareStatement(SELECT_POPULAR_DRINKS_QUERY);
			preparedStatement.setInt(1, amount);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				drink = new Drink();
				drinkInfo = new DrinkInfo();
				drink.setId(resultSet.getInt(1));
				drinkInfo.setName(resultSet.getString(2));
				drinkInfo.setImagePath(resultSet.getString(3));
				drinkInfo.setPrice(resultSet.getInt(4));
				drinkInfo.setDescription(resultSet.getString(5));
				drink.setInfo(drinkInfo);
				drinks.add(drink);
			}
		} catch (ConnectionPoolException  | SQLException e) {
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
