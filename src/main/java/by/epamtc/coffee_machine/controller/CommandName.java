package by.epamtc.coffee_machine.controller;

/**
 * Contains names of commands for which exist specific realization of
 * {@link by.epamtc.coffee_machine.controller.Command}.
 *
 */
public enum CommandName {
	SELECT_POPULAR_DRINKS, REGISTRATION, CHANGE_LANGUAGE, LOGIN, LOG_OUT, VIEW_MENU, VIEW_PRODUCT, VIEW_PRODUCT_EDIT,
	EDIT_PRODUCT, MAKE_ORDER, PAY_FOR_ORDER, NO_SUCH_COMMAND
}
