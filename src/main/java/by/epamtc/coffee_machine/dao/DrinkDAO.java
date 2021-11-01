package by.epamtc.coffee_machine.dao;

import java.util.List;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.DrinkIngredient;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;

/**
 * Provides methods for working with Drinks table and entities {@link Drink},
 * {@link DrinkTransfer}
 */
public interface DrinkDAO {
	/**
	 * Obtains existed drink with specified drink id.
	 * 
	 * @param drinkId {@code long} value which uniquely indicates the drink.
	 * @return {@code Drink} with specified id.
	 * @throws DAOException
	 */
	Drink read(long drinkId) throws DAOException;

	/**
	 * Obtains specified amount of drinks starting from indicated row.
	 * 
	 * @param startIndex the begin index, inclusive.
	 * @param amount     the amount of drinks to return.
	 * @return {@code List} of {@code DrinkTransfer} objects representing specified
	 *         {@code amount} of drinks starting from {@code startIndex}
	 * @throws DAOException
	 */
	List<DrinkTransfer> obtainDrinks(int startIndex, int amount) throws DAOException;

	/**
	 * Updates existed Drink.
	 * 
	 * @param drink {@code Drink} value which contains drink id of existed drink.
	 *              The fields of the already existed in database drink will be
	 *              replaced by the fields of passed drink.
	 * 
	 * @return {@code true} If the update was successful or {@code false} if update
	 *         was failed.
	 * @throws DAOException
	 */
	boolean update(Drink drink) throws DAOException;

	/**
	 * Returns general amount of drinks saved in database.
	 * 
	 * @return {@code int} value representing general amount of drinks.
	 * @throws DAOException
	 */
	int obtainGeneralDrinksAmount() throws DAOException;

	/**
	 * Updates image path for specified drink.
	 * 
	 * @param imagePath the image path to save
	 * @param drinkId   {@code long} value which uniquely indicates the drink.
	 * @return {@code true} If the update was successful or {@code false} if update
	 *         was failed.
	 * @throws DAOException
	 */
	boolean updateImage(String imagePath, long drinkId) throws DAOException;

	/**
	 * Creates new drink with specified drink and composition information.
	 * 
	 * @param drink            {@code Drink} value which to be saved.
	 * @param drinkIngredients {@code DrinkIngredient} objects which represent drink
	 *                         composition.
	 * @return {@code long} value representing drink id which was generated after
	 *         saving it in database.
	 * @throws DAOException
	 */
	long add(Drink drink, List<DrinkIngredient> drinkIngredients) throws DAOException;

	/**
	 * Checks if the drink with specified name exists.
	 * 
	 * @param drinkName the name to be checked.
	 * @return {@code true} if the drink with passed name exists and {@code false}
	 *         otherwise.
	 * @throws DAOException
	 */
	boolean containsDrinkName(String drinkName) throws DAOException;

}
