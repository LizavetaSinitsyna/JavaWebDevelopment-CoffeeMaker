package by.epamtc.coffee_machine.service;

import by.epamtc.coffee_machine.service.impl.AccountServiceImpl;
import by.epamtc.coffee_machine.service.impl.BonusAccountServiceImpl;
import by.epamtc.coffee_machine.service.impl.DrinkServiceImpl;
import by.epamtc.coffee_machine.service.impl.IngredientServiceImpl;
import by.epamtc.coffee_machine.service.impl.OrderServiceImpl;
import by.epamtc.coffee_machine.service.impl.UserServiceImpl;

/**
 * Singleton-based class which provides specific realization of Service-layer
 * interfaces.
 */
public class ServiceProvider {
	private UserService userService;
	private AccountService accountService;
	private BonusAccountService bonusAccountService;
	private DrinkService drinkService;
	private IngredientService ingredientService;
	private OrderService orderService;

	private ServiceProvider() {

	}

	private static class SingletonHelper {
		private static final ServiceProvider INSTANCE = new ServiceProvider();
	}

	public static ServiceProvider getInstance() {
		return SingletonHelper.INSTANCE;
	}

	public UserService getUserService() {
		if (userService == null) {
			userService = new UserServiceImpl();
		}
		return userService;
	}

	public AccountService getAccountService() {
		if (accountService == null) {
			accountService = new AccountServiceImpl();
		}
		return accountService;
	}

	public BonusAccountService getBonusAccountService() {
		if (bonusAccountService == null) {
			bonusAccountService = new BonusAccountServiceImpl();
		}
		return bonusAccountService;
	}

	public DrinkService getDrinkService() {
		if (drinkService == null) {
			drinkService = new DrinkServiceImpl();
		}
		return drinkService;
	}

	public IngredientService getIngredientService() {
		if (ingredientService == null) {
			ingredientService = new IngredientServiceImpl();
		}
		return ingredientService;
	}

	public OrderService getOrderService() {
		if (orderService == null) {
			orderService = new OrderServiceImpl();
		}
		return orderService;
	}

}
