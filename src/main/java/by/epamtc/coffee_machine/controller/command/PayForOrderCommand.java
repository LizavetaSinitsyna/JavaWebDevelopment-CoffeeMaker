package by.epamtc.coffee_machine.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.coffee_machine.controller.Command;

public class PayForOrderCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		Object user = request.getAttribute("");

	}

}
