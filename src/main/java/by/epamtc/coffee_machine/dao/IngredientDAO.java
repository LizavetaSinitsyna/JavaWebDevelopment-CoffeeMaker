/**
 * 
 */
package by.epamtc.coffee_machine.dao;

import java.util.List;

import by.epamtc.coffee_machine.bean.Ingredient;
import by.epamtc.coffee_machine.bean.IngredientInfo;
import by.epamtc.coffee_machine.bean.transfer.IngredientTransfer;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface IngredientDAO extends GenericDAO<Ingredient> {
	Ingredient read(long ingredientId) throws DAOException;

	List<IngredientTransfer> readAll() throws DAOException;

	@Override
	long add(Ingredient ingredient) throws DAOException;

	boolean remove(long ingredientId) throws DAOException;

	boolean updateAmount(long ingredientId, int amount) throws DAOException;

	boolean update(long ingredientId, IngredientInfo ingredientInfo) throws DAOException;

}
