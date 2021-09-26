/**
 * 
 */
package by.epamtc.coffee_machine.dao.sql.pool;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class InitConnectionPoolException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InitConnectionPoolException() {
		super();
	}

	public InitConnectionPoolException(String message) {
		super(message);
	}

	public InitConnectionPoolException(String message, Throwable cause) {
		super(message, cause);
	}

	public InitConnectionPoolException(Throwable cause) {
		super(cause);
	}
}
