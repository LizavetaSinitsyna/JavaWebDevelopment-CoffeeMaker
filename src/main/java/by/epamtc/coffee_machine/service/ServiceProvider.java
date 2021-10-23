package by.epamtc.coffee_machine.service;

import by.epamtc.coffee_machine.service.impl.AccountServiceImpl;
import by.epamtc.coffee_machine.service.impl.BonusAccountServiceImpl;
import by.epamtc.coffee_machine.service.impl.DrinkIngredientServiceImpl;
import by.epamtc.coffee_machine.service.impl.DrinkServiceImpl;
import by.epamtc.coffee_machine.service.impl.IngredientServiceImpl;
import by.epamtc.coffee_machine.service.impl.OrderDrinkServiceImpl;
import by.epamtc.coffee_machine.service.impl.OrderServiceImpl;
import by.epamtc.coffee_machine.service.impl.UserServiceImpl;

/**
 * Singleton-based class which provides specific realization of Service-layer
 * interfaces.
 */
public class ServiceProvider {
	private OrderDrinkService orderDrinkService = new OrderDrinkServiceImpl();
	private UserService userService = new UserServiceImpl();
	private AccountService accountService = new AccountServiceImpl();
	private BonusAccountService bonusAccountService = new BonusAccountServiceImpl();
	private DrinkService drinkService = new DrinkServiceImpl();
	private DrinkIngredientService drinkIngredientService = new DrinkIngredientServiceImpl();
	private IngredientService ingredientService = new IngredientServiceImpl();
	private OrderService orderService = new OrderServiceImpl();

	private ServiceProvider() {

	}

	private static class SingletonHelper {
		private static final ServiceProvider INSTANCE = new ServiceProvider();
	}

	public static ServiceProvider getInstance() {
		return SingletonHelper.INSTANCE;
	}

	public OrderDrinkService getOrderDrinkService() {
		return orderDrinkService;
	}

	public void setOrderDrinkService(OrderDrinkService orderDrinkService) {
		this.orderDrinkService = orderDrinkService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public BonusAccountService getBonusAccountService() {
		return bonusAccountService;
	}

	public void setBonusAccountService(BonusAccountService bonusAccountService) {
		this.bonusAccountService = bonusAccountService;
	}

	public DrinkService getDrinkService() {
		return drinkService;
	}

	public void setDrinkService(DrinkService drinkService) {
		this.drinkService = drinkService;
	}

	public DrinkIngredientService getDrinkIngredientService() {
		return drinkIngredientService;
	}

	public void setDrinkIngredientService(DrinkIngredientService drinkIngredientService) {
		this.drinkIngredientService = drinkIngredientService;
	}

	public IngredientService getIngredientService() {
		return ingredientService;
	}

	public void setIngredientService(IngredientService ingredientService) {
		this.ingredientService = ingredientService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

}
