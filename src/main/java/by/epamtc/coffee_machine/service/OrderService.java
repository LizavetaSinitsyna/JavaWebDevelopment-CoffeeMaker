/**
 * 
 */
package by.epamtc.coffee_machine.service;

import by.epamtc.coffee_machine.bean.OrderInfo;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface OrderService {


	/**
	 * @param drinksId
	 * @param drinksAmount
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	OrderInfo placeOrder(String[] drinksId, String[] drinksAmount, int userId) throws ServiceException;
	
	void removeUnpaidOrders() throws ServiceException;

}
