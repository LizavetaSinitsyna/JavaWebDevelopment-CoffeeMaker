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
public class MakeOrderCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String[] drinksId = request.getParameterValues(AttributeName.DRINK_ID);
		String[] drinksAmount = request.getParameterValues(AttributeName.DRINK_AMOUNT);

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
