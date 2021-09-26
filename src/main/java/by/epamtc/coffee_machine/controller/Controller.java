package by.epamtc.coffee_machine.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.coffee_machine.dao.sql.pool.ConnectionPoolImpl;


/**
 * Servlet implementation class Controller
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
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
		/*-Account account = new Account();
		account.setBalance(200);
		SQLAccountDAO dao = new SQLAccountDAO();
		try {
			Connection connection1 = TomcatConnectionPoolImpl.getConnectionPool().retrieveConnection();
		
			// dao.add(account);
			// System.out.println(dao.read(2).getBalance());
			// dao.update(3, 600);
		} catch (DAOException e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		execute(request, response);
	}

	private void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			//log4j2
			e.printStackTrace();
		}
		CommandProvider.getInstance().retriveCommand(request.getParameter("command")).execute(request, response);
	}

}
