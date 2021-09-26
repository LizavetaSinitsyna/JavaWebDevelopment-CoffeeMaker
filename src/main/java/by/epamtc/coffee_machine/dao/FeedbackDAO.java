/**
 * 
 */
package by.epamtc.coffee_machine.dao;

import java.util.List;

import by.epamtc.coffee_machine.bean.Feedback;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface FeedbackDAO extends GenericDAO<Feedback> {
	List<Feedback> readByDrinkId(int drink_id);

	@Override
	int add(Feedback feedback);

}
