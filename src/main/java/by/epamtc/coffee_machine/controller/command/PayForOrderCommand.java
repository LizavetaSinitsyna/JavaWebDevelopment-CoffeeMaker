package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epamtc.coffee_machine.bean.transfer.UserLoginTransfer;
import by.epamtc.coffee_machine.controller.AttributeName;
import by.epamtc.coffee_machine.controller.Command;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;

/**
 * 
 * {@code Command} realization for performing pay for order action.
 *
 */

public class PayForOrderCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(PayForOrderCommand.class.getName());

	private static final String NEXT_PATH_SUCCESSFUL_PAYMENT = "/successPayment";
	private static final String NEXT_PATH_FAILED_PAYMENT = "/basket";

	/**
	 * Takes order and user information from request, performs pay action and
	 * redirects according to the result of payment. If there is no enough money or
	 * order is expired/incorrect forwards to the basket page with error messages.
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		UserLoginTransfer user = (UserLoginTransfer) request.getSession().getAttribute(AttributeName.USER);
		long userId = user.getId();
		long orderId = Long.parseLong(request.getParameter(AttributeName.ORDER_ID));

		try {
			boolean isPaid = ServiceProvider.getInstance().getOrderService().pay(orderId, userId);
			if (isPaid) {
				response.sendRedirect(request.getContextPath() + NEXT_PATH_SUCCESSFUL_PAYMENT);
			} else {
				request.setAttribute(AttributeName.FAILED_PAYMENT, AttributeName.FAILED_PAYMENT);
				request.getRequestDispatcher(NEXT_PATH_FAILED_PAYMENT).forward(request, response);
			}
		} catch (ServiceException | ServletException e) {
			LOG.error(e.getMessage(), e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
