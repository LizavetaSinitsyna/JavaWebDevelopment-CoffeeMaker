/**
 * 
 */
package by.epamtc.coffee_machine.dao;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface GenericDAO<T> {
	int add(T object) throws DAOException;
}
