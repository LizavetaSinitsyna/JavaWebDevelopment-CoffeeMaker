package by.epamtc.coffee_machine.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Represents the interface which must be implemented in order to be executed by
 * {@link by.epamtc.coffee_machine.controller.Controller}
 *
 * @see CommandProvider
 *
 */
public interface Command {
	void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
