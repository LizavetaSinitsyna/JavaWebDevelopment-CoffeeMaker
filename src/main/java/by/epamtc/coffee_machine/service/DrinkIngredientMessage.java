/**
 * 
 */
package by.epamtc.coffee_machine.service;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public enum DrinkIngredientMessage {
	ILLEGAL_INGREDIENT_AMOUNT("illegal_ingredient_amount"), INVALID_DRINK_ID("invalid_drink_id"),
	ILLEGAL_DRINK_INGREDIENT_AMOUNT("illegal_drink_ingredient_amount"), UNABLE_EDIT("unable_edit"),
	INVALID_INGREDIENT_ID("invalid_ingredient_id"), DUPLICATE_INGREDIENT("dublicate_ingredient");

	private String value;

	DrinkIngredientMessage(String value) {
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
