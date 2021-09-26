/**
 * 
 */
package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;
import by.epamtc.coffee_machine.service.UserService;
import by.epamtc.coffee_machine.service.UserValidationError;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class RegistrationCommand implements Command {
	private static final String NEXT_PATH_SUCCESS_REGISTRATION = "/successRegistration.jsp";
	private static final String NEXT_PATH_FAILED_REGISTRATION = "/registration.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter(UserAttributeName.EMAIL);
		String password = request.getParameter(UserAttributeName.PASSWORD);
		String repeatPassword = request.getParameter(UserAttributeName.REPEAT_PASSWORD);
		String username = request.getParameter(UserAttributeName.USERNAME);
		String name = request.getParameter(UserAttributeName.NAME);
		String phone = request.getParameter(UserAttributeName.PHONE);
		String nextPath;
		HttpSession session = request.getSession();
		try {
			UserService userService = ServiceProvider.getInstance().getUserService();
			Set<UserValidationError> registrationErrors = userService.registration(email, password, repeatPassword,
					username, name, phone);
			if (registrationErrors.size() > 0) {
				session.setAttribute(UserAttributeName.REGISTRATION_ERRORS, registrationErrors);
				session.setAttribute(UserAttributeName.EMAIL, email);
				session.setAttribute(UserAttributeName.USERNAME, username);
				session.setAttribute(UserAttributeName.NAME, name);
				session.setAttribute(UserAttributeName.PHONE, phone);
				nextPath = NEXT_PATH_FAILED_REGISTRATION;
			} else {
				session.removeAttribute(UserAttributeName.REGISTRATION_ERRORS);
				session.removeAttribute(UserAttributeName.EMAIL);
				session.removeAttribute(UserAttributeName.USERNAME);
				session.removeAttribute(UserAttributeName.NAME);
				session.removeAttribute(UserAttributeName.PHONE);
				nextPath = NEXT_PATH_SUCCESS_REGISTRATION;
			}
			response.sendRedirect(request.getContextPath() + nextPath);
		} catch (ServiceException | IOException e) {
			// log4j2
			e.printStackTrace();
		}

	}

}
