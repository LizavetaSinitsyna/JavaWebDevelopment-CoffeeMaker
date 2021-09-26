/**
 * 
 */
package by.epamtc.coffee_machine.dao.sql.pool;

import java.sql.Connection;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface ConnectionPool {
	Connection retrieveConnection() throws ConnectionPoolException;
}
