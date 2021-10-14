/**
 * 
 */
package by.epamtc.coffee_machine.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface DrinkService {

	/**
	 * @param pageNumber
	 * @return
	 * @throws ServiceException
	 */
	List<DrinkTransfer> obtainMenu(int pageNumber) throws ServiceException;

	/**
	 * @param drink_id
	 * @return
	 * @throws ServiceException
	 */
	Drink obtainDrink(long drinkId) throws ServiceException;

	/**
	 * @return
	 * @throws ServiceException
	 */
	int obtainMenuPagesAmount() throws ServiceException;

	/**
	 * @param imagePath
	 * @param drinkId
	 * @param price
	 * @param description
	 * @return
	 * @throws ServiceException
	 */
	Set<DrinkMessage> edit(String imagePath, long drinkId, BigDecimal price, String description) throws ServiceException;

}
