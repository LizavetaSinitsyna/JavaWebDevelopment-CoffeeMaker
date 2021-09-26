/**
 * 
 */
package by.epamtc.coffee_machine.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import by.epamtc.coffee_machine.bean.Account;
import by.epamtc.coffee_machine.dao.AccountDAO;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.sql.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.dao.sql.pool.ConnectionPoolImpl;
import by.epamtc.coffee_machine.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class SQLAccountDAO implements AccountDAO {
	private final ConnectionPoolImpl connectionPool = ConnectionPoolImpl.retrieveConnectionPool();

	private final String ADD_QUERY = "INSERT INTO accounts (balance) VALUES (?)";
	private final String UPDATE_QUERY = "UPDATE accounts SET balance = ? WHERE account_id = ?";
	private final String SELECT_BY_ID_QUERY = "SELECT * FROM accounts WHERE account_id = ?";

	@Override
	public Account read(int account_id) throws DAOException {
		Account account = null;
		try (Connection connection = ConnectionPoolImpl.retrieveConnectionPool().retrieveConnection()) {
			if (ValidationHelper.isNull(connection)) {
				throw new DAOException("Database �onnection can't be null.");
			}
			try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
				preparedStatement.setInt(1, account_id);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						account = new Account();
						account.setId(resultSet.getInt(1));
						account.setBalance(resultSet.getInt(2));
					}
				} catch (SQLException e) {
					throw new DAOException(e.getMessage(), e);
				}
			} catch (SQLException e) {
				throw new DAOException(e.getMessage(), e);
			}
		} catch (SQLException | ConnectionPoolException e) {
			throw new DAOException(e.getMessage(), e);
		}
		return account;

	}

	@Override
	public int add(Account account) throws DAOException {
		if (ValidationHelper.isNull(account)) {
			return -1;
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = ConnectionPoolImpl.retrieveConnectionPool().retrieveConnection();
			if (connection == null) {
				throw new DAOException(ValidationHelper.NULL_CONNECTION_EXCEPTION);
			}
			preparedStatement = connection.prepareStatement(ADD_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, account.getBalance());
			preparedStatement.executeUpdate();
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				} else {
					throw new DAOException(ValidationHelper.NO_GENERATED_ID_EXCEPTION);
				}
			}
		} catch (SQLException | ConnectionPoolException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				connectionPool.closeConnection(connection, preparedStatement);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}

	}

	@Override
	public void update(int account_id, int amount) throws DAOException {
		try (Connection connection = ConnectionPoolImpl.retrieveConnectionPool().retrieveConnection()) {
			if (ValidationHelper.isNull(connection)) {
				throw new DAOException("Database �onnection can't be null.");
			}
			try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
				preparedStatement.setInt(1, amount);
				preparedStatement.setInt(2, account_id);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		} catch (SQLException | ConnectionPoolException e) {
			throw new DAOException(e.getMessage(), e);
		}
	}
}
