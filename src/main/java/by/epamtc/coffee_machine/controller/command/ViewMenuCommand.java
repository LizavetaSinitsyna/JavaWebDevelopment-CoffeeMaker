package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;
import by.epamtc.coffee_machine.controller.AttributeName;
import by.epamtc.coffee_machine.service.DrinkService;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;

public class ViewMenuCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(ViewMenuCommand.class.getName());
	
	private static final int FIRST_PAGE = 1;
	private static final String NEXT_PATH = "/WEB-INF/jsp/menu.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String pageRequest = request.getParameter(AttributeName.PAGE);
		int page;

		try {
			page = Integer.parseInt(pageRequest);
		} catch (NumberFormatException e) {
			page = FIRST_PAGE;
		}

		List<DrinkTransfer> drinks;
		int pagesAmount;
		DrinkService drinkService = ServiceProvider.getInstance().getDrinkService();
		try {
			drinks = drinkService.obtainMenu(page);
			pagesAmount = drinkService.obtainMenuPagesAmount();
			request.setAttribute(AttributeName.MENU, drinks);
			request.setAttribute(AttributeName.PAGES_AMOUNT, pagesAmount);
			request.getRequestDispatcher(NEXT_PATH).forward(request, response);
		} catch (ServiceException | ServletException | IOException e) {
			LOG.error(e.getMessage(), e);
		}

	}

}
