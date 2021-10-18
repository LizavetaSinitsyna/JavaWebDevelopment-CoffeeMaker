package by.epamtc.coffee_machine.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import by.epamtc.coffee_machine.bean.BonusAccount;
import by.epamtc.coffee_machine.dao.BonusAccountDAO;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.service.CommonExceptionMessage;
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPool;

public class SQLBonusAccountDAO implements BonusAccountDAO {
	private static final ConnectionPool CONNECTION_POOL = ConnectionPool.retrieveConnectionPool();

	private static final String ADD_QUERY = "INSERT INTO bonus_accounts (balance) VALUES (%s)";
	private static final String UPDATE_QUERY = "UPDATE bonus_accounts SET balance = %s WHERE bonus_account_id = %s";
	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM bonus_accounts WHERE bonus_account_id = %s";
	private static final String SELECT_BY_USER_ID_QUERY = "SELECT * FROM bonus_accounts INNER JOIN users ON "
			+ "bonus_accounts.bonus_account_id = users.bonus_account_id WHERE user_id = %s";

	@Override
	public BonusAccount read(long accountId) throws DAOException {
		BonusAccount account = null;

		if (accountId <= 0) {
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
				account = new BonusAccount();
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
	public BonusAccount readByUserId(long userId) throws DAOException {
		BonusAccount account = null;

		if (userId <= 0) {
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
				account = new BonusAccount();
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
	public long add(BonusAccount account) throws DAOException {
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
	public boolean update(int accountId, int amount) throws DAOException {
		if (accountId <= 0 || amount <= 0) {
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
