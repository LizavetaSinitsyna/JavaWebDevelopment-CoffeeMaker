/**
 * 
 */
package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class SelectPopularDrinksCommand implements Command {
	private static final String NEXT_PATH = "/WEB-INF/jsp/welcome.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		try {
			List<DrinkTransfer> drinks = serviceProvider.getOrderDrinkService().selectPopularDrinks();
			request.setAttribute(AttributeName.MENU, drinks);
			request.getRequestDispatcher(NEXT_PATH).forward(request, response);
		} catch (ServiceException | ServletException | IOException e) {
			// log4j2
			e.printStackTrace();
		}
	}

}
