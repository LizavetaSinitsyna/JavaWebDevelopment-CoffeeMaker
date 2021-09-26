/**
 * 
 */
package by.epamtc.coffee_machine.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import by.epamtc.coffee_machine.bean.User;
import by.epamtc.coffee_machine.bean.UserInfo;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.UserDAO;
import by.epamtc.coffee_machine.dao.sql.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.dao.sql.pool.ConnectionPoolImpl;
import by.epamtc.coffee_machine.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class SQLUserDAO implements UserDAO {
	private final ConnectionPoolImpl connectionPool = ConnectionPoolImpl.retrieveConnectionPool();
	private final String ADD_QUERY = "INSERT INTO users "
			+ "(login, password, bonus_account_id, account_id, name, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private final String SEARCH_EMAIL_QUERY = "SELECT * FROM users WHERE email = ?";
	private final String SEARCH_LOGIN_QUERY = "SELECT * FROM users WHERE login = ?";

	@Override
	public boolean authorization(String login, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registration(UserInfo userInfo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User searchByLogin(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsEmail(String email) throws DAOException {
		boolean result = false;

		if (ValidationHelper.isNull(email)) {
			return result;
		}

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = connectionPool.retrieveConnection();
			statement = connection.prepareStatement(SEARCH_EMAIL_QUERY);
			statement.setString(1, email);
			resultSet = statement.executeQuery();
			result = resultSet.next();
		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				connectionPool.closeConnection(connection, statement, resultSet);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}
		return result;
	}

	@Override
	public boolean containsUsername(String username) throws DAOException {
		boolean result = false;

		if (ValidationHelper.isNull(username)) {
			return result;
		}

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.retrieveConnection();
			statement = connection.prepareStatement(SEARCH_LOGIN_QUERY);
			statement.setString(1, username);
			resultSet = statement.executeQuery();
			result = resultSet.next();
		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				connectionPool.closeConnection(connection, statement, resultSet);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}
		return result;
	}

	@Override
	public int add(User user) throws DAOException {
		if (ValidationHelper.isNull(user)) {
			return -1;
		}
		UserInfo userInfo = user.getInfo();
		if (ValidationHelper.isNull(userInfo)) {
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
			preparedStatement.setString(1, userInfo.getLogin());
			preparedStatement.setString(2, userInfo.getPassword());
			preparedStatement.setInt(3, user.getBonusAccount().getId());
			preparedStatement.setInt(4, user.getAccount().getId());
			preparedStatement.setString(5, userInfo.getName());
			preparedStatement.setString(6, userInfo.getEmail());
			preparedStatement.setString(7, userInfo.getPhone());
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
