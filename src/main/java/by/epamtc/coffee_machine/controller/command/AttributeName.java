/**
 * 
 */
package by.epamtc.coffee_machine.controller.command;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public enum AttributeName {
	POPULAR_DRINKS("popular_drinks");

	private String name;

	AttributeName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
