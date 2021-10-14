/**
 * 
 */
package by.epamtc.coffee_machine.dao.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.coffee_machine.bean.Ingredient;
import by.epamtc.coffee_machine.bean.IngredientInfo;
import by.epamtc.coffee_machine.bean.transfer.IngredientTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.IngredientDAO;
import by.epamtc.coffee_machine.dao.sql.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.dao.sql.pool.ConnectionPoolImpl;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class SQLIngredientDAO implements IngredientDAO {
	private static final ConnectionPoolImpl CONNECTION_POOL = ConnectionPoolImpl.retrieveConnectionPool();
	private static final String SELECT_ALL_QUERY = "SELECT name FROM ingredients";
	private static final String OBTAIN_INGREDIENT_QUERY = "SELECT name, current_amount, image_path FROM ingredients WHERE ingredient_id = %s";

	@Override
	public Ingredient read(long ingredientId) throws DAOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		Ingredient ingredient = null;

		try {
			connection = CONNECTION_POOL.retrieveConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(String.format(OBTAIN_INGREDIENT_QUERY, ingredientId));
			if (resultSet.next()) {
				ingredient = new Ingredient();
				IngredientInfo ingredientInfo = new IngredientInfo();
				ingredient.setId(ingredientId);
				ingredient.setCurrentAmount(resultSet.getInt(2));
				ingredientInfo.setName(resultSet.getString(1));
				ingredientInfo.setImagePath(resultSet.getString(3));
				ingredient.setInfo(ingredientInfo);
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
		return ingredient;
	}

	@Override
	public long add(Ingredient ingredient) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean remove(long ingredientId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAmount(long ingredientId, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(long ingredientId, IngredientInfo ingredientInfo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IngredientTransfer> readAll() throws DAOException {
		List<IngredientTransfer> ingredients = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		IngredientTransfer ingredient;
		ingredients = new ArrayList<>();

		try {
			connection = CONNECTION_POOL.retrieveConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SELECT_ALL_QUERY);
			while (resultSet.next()) {
				ingredient = new IngredientTransfer();
				ingredient.setId(resultSet.getLong(1));
				ingredient.setName(resultSet.getString(2));
				ingredients.add(ingredient);
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
		return ingredients;
	}
}
