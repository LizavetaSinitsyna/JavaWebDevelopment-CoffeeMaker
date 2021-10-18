package by.epamtc.coffee_machine.controller.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPoolException;
import by.epamtc.coffee_machine.dao.impl.pool.ConnectionPool;

/**
 * Initializes and destroys connection pool
 *
 */

public class ContextListener implements ServletContextListener {
	private static final Logger LOG = LogManager.getLogger(ContextListener.class.getName());

	public ContextListener() {

	}

	/**
	 * Destroys connection pool
	 */

	public void contextDestroyed(ServletContextEvent sce) {
		try {
			ConnectionPool.retrieveConnectionPool().clearConnectionPool();
		} catch (ConnectionPoolException e) {
			LOG.error(e);
		}
	}

	/**
	 * Initializes connection pool
	 */
	public void contextInitialized(ServletContextEvent sce) {
		ConnectionPool.retrieveConnectionPool().initPool();
	}

}
