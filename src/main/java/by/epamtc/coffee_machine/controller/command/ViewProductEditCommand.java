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
import by.epamtc.coffee_machine.bean.transfer.DrinkIngredientTransfer;
import by.epamtc.coffee_machine.bean.transfer.IngredientTransfer;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class ViewProductEditCommand implements Command {
	private static final String NEXT_PATH = "/WEB-INF/jsp/drink_edit.jsp";
	private static final ServiceProvider SERVICE_PROVIDER = ServiceProvider.getInstance();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			Drink drink;
			long drinkId;

			drinkId = Long.parseLong(request.getParameter(AttributeName.DRINK_ID));
			drink = SERVICE_PROVIDER.getDrinkService().obtainDrink(drinkId);

			List<DrinkIngredientTransfer> drinkIingredients = SERVICE_PROVIDER.getDrinkIngredientService()
					.obtainIngredientsForSpecificDrink(drinkId);

			List<IngredientTransfer> allIngredients = SERVICE_PROVIDER.getIngredientService().obtainAllIngredients();

			request.setAttribute(AttributeName.DRINK, drink);
			request.setAttribute(AttributeName.DRINK_INGREDIENTS, drinkIingredients);
			request.setAttribute(AttributeName.INGREDIENTS, allIngredients);

			request.getRequestDispatcher(NEXT_PATH).forward(request, response);
		} catch (ServletException | ServiceException | IOException e) {
			// log4j2
			e.printStackTrace();
		}

	}

}
