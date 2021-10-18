package by.epamtc.coffee_machine.dao;

import java.util.List;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;

public interface DrinkDAO {
	Drink read(long drinkId) throws DAOException;

	List<DrinkTransfer> obtainDrinks(int startIndex, int amount) throws DAOException;

	boolean remove(long drinkId) throws DAOException;

	boolean update(Drink drink) throws DAOException;

	int obtainGeneralDrinksAmount() throws DAOException;

	long add(Drink drink);

}
