/**
 * 
 */
package by.epamtc.coffee_machine.validation;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class ValidationHelper {
	public static final String NEGATIVE_PARAM_EXCEPTION = "Parameter can't be negative!";
	public static final String NULL_CONNECTION_EXCEPTION = "Database connection can't be null.";
	public static final String NULL_ARGUMENT_EXCEPTION = "Passed parameter can't be null";
	public static final String NO_GENERATED_ID_EXCEPTION = "Add process failed, no ID was generated.";
	public static final String NULL_ACCOUNT_EXCEPTION = "Created account can't be null!";
	
	public static final String DIGITS_REGEX = "\\d+";
	
	private ValidationHelper() {
		
	}

	public static boolean isNull(Object obj) {
		return obj == null;
	}

	public static boolean isNegative(double number) {
		return number < 0;
	}
	
	public static boolean isNegative(int number) {
		return number < 0;
	}
	
	public static boolean isPositive(double number) {
		return number > 0;
	}
	
	public static boolean isPositive(int number) {
		return number > 0;
	}
}
