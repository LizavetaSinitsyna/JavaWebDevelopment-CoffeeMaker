package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epamtc.coffee_machine.bean.transfer.UserLoginTransfer;
import by.epamtc.coffee_machine.controller.AttributeName;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;
import by.epamtc.coffee_machine.service.UserService;

public class LoginCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(LoginCommand.class.getName());
	
	private static final String NEXT_PATH_SUCCESS_LOGIN = "/index.jsp";
	private static final String NEXT_PATH_FAILED_LOGIN = "/login";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String login = request.getParameter(AttributeName.LOGIN);
		String password = request.getParameter(AttributeName.PASSWORD);
		UserService userService = ServiceProvider.getInstance().getUserService();
		String nextPath = request.getParameter(AttributeName.NEXT_PAGE);
		
		try {
			UserLoginTransfer user = userService.login(login, password);
			if (user == null) {
				nextPath = NEXT_PATH_FAILED_LOGIN;
				request.setAttribute(AttributeName.INCORRECT_CREDENTIALS, true);
			} else {
				HttpSession session = request.getSession();
				session.setAttribute(AttributeName.USER, user);
				if (nextPath == null) {
					nextPath = NEXT_PATH_SUCCESS_LOGIN;
				}
			}
			request.getRequestDispatcher(nextPath).forward(request, response);
		} catch (ServiceException | ServletException | IOException e) {
			LOG.error(e.getMessage(), e);
		}

	}

}
