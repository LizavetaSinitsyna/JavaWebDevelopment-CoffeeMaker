/**
 * 
 */
package by.epamtc.coffee_machine.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import by.epamtc.coffee_machine.service.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class OrderDrink implements Serializable, Iterable<Entry<Long, Integer>> {

	private static final long serialVersionUID = 1L;

	private long orderId;
	private Map<Long, Integer> drinksAmount;

	public OrderDrink() {

	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public boolean addDrink(long drinkId, int amount) {
		if (!ValidationHelper.isPositive(drinkId) || !ValidationHelper.isPositive(amount)) {
			return false;
		}
		if (drinksAmount == null) {
			drinksAmount = new HashMap<>();
		}
		drinksAmount.put(drinkId, amount);
		return true;
	}

	public Map<Long, Integer> getDrinksAmount() {
		return retrieveMapCopy(drinksAmount);
	}

	public void setDrinksAmount(Map<Long, Integer> drinksAmount) {
		this.drinksAmount = retrieveMapCopy(drinksAmount);
	}

	@Override
	public Iterator<Entry<Long, Integer>> iterator() {
		if (drinksAmount != null) {
			return drinksAmount.entrySet().iterator();
		}
		return null;
	}

	private Map<Long, Integer> retrieveMapCopy(Map<Long, Integer> drinksAmount) {
		Map<Long, Integer> copy = new HashMap<>();
		if (drinksAmount == null) {
			return copy;
		}
		Long key;
		Integer amount;
		for (Map.Entry<Long, Integer> element : drinksAmount.entrySet()) {
			key = element.getKey();
			amount = element.getValue();
			if (key != null) {
				copy.put(key, amount == null ? 0 : amount);
			}
		}
		return copy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((drinksAmount == null) ? 0 : drinksAmount.hashCode());
		result = prime * result + (int) (orderId ^ (orderId >>> 32));
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
		OrderDrink other = (OrderDrink) obj;
		if (drinksAmount == null) {
			if (other.drinksAmount != null)
				return false;
		} else if (!drinksAmount.equals(other.drinksAmount))
			return false;
		if (orderId != other.orderId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderDrink [orderId=");
		builder.append(orderId);
		builder.append(", drinksAmount=");
		builder.append(drinksAmount);
		builder.append("]");
		return builder.toString();
	}

}
