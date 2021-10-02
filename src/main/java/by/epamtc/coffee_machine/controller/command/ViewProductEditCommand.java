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
import by.epamtc.coffee_machine.bean.transfer.UserLoginTransfer;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;
import by.epamtc.coffee_machine.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class ViewProductEditCommand implements Command {
	private static final String NEXT_PATH = "/WEB-INF/jsp/drink_edit.jsp";
	private static final String FORBIDEN_ACCESS_NEXT_PATH = "/WEB-INF/jsp/forbidenAccess.jsp";
	private static final String ADMIN_ROLE_NAME = "Admin";
	private static final ServiceProvider SERVICE_PROVIDER = ServiceProvider.getInstance();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		Object user = request.getSession().getAttribute(AttributeName.USER);
		String nextPath = FORBIDEN_ACCESS_NEXT_PATH;
		if (!ValidationHelper.isNull(user) && ((UserLoginTransfer) user).getRoleName().equals(ADMIN_ROLE_NAME)) {

			try {
				nextPath = NEXT_PATH;
				Drink drink;
				int drinkId;

				Object drinkRequest = request.getParameter(AttributeName.DRINK);
				if (ValidationHelper.isNull(drinkRequest)) {
					drinkId = Integer.parseInt(request.getParameter(AttributeName.DRINK_ID));
					drink = SERVICE_PROVIDER.getDrinkService().obtainDrink(drinkId);
				} else {
					drink = (Drink) drinkRequest;
					drinkId = drink.getId();
				}

				List<DrinkIngredientTransfer> ingredients = SERVICE_PROVIDER.getDrinkIngredientService()
						.obtainIngredientsForSpecificDrink(drinkId);
				request.setAttribute(AttributeName.DRINK, drink);
				request.setAttribute(AttributeName.INGREDIENTS, ingredients);
			} catch (ServiceException e) {
				// log4j2
				e.printStackTrace();
			}
		}

		try {
			request.getRequestDispatcher(nextPath).forward(request, response);
		} catch (ServletException | IOException e) {
			// log4j2
			e.printStackTrace();
		}

	}

}
