package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.controller.AttributeName;
import by.epamtc.coffee_machine.controller.Command;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;

/**
 * 
 * {@code Command} realization for performing view product action.
 *
 */
public class ViewProductCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(ViewProductCommand.class.getName());

	private static final String NEXT_PATH = "/WEB-INF/jsp/drink.jsp";

	/**
	 * Takes product id from request and forwards to product page with product
	 * information for showing on it.
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		long productId = Long.parseLong(request.getParameter(AttributeName.DRINK_ID));
		try {
			Drink drink = ServiceProvider.getInstance().getDrinkService().obtainDrink(productId);
			request.setAttribute(AttributeName.DRINK, drink);
			request.getRequestDispatcher(NEXT_PATH).forward(request, response);
		} catch (ServiceException | ServletException | IOException e) {
			LOG.error(e.getMessage(), e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

	}

}
