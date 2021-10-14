/**
 * 
 */
package by.epamtc.coffee_machine.bean;

import java.io.Serializable;

import by.epamtc.coffee_machine.service.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	private long orderId;
	private long userId;
	private OrderInfo info;

	public Order() {

	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public OrderInfo getInfo() {
		return ValidationHelper.isNull(info) ? info : infoCopy(info);
	}

	public void setInfo(OrderInfo info) {
		if (!ValidationHelper.isNull(info)) {
			this.info = infoCopy(info);
		}
	}

	private OrderInfo infoCopy(OrderInfo info) {
		OrderInfo copy = new OrderInfo();
		copy.setDateTime(info.getDateTime());
		copy.setStatus(info.getStatus());
		copy.setCost(info.getCost());
		return copy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + (int) (orderId ^ (orderId >>> 32));
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (orderId != other.orderId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [orderId=");
		builder.append(orderId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", info=");
		builder.append(info);
		builder.append("]");
		return builder.toString();
	}

}
