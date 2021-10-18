package by.epamtc.coffee_machine.bean.transfer;

import by.epamtc.coffee_machine.bean.Order;
import by.epamtc.coffee_machine.bean.OrderDrink;

public class OrderTransfer {
	private Order order;
	private OrderDrink orderDrink;
	private UnavailableIngredientTransfer unavailableIngredient;

	public OrderTransfer() {

	}

	public Order getOrder() {
		return order == null ? order : orderCopy(order);
	}

	public void setOrder(Order order) {
		this.order = order == null ? order : orderCopy(order);
	}

	public OrderDrink getOrderDrink() {
		return orderDrink == null ? orderDrink : orderDrinkCopy(orderDrink);
	}

	public void setOrderDrink(OrderDrink orderDrink) {
		this.orderDrink = orderDrink == null ? orderDrink : orderDrinkCopy(orderDrink);
	}

	public UnavailableIngredientTransfer getUnavailableIngredient() {
		return unavailableIngredient == null ? unavailableIngredient : unavailableIngredientCopy(unavailableIngredient);
	}

	public void setUnavailableIngredient(UnavailableIngredientTransfer unavailableIngredient) {
		this.unavailableIngredient = unavailableIngredient == null ? unavailableIngredient
				: unavailableIngredientCopy(unavailableIngredient);
	}

	private Order orderCopy(Order order) {
		Order copy = new Order();
		copy.setInfo(order.getInfo());
		copy.setOrderId(order.getOrderId());
		copy.setUserId(order.getUserId());
		return copy;
	}

	private OrderDrink orderDrinkCopy(OrderDrink orderDrink) {
		OrderDrink copy = new OrderDrink();
		copy.setOrderId(orderDrink.getOrderId());
		copy.setDrinksAmount(orderDrink.getDrinksAmount());
		return copy;
	}

	private UnavailableIngredientTransfer unavailableIngredientCopy(
			UnavailableIngredientTransfer unavailableIngredient) {
		UnavailableIngredientTransfer copy = new UnavailableIngredientTransfer();
		copy.setAvailableDrinkAmount(unavailableIngredient.getAvailableDrinkAmount());
		copy.setIngredientId(unavailableIngredient.getIngredientId());
		copy.setDrinkId(unavailableIngredient.getDrinkId());
		copy.setIngredientName(unavailableIngredient.getIngredientName());
		return copy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + ((orderDrink == null) ? 0 : orderDrink.hashCode());
		result = prime * result + ((unavailableIngredient == null) ? 0 : unavailableIngredient.hashCode());
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
		OrderTransfer other = (OrderTransfer) obj;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (orderDrink == null) {
			if (other.orderDrink != null)
				return false;
		} else if (!orderDrink.equals(other.orderDrink))
			return false;
		if (unavailableIngredient == null) {
			if (other.unavailableIngredient != null)
				return false;
		} else if (!unavailableIngredient.equals(other.unavailableIngredient))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderTransfer [order=");
		builder.append(order);
		builder.append(", orderDrink=");
		builder.append(orderDrink);
		builder.append(", unavailableIngredient=");
		builder.append(unavailableIngredient);
		builder.append("]");
		return builder.toString();
	}

}
