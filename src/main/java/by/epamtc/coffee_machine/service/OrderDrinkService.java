package by.epamtc.coffee_machine.service;

import java.util.List;

import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;

public interface OrderDrinkService {
	public List<DrinkTransfer> selectPopularDrinks() throws ServiceException;

}
