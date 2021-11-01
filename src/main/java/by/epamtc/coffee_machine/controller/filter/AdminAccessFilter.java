package by.epamtc.coffee_machine.controller.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.coffee_machine.bean.transfer.UserLoginTransfer;
import by.epamtc.coffee_machine.controller.AttributeName;

/**
 * Provides support in check of administrator authorization.
 *
 */
public class AdminAccessFilter implements Filter {
	private static final int ADMIN_ROLE_ID = 1;
	private static final String COMMANDS_PARAMETER_NAME = "commands";

	/**
	 * Contains names of commands for which the check of authorization under
	 * administrator will be performed.
	 */
	private Set<String> commandsWithAccessControl;

	/**
	 * Destroys the list of commands.
	 */
	public void destroy() {
		commandsWithAccessControl = null;
	}

	/**
	 * Check if the user from the request has administrator's role. If no, sets
	 * error to the response with code 403.
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String command = httpRequest.getParameter(AttributeName.COMMAND);
		if (command != null && commandsWithAccessControl.contains(command.toLowerCase())) {
			Object user = httpRequest.getSession().getAttribute(AttributeName.USER);
			if (user == null || ((UserLoginTransfer) user).getRoleId() != ADMIN_ROLE_ID) {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
		}

		chain.doFilter(request, response);
	}

	/**
	 * Initialize the list of command names for which the check of authorization
	 * under administrator will be performed. The list of commands is specified in
	 * web.xml.
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		commandsWithAccessControl = new HashSet<>();
		String[] commands = fConfig.getInitParameter(COMMANDS_PARAMETER_NAME).split(";");
		for (String element : commands) {
			commandsWithAccessControl.add(element.toLowerCase().trim());
		}
	}

}
