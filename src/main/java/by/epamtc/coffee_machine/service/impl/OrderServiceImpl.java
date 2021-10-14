/**
 * 
 */
package by.epamtc.coffee_machine.service.impl;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import by.epamtc.coffee_machine.bean.Ingredient;
import by.epamtc.coffee_machine.bean.Order;
import by.epamtc.coffee_machine.bean.OrderDrink;
import by.epamtc.coffee_machine.bean.OrderInfo;
import by.epamtc.coffee_machine.bean.OrderStatus;
import by.epamtc.coffee_machine.bean.transfer.DrinkIngredientTransfer;
import by.epamtc.coffee_machine.bean.transfer.IngredientDrinkAvailabilityTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DAOProvider;
import by.epamtc.coffee_machine.dao.DrinkDAO;
import by.epamtc.coffee_machine.dao.IngredientDAO;
import by.epamtc.coffee_machine.dao.OrderDAO;
import by.epamtc.coffee_machine.service.DrinkIngredientService;
import by.epamtc.coffee_machine.service.OrderService;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.ServiceProvider;
import by.epamtc.coffee_machine.service.utility.OrderParameter;
import by.epamtc.coffee_machine.service.utility.OrderPropertyProvider;
import by.epamtc.coffee_machine.service.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class OrderServiceImpl implements OrderService {
	private static final DrinkDAO DRINK_DAO = DAOProvider.getInstance().getDrinkDAO();
	private static final OrderDAO ORDER_DAO = DAOProvider.getInstance().getOrderDAO();
	private static final IngredientDAO INGREDIENT_DAO = DAOProvider.getInstance().getIngredientDAO();
	private static final DrinkIngredientService DRINK_INGREDIENT_SERVICE = ServiceProvider.getInstance()
			.getDrinkIngredientService();
	private static final OrderPropertyProvider ORDER_PROPERTY_PROVIDER = OrderPropertyProvider.getInstance();
	private static final String ILLEGAL_ARRAYS_LENGTH_MESSAGE = "Lengths of passed arrays must be equal";
	private static final String ILLEGAL_ARRAY_PARAMETERS_MESSAGE = "Passed arrays should contain only positive integers";

	@Override
	public OrderInfo placeOrder(String[] drinksId, String[] drinksAmount, int userId) throws ServiceException {
		if (ValidationHelper.isNull(drinksId) || ValidationHelper.isNull(drinksAmount)) {
			throw new ServiceException(ValidationHelper.NULL_ARGUMENT_EXCEPTION);
		}

		if (drinksId.length != drinksAmount.length) {
			throw new ServiceException(ILLEGAL_ARRAYS_LENGTH_MESSAGE);
		}

		int length = drinksId.length;

		long id;
		int amount;
		BigDecimal price;
		BigDecimal cost = new BigDecimal(0);

		Pattern pattern = Pattern.compile(ValidationHelper.DIGITS_REGEX);
		OrderDrink orderDrink = new OrderDrink();

		for (int i = 0; i < length; i++) {
			if (pattern.matcher(drinksId[i]).matches() && pattern.matcher(drinksAmount[i]).matches()) {
				id = Long.parseLong(drinksId[i]);
				amount = Integer.parseInt(drinksAmount[i]);
				orderDrink.addDrink(id, amount);
				try {
					price = DRINK_DAO.read(id).getInfo().getPrice();
				} catch (DAOException e) {
					throw new ServiceException(e.getMessage(), e);
				}
				cost = cost.add(price.multiply(new BigDecimal(amount)));
			} else {
				throw new ServiceException(ILLEGAL_ARRAY_PARAMETERS_MESSAGE);
			}
		}

		Order order = new Order();
		order.setUserId(userId);
		OrderInfo orderInfo = new OrderInfo();

		OffsetDateTime dateTime = retrieveCurrentDateTime();
		orderInfo.setDateTime(dateTime);
		orderInfo.setStatus(OrderStatus.CREATED);
		orderInfo.setCost(cost);

		removeUnpaidOrders();

		return null;
	}

	private IngredientDrinkAvailabilityTransfer checkIngredientsAmount(OrderDrink orderDrink) throws ServiceException {
		int currentAmount;
		Map<Long, Integer> requiredIngredientsAmount = new HashMap<>();
		Iterator<Map.Entry<Long, Integer>> drinksIterator = orderDrink.iterator();
		while (drinksIterator.hasNext()) {
			Map.Entry<Long, Integer> drink = drinksIterator.next();
			List<DrinkIngredientTransfer> ingredients = DRINK_INGREDIENT_SERVICE
					.obtainIngredientsForSpecificDrink(drink.getKey());
			for (DrinkIngredientTransfer element : ingredients) {
				long ingredientId = element.getIngredientId();
				int ingredientAmount = element.getIngredientAmount();
				if (requiredIngredientsAmount.containsKey(ingredientId)) {
					requiredIngredientsAmount.put(ingredientId, (ingredientAmount * drink.getValue()));
				} else {
					currentAmount = requiredIngredientsAmount.get(ingredientId);
					requiredIngredientsAmount.put(ingredientId, currentAmount + (ingredientAmount * drink.getValue()));
				}
			}
		}
		
		Ingredient ingredient = null;
		IngredientDrinkAvailabilityTransfer result = null;
		
		for(Map.Entry<Long, Integer> element : requiredIngredientsAmount.entrySet()) {
			try {
				ingredient = INGREDIENT_DAO.read(element.getKey());
			} catch (DAOException e) {
				throw new ServiceException(e.getMessage(), e);
			}
			if(ingredient.getCurrentAmount() < element.getValue()) {
				result = new IngredientDrinkAvailabilityTransfer();
			}
			
		}

		return result;

	}

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
