package by.epamtc.coffee_machine.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.coffee_machine.controller.command.AttributeName;
import by.epamtc.coffee_machine.dao.sql.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.dao.sql.pool.ConnectionPoolImpl;

/**
 * Servlet implementation class Controller
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor which additionally initialize ConnectionPool.
	 */
	public Controller() {
		super();
		ConnectionPoolImpl.retrieveConnectionPool().initPool();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		execute(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		execute(request, response);
	}
	
	/**
	 * Retrieves command from request and executes it.
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response) {
		CommandProvider.getInstance().retriveCommand(request.getParameter(AttributeName.COMMAND)).execute(request, response);
	}
	
	/**
	 * Default destroy method which additionally close ConnectionPool.
	 */
	@Override
	public void destroy() {
		try {
			ConnectionPoolImpl.retrieveConnectionPool().clearConnectionPool();
		} catch (ConnectionPoolException e) {
			// log4j2
			e.printStackTrace();
		}
	    super.destroy();
	}
	

}
