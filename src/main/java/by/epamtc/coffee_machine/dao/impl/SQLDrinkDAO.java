package by.epamtc.coffee_machine.dao.impl;

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
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPool;
import by.epamtc.coffee_machine.service.utility.DecimalExchange;

/**
 * Provides methods for working with Drinks table and entities {@link Drink},
 * {@link DrinkTransfer}
 */
public class SQLDrinkDAO implements DrinkDAO {
	private static final ConnectionPool CONNECTION_POOL = ConnectionPool.retrieveConnectionPool();
	private static final String OBTAIN_DRINKS_QUERY = "SELECT drink_id, name, image_path, price "
			+ "FROM drinks LIMIT ?, ?";
	private static final String OBTAIN_DRINK_QUERY = "SELECT * FROM drinks WHERE drink_id = ?";
	private static final String OBTAIN_GENERAL_DRINKS_AMOUNT_QUERY = "SELECT COUNT(*) FROM drinks";
	private static final String UPDATE_DRINK_QUERY = "UPDATE drinks SET image_path = ?, price = ?, description = ? WHERE drink_id = ?";

	/**
	 * Obtains existed drink with specified drink id.
	 * 
	 * @param drinkId {@code long} value which uniquely indicates the drink.
	 * @return {@code Drink} with specified id.
	 * @throws DAOException If problem occurs during interaction with database.
	 */
	@Override
	public Drink read(long drinkId) throws DAOException {
		Drink drink = null;

		if (drinkId <= 0) {
			return drink;
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		drink = new Drink();

		try {
			connection = CONNECTION_POOL.retrieveConnection();
			preparedStatement = connection.prepareStatement(OBTAIN_DRINK_QUERY);
			preparedStatement.setLong(1, drinkId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				DrinkInfo drinkInfo = new DrinkInfo();
				drink.setId(resultSet.getLong(1));
				drinkInfo.setName(resultSet.getString(2));
				drinkInfo.setImagePath(resultSet.getString(3));
				drinkInfo.setPrice(DecimalExchange.obtainFromInt(resultSet.getInt(4)));
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

	/**
	 * Obtains specified amount of drinks starting from indicated row.
	 * 
	 * @param startIndex the begin index, inclusive.
	 * @param amount     the amount of drinks to return.
	 * @return {@code List} of {@code DrinkTransfer} objects representing specified
	 *         {@code amount} of drinks starting from {@code startIndex} or
	 *         {@code null} if passed parameters are invalid.
	 * @throws DAOException If problem occurs during interaction with database.
	 */
	@Override
	public List<DrinkTransfer> obtainDrinks(int startIndex, int amount) throws DAOException {
		List<DrinkTransfer> drinks = null;

		if (amount < 0 || startIndex < 0) {
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

	/**
	 * Returns general amount of drinks saved in database.
	 * 
	 * @return {@code int} value representing general amount of drinks.
	 * @throws DAOException If problem occurs during interaction with database.
	 */
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

	/**
	 * Updates existed Drink.
	 * 
	 * @param drink {@code Drink} value which contains drink id of existed drink.
	 *              The field of the already existed in database drink will be
	 *              replaced by the fields of passed drink.
	 * 
	 * @return {@code true} If the update was successful or {@code false} if update
	 *         was failed or fields of passed drink are invalid.
	 * @throws DAOException If problem occurs during interaction with database.
	 */
	@Override
	public boolean update(Drink drink) throws DAOException {

		if (drink == null || drink.getId() <= 0) {
			return false;
		}

		DrinkInfo info = drink.getInfo();
		if (info == null) {
			return false;
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int effectedRows = 0;

		try {
			connection = CONNECTION_POOL.retrieveConnection();
			preparedStatement = connection.prepareStatement(UPDATE_DRINK_QUERY);
			preparedStatement.setString(1, info.getImagePath());
			preparedStatement.setInt(2, DecimalExchange.revertToInt(info.getPrice()));
			preparedStatement.setString(3, info.getDescription());
			preparedStatement.setLong(4, drink.getId());
			effectedRows = preparedStatement.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				CONNECTION_POOL.closeConnection(connection, preparedStatement);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}
		return effectedRows > 0;
	}

}
