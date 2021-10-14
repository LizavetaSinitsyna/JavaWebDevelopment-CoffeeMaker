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

import by.epamtc.coffee_machine.controller.command.AttributeName;
import by.epamtc.coffee_machine.service.validation.ValidationHelper;

/**
 * Servlet Filter implementation class AdminAccessFilter
 */
public class LoginAccessFilter implements Filter {
	private static final String LOGIN_PATH = "/login";

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		Object user = httpRequest.getSession().getAttribute(AttributeName.USER);
		if (ValidationHelper.isNull(user)) {
			httpRequest.getRequestDispatcher(LOGIN_PATH).forward(httpRequest, httpResponse);
			return;
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
