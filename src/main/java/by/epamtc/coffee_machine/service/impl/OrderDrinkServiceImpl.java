/**
 * 
 */
package by.epamtc.coffee_machine.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DAOProvider;
import by.epamtc.coffee_machine.service.OrderDrinkService;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class OrderDrinkServiceImpl implements OrderDrinkService {
	private DAOProvider daoProvider = DAOProvider.getInstance();
	private final String popularDrinkPropertyFile = "popular_drink.properties";

	@Override
	public List<Drink> selectPopularDrinks() throws ServiceException {
		List<Drink> drinks = null;
		int amount;
		Properties properties = new Properties();
		try (InputStream is = getClass().getClassLoader().getResourceAsStream(popularDrinkPropertyFile)) {
			properties.load(is);
			amount = Integer.parseInt(properties.getProperty("popular_drinks_amount"));
			if (ValidationHelper.isNegative(amount)) {
				throw new ServiceException(ValidationHelper.NEGATIVE_PARAM_EXCEPTION);
			}
			drinks = daoProvider.getOrderDrinkDAO().selectPopularDrinks(amount);
		} catch (DAOException | IOException | NumberFormatException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return drinks;
	}

}
