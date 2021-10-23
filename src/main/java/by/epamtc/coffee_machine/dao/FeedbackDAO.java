package by.epamtc.coffee_machine.dao;

import java.util.List;

import by.epamtc.coffee_machine.bean.Feedback;

public interface FeedbackDAO {
	List<Feedback> readByDrinkId(long drinkId) throws DAOException;

	long add(Feedback feedback) throws DAOException;

}
