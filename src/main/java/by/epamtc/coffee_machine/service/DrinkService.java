package by.epamtc.coffee_machine.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;

public interface DrinkService {

	List<DrinkTransfer> obtainMenu(int pageNumber) throws ServiceException;

	Drink obtainDrink(long drinkId) throws ServiceException;

	int obtainMenuPagesAmount() throws ServiceException;

	Set<DrinkMessage> edit(String imagePath, long drinkId, BigDecimal price, String description) throws ServiceException;

}
