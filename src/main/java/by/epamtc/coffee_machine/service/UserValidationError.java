/**
 * 
 */
package by.epamtc.coffee_machine.service;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public enum UserValidationError {
	EMPTY_EMAIL("empty_email"), EMPTY_PASSWORD("empty_password"), EMPTY_REPEAT_PASSWORD("empty_repeat_password"),
	EMPTY_USERNAME("empty_username"), EMPTY_NAME("empty_name"), EMPTY_PHONE("empty_phone"),
	ILLEGAL_EMAIL("illegal_email"), ILLEGAL_PASSWORD("illegal_password"), INCORRECT_PASSWORD("incorrect_password"),
	ILLEGAL_USERNAME("illegal_username"), DUBLICATE_EMAIL("dublicate_email"), DUBLICATE_USERNAME("dublicate_username"),
	PASSWORD_NOT_CONFIRMED("password_not_confirmed"), ILLEGAL_PHONE("illegal_phone");

	private String value;

	UserValidationError(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
