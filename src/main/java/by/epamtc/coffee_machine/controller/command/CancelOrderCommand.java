package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epamtc.coffee_machine.controller.AttributeName;
import by.epamtc.coffee_machine.controller.Command;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;

/**
 * 
 * {@code Command} realization for performing cancel of order action.
 *
 */
public class CancelOrderCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(CancelOrderCommand.class.getName());

	private static final String NEXT_PATH_SUCCESS_ORDER = "/successOrderCancel";
	private static final String NEXT_PATH_FAILED_ORDER = "/basket";

	/**
	 * Takes order id from request, deletes this order and redirects according to
	 * the result of update. If cancel was failed forwards to the basket page with
	 * error messages.
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long orderId = Long.parseLong(request.getParameter(AttributeName.ORDER_ID));
		boolean result = false;
		try {
			result = ServiceProvider.getInstance().getOrderService().cancel(orderId);
		} catch (ServiceException e) {
			LOG.error(e.getMessage(), e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		if (result) {
			response.sendRedirect(request.getContextPath() + NEXT_PATH_SUCCESS_ORDER);
		} else {
			request.setAttribute(AttributeName.FAILED_ORDER_CANCEL, AttributeName.FAILED_ORDER_CANCEL);
			request.getRequestDispatcher(NEXT_PATH_FAILED_ORDER).forward(request, response);
		}

	}

}
