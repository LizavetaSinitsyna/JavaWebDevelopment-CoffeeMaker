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
	private AccountDAO accountDAO;
	private OrderDrinkDAO orderDrinkDAO;
	private UserDAO userDAO;
	private BonusAccountDAO bonusAccountDAO;
	private DrinkDAO drinkDAO;
	private DrinkIngredientDAO drinkIngredientDAO;
	private IngredientDAO ingredientDAO;
	private OrderDAO orderDAO;

	private DAOProvider() {
	}

	private static class SingletonHelper {
		private static final DAOProvider INSTANCE = new DAOProvider();
	}

	public static DAOProvider getInstance() {
		return SingletonHelper.INSTANCE;
	}

	public AccountDAO getAccountDAO() {
		if (accountDAO == null) {
			accountDAO = new SQLAccountDAO();
		}
		return accountDAO;
	}

	public OrderDrinkDAO getOrderDrinkDAO() {
		if (orderDrinkDAO == null) {
			orderDrinkDAO = new SQLOrderDrinkDAO();
		}
		return orderDrinkDAO;
	}

	public UserDAO getUserDAO() {
		if (userDAO == null) {
			userDAO = new SQLUserDAO();
		}
		return userDAO;
	}

	public BonusAccountDAO getBonusAccountDAO() {
		if (bonusAccountDAO == null) {
			bonusAccountDAO = new SQLBonusAccountDAO();
		}
		return bonusAccountDAO;
	}

	public DrinkDAO getDrinkDAO() {
		if (drinkDAO == null) {
			drinkDAO = new SQLDrinkDAO();
		}
		return drinkDAO;
	}

	public DrinkIngredientDAO getDrinkIngredientDAO() {
		if (drinkIngredientDAO == null) {
			drinkIngredientDAO = new SQLDrinkIngredientDAO();
		}
		return drinkIngredientDAO;
	}

	public IngredientDAO getIngredientDAO() {
		if (ingredientDAO == null) {
			ingredientDAO = new SQLIngredientDAO();
		}
		return ingredientDAO;
	}

	public OrderDAO getOrderDAO() {
		if (orderDAO == null) {
			orderDAO = new SQLOrderDAO();
		}
		return orderDAO;
	}

}
