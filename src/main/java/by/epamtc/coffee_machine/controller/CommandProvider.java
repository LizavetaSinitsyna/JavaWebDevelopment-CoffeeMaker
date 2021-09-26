/**
 * 
 */
package by.epamtc.coffee_machine.controller;

import java.util.HashMap;

import by.epamtc.coffee_machine.controller.command.ChangeLanguageCommand;
import by.epamtc.coffee_machine.controller.command.Command;
import by.epamtc.coffee_machine.controller.command.NoSuchCommand;
import by.epamtc.coffee_machine.controller.command.RegistrationCommand;
import by.epamtc.coffee_machine.controller.command.SelectPopularDrinksCommand;
import by.epamtc.coffee_machine.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class CommandProvider {
	private HashMap<CommandName, Command> commands = new HashMap<>();

	private CommandProvider() {
		commands.put(CommandName.SELECT_POPULAR_DRINKS, new SelectPopularDrinksCommand());
		commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
		commands.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguageCommand());
		commands.put(CommandName.REGISTRATION, new RegistrationCommand());
	}

	private static final class SingletonHelper {
		private static final CommandProvider INSTANCE = new CommandProvider();
	}

	public static CommandProvider getInstance() {
		return SingletonHelper.INSTANCE;
	}

	public Command retriveCommand(String commandName) {
		Command command = commands.get(CommandName.NO_SUCH_COMMAND);

		if (ValidationHelper.isNull(commandName)) {
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
