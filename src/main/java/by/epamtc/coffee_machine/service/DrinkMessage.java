package by.epamtc.coffee_machine.service;

/**
 * Contains {@code enum} values representing possible {@code Drink}
 * validation messages.
 *
 */
public enum DrinkMessage {
	ILLEGAL_IMAGE_PATH("illegal_image_path"), ILLEGAL_DRINK_NAME("illegal_drink_name"),
	DUBLICATE_DRINK_NAME("dublicate_drink_name"), ILLEGAL_PRICE("illegal_price"),
	ILLEGAL_DESCRIPTION("illegal_description"), UNABLE_EDIT("unable_edit"), INVALID_DRINK_ID("invalid_drink_id");

	private String value;

	DrinkMessage(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
