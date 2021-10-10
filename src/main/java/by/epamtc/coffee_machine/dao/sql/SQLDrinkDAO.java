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
	private static final ConnectionPoolImpl CONNECTION_POOL = ConnectionPoolImpl.retrieveConnectionPool();
	private static final BigDecimal PRICE_DIVISOR = new BigDecimal(
			MenuPropertyProvider.getInstance().retrieveValue(MenuParameter.DRINK_PRICE_DIVISOR));
	private static final String OBTAIN_DRINKS_QUERY = "SELECT drink_id, name, image_path, price "
			+ "FROM drinks LIMIT ?, ?";
	private static final String OBTAIN_DRINK_QUERY = "SELECT * FROM drinks WHERE drink_id = ?";
	private static final String OBTAIN_GENERAL_DRINKS_AMOUNT_QUERY = "SELECT COUNT(*) FROM drinks";
	private static final String UPDATE_DRINK_QUERY = "UPDATE drinks SET image_path = ?, price = ?, description = ? WHERE drink_id = ?";
	private static final String OBTAIN_NAME_QUERY = "SELECT name FROM drinks WHERE drink_id = ?";

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

		try {
			connection = CONNECTION_POOL.retrieveConnection();
			preparedStatement = connection.prepareStatement(OBTAIN_DRINK_QUERY);
			preparedStatement.setInt(1, drink_id);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				DrinkInfo drinkInfo = new DrinkInfo();
				drink.setId(resultSet.getInt(1));
				drinkInfo.setName(resultSet.getString(2));
				drinkInfo.setImagePath(resultSet.getString(3));

				BigDecimal priceDB = new BigDecimal(resultSet.getInt(4));
				BigDecimal price = priceDB.divide(PRICE_DIVISOR);
				drinkInfo.setPrice(price);

				drinkInfo.setDescription(resultSet.getString(5));
				drink.setInfo(drinkInfo);
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

		try {
			connection = CONNECTION_POOL.retrieveConnection();
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
				BigDecimal price = priceDB.divide(PRICE_DIVISOR);
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

	@Override
	public int obtainGeneralDrinksAmount() throws DAOException {
		int result = 0;

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = CONNECTION_POOL.retrieveConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(OBTAIN_GENERAL_DRINKS_AMOUNT_QUERY);
			while (resultSet.next()) {
				result = resultSet.getInt(1);
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				CONNECTION_POOL.closeConnection(connection, statement, resultSet);
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
	public int update(Drink drink) throws DAOException {
		int effectedColumns = 0;

		if (ValidationHelper.isNull(drink) || !ValidationHelper.isPositive(drink.getId())) {
			return effectedColumns;
		}

		DrinkInfo info = drink.getInfo();
		if (ValidationHelper.isNull(info)) {
			return effectedColumns;
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = CONNECTION_POOL.retrieveConnection();
			preparedStatement = connection.prepareStatement(UPDATE_DRINK_QUERY);
			preparedStatement.setString(1, info.getImagePath());

			BigDecimal inputPrice = info.getPrice();
			BigDecimal priceDB = inputPrice.multiply(PRICE_DIVISOR);
			preparedStatement.setInt(2, priceDB.intValue());

			preparedStatement.setString(3, info.getDescription());
			preparedStatement.setInt(4, drink.getId());
			effectedColumns = preparedStatement.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				CONNECTION_POOL.closeConnection(connection, preparedStatement);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}
		return effectedColumns;
	}
	
	// unused
	@Override
	public String obtainDrinkName(int drinkId) throws DAOException {
		String result = null;

		if (!ValidationHelper.isPositive(drinkId)) {
			return result;
		}

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = CONNECTION_POOL.retrieveConnection();
			statement = connection.prepareStatement(OBTAIN_NAME_QUERY);
			statement.setInt(1, drinkId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				result = resultSet.getString(1);
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				CONNECTION_POOL.closeConnection(connection, statement, resultSet);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}
		return result;
	}

}
