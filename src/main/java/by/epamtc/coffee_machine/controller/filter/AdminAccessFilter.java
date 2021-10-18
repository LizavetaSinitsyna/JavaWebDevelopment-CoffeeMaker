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

public class AdminAccessFilter implements Filter {
	private static final String FORBIDEN_ACCESS_NEXT_PATH = "/forbidenAccess";
	private static final int ADMIN_ROLE_ID = 1;
	private static final String COMMANDS_PARAMETER_NAME = "commands";

	private Set<String> commandsWithAccessControl;

	public void destroy() {
		commandsWithAccessControl = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String command = httpRequest.getParameter(AttributeName.COMMAND);
		if (command != null && commandsWithAccessControl.contains(command.toLowerCase())) {
			Object user = httpRequest.getSession().getAttribute(AttributeName.USER);
			if (user == null || ((UserLoginTransfer) user).getRoleId() != ADMIN_ROLE_ID) {
				httpResponse.sendRedirect(httpRequest.getContextPath() + FORBIDEN_ACCESS_NEXT_PATH);
				return;
			}
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		commandsWithAccessControl = new HashSet<>();
		String[] commands = fConfig.getInitParameter(COMMANDS_PARAMETER_NAME).split("\\n");
		for (String element : commands) {
			commandsWithAccessControl.add(element.toLowerCase());
		}
	}

}
