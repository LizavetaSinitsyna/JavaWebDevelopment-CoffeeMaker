/**
 * 
 */
package by.epamtc.coffee_machine.service;

import by.epamtc.coffee_machine.service.impl.AccountServiceImpl;
import by.epamtc.coffee_machine.service.impl.BonusAccountServiceImpl;
import by.epamtc.coffee_machine.service.impl.DrinkIngredientServiceImpl;
import by.epamtc.coffee_machine.service.impl.DrinkServiceImpl;
import by.epamtc.coffee_machine.service.impl.OrderDrinkServiceImpl;
import by.epamtc.coffee_machine.service.impl.UserServiceImpl;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class ServiceProvider {
	private OrderDrinkService orderDrinkService = new OrderDrinkServiceImpl();
	private UserService userService = new UserServiceImpl();
	private AccountService accountService = new AccountServiceImpl();
	private BonusAccountService bonusAccountService = new BonusAccountServiceImpl();
	private DrinkService drinkService = new DrinkServiceImpl();
	private DrinkIngredientService drinkIngredientService = new DrinkIngredientServiceImpl();

	private ServiceProvider() {

	}

	private static class SingletonHelper {
		private static final ServiceProvider INSTANCE = new ServiceProvider();
	}

	public static ServiceProvider getInstance() {
		return SingletonHelper.INSTANCE;
	}

	/**
	 * @return the orderDrinkService
	 */
	public OrderDrinkService getOrderDrinkService() {
		return orderDrinkService;
	}

	/**
	 * @param orderDrinkService the orderDrinkService to set
	 */
	public void setOrderDrinkService(OrderDrinkService orderDrinkService) {
		this.orderDrinkService = orderDrinkService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the accountService
	 */
	public AccountService getAccountService() {
		return accountService;
	}

	/**
	 * @param accountService the accountService to set
	 */
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	/**
	 * @return the bonusAccountService
	 */
	public BonusAccountService getBonusAccountService() {
		return bonusAccountService;
	}

	/**
	 * @param bonusAccountService the bonusAccountService to set
	 */
	public void setBonusAccountService(BonusAccountService bonusAccountService) {
		this.bonusAccountService = bonusAccountService;
	}

	/**
	 * @return the drinkService
	 */
	public DrinkService getDrinkService() {
		return drinkService;
	}

	/**
	 * @param drinkService the drinkService to set
	 */
	public void setDrinkService(DrinkService drinkService) {
		this.drinkService = drinkService;
	}

	/**
	 * @return the drinkIngredientService
	 */
	public DrinkIngredientService getDrinkIngredientService() {
		return drinkIngredientService;
	}

	/**
	 * @param drinkIngredientService the drinkIngredientService to set
	 */
	public void setDrinkIngredientService(DrinkIngredientService drinkIngredientService) {
		this.drinkIngredientService = drinkIngredientService;
	}
	
	
	
}
