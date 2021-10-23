package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epamtc.coffee_machine.controller.AttributeName;
import by.epamtc.coffee_machine.controller.Command;

/**
 * 
 * {@code Command} realization for performing user's log out action.
 *
 */
public class LogOutCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(LogOutCommand.class.getName());

	private static final String NEXT_PATH = "/index.jsp";

	/**
	 * Deletes user from session.
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		if (session != null) {
			session.removeAttribute(AttributeName.USER);
		}
		try {
			response.sendRedirect(request.getContextPath() + NEXT_PATH);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
