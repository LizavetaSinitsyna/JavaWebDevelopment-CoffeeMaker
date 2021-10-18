package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epamtc.coffee_machine.controller.AttributeName;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;
import by.epamtc.coffee_machine.service.UserService;
import by.epamtc.coffee_machine.service.UserValidationError;

public class RegistrationCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(RegistrationCommand.class.getName());
	
	private static final String NEXT_PATH_SUCCESS_REGISTRATION = "/successRegistration";
	private static final String NEXT_PATH_FAILED_REGISTRATION = "/registration";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter(AttributeName.EMAIL);
		String password = request.getParameter(AttributeName.PASSWORD);
		String repeatPassword = request.getParameter(AttributeName.REPEAT_PASSWORD);
		String username = request.getParameter(AttributeName.USERNAME);
		String name = request.getParameter(AttributeName.NAME);
		String phone = request.getParameter(AttributeName.PHONE);

		try {
			UserService userService = ServiceProvider.getInstance().getUserService();
			Set<UserValidationError> registrationErrors = userService.registration(email, password, repeatPassword,
					username, name, phone);
			if (registrationErrors.size() > 0) {
				request.setAttribute(AttributeName.ERRORS, registrationErrors);
				request.setAttribute(AttributeName.EMAIL, email);
				request.setAttribute(AttributeName.USERNAME, username);
				request.setAttribute(AttributeName.NAME, name);
				request.setAttribute(AttributeName.PHONE, phone);
				request.getRequestDispatcher(NEXT_PATH_FAILED_REGISTRATION).forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + NEXT_PATH_SUCCESS_REGISTRATION);
			}

		} catch (ServiceException | IOException | ServletException e) {
			LOG.error(e.getMessage(), e);
		}

	}

}
