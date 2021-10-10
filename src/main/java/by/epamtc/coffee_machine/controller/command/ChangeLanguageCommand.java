/**
 * 
 */
package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class ChangeLanguageCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			String language = request.getParameter(AttributeName.NEW_LANGUAGE);
			String path = request.getParameter(AttributeName.CURRENT_PAGE);
			Cookie cookie = new Cookie(AttributeName.LANGUAGE, language);
			response.addCookie(cookie);
			response.sendRedirect(request.getContextPath() + path);
		} catch (IOException e) {
			// log4j2
			e.printStackTrace();
		}

	}

}
