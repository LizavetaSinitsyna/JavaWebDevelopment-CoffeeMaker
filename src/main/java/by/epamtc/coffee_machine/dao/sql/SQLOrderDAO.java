/**
 * 
 */
package by.epamtc.coffee_machine.dao.sql;

import java.time.LocalDateTime;
import java.util.Map;

import by.epamtc.coffee_machine.bean.Order;
import by.epamtc.coffee_machine.bean.OrderInfo;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.OrderDAO;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class SQLOrderDAO implements OrderDAO {

	@Override
	public Order read(int order_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int add(int userId, Map<Integer, Integer> drinksAmount, LocalDateTime dateTime) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean remove(int order_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(int order_id, OrderInfo info) {
		// TODO Auto-generated method stub
		return false;
	}

}
