/**
 * 
 */
package by.epamtc.coffee_machine.dao;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class DAOException extends Exception {
	private static final long serialVersionUID = 1L;

	public DAOException() {
		super();
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}

}
