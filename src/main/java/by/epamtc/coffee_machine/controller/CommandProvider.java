package by.epamtc.coffee_machine.controller;

import java.util.HashMap;

import by.epamtc.coffee_machine.controller.command.AddProductCommand;
import by.epamtc.coffee_machine.controller.command.CancelOrderCommand;
import by.epamtc.coffee_machine.controller.command.ChangeLanguageCommand;
import by.epamtc.coffee_machine.controller.command.EditProductCommand;
import by.epamtc.coffee_machine.controller.command.LogOutCommand;
import by.epamtc.coffee_machine.controller.command.LoginCommand;
import by.epamtc.coffee_machine.controller.command.MakeOrderCommand;
import by.epamtc.coffee_machine.controller.command.NoSuchCommand;
import by.epamtc.coffee_machine.controller.command.PayForOrderCommand;
import by.epamtc.coffee_machine.controller.command.RegistrationCommand;
import by.epamtc.coffee_machine.controller.command.SelectPopularDrinksCommand;
import by.epamtc.coffee_machine.controller.command.ViewMenuCommand;
import by.epamtc.coffee_machine.controller.command.ViewProductAddCommand;
import by.epamtc.coffee_machine.controller.command.ViewProductCommand;
import by.epamtc.coffee_machine.controller.command.ViewProductEditCommand;

/**
 * Singleton-based class which provides specific realization of {@link Command}
 * by command name.
 * 
 * @see CommandName
 * @see Command
 */
public class CommandProvider {
	private HashMap<CommandName, Command> commands = new HashMap<>();

	private CommandProvider() {
		commands.put(CommandName.SELECT_POPULAR_DRINKS, new SelectPopularDrinksCommand());
		commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
		commands.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguageCommand());
		commands.put(CommandName.REGISTRATION, new RegistrationCommand());
		commands.put(CommandName.LOGIN, new LoginCommand());
		commands.put(CommandName.LOG_OUT, new LogOutCommand());
		commands.put(CommandName.VIEW_MENU, new ViewMenuCommand());
		commands.put(CommandName.VIEW_PRODUCT, new ViewProductCommand());
		commands.put(CommandName.VIEW_PRODUCT_EDIT, new ViewProductEditCommand());
		commands.put(CommandName.EDIT_PRODUCT, new EditProductCommand());
		commands.put(CommandName.MAKE_ORDER, new MakeOrderCommand());
		commands.put(CommandName.PAY_FOR_ORDER, new PayForOrderCommand());
		commands.put(CommandName.CANCEL_ORDER, new CancelOrderCommand());
		commands.put(CommandName.VIEW_PRODUCT_ADD, new ViewProductAddCommand());
		commands.put(CommandName.ADD_PRODUCT, new AddProductCommand());
	}

	private static final class SingletonHelper {
		private static final CommandProvider INSTANCE = new CommandProvider();
	}

	public static CommandProvider getInstance() {
		return SingletonHelper.INSTANCE;
	}

	/**
	 * Provides realization of {@link Command} by specified name.
	 * 
	 * @param commandName the name of required command. The possible command names
	 *                    are stated in {@link CommandName}.
	 * @return realization of {@link Command} with specified name or
	 *         {@link NoSuchCommand} if passed parameter is invalid or command with
	 *         such name doesn't exist.
	 */
	public Command retriveCommand(String commandName) {
		Command command = commands.get(CommandName.NO_SUCH_COMMAND);

		if (commandName == null) {
			return command;
		}

		try {
			command = commands.get(CommandName.valueOf(commandName.toUpperCase()));
		} catch (IllegalArgumentException e) {
			return command;
		}

		return command;

	}
}
