package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epamtc.coffee_machine.bean.DrinkIngredient;
import by.epamtc.coffee_machine.controller.AttributeName;
import by.epamtc.coffee_machine.controller.Command;
import by.epamtc.coffee_machine.service.DrinkIngredientMessage;
import by.epamtc.coffee_machine.service.DrinkMessage;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;

/**
 * 
 * {@code Command} realization for performing edit of product action.
 *
 */
public class EditProductCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(EditProductCommand.class.getName());

	private static final String NEXT_PATH_SUCCESS_EDIT = "/successEdit";
	private static final String NEXT_PATH_FAILED_EDIT = "/failedEdit";
	private static final String NEXT_PATH_ERROR_EDIT = "/error500.jsp";

	/**
	 * Takes drink information and its composition (ingredients) from request,
	 * performs update of existed drink with received from request information and
	 * redirects according to the result of update. If drink or ingredients
	 * information was invalid forwards to the same page with error messages.
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String currentPage = request.getParameter(AttributeName.CURRENT_PAGE);
		String realPathForImage = request.getServletContext().getRealPath("/images");
		InputStream imageContent = null;
		long drinkId = Long.parseLong(request.getParameter(AttributeName.DRINK_ID));
		BigDecimal price = new BigDecimal(request.getParameter(AttributeName.PRICE));
		String description = request.getParameter(AttributeName.DESCRIPTION);
		String[] ingredientsId = request.getParameterValues(AttributeName.INGREDIENT);
		String[] ingredientsAmount = request.getParameterValues(AttributeName.INGREDIENT_AMOUNT);
		String[] ingredientsOptional = request.getParameterValues(AttributeName.OPTIONAL);

		try {
			Part imagePart = request.getPart(AttributeName.IMAGE);
			String imageName = imagePart.getSubmittedFileName();
			imageContent = imagePart.getInputStream();
			Set<DrinkMessage> drinkMessages = ServiceProvider.getInstance().getDrinkService()
					.editDrink(realPathForImage, imageContent, imageName, drinkId, price, description);
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

			Set<DrinkIngredientMessage> ingredientsMessages = ServiceProvider.getInstance().getDrinkService()
					.editDrinkComposition(drinkId, drinkIngredients);

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
				request.setAttribute(AttributeName.SUCCESS_INGREDIENTS_UPDATE,
						AttributeName.SUCCESS_INGREDIENTS_UPDATE);
			}

			if (errorPage) {
				request.getRequestDispatcher(currentPage).forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + NEXT_PATH_SUCCESS_EDIT);
			}

		} catch (ServiceException | IOException | ServletException e) {
			LOG.error(e.getMessage(), e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} finally {
			if (imageContent != null) {
				imageContent.close();
			}
		}

	}

}
