package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;
import by.epamtc.coffee_machine.controller.AttributeName;
import by.epamtc.coffee_machine.controller.Command;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;

/**
 * 
 * {@code Command} realization for performing selecting popular drinks action.
 *
 */
public class SelectPopularDrinksCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(SelectPopularDrinksCommand.class.getName());

	private static final String NEXT_PATH = "/WEB-INF/jsp/welcome.jsp";

	/**
	 * Forwards to the main page with information about popular drinks.
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		try {
			List<DrinkTransfer> drinks = serviceProvider.getOrderDrinkService().selectPopularDrinks();
			request.setAttribute(AttributeName.MENU, drinks);
			request.getRequestDispatcher(NEXT_PATH).forward(request, response);
		} catch (ServiceException | ServletException | IOException e) {
			LOG.error(e.getMessage(), e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
