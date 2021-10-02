/**
 * 
 */
package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class ViewProductCommand implements Command {
	private static final String NEXT_PATH = "/WEB-INF/jsp/drink.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		int productId = Integer.parseInt(request.getParameter(AttributeName.DRINK_ID));
		try {
			Drink drink = ServiceProvider.getInstance().getDrinkService().obtainDrink(productId);
			request.setAttribute(AttributeName.DRINK, drink);
			request.getRequestDispatcher(NEXT_PATH).forward(request, response);
		} catch (ServiceException | ServletException | IOException e) {
			// log4j2
			e.printStackTrace();
		}
		

	}

}
