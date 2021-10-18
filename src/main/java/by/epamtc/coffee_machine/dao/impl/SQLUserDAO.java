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

public class SQLUserDAO implements UserDAO {
	private static final ConnectionPool CONNECTION_POOL = ConnectionPool.retrieveConnectionPool();
	private static final String ADD_QUERY = "INSERT INTO users "
			+ "(login, password, bonus_account_id, account_id, name, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String SEARCH_EMAIL_QUERY = "SELECT * FROM users WHERE email = ?";
	private static final String SEARCH_LOGIN_QUERY = "SELECT * FROM users WHERE login = ?";
	private static final String LOGIN_QUERY = "SELECT user_id, role_id, password FROM users WHERE login = ? OR email = ?";

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

	@Override
	public long add(User user) throws DAOException {
		if (user == null) {
			return -1;
		}
		UserInfo userInfo = user.getInfo();
		if (userInfo == null) {
			return -1;
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

	@Override
	public boolean remove(int user_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(int user_id, UserInfo userInfo) {
		// TODO Auto-generated method stub
		return false;
	}

}
