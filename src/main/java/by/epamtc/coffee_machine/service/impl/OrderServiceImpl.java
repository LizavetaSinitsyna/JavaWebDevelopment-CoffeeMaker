/**
 * 
 */
package by.epamtc.coffee_machine.service.impl;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.regex.Pattern;

import by.epamtc.coffee_machine.bean.OrderDrink;
import by.epamtc.coffee_machine.bean.OrderInfo;
import by.epamtc.coffee_machine.service.OrderService;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.utility.MenuParameter;
import by.epamtc.coffee_machine.utility.OrderParameter;
import by.epamtc.coffee_machine.utility.OrderPropertyProvider;
import by.epamtc.coffee_machine.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class OrderServiceImpl implements OrderService {
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

		int id;
		int amount;
		Pattern pattern = Pattern.compile(ValidationHelper.DIGITS_REGEX);

		for (int i = 0; i < length; i++) {
			if (pattern.matcher(drinksId[i]).matches() && pattern.matcher(drinksAmount[i]).matches()) {
				id = Integer.parseInt(drinksId[i]);
				amount = Integer.parseInt(drinksAmount[i]);
				OrderDrink orderDrink = new OrderDrink();
				orderDrink.addDrink(id, amount);
			} else {
				throw new ServiceException(ILLEGAL_ARRAY_PARAMETERS_MESSAGE);
			}
		}
		
		OffsetDateTime dateTime = retrieveOrderDateTime();

		return null;
	}

	private OffsetDateTime retrieveOrderDateTime() {
		String timeZoneParam= OrderPropertyProvider.getInstance().retrieveValue(OrderParameter.DB_TIME_ZONE);
		ZoneId zoneId = ZoneId.of(timeZoneParam);
		OffsetDateTime dateTime = OffsetDateTime.now(zoneId);
		
		return dateTime;
	}

}
