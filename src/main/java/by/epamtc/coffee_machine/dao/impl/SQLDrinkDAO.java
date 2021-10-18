package by.epamtc.coffee_machine.dao.impl;

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
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPool;
import by.epamtc.coffee_machine.service.utility.MenuParameter;
import by.epamtc.coffee_machine.service.utility.MenuPropertyProvider;

public class SQLDrinkDAO implements DrinkDAO {
	private static final ConnectionPool CONNECTION_POOL = ConnectionPool.retrieveConnectionPool();
	private static final BigDecimal PRICE_DIVISOR = new BigDecimal(
			MenuPropertyProvider.getInstance().retrieveValue(MenuParameter.DRINK_PRICE_DIVISOR));
	private static final String OBTAIN_DRINKS_QUERY = "SELECT drink_id, name, image_path, price "
			+ "FROM drinks LIMIT ?, ?";
	private static final String OBTAIN_DRINK_QUERY = "SELECT * FROM drinks WHERE drink_id = ?";
	private static final String OBTAIN_GENERAL_DRINKS_AMOUNT_QUERY = "SELECT COUNT(*) FROM drinks";
	private static final String UPDATE_DRINK_QUERY = "UPDATE drinks SET image_path = ?, price = ?, description = ? WHERE drink_id = ?";

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
	public long add(Drink drink) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean remove(long drinkId) {
		// TODO Auto-generated method stub
		return false;
	}

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

			BigDecimal inputPrice = info.getPrice();
			BigDecimal priceDB = inputPrice.multiply(PRICE_DIVISOR);
			preparedStatement.setInt(2, priceDB.intValue());

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
