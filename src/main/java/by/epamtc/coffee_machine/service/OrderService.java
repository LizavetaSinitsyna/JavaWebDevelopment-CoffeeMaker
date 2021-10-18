package by.epamtc.coffee_machine.service;

import by.epamtc.coffee_machine.bean.transfer.OrderTransfer;

public interface OrderService {
	
	void removeUnpaidOrders() throws ServiceException;

	OrderTransfer placeOrder(String[] drinksId, String[] drinksAmount, long userId) throws ServiceException;

}
