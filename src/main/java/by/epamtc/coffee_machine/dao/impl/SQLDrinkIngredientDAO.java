package by.epamtc.coffee_machine.dao.impl;

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
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPool;

public class SQLDrinkIngredientDAO implements DrinkIngredientDAO {
	private static final ConnectionPool CONNECTION_POOL = ConnectionPool.retrieveConnectionPool();
	private static final String READ_INGREDIENTS_FOR_SPECIFIC_DRINK_QUERY = "SELECT ingredients.ingredient_id, ingredients.name, ingredient_amount, is_optional FROM "
			+ "ingredients INNER JOIN drink_ingredients ON "
			+ "drink_ingredients.ingredient_id = ingredients.ingredient_id WHERE drink_id = ";
	private static final String DELETE_DRINK_INGREDIENTS_QUERY = "DELETE FROM drink_ingredients WHERE drink_id = ";
	private static final String INSERT_DRINK_INGREDIENTS_QUERY = "INSERT INTO drink_ingredients (drink_id, ingredient_id, ingredient_amount, is_optional) VALUES (?, ?, ?, ?)";

	@Override
	public List<DrinkIngredient> findDrinksWithSpecificIngredient(long ingredientId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DrinkIngredientTransfer> readIngredientsForSpecificDrink(long drinkId) throws DAOException {
		List<DrinkIngredientTransfer> result = null;
		if (drinkId <= 0) {
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
			resultSet = statement.executeQuery(READ_INGREDIENTS_FOR_SPECIFIC_DRINK_QUERY + drinkId);
			while (resultSet.next()) {
				drinkIngredientTransfer = new DrinkIngredientTransfer();
				drinkIngredientTransfer.setIngredientId(resultSet.getLong(1));
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
	public long add(DrinkIngredient drinkIngredient) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean remove(DrinkIngredient drinkIngredient) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(DrinkIngredientMap drinkIngredientMap) throws DAOException {

		if (drinkIngredientMap == null) {
			return false;
		}

		List<DrinkIngredient> ingredients = drinkIngredientMap.getIngredients();

		if (ingredients == null || ingredients.isEmpty()) {
			return false;
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Statement statement = null;
		int effectedRows = 0; 
		
		long drinkId = drinkIngredientMap.getDrinkId();
		if (drinkId <= 0) {
			return false;
		}

		try {
			connection = CONNECTION_POOL.retrieveConnection();
			connection.setAutoCommit(false);

			statement = connection.createStatement();
			effectedRows = statement.executeUpdate(DELETE_DRINK_INGREDIENTS_QUERY + drinkId);

			if (effectedRows < 0) {
				return false;
			}

			preparedStatement = connection.prepareStatement(INSERT_DRINK_INGREDIENTS_QUERY);
			for (DrinkIngredient element : ingredients) {
				preparedStatement.setLong(1, drinkId);
				preparedStatement.setLong(2, element.getIngredientId());
				preparedStatement.setInt(3, element.getIngredientAmount());
				preparedStatement.setBoolean(4, element.isOptional());
				effectedRows += preparedStatement.executeUpdate();
			}

			connection.commit();
		} catch (ConnectionPoolException | SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e.getMessage(), e);
			}
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				connection.setAutoCommit(true);
				CONNECTION_POOL.closeConnection(connection, preparedStatement);
			} catch (ConnectionPoolException | SQLException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}
		return effectedRows > 0;
	}
}
