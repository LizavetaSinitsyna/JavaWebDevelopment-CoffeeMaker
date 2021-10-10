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
	private static final String SELECT_ALL_QUERY = "SELECT ingredient_id, name FROM ingredients";

	@Override
	public Ingredient read(int ingredient_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int add(Ingredient ingredient) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean remove(int ingredient_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAmount(int ingredient_id, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(int ingredient_id, IngredientInfo ingredientInfo) {
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
				ingredient.setId(resultSet.getInt(1));
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
