/**
 * 
 */
package by.epamtc.coffee_machine.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import by.epamtc.coffee_machine.bean.BonusAccount;
import by.epamtc.coffee_machine.dao.BonusAccountDAO;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.sql.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.dao.sql.pool.ConnectionPoolImpl;
import by.epamtc.coffee_machine.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class SQLBonusAccountDAO implements BonusAccountDAO {
	private static final ConnectionPoolImpl CONNECTION_POOL = ConnectionPoolImpl.retrieveConnectionPool();

	private static final String ADD_QUERY = "INSERT INTO bonus_accounts (balance) VALUES (?)";

	@Override
	public BonusAccount read(int account_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int add(BonusAccount account) throws DAOException {
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
				CONNECTION_POOL.closeConnection(connection, preparedStatement);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}
	}

	@Override
	public boolean update(int account_id, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
