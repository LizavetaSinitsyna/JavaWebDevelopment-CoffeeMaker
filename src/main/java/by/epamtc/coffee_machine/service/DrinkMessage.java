/**
 * 
 */
package by.epamtc.coffee_machine.service;

/**
 * @author Lizaveta Sinitsyna
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
