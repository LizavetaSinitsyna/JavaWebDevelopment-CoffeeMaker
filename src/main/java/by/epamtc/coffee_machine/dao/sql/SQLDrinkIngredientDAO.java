/**
 * 
 */
package by.epamtc.coffee_machine.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.coffee_machine.bean.DrinkIngredient;
import by.epamtc.coffee_machine.bean.DrinkIngredientMap;
import by.epamtc.coffee_machine.bean.transfer.DrinkIngredientTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DrinkIngredientDAO;
import by.epamtc.coffee_machine.dao.sql.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.dao.sql.pool.ConnectionPoolImpl;
import by.epamtc.coffee_machine.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class SQLDrinkIngredientDAO implements DrinkIngredientDAO {
	private static final ConnectionPoolImpl CONNECTION_POOL = ConnectionPoolImpl.retrieveConnectionPool();
	private static final String READ_INGREDIENTS_FOR_SPECIFIC_DRINK_QUERY = "SELECT ingredients.ingredient_id, ingredients.name, ingredient_amount, is_optional FROM "
			+ "ingredients INNER JOIN drink_ingredients ON "
			+ "drink_ingredients.ingredient_id = ingredients.ingredient_id WHERE drink_id = ";
	private static final String DELETE_DRINK_INGREDIENTS_QUERY = "DELETE FROM drink_ingredients WHERE drink_id = ";
	private static final String INSERT_DRINK_INGREDIENTS_QUERY = "INSERT INTO drink_ingredients (drink_id, ingredient_id, ingredient_amount, is_optional) VALUES (?, ?, ?, ?)";

	@Override
	public List<DrinkIngredient> findDrinksWithSpecificIngredient(int ingredient_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DrinkIngredientTransfer> readIngredientsForSpecificDrink(int drink_id) throws DAOException {
		List<DrinkIngredientTransfer> result = null;
		if (!ValidationHelper.isPositive(drink_id)) {
			return result;
		}

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		result = new ArrayList<>();
		DrinkIngredientTransfer drinkIngredientTransfer;

		try {
			connection = CONNECTION_POOL.retrieveConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(READ_INGREDIENTS_FOR_SPECIFIC_DRINK_QUERY + drink_id);
			while (resultSet.next()) {
				drinkIngredientTransfer = new DrinkIngredientTransfer();
				drinkIngredientTransfer.setIngredientId(resultSet.getInt(1));
				drinkIngredientTransfer.setIngredientName(resultSet.getString(2));
				drinkIngredientTransfer.setIngredientAmount(resultSet.getInt(3));
				drinkIngredientTransfer.setOptional(resultSet.getBoolean(4));
				result.add(drinkIngredientTransfer);
			}
		} catch (SQLException | ConnectionPoolException e) {
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
	public int add(DrinkIngredient drinkIngredient) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DrinkIngredient read(int drink_id, int ingredient_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(DrinkIngredient drinkIngredient) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int update(DrinkIngredientMap drinkIngredientMap) throws DAOException {
		int effectedColumns = 0;

		if (ValidationHelper.isNull(drinkIngredientMap)) {
			return effectedColumns;
		}

		List<DrinkIngredient> ingredients = drinkIngredientMap.getIngredients();

		if (ValidationHelper.isNull(ingredients) || ingredients.isEmpty()) {
			return effectedColumns;
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Statement statement = null;

		int drinkId = drinkIngredientMap.getDrinkId();
		if (!ValidationHelper.isPositive(drinkId)) {
			return effectedColumns;
		}

		try {
			connection = CONNECTION_POOL.retrieveConnection();
			connection.setAutoCommit(false);

			statement = connection.createStatement();
			effectedColumns = statement.executeUpdate(DELETE_DRINK_INGREDIENTS_QUERY + drinkId);

			if (!ValidationHelper.isPositive(effectedColumns)) {
				return effectedColumns;
			}
			preparedStatement = connection.prepareStatement(INSERT_DRINK_INGREDIENTS_QUERY);
			for (DrinkIngredient element : ingredients) {
				preparedStatement.setInt(1, drinkId);
				preparedStatement.setInt(2, element.getIngredientId());
				preparedStatement.setInt(3, element.getIngredientAmount());
				preparedStatement.setBoolean(4, element.isOptional());
				effectedColumns += preparedStatement.executeUpdate();
			}

			connection.commit();
			connection.setAutoCommit(true);
		} catch (ConnectionPoolException | SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e.getMessage(), e);
			}
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

	private int deleteDrink(int drinkId) throws DAOException {
		int effectedColumns = 0;

		if (!ValidationHelper.isPositive(drinkId)) {
			return effectedColumns;
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = CONNECTION_POOL.retrieveConnection();

			preparedStatement = connection.prepareStatement(DELETE_DRINK_INGREDIENTS_QUERY);
			preparedStatement.setInt(1, drinkId);
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

}
