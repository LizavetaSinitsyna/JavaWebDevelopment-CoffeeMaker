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
import by.epamtc.coffee_machine.bean.DrinkIngredient;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DrinkDAO;
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPool;
import by.epamtc.coffee_machine.service.CommonExceptionMessage;
import by.epamtc.coffee_machine.service.utility.DecimalExchange;

/**
 * Provides methods for working with Drinks table and entities {@link Drink},
 * {@link DrinkTransfer}
 */
public class SQLDrinkDAO implements DrinkDAO {
	private static final ConnectionPool CONNECTION_POOL = ConnectionPool.retrieveConnectionPool();
	private static final String OBTAIN_DRINKS_QUERY = "SELECT drink_id, name, image_path, price "
			+ "FROM drinks WHERE is_deleted <> 1 LIMIT ?, ?";
	private static final String OBTAIN_DRINK_QUERY = "SELECT * FROM drinks WHERE drink_id = ? AND is_deleted <> 1";
	private static final String OBTAIN_GENERAL_DRINKS_AMOUNT_QUERY = "SELECT COUNT(*) FROM drinks WHERE is_deleted <> 1";
	private static final String UPDATE_DRINK_ALPGANUMERIC_FIELDS_QUERY = "UPDATE drinks SET price = ?, description = ? WHERE drink_id = ? AND is_deleted <> 1";
	private static final String UPDATE_DRINK_IMAGE_QUERY = "UPDATE drinks SET image_path = ? WHERE drink_id = ? AND is_deleted <> 1";
	private static final String ADD_DRINK_QUERY = "INSERT INTO drinks (name, image_path, price, description) VALUES(?, ?, ?, ?)";
	private static final String SEARCH_NAME_QUERY = "SELECT * FROM drinks WHERE name = ?";
	private static final String INSERT_DRINK_INGREDIENTS_QUERY = "INSERT INTO drink_ingredients (drink_id, ingredient_id, ingredient_amount, is_optional) VALUES (?, ?, ?, ?)";

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
			preparedStatement = connection.prepareStatement(UPDATE_DRINK_ALPGANUMERIC_FIELDS_QUERY);
			preparedStatement.setInt(1, DecimalExchange.revertToInt(info.getPrice()));
			preparedStatement.setString(2, info.getDescription());
			preparedStatement.setLong(3, drink.getId());
			effectedRows = preparedStatement.executeUpdate();

			updateImage(info.getImagePath(), drink.getId());

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

	/**
	 * Updates image path for specified drink.
	 * 
	 * @param imagePath the image path to save
	 * @param drinkId   {@code long} value which uniquely indicates the drink.
	 * @return {@code true} If the update was successful or {@code false} if update
	 *         was failed or passed image name is null or empty.
	 * @throws DAOException If problem occurs during interaction with database.
	 */
	@Override
	public boolean updateImage(String imagePath, long drinkId) throws DAOException {
		if (imagePath == null || imagePath.isBlank()) {
			return false;
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int effectedRows = 0;

		try {
			connection = CONNECTION_POOL.retrieveConnection();
			preparedStatement = connection.prepareStatement(UPDATE_DRINK_IMAGE_QUERY);
			preparedStatement.setString(1, imagePath);
			preparedStatement.setLong(2, drinkId);
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

	/**
	 * Creates new drink with specified drink and composition information.
	 * 
	 * @param drink            {@code Drink} value which to be saved.
	 * @param drinkIngredients {@code DrinkIngredient} objects which represent drink
	 *                         composition.
	 * @return {@code long} value representing drink id which was generated after
	 *         saving it in database.
	 * @throws DAOException If problem occurs during interaction with database or
	 *                      passed parameters are invalid.
	 */
	@Override
	public long add(Drink drink, List<DrinkIngredient> drinkIngredients) throws DAOException {

		if (drink == null || drinkIngredients == null) {
			throw new DAOException(CommonExceptionMessage.NULL_ARGUMENT);
		}

		DrinkInfo info = drink.getInfo();
		if (info == null) {
			throw new DAOException(CommonExceptionMessage.NULL_ARGUMENT);
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generatedKeys = null;
		boolean result = false;
		long drinkId;

		try {
			connection = CONNECTION_POOL.retrieveConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(ADD_DRINK_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, info.getName());
			preparedStatement.setString(2, info.getImagePath());
			preparedStatement.setInt(3, DecimalExchange.revertToInt(info.getPrice()));
			preparedStatement.setString(4, info.getDescription());
			preparedStatement.executeUpdate();
			generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				drinkId = generatedKeys.getLong(1);
			} else {
				throw new DAOException(CommonExceptionMessage.NO_GENERATED_ID);
			}

			result = add(connection, drinkId, drinkIngredients);

			if (result) {
				connection.commit();
			} else {
				connection.rollback();
			}
		} catch (ConnectionPoolException | SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e1.getMessage(), e1);
			}
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				CONNECTION_POOL.closeConnection(connection, preparedStatement, generatedKeys);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}
		return drinkId;
	}

	private boolean add(Connection connection, long drinkId, List<DrinkIngredient> ingredients) throws DAOException {
		if (connection == null) {
			throw new DAOException(CommonExceptionMessage.NULL_CONNECTION);
		}
		if (drinkId <= 0 || ingredients == null || ingredients.isEmpty()) {
			return false;
		}
		int effectedRows = 0;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(INSERT_DRINK_INGREDIENTS_QUERY);
			for (DrinkIngredient element : ingredients) {
				preparedStatement.setLong(1, drinkId);
				preparedStatement.setLong(2, element.getIngredientId());
				preparedStatement.setInt(3, element.getIngredientAmount());
				preparedStatement.setBoolean(4, element.isOptional());
				effectedRows += preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}

		return effectedRows > 0;

	}

	/**
	 * Checks if the drink with specified name exists.
	 * 
	 * @param drinkName the name to be checked.
	 * @return {@code true} if the drink with passed name exists and {@code false}
	 *         otherwise.
	 * @throws DAOException If problem occurs during interaction with database.
	 */

	@Override
	public boolean containsDrinkName(String drinkName) throws DAOException {
		boolean result = false;

		if (drinkName == null) {
			return result;
		}

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = CONNECTION_POOL.retrieveConnection();
			statement = connection.prepareStatement(SEARCH_NAME_QUERY);
			statement.setString(1, drinkName);
			resultSet = statement.executeQuery();
			result = resultSet.next();
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
