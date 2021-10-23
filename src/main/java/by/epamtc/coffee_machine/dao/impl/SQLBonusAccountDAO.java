package by.epamtc.coffee_machine.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import by.epamtc.coffee_machine.bean.BonusAccount;
import by.epamtc.coffee_machine.dao.BonusAccountDAO;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.service.CommonExceptionMessage;
import by.epamtc.coffee_machine.service.utility.DecimalExchange;
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPool;

/**
 * Provides methods for working with BonusAccounts table and
 * {@link BonusAccount} entity
 */
public class SQLBonusAccountDAO implements BonusAccountDAO {
	private static final ConnectionPool CONNECTION_POOL = ConnectionPool.retrieveConnectionPool();

	private static final String ADD_QUERY = "INSERT INTO bonus_accounts (balance) VALUES (%s)";
	private static final String UPDATE_QUERY = "UPDATE bonus_accounts SET balance = %s WHERE bonus_account_id = %s";
	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM bonus_accounts WHERE bonus_account_id = %s";
	private static final String SELECT_BY_USER_ID_QUERY = "SELECT * FROM bonus_accounts INNER JOIN users ON "
			+ "bonus_accounts.bonus_account_id = users.bonus_account_id WHERE user_id = %s";

	/**
	 * Read existed bonus account from database with specified account id.
	 * 
	 * @param accountId {@code long} value which uniquely indicates the account.
	 * @return {@code BonusAccount} with specified id or {@code null} if passed
	 *         {@code accountId} is invalid.
	 * @throws DAOException If problem occurs during interaction with database.
	 */
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
				account.setBalance(DecimalExchange.obtainFromInt(resultSet.getInt(2)));
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

	/**
	 * Read bonus account from database for specified user by user's id.
	 * 
	 * @param userId {@code long} value which uniquely indicates the user.
	 * @return {@code BonusAccount} for specified user or {@code null} if passed
	 *         {@code userId} is invalid.
	 * @throws DAOException If problem occurs during interaction with database.
	 */
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
				account.setBalance(DecimalExchange.obtainFromInt(resultSet.getInt(2)));
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

	/**
	 * Add passed bonus account to database.
	 * 
	 * @param account the account to be saved in database.
	 * @return {@code long} value representing bonus account id which was generated
	 *         after saving it in database.
	 * @throws DAOException If problem occurs during interaction with database or
	 *                      passed {@code account} is {@code null}.
	 */
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
			statement.executeUpdate(String.format(ADD_QUERY, DecimalExchange.revertToInt(account.getBalance())),
					Statement.RETURN_GENERATED_KEYS);
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

	/**
	 * Updates the balance of already existed account.
	 * 
	 * @param accounId   {@code long} value which uniquely indicates the existed
	 *                   account.
	 * @param newBalance {@code BigDecimal} value representing new account balance.
	 * @return {@code true} If the update was successful or {@code false} if update
	 *         was failed or passed parameters are invalid.
	 * @throws DAOException If problem occurs during interaction with database.
	 */
	@Override
	public boolean update(long accountId, BigDecimal newBalance) throws DAOException {
		if (accountId <= 0 || newBalance.compareTo(new BigDecimal("0")) <= 0) {
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
			effectedRows = statement
					.executeUpdate(String.format(UPDATE_QUERY, DecimalExchange.revertToInt(newBalance), accountId));

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
