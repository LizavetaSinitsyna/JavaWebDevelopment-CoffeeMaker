/**
 * 
 */
package by.epamtc.coffee_machine.dao.sql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.DrinkInfo;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DrinkDAO;
import by.epamtc.coffee_machine.dao.sql.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.dao.sql.pool.ConnectionPoolImpl;
import by.epamtc.coffee_machine.utility.MenuParameter;
import by.epamtc.coffee_machine.utility.MenuPropertyProvider;
import by.epamtc.coffee_machine.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class SQLDrinkDAO implements DrinkDAO {
	private MenuPropertyProvider menuPropertyProvider = MenuPropertyProvider.getInstance();
	private static final String OBTAIN_DRINKS_QUERY = "SELECT drink_id, name, image_path, price "
			+ "FROM drinks LIMIT ?, ?";
	private static final String OBTAIN_DRINK_QUERY = "SELECT * FROM drinks WHERE drink_id = ?";
	private static final String OBTAIN_GENERAL_DRINKS_AMOUNT = "SELECT COUNT(*) FROM drinks";

	@Override
	public Drink read(int drink_id) throws DAOException {
		Drink drink = null;

		if (!ValidationHelper.isPositive(drink_id)) {
			return drink;
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		drink = new Drink();
		ConnectionPoolImpl connectionPool = ConnectionPoolImpl.retrieveConnectionPool();

		try {
			connection = connectionPool.retrieveConnection();
			preparedStatement = connection.prepareStatement(OBTAIN_DRINK_QUERY);
			preparedStatement.setInt(1, drink_id);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				DrinkInfo drinkInfo = new DrinkInfo();
				drink.setId(resultSet.getInt(1));
				drinkInfo.setName(resultSet.getString(2));
				drinkInfo.setImagePath(resultSet.getString(3));

				BigDecimal priceDB = new BigDecimal(resultSet.getInt(4));
				BigDecimal priceDivisor = new BigDecimal(
						menuPropertyProvider.retrieveValue(MenuParameter.DRINK_PRICE_DIVISOR));
				BigDecimal price = priceDB.divide(priceDivisor);
				drinkInfo.setPrice(price);

				drinkInfo.setDescription(resultSet.getString(5));
				drink.setInfo(drinkInfo);
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

		return drink;
	}

	@Override
	public List<DrinkTransfer> obtainDrinks(int startIndex, int amount) throws DAOException {
		List<DrinkTransfer> drinks = null;

		if (ValidationHelper.isNegative(amount) || (ValidationHelper.isNegative(startIndex))) {
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
			preparedStatement = connection.prepareStatement(OBTAIN_DRINKS_QUERY);
			preparedStatement.setInt(1, startIndex);
			preparedStatement.setInt(2, amount);
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
	
	@Override
	public int obtainGeneralDrinksAmount() throws DAOException {
		int result = 0;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ConnectionPoolImpl connectionPool = ConnectionPoolImpl.retrieveConnectionPool();

		try {
			connection = connectionPool.retrieveConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(OBTAIN_GENERAL_DRINKS_AMOUNT);
			while (resultSet.next()) {
				result = resultSet.getInt(1);
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				connectionPool.closeConnection(connection, statement, resultSet);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}
		return result;
	}

	@Override
	public int add(Drink drink) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean remove(int drink_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(int drink_id, DrinkInfo info) {
		// TODO Auto-generated method stub
		return false;
	}

}
