package by.epamtc.coffee_machine.service;

public class CommonExceptionMessage {
	public static final String NEGATIVE_PARAM = "Parameter can't be negative!";
	public static final String NULL_CONNECTION = "Database connection can't be null.";
	public static final String NULL_ARGUMENT = "Passed parameter can't be null";
	public static final String NULL_OR_EMPTY_ARGUMENT = "Passed parameter can't be null or empty";
	public static final String NO_GENERATED_ID = "Add process failed, no ID was generated.";
	public static final String NULL_ACCOUNT = "Created account can't be null!";
	
	private CommonExceptionMessage() {
		
	}
	
}
