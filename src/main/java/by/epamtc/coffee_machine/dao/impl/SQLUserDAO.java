package by.epamtc.coffee_machine.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import by.epamtc.coffee_machine.bean.User;
import by.epamtc.coffee_machine.bean.UserInfo;
import by.epamtc.coffee_machine.bean.transfer.UserLoginPasswordTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.UserDAO;
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.service.CommonExceptionMessage;
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPool;

/**
 * Provides methods for working with Users table and entities {@link User},
 * {@link UserLoginPasswordTransfer}
 */
public class SQLUserDAO implements UserDAO {
	private static final ConnectionPool CONNECTION_POOL = ConnectionPool.retrieveConnectionPool();
	private static final String ADD_QUERY = "INSERT INTO users "
			+ "(login, password, bonus_account_id, account_id, name, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String SEARCH_EMAIL_QUERY = "SELECT * FROM users WHERE email = ?";
	private static final String SEARCH_LOGIN_QUERY = "SELECT * FROM users WHERE login = ?";
	private static final String LOGIN_QUERY = "SELECT user_id, role_id, password FROM users WHERE login = ? OR email = ?";

	/**
	 * Returns User by the specified login.
	 * 
	 * @param login the login of the User
	 * @return {@code UserLoginPasswordTransfer} object representing user with
	 *         passed login or {@code null} if passed parameter is invalid or user
	 *         with such login doesn't exist.
	 * @throws DAOException If problem occurs during interaction with database.
	 */
	@Override
	public UserLoginPasswordTransfer login(String login) throws DAOException {
		UserLoginPasswordTransfer result = null;
		if (login == null) {
			return result;
		}

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = CONNECTION_POOL.retrieveConnection();
			statement = connection.prepareStatement(LOGIN_QUERY);
			statement.setString(1, login);
			statement.setString(2, login);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				result = new UserLoginPasswordTransfer();
				result.setId(resultSet.getInt(1));
				result.setRoleId(resultSet.getInt(2));
				result.setPassword(resultSet.getString(3));
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

	/**
	 * Checks if the user with specified email exists.
	 * 
	 * @param email the email to be checked.
	 * @return {@code true} if the user with passed email exists and {@code false}
	 *         in other case.
	 * @throws DAOException If problem occurs during interaction with database.
	 */
	@Override
	public boolean containsEmail(String email) throws DAOException {
		boolean result = false;

		if (email == null) {
			return result;
		}

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = CONNECTION_POOL.retrieveConnection();
			statement = connection.prepareStatement(SEARCH_EMAIL_QUERY);
			statement.setString(1, email);
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

	/**
	 * Checks if the user with specified username exists.
	 * 
	 * @param username the username to be checked.
	 * @return {@code true} if the user with passed username exists and
	 *         {@code false} in other case.
	 * @throws DAOException If problem occurs during interaction with database.
	 */
	@Override
	public boolean containsUsername(String username) throws DAOException {
		boolean result = false;

		if (username == null) {
			return result;
		}

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = CONNECTION_POOL.retrieveConnection();
			statement = connection.prepareStatement(SEARCH_LOGIN_QUERY);
			statement.setString(1, username);
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

	/**
	 * Add passed User to database.
	 * 
	 * @param user the user to be saved in database.
	 * @return {@code long} value which represents user id.
	 * @throws DAOException If problem occurs during interaction with database or
	 *                      passed parameter is invalid.
	 */

	@Override
	public long add(User user) throws DAOException {
		if (user == null) {
			throw new DAOException(CommonExceptionMessage.NULL_ARGUMENT);
		}
		UserInfo userInfo = user.getInfo();
		if (userInfo == null) {
			throw new DAOException(CommonExceptionMessage.NULL_ARGUMENT);
		}
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = ConnectionPool.retrieveConnectionPool().retrieveConnection();
			if (connection == null) {
				throw new DAOException(CommonExceptionMessage.NULL_CONNECTION);
			}
			preparedStatement = connection.prepareStatement(ADD_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, userInfo.getLogin());
			preparedStatement.setString(2, userInfo.getPassword());
			preparedStatement.setLong(3, user.getBonusAccountId());
			preparedStatement.setLong(4, user.getAccountId());
			preparedStatement.setString(5, userInfo.getName());
			preparedStatement.setString(6, userInfo.getEmail());
			preparedStatement.setString(7, userInfo.getPhone());
			preparedStatement.executeUpdate();
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getLong(1);
				} else {
					throw new DAOException(CommonExceptionMessage.NO_GENERATED_ID);
				}
			}
		} catch (SQLException | ConnectionPoolException e) {
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
