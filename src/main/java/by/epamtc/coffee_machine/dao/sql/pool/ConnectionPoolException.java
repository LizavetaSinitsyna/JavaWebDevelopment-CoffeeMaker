/**
 * 
 */
package by.epamtc.coffee_machine.dao.sql.pool;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class ConnectionPoolException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConnectionPoolException() {
		super();
	}

	public ConnectionPoolException(String message) {
		super(message);
	}

	public ConnectionPoolException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectionPoolException(Throwable cause) {
		super(cause);
	}
}
