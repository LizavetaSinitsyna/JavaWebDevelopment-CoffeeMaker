package by.epamtc.coffee_machine.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Represents Front controller.
 *
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Controller() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		execute(request, response);
	}

	/**
	 * Retrieves command from request and executes it.
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CommandProvider.getInstance().retriveCommand(request.getParameter(AttributeName.COMMAND)).execute(request,
				response);
	}

}
