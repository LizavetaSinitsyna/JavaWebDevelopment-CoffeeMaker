package by.epamtc.coffee_machine.dao;

import java.time.OffsetDateTime;

import by.epamtc.coffee_machine.bean.Order;
import by.epamtc.coffee_machine.bean.OrderInfo;
import by.epamtc.coffee_machine.bean.OrderStatus;
import by.epamtc.coffee_machine.bean.transfer.OrderTransfer;

public interface OrderDAO {
	Order read(long orderId) throws DAOException;

	boolean remove(long orderId) throws DAOException;

	boolean update(long orderId, OrderInfo info) throws DAOException;

	void removeExpiredOrders(OffsetDateTime dateTime, OrderStatus status) throws DAOException;

	long add(OrderTransfer order) throws DAOException;
}
