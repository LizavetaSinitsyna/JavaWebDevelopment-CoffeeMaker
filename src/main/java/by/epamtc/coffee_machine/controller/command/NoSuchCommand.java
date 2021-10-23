package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epamtc.coffee_machine.controller.Command;

/**
 * 
 * {@code Command} realization for the situation of no passed command.
 *
 */
public class NoSuchCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(NoSuchCommand.class.getName());

	private static final String NEXT_PATH = "/index.jsp";

	/**
	 * Performs redirect to the main page.
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			response.sendRedirect(request.getContextPath() + NEXT_PATH);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
