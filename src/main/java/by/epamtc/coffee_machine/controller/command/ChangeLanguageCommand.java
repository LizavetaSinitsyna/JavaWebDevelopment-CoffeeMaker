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
			String language = request.getParameter("new_lang");
			String path = request.getParameter("currentPage");
			Cookie cookie = new Cookie("lang", language);
			response.addCookie(cookie);
			response.sendRedirect(path);
		} catch (IOException e) {
			// log4j2
			e.printStackTrace();
		}

	}

}
