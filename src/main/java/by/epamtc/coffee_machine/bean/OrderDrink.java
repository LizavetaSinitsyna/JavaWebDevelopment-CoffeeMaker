/**
 * 
 */
package by.epamtc.coffee_machine.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import by.epamtc.coffee_machine.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class OrderDrink implements Serializable, Iterable {

	private static final long serialVersionUID = 1L;

	private int orderId;
	private Map<Integer, Integer> drinksAmount;

	public OrderDrink() {

	}

	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public boolean addDrink(int drinkId, int amount) {
		if (!ValidationHelper.isPositive(drinkId) || !ValidationHelper.isPositive(amount)) {
			return false;
		}
		if (drinksAmount == null) {
			drinksAmount = new HashMap<>();
		}
		drinksAmount.put(drinkId, amount);
		return true;
	}

	/**
	 * @return the drinksAmount
	 */
	public Map<Integer, Integer> getDrinksAmount() {
		return retrieveMapCopy(drinksAmount);
	}

	/**
	 * @param drinksAmount the drinksAmount to set
	 */
	public void setDrinksAmount(Map<Integer, Integer> drinksAmount) {
		this.drinksAmount = retrieveMapCopy(drinksAmount);
	}

	@Override
	public Iterator<Entry<Integer, Integer>> iterator() {
		if (drinksAmount != null) {
			return drinksAmount.entrySet().iterator();
		}
		return null;
	}

	private Map<Integer, Integer> retrieveMapCopy(Map<Integer, Integer> drinksAmount) {
		Map<Integer, Integer> copy = new HashMap<>();
		if (drinksAmount == null) {
			return copy;
		}
		Integer key;
		Integer amount;
		for (Map.Entry<Integer, Integer> element : drinksAmount.entrySet()) {
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
		result = prime * result + orderId;
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
		return getClass().getSimpleName() +" [orderId=" + orderId + ", drinksAmount=" + drinksAmount + "]";
	}

}
