/**
 * 
 */
package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;
import java.util.List;

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
public class SelectPopularDrinksCommand implements Command {
	private static final String nextPath = "/welcome.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		try {
			List<Drink> drinks = serviceProvider.getOrderDrinkService().selectPopularDrinks();
			request.setAttribute(AttributeName.POPULAR_DRINKS.getName(), drinks);
			request.getRequestDispatcher(nextPath).forward(request, response);
		} catch (ServiceException | ServletException | IOException e) {
			// log4j2
			e.printStackTrace();
		}
	}

}
