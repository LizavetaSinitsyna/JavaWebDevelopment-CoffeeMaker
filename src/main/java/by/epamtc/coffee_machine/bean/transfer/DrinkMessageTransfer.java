package by.epamtc.coffee_machine.bean.transfer;

import java.util.HashSet;
import java.util.Set;

import by.epamtc.coffee_machine.service.DrinkIngredientMessage;
import by.epamtc.coffee_machine.service.DrinkMessage;

public class DrinkMessageTransfer {
	private long drinkId;
	private Set<DrinkMessage> drinkMessages;
	private Set<DrinkIngredientMessage> drinkIngredientMessages;

	public DrinkMessageTransfer() {
		drinkMessages = new HashSet<>();
	}

	public long getDrinkId() {
		return drinkId;
	}

	public void setDrinkId(long drinkId) {
		this.drinkId = drinkId;
	}

	public Set<DrinkMessage> getDrinkMessages() {
		return drinkMessages == null ? drinkMessages : new HashSet<>(drinkMessages);
	}

	public void setDrinkMessages(Set<DrinkMessage> drinkMessages) {
		if (drinkMessages != null) {
			this.drinkMessages = new HashSet<>(drinkMessages);
		}
	}

	public void addDrinkMessage(DrinkMessage drinkMessage) {
		if (drinkMessage == null) {
			this.drinkMessages = new HashSet<>();
		}
		drinkMessages.add(drinkMessage);
	}

	public Set<DrinkIngredientMessage> getDrinkIngredientMessages() {
		return drinkIngredientMessages == null ? drinkIngredientMessages : new HashSet<>(drinkIngredientMessages);
	}

	public void setDrinkIngredientMessages(Set<DrinkIngredientMessage> drinkIngredientMessages) {
		if (drinkIngredientMessages != null) {
			this.drinkIngredientMessages = drinkIngredientMessages;
		}
	}

	public void addDrinkIngredientMessage(DrinkIngredientMessage drinkIngredientMessage) {
		if (drinkIngredientMessage != null) {
			drinkIngredientMessages.add(drinkIngredientMessage);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (drinkId ^ (drinkId >>> 32));
		result = prime * result + ((drinkIngredientMessages == null) ? 0 : drinkIngredientMessages.hashCode());
		result = prime * result + ((drinkMessages == null) ? 0 : drinkMessages.hashCode());
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
		DrinkMessageTransfer other = (DrinkMessageTransfer) obj;
		if (drinkId != other.drinkId)
			return false;
		if (drinkIngredientMessages == null) {
			if (other.drinkIngredientMessages != null)
				return false;
		} else if (!drinkIngredientMessages.equals(other.drinkIngredientMessages))
			return false;
		if (drinkMessages == null) {
			if (other.drinkMessages != null)
				return false;
		} else if (!drinkMessages.equals(other.drinkMessages))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DrinkMessageTransfer [drinkId=");
		builder.append(drinkId);
		builder.append(", drinkMessages=");
		builder.append(drinkMessages);
		builder.append(", drinkIngredientMessages=");
		builder.append(drinkIngredientMessages);
		builder.append("]");
		return builder.toString();
	}
}