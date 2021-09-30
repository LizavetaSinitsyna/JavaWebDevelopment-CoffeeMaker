/**
 * 
 */
package by.epamtc.coffee_machine.service;

import java.util.List;

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
	Drink obtainDrink(int drink_id) throws ServiceException;

	/**
	 * @return
	 * @throws ServiceException
	 */
	int obtainMenuPagesAmount() throws ServiceException;

}
