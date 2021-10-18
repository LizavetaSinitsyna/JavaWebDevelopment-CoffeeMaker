package by.epamtc.coffee_machine.dao;

import java.util.List;

import by.epamtc.coffee_machine.bean.Ingredient;
import by.epamtc.coffee_machine.bean.IngredientInfo;
import by.epamtc.coffee_machine.bean.transfer.IngredientTransfer;

public interface IngredientDAO {
	Ingredient read(long ingredientId) throws DAOException;

	List<IngredientTransfer> readAll() throws DAOException;

	long add(Ingredient ingredient) throws DAOException;

	boolean remove(long ingredientId) throws DAOException;

	boolean updateAmount(long ingredientId, int amount) throws DAOException;

	boolean update(long ingredientId, IngredientInfo ingredientInfo) throws DAOException;

	Ingredient readAvailable(long ingredientId) throws DAOException;

}
