package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epamtc.coffee_machine.bean.DrinkIngredient;
import by.epamtc.coffee_machine.controller.AttributeName;
import by.epamtc.coffee_machine.service.DrinkIngredientMessage;
import by.epamtc.coffee_machine.service.DrinkMessage;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;

public class EditProductCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(EditProductCommand.class.getName());
	
	private static final String NEXT_PATH_SUCCESS_EDIT = "/successEdit";
	private static final String NEXT_PATH_FAILED_EDIT = "/failedEdit";
	private static final String NEXT_PATH_ERROR_EDIT = "/errorPage.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String currentPage = request.getParameter(AttributeName.CURRENT_PAGE);
		String imagePath = request.getParameter(AttributeName.IMAGE_PATH);
		long drinkId = Long.parseLong(request.getParameter(AttributeName.DRINK_ID));
		BigDecimal price = new BigDecimal(request.getParameter(AttributeName.PRICE));
		String description = request.getParameter(AttributeName.DESCRIPTION);
		String[] ingredientsId = request.getParameterValues(AttributeName.INGREDIENT);
		String[] ingredientsAmount = request.getParameterValues(AttributeName.INGREDIENT_AMOUNT);
		String[] ingredientsOptional = request.getParameterValues(AttributeName.OPTIONAL);

		try {
			Set<DrinkMessage> drinkMessages = ServiceProvider.getInstance().getDrinkService().edit(imagePath, drinkId,
					price, description);
			if (drinkMessages.contains(DrinkMessage.INVALID_DRINK_ID)) {
				response.sendRedirect(request.getContextPath() + NEXT_PATH_ERROR_EDIT);
				return;
			}

			List<DrinkIngredient> drinkIngredients = new ArrayList<>();
			DrinkIngredient drinkIngredient;
			for (int i = 0; i < ingredientsId.length; i++) {
				drinkIngredient = new DrinkIngredient();
				drinkIngredient.setIngredientId(Long.parseLong(ingredientsId[i]));
				drinkIngredient.setIngredientAmount(Integer.parseInt(ingredientsAmount[i]));
				drinkIngredient.setOptional(Boolean.parseBoolean(ingredientsOptional[i]));
				drinkIngredients.add(drinkIngredient);
			}

			Set<DrinkIngredientMessage> ingredientsMessages = ServiceProvider.getInstance().getDrinkIngredientService()
					.edit(drinkId, drinkIngredients);

			if (ingredientsMessages.contains(DrinkIngredientMessage.INVALID_DRINK_ID)
					|| ingredientsMessages.contains(DrinkIngredientMessage.INVALID_INGREDIENT_ID)) {
				response.sendRedirect(request.getContextPath() + NEXT_PATH_ERROR_EDIT);
				return;
			}

			if (drinkMessages.contains(DrinkMessage.UNABLE_EDIT)
					|| ingredientsMessages.contains(DrinkIngredientMessage.UNABLE_EDIT)) {
				response.sendRedirect(request.getContextPath() + NEXT_PATH_FAILED_EDIT);
				return;
			}

			boolean errorPage = false;

			if (drinkMessages.size() > 0) {
				request.setAttribute(AttributeName.ERRORS, drinkMessages);
				errorPage = true;
			} else {
				request.setAttribute(AttributeName.SUCCESS_DRINK_UPDATE, AttributeName.SUCCESS_DRINK_UPDATE);
			}

			if (ingredientsMessages.size() > 0) {
				request.setAttribute(AttributeName.INGREDIENT_ERRORS, ingredientsMessages);
				errorPage = true;
			} else {
				request.setAttribute(AttributeName.SUCCESS_INGREDIENTS_UPDATE, AttributeName.SUCCESS_INGREDIENTS_UPDATE);
			}

			if (errorPage) {
				request.getRequestDispatcher(currentPage).forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + NEXT_PATH_SUCCESS_EDIT);
			}

		} catch (ServiceException | IOException | ServletException e) {
			LOG.error(e.getMessage(), e);
		}

	}

}
