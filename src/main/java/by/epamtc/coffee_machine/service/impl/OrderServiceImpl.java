package by.epamtc.coffee_machine.service.impl;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import by.epamtc.coffee_machine.bean.Account;
import by.epamtc.coffee_machine.bean.BonusAccount;
import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.Ingredient;
import by.epamtc.coffee_machine.bean.Order;
import by.epamtc.coffee_machine.bean.OrderDrink;
import by.epamtc.coffee_machine.bean.OrderInfo;
import by.epamtc.coffee_machine.bean.OrderStatus;
import by.epamtc.coffee_machine.bean.transfer.DrinkIngredientTransfer;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;
import by.epamtc.coffee_machine.bean.transfer.OrderTransfer;
import by.epamtc.coffee_machine.bean.transfer.UnavailableIngredientTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DAOProvider;
import by.epamtc.coffee_machine.dao.DrinkDAO;
import by.epamtc.coffee_machine.dao.DrinkIngredientDAO;
import by.epamtc.coffee_machine.dao.IngredientDAO;
import by.epamtc.coffee_machine.dao.OrderDAO;
import by.epamtc.coffee_machine.dao.OrderDrinkDAO;
import by.epamtc.coffee_machine.service.AccountService;
import by.epamtc.coffee_machine.service.BonusAccountService;
import by.epamtc.coffee_machine.service.CommonExceptionMessage;
import by.epamtc.coffee_machine.service.OrderService;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;
import by.epamtc.coffee_machine.service.utility.MenuParameter;
import by.epamtc.coffee_machine.service.utility.MenuPropertyProvider;
import by.epamtc.coffee_machine.service.utility.OrderParameter;
import by.epamtc.coffee_machine.service.utility.OrderPropertyProvider;

/**
 * Provides access to {@link OrderDAO} and support for working with entities
 * {@link Order}, {@link OrderInfo}.
 */
public class OrderServiceImpl implements OrderService {
	private final DrinkDAO drinkDao = DAOProvider.getInstance().getDrinkDAO();
	private final OrderDAO orderDao = DAOProvider.getInstance().getOrderDAO();
	private final IngredientDAO ingredientDao = DAOProvider.getInstance().getIngredientDAO();
	private final DrinkIngredientDAO drinkIngredientDao = DAOProvider.getInstance().getDrinkIngredientDAO();
	private final OrderDrinkDAO orderDrinkDao = DAOProvider.getInstance().getOrderDrinkDAO();
	private final AccountService accountService = ServiceProvider.getInstance().getAccountService();
	private final BonusAccountService bonusAccountService = ServiceProvider.getInstance().getBonusAccountService();
	private MenuPropertyProvider menuPropertyProvider = MenuPropertyProvider.getInstance();

	private static final OrderPropertyProvider ORDER_PROPERTY_PROVIDER = OrderPropertyProvider.getInstance();

	private static final String ILLEGAL_ARRAYS_LENGTH_MESSAGE = "Lengths of passed arrays must be equal";
	private static final String ILLEGAL_ARRAY_PARAMETERS_MESSAGE = "Passed arrays should contain only positive integers";
	private static final String DIGITS_REGEX = "\\d+";
	private static final BigDecimal ZERO = new BigDecimal("0");
	private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

	/**
	 * Creates new Order. Before it removes unpaid orders and checks the
	 * availability of ingredients.
	 * 
	 * @param drinksId     Array of {@code String} values which contains id of
	 *                     drinks for the order.
	 * @param drinksAmount Array of {@code String} values which contains amount of
	 *                     each drink in the order.
	 * @param userId       {@code long} value which uniquely indicates the user who
	 *                     made the order.
	 * @return {@code OrderTransfer} object representing the created Order.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer
	 *                          or passed parameters are invalid.
	 */
	@Override
	public OrderTransfer placeOrder(String[] drinksId, String[] drinksAmount, long userId) throws ServiceException {
		if (drinksId == null || drinksAmount == null) {
			throw new ServiceException(CommonExceptionMessage.NULL_ARGUMENT);
		}

		if (drinksId.length != drinksAmount.length) {
			throw new ServiceException(ILLEGAL_ARRAYS_LENGTH_MESSAGE);
		}

		int length = drinksId.length;

		long id;
		int amount;
		BigDecimal price;
		BigDecimal cost = new BigDecimal(0);

		Pattern pattern = Pattern.compile(DIGITS_REGEX);
		OrderDrink orderDrink = new OrderDrink();

		for (int i = 0; i < length; i++) {
			if (pattern.matcher(drinksId[i]).matches() && pattern.matcher(drinksAmount[i]).matches()) {
				id = Long.parseLong(drinksId[i]);
				amount = Integer.parseInt(drinksAmount[i]);
				try {
					Drink drink = drinkDao.read(id);
					DrinkTransfer drinkTransfer = new DrinkTransfer();
					price = drink.getInfo().getPrice();

					drinkTransfer.setId(drink.getId());
					drinkTransfer.setImagePath(drink.getInfo().getImagePath());
					drinkTransfer.setName(drink.getInfo().getName());
					drinkTransfer.setPrice(price);

					orderDrink.addDrink(drinkTransfer, amount);
				} catch (DAOException e) {
					throw new ServiceException(e.getMessage(), e);
				}
				cost = cost.add(price.multiply(new BigDecimal(amount)));
			} else {
				throw new ServiceException(ILLEGAL_ARRAY_PARAMETERS_MESSAGE);
			}
		}

		OrderTransfer orderTransfer = new OrderTransfer();

		Order order = new Order();
		order.setUserId(userId);
		OrderInfo orderInfo = new OrderInfo();

		OffsetDateTime dateTime = retrieveCurrentDateTime();
		orderInfo.setDateTime(dateTime);
		orderInfo.setStatus(OrderStatus.CREATED);
		orderInfo.setCost(cost);

		order.setInfo(orderInfo);

		orderTransfer.setOrder(order);
		orderTransfer.setOrderDrink(orderDrink);

		removeUnpaidOrders();

		UnavailableIngredientTransfer unavailableIngredient = checkAvailableIngredientsAmount(orderDrink);
		if (unavailableIngredient == null) {
			try {
				long orderId = orderDao.add(orderTransfer);
				order.setOrderId(orderId);
				orderTransfer.setOrder(order);
			} catch (DAOException e) {
				throw new ServiceException(e.getMessage(), e);
			}
		} else {

			orderTransfer.setUnavailableIngredient(unavailableIngredient);
		}
		return orderTransfer;
	}

	private UnavailableIngredientTransfer checkAvailableIngredientsAmount(OrderDrink orderDrink)
			throws ServiceException {
		UnavailableIngredientTransfer result = null;

		if (orderDrink == null) {
			throw new ServiceException(CommonExceptionMessage.NULL_ARGUMENT);
		}

		Iterator<Map.Entry<DrinkTransfer, Integer>> orderDrinksIterator = orderDrink.iterator();

		Map<Long, Integer> usedIngredientsAmount = new HashMap<>();

		while (orderDrinksIterator.hasNext() && result == null) {
			int currentAmount;

			Map<Long, Integer> requiredForSpecifiedDrinkIngredientsAmount = new HashMap<>();

			Map.Entry<DrinkTransfer, Integer> drink = orderDrinksIterator.next();
			long drinkId = drink.getKey().getId();
			int drinkAmount = drink.getValue();

			List<DrinkIngredientTransfer> drinkIngredients = obtainIngredientsForSpecificDrink(drinkId);

			// Find general amount of ingredients for specified drink taking into account
			// its amount in the order

			for (DrinkIngredientTransfer drinkIngredient : drinkIngredients) {
				long ingredientId = drinkIngredient.getIngredientId();
				int ingredientAmount = drinkIngredient.getIngredientAmount();

				if (requiredForSpecifiedDrinkIngredientsAmount.containsKey(ingredientId)) {
					currentAmount = requiredForSpecifiedDrinkIngredientsAmount.get(ingredientId);
					requiredForSpecifiedDrinkIngredientsAmount.put(ingredientId,
							currentAmount + ingredientAmount * drinkAmount);
				} else {
					requiredForSpecifiedDrinkIngredientsAmount.put(ingredientId, ingredientAmount * drinkAmount);
				}
			}

			// Compare amount of required ingredients for particular drink with current
			// amount of ingredients minus
			// ingredients used for previous positions in the order

			Ingredient ingredient = null;
			for (Map.Entry<Long, Integer> element : requiredForSpecifiedDrinkIngredientsAmount.entrySet()) {
				long ingredientId = element.getKey();
				int requiredIngredientAmount = element.getValue();
				try {
					ingredient = ingredientDao.readAvailable(element.getKey());
					currentAmount = ingredient == null ? 0 : ingredient.getCurrentAmount();
					if (usedIngredientsAmount.containsKey(ingredientId)) {
						currentAmount -= usedIngredientsAmount.get(ingredientId);
					}
				} catch (DAOException e) {
					throw new ServiceException(e.getMessage(), e);
				}
				if (currentAmount < requiredIngredientAmount) {
					result = new UnavailableIngredientTransfer();
					result.setDrinkId(drink.getKey().getId());
					result.setIngredientId(element.getKey());
					result.setIngredientName(ingredient.getInfo().getName());
					result.setAvailableDrinkAmount(currentAmount / (requiredIngredientAmount / drinkAmount));
				} else {
					if (usedIngredientsAmount.containsKey(ingredientId)) {
						currentAmount = usedIngredientsAmount.get(ingredientId);
						usedIngredientsAmount.put(ingredientId, currentAmount + requiredIngredientAmount);
					} else {
						usedIngredientsAmount.put(ingredientId, requiredIngredientAmount);
					}
				}

			}

		}

		return result;
	}

	/**
	 * Obtains all ingredients for specified drink.
	 * 
	 * @param drinkId {@code long} value which uniquely indicates the drink.
	 * @return {@code List} of {@code DrinkIngredientTransfer} objects representing
	 *         ingredients for the specified drink or {@code null} if drinkId is
	 *         invalid.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */
	@Override
	public List<DrinkIngredientTransfer> obtainIngredientsForSpecificDrink(long drinkId) throws ServiceException {
		List<DrinkIngredientTransfer> result = null;
		if (drinkId <= 0) {
			return result;
		}

		try {
			result = drinkIngredientDao.readIngredientsForSpecificDrink(drinkId);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * Removes unpaid orders using time for payment parameter provided by
	 * {@link OrderPropertyProvider}.
	 * 
	 * @throws ServiceException
	 */
	@Override
	public void removeUnpaidOrders() throws ServiceException {
		String availableTimeForPayment = ORDER_PROPERTY_PROVIDER.retrieveValue(OrderParameter.TIME_FOR_PAYMENT);
		OffsetDateTime expired = retrieveCurrentDateTime().minusMinutes(Long.parseLong(availableTimeForPayment));
		try {
			orderDao.removeExpiredOrders(expired, OrderStatus.CREATED);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	private OffsetDateTime retrieveCurrentDateTime() {
		String timeZoneParam = ORDER_PROPERTY_PROVIDER.retrieveValue(OrderParameter.DB_TIME_ZONE);
		ZoneOffset zoneId = ZoneOffset.of(timeZoneParam);
		OffsetDateTime dateTime = OffsetDateTime.now(zoneId);

		return dateTime;
	}

	/**
	 * 
	 * Obtains order by specified id.
	 * 
	 * @param orderId {@code long} value which uniquely indicates the order.
	 * @return {@code Order} with specified id or {@code null} if order id is
	 *         invalid
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */
	@Override
	public Order read(long orderId) throws ServiceException {
		if (orderId <= 0) {
			return null;
		}

		Order order = null;
		try {
			order = orderDao.read(orderId);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}

		return order;
	}

	/**
	 * Performs payment for the order operation.
	 * 
	 * @param orderId {@code long} value which uniquely indicates the order.
	 * @param userId  {@code long} value which uniquely indicates the user who made
	 *                the order.
	 * @return {@code true} if payment was successful or {@code false} otherwise.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */
	@Override
	public boolean pay(long orderId, long userId) throws ServiceException {
		boolean result = false;

		if (orderId <= 0 || userId <= 0) {
			return result;
		}
		Account account = accountService.obtainAccountByUserId(userId);
		BonusAccount bonusAccount = bonusAccountService.obtainAccountByUserId(userId);
		Order order = read(orderId);

		if (account == null || bonusAccount == null || order == null) {
			return result;
		}
		String cashbackPercent = ORDER_PROPERTY_PROVIDER.retrieveValue(OrderParameter.CASHBACK);

		BigDecimal accountBalance = account.getBalance();
		BigDecimal bonusAccountBalance = bonusAccount.getBalance();
		BigDecimal orderCost = order.getInfo().getCost();
		BigDecimal cashback = orderCost.multiply(new BigDecimal(cashbackPercent)).divide(ONE_HUNDRED);

		BigDecimal remainingCost = orderCost.subtract(bonusAccountBalance);

		try {
			if (remainingCost.compareTo(ZERO) > 0) {
				if (accountBalance.subtract(remainingCost).compareTo(ZERO) > 0) {
					result = orderDao.pay(orderId, remainingCost, bonusAccountBalance, cashback);
				}
			} else {
				result = orderDao.pay(orderId, ZERO, orderCost, cashback);
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}

		return result;
	}

	/**
	 * 
	 * Deletes order with specified id.
	 * 
	 * @param orderId {@code long} value which uniquely indicates the order.
	 * @return {@code true} if removal was successful or {@code false} otherwise.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */
	@Override
	public boolean cancel(long orderId) throws ServiceException {
		boolean result = false;
		if (orderId <= 0) {
			return result;
		}

		try {
			result = orderDao.delete(orderId);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}

		return result;
	}

	/**
	 * Select popular drinks using amount of drinks to be selected provided by
	 * {@link MenuPropertyProvider}.
	 * 
	 * @return {@code List} of {@code DrinkTransfer} objects representing popular
	 *         drinks.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer
	 *                          or popular drinks' amount is invalid.
	 */
	@Override
	public List<DrinkTransfer> selectPopularDrinks() throws ServiceException {
		List<DrinkTransfer> drinks = null;
		int amount;
		try {
			amount = Integer.parseInt(menuPropertyProvider.retrieveValue(MenuParameter.POPULAR_DRINKS_AMOUNT));
			if (amount < 0) {
				throw new ServiceException(CommonExceptionMessage.NEGATIVE_PARAM);
			}
			drinks = orderDrinkDao.selectPopularDrinks(amount);
		} catch (DAOException | NumberFormatException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return drinks;
	}

}
