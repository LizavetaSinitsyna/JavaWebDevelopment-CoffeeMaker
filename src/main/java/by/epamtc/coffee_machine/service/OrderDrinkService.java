/**
 * 
 */
package by.epamtc.coffee_machine.service;

import java.util.List;

import by.epamtc.coffee_machine.bean.Drink;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface OrderDrinkService {
	public List<Drink> selectPopularDrinks() throws ServiceException;

}
