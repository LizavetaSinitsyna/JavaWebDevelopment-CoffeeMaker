package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epamtc.coffee_machine.controller.AttributeName;
import by.epamtc.coffee_machine.controller.Command;

/**
 * 
 * {@code Command} realization for performing changing of language action.
 *
 */
public class ChangeLanguageCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(ChangeLanguageCommand.class.getName());

	/**
	 * Takes chosen language from request, saves it into the cookies and redirects to
	 * the same page.
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String language = request.getParameter(AttributeName.NEW_LANGUAGE);
			String path = request.getParameter(AttributeName.CURRENT_PAGE);
			Cookie cookie = new Cookie(AttributeName.LANGUAGE, language);
			response.addCookie(cookie);
			response.sendRedirect(request.getContextPath() + path);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

		}

	}

}
