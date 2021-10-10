/**
 * 
 */
package by.epamtc.coffee_machine.dao;

import by.epamtc.coffee_machine.dao.sql.SQLAccountDAO;
import by.epamtc.coffee_machine.dao.sql.SQLOrderDrinkDAO;
import by.epamtc.coffee_machine.dao.sql.SQLUserDAO;
import by.epamtc.coffee_machine.dao.sql.SQLBonusAccountDAO;
import by.epamtc.coffee_machine.dao.sql.SQLDrinkDAO;
import by.epamtc.coffee_machine.dao.sql.SQLDrinkIngredientDAO;
import by.epamtc.coffee_machine.dao.sql.SQLIngredientDAO;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class DAOProvider {
	private AccountDAO accountDAO = new SQLAccountDAO();
	private OrderDrinkDAO orderDrinkDAO = new SQLOrderDrinkDAO();
	private UserDAO userDAO = new SQLUserDAO();
	private BonusAccountDAO bonusAccountDAO = new SQLBonusAccountDAO();
	private DrinkDAO drinkDAO = new SQLDrinkDAO();
	private DrinkIngredientDAO drinkIngredientDAO = new SQLDrinkIngredientDAO();
	private IngredientDAO ingredientDAO = new SQLIngredientDAO();
	
	private DAOProvider() {
	}
	
	private static class SingletonHelper {
		private static final DAOProvider INSTANCE = new DAOProvider();
	}
	
	public static DAOProvider getInstance() {
		return SingletonHelper.INSTANCE;
	}

	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public OrderDrinkDAO getOrderDrinkDAO() {
		return orderDrinkDAO;
	}

	public void setOrderDrinkDAO(OrderDrinkDAO orderDrinkDAO) {
		this.orderDrinkDAO = orderDrinkDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public BonusAccountDAO getBonusAccountDAO() {
		return bonusAccountDAO;
	}

	public void setBonusAccountDAO(BonusAccountDAO bonusAccountDAO) {
		this.bonusAccountDAO = bonusAccountDAO;
	}

	/**
	 * @return the drinkDAO
	 */
	public DrinkDAO getDrinkDAO() {
		return drinkDAO;
	}

	/**
	 * @param drinkDAO the drinkDAO to set
	 */
	public void setDrinkDAO(DrinkDAO drinkDAO) {
		this.drinkDAO = drinkDAO;
	}

	/**
	 * @return the drinkIngredientDAO
	 */
	public DrinkIngredientDAO getDrinkIngredientDAO() {
		return drinkIngredientDAO;
	}

	/**
	 * @param drinkIngredientDAO the drinkIngredientDAO to set
	 */
	public void setDrinkIngredientDAO(DrinkIngredientDAO drinkIngredientDAO) {
		this.drinkIngredientDAO = drinkIngredientDAO;
	}

	/**
	 * @return the ingredientDAO
	 */
	public IngredientDAO getIngredientDAO() {
		return ingredientDAO;
	}

	/**
	 * @param ingredientDAO the ingredientDAO to set
	 */
	public void setIngredientDAO(IngredientDAO ingredientDAO) {
		this.ingredientDAO = ingredientDAO;
	}
	
	
}
