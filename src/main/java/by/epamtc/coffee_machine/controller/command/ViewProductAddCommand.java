package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epamtc.coffee_machine.bean.transfer.IngredientTransfer;
import by.epamtc.coffee_machine.controller.AttributeName;
import by.epamtc.coffee_machine.controller.Command;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;

/**
 * 
 * {@code Command} realization for performing view of product add page.
 *
 */
public class ViewProductAddCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(ViewProductAddCommand.class.getName());

	private static final String NEXT_PATH = "/WEB-INF/jsp/drink_add.jsp";
	private static final ServiceProvider SERVICE_PROVIDER = ServiceProvider.getInstance();

	/**
	 * Forwards to product add page with information about possible ingredients.
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			List<IngredientTransfer> allIngredients = SERVICE_PROVIDER.getIngredientService().obtainAllIngredients();

			request.setAttribute(AttributeName.INGREDIENTS, allIngredients);

			request.getRequestDispatcher(NEXT_PATH).forward(request, response);
		} catch (ServletException | ServiceException | IOException e) {
			LOG.error(e.getMessage(), e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

	}

}
