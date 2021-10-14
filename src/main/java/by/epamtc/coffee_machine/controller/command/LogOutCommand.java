/**
 * 
 */
package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.coffee_machine.service.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class LogOutCommand implements Command {
	private static final String NEXT_PATH = "/index.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (!ValidationHelper.isNull(session)) {
			session.removeAttribute(AttributeName.USER);
		}
		try {
			response.sendRedirect(request.getContextPath() + NEXT_PATH);
		} catch (IOException e) {
			// log4j2
			e.printStackTrace();
		}
	}

}
