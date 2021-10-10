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
public class AddToBasketCommand implements Command {
	private final static char DRINKS_DELIMITER = '|';

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String newDrinkId = request.getParameter(AttributeName.DRINK_ID);
		String nextPath = request.getParameter(AttributeName.CURRENT_PAGE);

		StringBuilder currentBasket = new StringBuilder();
		String cookieName;
		Cookie[] requestCookies = request.getCookies();
		for (Cookie cookie : requestCookies) {
			cookieName = cookie.getName();
			if (cookieName.equals(AttributeName.BASKET)) {
				currentBasket.append(cookie.getValue());
				break;
			}
		}
		if (currentBasket.length() > 0) {
			currentBasket.append(DRINKS_DELIMITER);
		}
		currentBasket.append(newDrinkId);

		Cookie cookie = new Cookie(AttributeName.BASKET, currentBasket.toString());
		response.addCookie(cookie);
		try {
			response.sendRedirect(request.getContextPath() + nextPath);
		} catch (IOException e) {
			// log4j2
			e.printStackTrace();
		}
	}

}
