package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epamtc.coffee_machine.bean.transfer.OrderTransfer;
import by.epamtc.coffee_machine.bean.transfer.UnavailableIngredientTransfer;
import by.epamtc.coffee_machine.bean.transfer.UserLoginTransfer;
import by.epamtc.coffee_machine.controller.AttributeName;
import by.epamtc.coffee_machine.controller.Command;
import by.epamtc.coffee_machine.service.OrderService;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;

/**
 * 
 * {@code Command} realization for performing user's make order action.
 *
 */
public class MakeOrderCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(MakeOrderCommand.class.getName());
	private static final String NEXT_PATH_FAILED = "/basket";
	private static final String NEXT_PATH_ORDER_CHECKOUT = "/order";

	/**
	 * Takes order information from request and creates the order. If the creation
	 * was failed redirects to the same page with error message, otherwise redirects
	 * to the payment page.
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String nextPath = NEXT_PATH_FAILED;
		String[] drinksId = request.getParameterValues(AttributeName.DRINK_ID);
		String[] drinksAmount = request.getParameterValues(AttributeName.DRINK_AMOUNT);

		HttpSession session = request.getSession();
		Object user = session.getAttribute(AttributeName.USER);

		if (drinksId != null && drinksAmount != null && user != null && drinksId.length == drinksAmount.length) {
			long userId = ((UserLoginTransfer) user).getId();
			OrderService orderService = ServiceProvider.getInstance().getOrderService();
			try {
				OrderTransfer orderTransfer = orderService.placeOrder(drinksId, drinksAmount, userId);
				UnavailableIngredientTransfer unavailableIngredient = orderTransfer.getUnavailableIngredient();
				if (unavailableIngredient == null) {
					nextPath = NEXT_PATH_ORDER_CHECKOUT;
					session.setAttribute(AttributeName.ORDER, orderTransfer);
					session.setAttribute(AttributeName.ACCOUNT,
							ServiceProvider.getInstance().getAccountService().obtainAccountByUserId(userId));
					session.setAttribute(AttributeName.BONUS_ACCOUNT,
							ServiceProvider.getInstance().getBonusAccountService().obtainAccountByUserId(userId));
					response.sendRedirect(request.getContextPath() + nextPath);
					return;
				} else {
					request.setAttribute(AttributeName.UNAVAILABLE_INGREDIENT, unavailableIngredient);
				}
			} catch (ServiceException | IOException e) {
				LOG.error(e.getMessage(), e);
			}
		}

		try {
			request.getRequestDispatcher(nextPath).forward(request, response);
		} catch (IOException | ServletException e) {
			LOG.error(e.getMessage(), e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
