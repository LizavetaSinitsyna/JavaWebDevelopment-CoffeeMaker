package by.epamtc.coffee_machine.service;

/**
 * Contains {@code enum} values representing possible {@code User} validation
 * messages.
 *
 */
public enum UserValidationError {
	ILLEGAL_NAME("illegal_name"), ILLEGAL_EMAIL("illegal_email"), ILLEGAL_PASSWORD("illegal_password"),
	ILLEGAL_USERNAME("illegal_username"), DUBLICATE_EMAIL("dublicate_email"), DUBLICATE_USERNAME("dublicate_username"),
	PASSWORD_NOT_CONFIRMED("password_not_confirmed"), ILLEGAL_PHONE("illegal_phone");

	private String value;

	UserValidationError(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
