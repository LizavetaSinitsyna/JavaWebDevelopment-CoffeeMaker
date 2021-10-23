package by.epamtc.coffee_machine.bean.transfer;

import java.io.Serializable;

/**
 * Transfers characteristics of the particular ingredient as the part of
 * specified drink.
 *
 */
public class DrinkIngredientTransfer implements Serializable {

	private static final long serialVersionUID = 1L;

	private long ingredientId;
	private String ingredientName;
	private int ingredientAmount;
	private boolean isOptional;

	public DrinkIngredientTransfer() {

	}

	public long getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(long ingredientId) {
		this.ingredientId = ingredientId;
	}

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	public int getIngredientAmount() {
		return ingredientAmount;
	}

	public void setIngredientAmount(int ingredientAmount) {
		this.ingredientAmount = ingredientAmount;
	}

	public boolean isOptional() {
		return isOptional;
	}

	public void setOptional(boolean isOptional) {
		this.isOptional = isOptional;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ingredientAmount;
		result = prime * result + (int) (ingredientId ^ (ingredientId >>> 32));
		result = prime * result + ((ingredientName == null) ? 0 : ingredientName.hashCode());
		result = prime * result + (isOptional ? 1231 : 1237);
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
		DrinkIngredientTransfer other = (DrinkIngredientTransfer) obj;
		if (ingredientAmount != other.ingredientAmount)
			return false;
		if (ingredientId != other.ingredientId)
			return false;
		if (ingredientName == null) {
			if (other.ingredientName != null)
				return false;
		} else if (!ingredientName.equals(other.ingredientName))
			return false;
		if (isOptional != other.isOptional)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DrinkIngredientTransfer [ingredientId=");
		builder.append(ingredientId);
		builder.append(", ingredientName=");
		builder.append(ingredientName);
		builder.append(", ingredientAmount=");
		builder.append(ingredientAmount);
		builder.append(", isOptional=");
		builder.append(isOptional);
		builder.append("]");
		return builder.toString();
	}

}
