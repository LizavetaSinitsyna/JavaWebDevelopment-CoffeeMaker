/**-
 * In order to use this implementation of ConnectionPool the following information should be put on web.xml file:
 * 	<description>MySQL</description>
	<resource-ref>
		<description>MySQL DB Connection Pool</description>
		<res-ref-name>jdbc/CoffeeMachine</res-ref-name>
		<res-type>javax.sql.DataSource</res-type>
		<res-auth>Container</res-auth>
	</resource-ref>
 */
package by.epamtc.coffee_machine.dao.sql.pool;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class TomcatConnectionPoolImpl implements ConnectionPool {
	private TomcatConnectionPoolImpl() {

	}

	private static class ConnectionPoolHelper {
		private static final TomcatConnectionPoolImpl INSTANCE = new TomcatConnectionPoolImpl();
	}

	public static TomcatConnectionPoolImpl retrieveConnectionPool() {
		return ConnectionPoolHelper.INSTANCE;
	}

	@Override
	public Connection retrieveConnection() throws ConnectionPoolException {
		Connection connection = null;
		try {
			InitialContext initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/CoffeeMachine");
			connection = ds.getConnection();
		} catch (NamingException | SQLException e) {
			throw new ConnectionPoolException(e);
		}

		return connection;
	}

}
