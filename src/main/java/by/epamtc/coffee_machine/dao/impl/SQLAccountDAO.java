package by.epamtc.coffee_machine.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import by.epamtc.coffee_machine.bean.Account;
import by.epamtc.coffee_machine.dao.AccountDAO;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.service.CommonExceptionMessage;
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPool;

public class SQLAccountDAO implements AccountDAO {
	private static final ConnectionPool CONNECTION_POOL = ConnectionPool.retrieveConnectionPool();

	private static final String ADD_QUERY = "INSERT INTO accounts (balance) VALUES (%s)";
	private static final String UPDATE_QUERY = "UPDATE accounts SET balance = %s WHERE account_id = %s";
	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM accounts WHERE account_id = %s";
	private static final String SELECT_BY_USER_ID_QUERY = "SELECT * FROM accounts INNER JOIN users ON "
			+ "accounts.account_id = users.account_id WHERE user_id = %s";

	@Override
	public Account read(long accountId) throws DAOException {
		Account account = null;
		
		if(accountId <= 0) {
			return account;
		}
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = CONNECTION_POOL.retrieveConnection();

			if (connection == null) {
				throw new DAOException(CommonExceptionMessage.NULL_CONNECTION);
			}
			statement = connection.createStatement();
			resultSet = statement.executeQuery(String.format(SELECT_BY_ID_QUERY, accountId));
			if (resultSet.next()) {
				account = new Account();
				account.setId(resultSet.getLong(1));
				account.setBalance(resultSet.getInt(2));
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

		return account;

	}

	@Override
	public Account readByUserId(long userId) throws DAOException {
		Account account = null;
		
		if(userId <= 0) {
			return account;
		}

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = CONNECTION_POOL.retrieveConnection();

			if (connection == null) {
				throw new DAOException(CommonExceptionMessage.NULL_CONNECTION);
			}
			statement = connection.createStatement();
			resultSet = statement.executeQuery(String.format(SELECT_BY_USER_ID_QUERY, userId));
			if (resultSet.next()) {
				account = new Account();
				account.setId(resultSet.getLong(1));
				account.setBalance(resultSet.getInt(2));
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

		return account;

	}

	@Override
	public long add(Account account) throws DAOException {
		if (account == null) {
			throw new DAOException(CommonExceptionMessage.NULL_ARGUMENT);
		}

		Connection connection = null;
		Statement statement = null;
		ResultSet generatedKeys = null;
		long accountId;

		try {
			connection = ConnectionPool.retrieveConnectionPool().retrieveConnection();
			if (connection == null) {
				throw new DAOException(CommonExceptionMessage.NULL_CONNECTION);
			}
			statement = connection.createStatement();
			statement.executeUpdate(String.format(ADD_QUERY, account.getBalance()), Statement.RETURN_GENERATED_KEYS);
			generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				accountId = generatedKeys.getLong(1);
			} else {
				throw new DAOException(CommonExceptionMessage.NO_GENERATED_ID);
			}

		} catch (SQLException | ConnectionPoolException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				CONNECTION_POOL.closeConnection(connection, statement, generatedKeys);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}

		return accountId;

	}

	@Override
	public boolean update(long accountId, int amount) throws DAOException {
		if(accountId <= 0 || amount <= 0) {
			return false;
		}
		
		Connection connection = null;
		Statement statement = null;
		int effectedRows = 0;
		try {
			connection = CONNECTION_POOL.retrieveConnection();
			if (connection == null) {
				throw new DAOException(CommonExceptionMessage.NULL_CONNECTION);
			}
			statement = connection.createStatement();
			effectedRows = statement.executeUpdate(String.format(UPDATE_QUERY, amount, accountId));

		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				CONNECTION_POOL.closeConnection(connection, statement);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}
		return effectedRows > 0;

	}
}
