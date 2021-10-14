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
import by.epamtc.coffee_machine.service.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class SQLAccountDAO implements AccountDAO {
	private static final ConnectionPoolImpl CONNECTION_POOL = ConnectionPoolImpl.retrieveConnectionPool();

	private static final String ADD_QUERY = "INSERT INTO accounts (balance) VALUES (?)";
	private static final String UPDATE_QUERY = "UPDATE accounts SET balance = ? WHERE account_id = ?";
	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM accounts WHERE account_id = ?";

	@Override
	public Account read(long accountId) throws DAOException {
		Account account = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = CONNECTION_POOL.retrieveConnection();

			if (ValidationHelper.isNull(connection)) {
				throw new DAOException(ValidationHelper.NULL_CONNECTION_EXCEPTION);
			}
			preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY);
			preparedStatement.setLong(1, accountId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				account = new Account();
				account.setId(resultSet.getLong(1));
				account.setBalance(resultSet.getInt(2));
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

		return account;

	}

	@Override
	public long add(Account account) throws DAOException {
		if (ValidationHelper.isNull(account)) {
			return -1;
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generatedKeys = null;

		try {
			connection = ConnectionPoolImpl.retrieveConnectionPool().retrieveConnection();
			if (ValidationHelper.isNull(connection)) {
				throw new DAOException(ValidationHelper.NULL_CONNECTION_EXCEPTION);
			}
			preparedStatement = connection.prepareStatement(ADD_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, account.getBalance());
			preparedStatement.executeUpdate();
			generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				return generatedKeys.getLong(1);
			} else {
				throw new DAOException(ValidationHelper.NO_GENERATED_ID_EXCEPTION);
			}

		} catch (SQLException | ConnectionPoolException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				CONNECTION_POOL.closeConnection(connection, preparedStatement, generatedKeys);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}

	}

	@Override
	public void update(long accountId, int amount) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = CONNECTION_POOL.retrieveConnection();
			if (ValidationHelper.isNull(connection)) {
				throw new DAOException(ValidationHelper.NULL_CONNECTION_EXCEPTION);
			}
			preparedStatement = connection.prepareStatement(UPDATE_QUERY);
			preparedStatement.setInt(1, amount);
			preparedStatement.setLong(2, accountId);
			preparedStatement.executeUpdate();

		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				CONNECTION_POOL.closeConnection(connection, preparedStatement);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}

	}
}
