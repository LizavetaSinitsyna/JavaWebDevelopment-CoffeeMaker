package by.epamtc.coffee_machine.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import by.epamtc.coffee_machine.bean.BonusAccount;
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPool;
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.dao.impl.pool.DBResourceManager;
import by.epamtc.coffee_machine.service.BonusAccountService;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;

public class BonusAccountServiceImplTest {
	private static ConnectionPool connectionPool;
	private static final String REMOVE_ACCOUNT_BY_ID = "DELETE FROM bonus_accounts WHERE bonus_account_id = %s";
	private static final String UPDATE_AUTO_INCREMENT = "ALTER TABLE bonus_accounts AUTO_INCREMENT = %s";

	@BeforeClass
	public static void initPool() {
		ResourceBundle rb = ResourceBundle.getBundle("resources/databaseTest");
		DBResourceManager.getInstance().setBundle(rb);
		connectionPool = ConnectionPool.retrieveConnectionPool();
		connectionPool.initPool();
	}

	@Test
	public void testCreateAccount() throws ServiceException, ConnectionPoolException {
		BonusAccountService accountService = ServiceProvider.getInstance().getBonusAccountService();
		BonusAccount actual = accountService.createAccount();
		BonusAccount expected = new BonusAccount();
		expected.setId(6);
		Connection connection = ConnectionPool.retrieveConnectionPool().retrieveConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(String.format(REMOVE_ACCOUNT_BY_ID, actual.getId()));
			statement.executeUpdate(String.format(UPDATE_AUTO_INCREMENT, actual.getId()));
		} catch (SQLException e) {
			throw new ServiceException(e.getMessage(), e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testObtainAccountByUserIdWithValidId() throws ServiceException {
		BonusAccountService accountService = ServiceProvider.getInstance().getBonusAccountService();
		BonusAccount actual = accountService.obtainAccountByUserId(1);
		BonusAccount expected = new BonusAccount();
		expected.setId(1);
		expected.setBalance(new BigDecimal("0.35"));
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testObtainAccountByUserIdWithInvalidId() throws ServiceException {
		BonusAccountService accountService = ServiceProvider.getInstance().getBonusAccountService();
		BonusAccount actual = accountService.obtainAccountByUserId(0);
		BonusAccount expected = null;
		Assert.assertEquals(expected, actual);
	}

	@AfterClass
	public static void closePool() throws ConnectionPoolException {
		connectionPool.clearConnectionPool();
	}

}
