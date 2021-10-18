package by.epamtc.coffee_machine.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;

public class OrderDrink implements Serializable, Iterable<Entry<DrinkTransfer, Integer>> {

	private static final long serialVersionUID = 1L;

	private long orderId;
	private Map<DrinkTransfer, Integer> drinksAmount;

	public OrderDrink() {
		drinksAmount = new HashMap<>();
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public boolean addDrink(DrinkTransfer drinkTransfer, int amount) {
		if (drinkTransfer == null || amount <= 0) {
			return false;
		}
		if (drinksAmount == null) {
			drinksAmount = new HashMap<>();
		}
		drinksAmount.put(drinkTransfer, amount);
		return true;
	}

	public Map<DrinkTransfer, Integer> getDrinksAmount() {
		return retrieveMapCopy(drinksAmount);
	}

	public void setDrinksAmount(Map<DrinkTransfer, Integer> drinksAmount) {
		this.drinksAmount = retrieveMapCopy(drinksAmount);
	}

	@Override
	public Iterator<Entry<DrinkTransfer, Integer>> iterator() {
		if (drinksAmount != null) {
			return drinksAmount.entrySet().iterator();
		}
		return null;
	}

	private Map<DrinkTransfer, Integer> retrieveMapCopy(Map<DrinkTransfer, Integer> drinksAmount) {
		Map<DrinkTransfer, Integer> copy = new HashMap<>();
		if (drinksAmount == null) {
			return copy;
		}
		DrinkTransfer key;
		Integer amount;
		for (Map.Entry<DrinkTransfer, Integer> element : drinksAmount.entrySet()) {
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
