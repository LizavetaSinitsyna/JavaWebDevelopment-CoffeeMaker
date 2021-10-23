package by.epamtc.coffee_machine.dao;

import by.epamtc.coffee_machine.dao.impl.SQLAccountDAO;
import by.epamtc.coffee_machine.dao.impl.SQLBonusAccountDAO;
import by.epamtc.coffee_machine.dao.impl.SQLDrinkDAO;
import by.epamtc.coffee_machine.dao.impl.SQLDrinkIngredientDAO;
import by.epamtc.coffee_machine.dao.impl.SQLIngredientDAO;
import by.epamtc.coffee_machine.dao.impl.SQLOrderDAO;
import by.epamtc.coffee_machine.dao.impl.SQLOrderDrinkDAO;
import by.epamtc.coffee_machine.dao.impl.SQLUserDAO;

/**
 * Singleton-based class which provides specific realization of DAO-layer
 * interfaces.
 */
public class DAOProvider {
	private AccountDAO accountDAO = new SQLAccountDAO();
	private OrderDrinkDAO orderDrinkDAO = new SQLOrderDrinkDAO();
	private UserDAO userDAO = new SQLUserDAO();
	private BonusAccountDAO bonusAccountDAO = new SQLBonusAccountDAO();
	private DrinkDAO drinkDAO = new SQLDrinkDAO();
	private DrinkIngredientDAO drinkIngredientDAO = new SQLDrinkIngredientDAO();
	private IngredientDAO ingredientDAO = new SQLIngredientDAO();
	private OrderDAO orderDAO = new SQLOrderDAO();

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

	public DrinkDAO getDrinkDAO() {
		return drinkDAO;
	}

	public void setDrinkDAO(DrinkDAO drinkDAO) {
		this.drinkDAO = drinkDAO;
	}

	public DrinkIngredientDAO getDrinkIngredientDAO() {
		return drinkIngredientDAO;
	}

	public void setDrinkIngredientDAO(DrinkIngredientDAO drinkIngredientDAO) {
		this.drinkIngredientDAO = drinkIngredientDAO;
	}

	public IngredientDAO getIngredientDAO() {
		return ingredientDAO;
	}

	public void setIngredientDAO(IngredientDAO ingredientDAO) {
		this.ingredientDAO = ingredientDAO;
	}

	public OrderDAO getOrderDAO() {
		return orderDAO;
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

}
