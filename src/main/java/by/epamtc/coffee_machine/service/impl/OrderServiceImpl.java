package by.epamtc.coffee_machine.service.impl;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
import by.epamtc.coffee_machine.dao.IngredientDAO;
import by.epamtc.coffee_machine.dao.OrderDAO;
import by.epamtc.coffee_machine.service.CommonExceptionMessage;
import by.epamtc.coffee_machine.service.OrderService;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;
import by.epamtc.coffee_machine.service.utility.OrderParameter;
import by.epamtc.coffee_machine.service.utility.OrderPropertyProvider;

/**
 * Provides access to {@link OrderDAO} and support for working with entities
 * {@link Order}, {@link OrderInfo}.
 */
public class OrderServiceImpl implements OrderService {
	private static final DrinkDAO DRINK_DAO = DAOProvider.getInstance().getDrinkDAO();
	private static final OrderDAO ORDER_DAO = DAOProvider.getInstance().getOrderDAO();
	private static final IngredientDAO INGREDIENT_DAO = DAOProvider.getInstance().getIngredientDAO();
	private static final OrderPropertyProvider ORDER_PROPERTY_PROVIDER = OrderPropertyProvider.getInstance();

	private static final String ILLEGAL_ARRAYS_LENGTH_MESSAGE = "Lengths of passed arrays must be equal";
	private static final String ILLEGAL_ARRAY_PARAMETERS_MESSAGE = "Passed arrays should contain only positive integers";
	public static final String DIGITS_REGEX = "\\d+";

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
					Drink drink = DRINK_DAO.read(id);
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
		// removeUnpaidOrders();
		UnavailableIngredientTransfer unavailableIngredient = checkAvailableIngredientsAmount(orderDrink);
		if (unavailableIngredient == null) {
			try {
				long orderId = ORDER_DAO.add(orderTransfer);
				orderTransfer.getOrder().setOrderId(orderId);
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

			List<DrinkIngredientTransfer> drinkIngredients = ServiceProvider.getInstance().getDrinkIngredientService()
					.obtainIngredientsForSpecificDrink(drinkId);

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
					ingredient = INGREDIENT_DAO.readAvailable(element.getKey());
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
			ORDER_DAO.removeExpiredOrders(expired, OrderStatus.CREATED);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	private OffsetDateTime retrieveCurrentDateTime() {
		String timeZoneParam = ORDER_PROPERTY_PROVIDER.retrieveValue(OrderParameter.DB_TIME_ZONE);
		ZoneId zoneId = ZoneId.of(timeZoneParam);
		OffsetDateTime dateTime = OffsetDateTime.now(zoneId);

		return dateTime;
	}

}
