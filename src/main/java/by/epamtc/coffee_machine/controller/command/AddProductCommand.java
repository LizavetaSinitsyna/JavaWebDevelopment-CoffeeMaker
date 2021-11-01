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
import by.epamtc.coffee_machine.bean.transfer.DrinkMessageTransfer;
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
public class AddProductCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(AddProductCommand.class.getName());

	private static final String NEXT_PATH_SUCCESS_ADD = "/Controller?command=view_product&drink_id=%d";

	/**
	 * Takes drink information and its composition (ingredients) from request,
	 * performs add of existed drink with received from request information and
	 * redirects to the drink page if drink was added successfully. If drink or
	 * ingredients information was invalid forwards to the same page with error
	 * messages.
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String currentPage = request.getParameter(AttributeName.CURRENT_PAGE);
		String realPathForImage = request.getServletContext().getRealPath("/images");
		InputStream imageContent = null;
		String drinkName = request.getParameter(AttributeName.NAME);
		String requestPrice = (request.getParameter(AttributeName.PRICE));
		BigDecimal price = new BigDecimal(requestPrice == null || requestPrice.isBlank() ? "0" : requestPrice);
		String description = request.getParameter(AttributeName.DESCRIPTION);
		String[] ingredientsId = request.getParameterValues(AttributeName.INGREDIENT);
		String[] ingredientsAmount = request.getParameterValues(AttributeName.INGREDIENT_AMOUNT);
		String[] ingredientsOptional = request.getParameterValues(AttributeName.OPTIONAL);

		try {
			Part imagePart = request.getPart(AttributeName.IMAGE);
			String imageName = imagePart.getSubmittedFileName();
			imageContent = imagePart.getInputStream();

			List<DrinkIngredient> drinkIngredients = new ArrayList<>();
			DrinkIngredient drinkIngredient;
			for (int i = 0; i < ingredientsId.length; i++) {
				drinkIngredient = new DrinkIngredient();
				drinkIngredient.setIngredientId(Long.parseLong(ingredientsId[i]));
				drinkIngredient.setIngredientAmount(Integer.parseInt(ingredientsAmount[i]));
				drinkIngredient.setOptional(Boolean.parseBoolean(ingredientsOptional[i]));
				drinkIngredients.add(drinkIngredient);
			}

			DrinkMessageTransfer drinkMessageTransfer = ServiceProvider.getInstance().getDrinkService()
					.add(realPathForImage, imageContent, imageName, drinkName, price, description, drinkIngredients);
			Set<DrinkMessage> drinkMessages = drinkMessageTransfer.getDrinkMessages();
			Set<DrinkIngredientMessage> drinkIngredientMessages = drinkMessageTransfer.getDrinkIngredientMessages();
			if ((drinkMessages == null || drinkMessages.isEmpty())
					&& (drinkIngredientMessages == null || drinkIngredientMessages.isEmpty())) {
				response.sendRedirect(String.format((request.getContextPath() + NEXT_PATH_SUCCESS_ADD),
						drinkMessageTransfer.getDrinkId()));
			} else {
				request.setAttribute(AttributeName.ERRORS, drinkMessages);
				request.setAttribute(AttributeName.INGREDIENT_ERRORS, drinkIngredientMessages);
				request.setAttribute(AttributeName.NAME, drinkName);
				request.setAttribute(AttributeName.PRICE, price);
				request.setAttribute(AttributeName.DESCRIPTION, description);
				request.getRequestDispatcher(currentPage).forward(request, response);
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
