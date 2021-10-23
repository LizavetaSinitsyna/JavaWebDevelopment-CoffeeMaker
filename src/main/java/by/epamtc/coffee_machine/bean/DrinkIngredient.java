package by.epamtc.coffee_machine.bean;

import java.io.Serializable;

/**
 * Contains characteristics of the particular ingredient as the part of
 * specified drink.
 *
 */
public class DrinkIngredient implements Serializable {

	private static final long serialVersionUID = 1L;

	private long ingredientId;
	private int ingredientAmount;
	private boolean optional;

	public DrinkIngredient() {

	}

	public long getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(long ingredientId) {
		this.ingredientId = ingredientId;
	}

	public int getIngredientAmount() {
		return ingredientAmount;
	}

	public void setIngredientAmount(int ingredientAmount) {
		this.ingredientAmount = ingredientAmount;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ingredientAmount;
		result = prime * result + (int) (ingredientId ^ (ingredientId >>> 32));
		result = prime * result + (optional ? 1231 : 1237);
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
		DrinkIngredient other = (DrinkIngredient) obj;
		if (ingredientAmount != other.ingredientAmount)
			return false;
		if (ingredientId != other.ingredientId)
			return false;
		if (optional != other.optional)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DrinkIngredient [ingredientId=");
		builder.append(ingredientId);
		builder.append(", ingredientAmount=");
		builder.append(ingredientAmount);
		builder.append(", optional=");
		builder.append(optional);
		builder.append("]");
		return builder.toString();
	}

}
