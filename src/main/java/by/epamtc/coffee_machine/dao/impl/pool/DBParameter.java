package by.epamtc.coffee_machine.dao.impl.pool;

/**
 * Contains names of parameters which can be specified in database property file.
 *
 */
public class DBParameter {
	private DBParameter() {
	}

	public static final String DB_DRIVER = "db.driverClassName";
	public static final String DB_URL = "db.url";
	public static final String DB_USERNAME = "db.username";
	public static final String DB_PASSWORD = "db.password";
	public static final String DB_MAX_POOL_SIZE = "db.maxPoolSize";
	public static final String DB_MAX_IDLE = "db.maxIdle";
	public static final String DB_MAX_WAIT = "db.maxWait";

}
