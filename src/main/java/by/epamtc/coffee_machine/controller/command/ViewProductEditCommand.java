package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.transfer.DrinkIngredientTransfer;
import by.epamtc.coffee_machine.bean.transfer.IngredientTransfer;
import by.epamtc.coffee_machine.controller.AttributeName;
import by.epamtc.coffee_machine.controller.Command;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;

/**
 * 
 * {@code Command} realization for performing view of product edit page.
 *
 */
public class ViewProductEditCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(ViewProductEditCommand.class.getName());

	private static final String NEXT_PATH = "/WEB-INF/jsp/drink_edit.jsp";
	private static final ServiceProvider SERVICE_PROVIDER = ServiceProvider.getInstance();

	/**
	 * Takes product id from request and forwards to product page with product
	 * information for showing on it.
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			Drink drink;
			long drinkId;

			drinkId = Long.parseLong(request.getParameter(AttributeName.DRINK_ID));
			drink = SERVICE_PROVIDER.getDrinkService().obtainDrink(drinkId);

			List<DrinkIngredientTransfer> drinkIingredients = SERVICE_PROVIDER.getOrderService()
					.obtainIngredientsForSpecificDrink(drinkId);

			List<IngredientTransfer> allIngredients = SERVICE_PROVIDER.getIngredientService().obtainAllIngredients();

			request.setAttribute(AttributeName.DRINK, drink);
			request.setAttribute(AttributeName.DRINK_INGREDIENTS, drinkIingredients);
			request.setAttribute(AttributeName.INGREDIENTS, allIngredients);

			request.getRequestDispatcher(NEXT_PATH).forward(request, response);
		} catch (ServletException | ServiceException | IOException e) {
			LOG.error(e.getMessage(), e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

	}

}
