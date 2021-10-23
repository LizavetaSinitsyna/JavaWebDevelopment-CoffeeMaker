package by.epamtc.coffee_machine.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.coffee_machine.controller.AttributeName;

/**
 * Provides support in check of user authorization.
 *
 */
public class LoginAccessFilter implements Filter {

	public void destroy() {
	}

	/**
	 * Check if the user from the request has signed in. If no, sets error to the
	 * response with code 401.
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		Object user = httpRequest.getSession().getAttribute(AttributeName.USER);
		if (user == null) {
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
