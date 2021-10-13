/**
 * 
 */
package by.epamtc.coffee_machine.bean;

import java.io.Serializable;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class DrinkIngredient implements Serializable {

	private static final long serialVersionUID = 1L;

	private int ingredientId;
	private int ingredientAmount;
	private boolean optional;

	public DrinkIngredient() {

	}

	/**
	 * @return the ingredientId
	 */
	public int getIngredientId() {
		return ingredientId;
	}

	/**
	 * @param ingredientId the ingredientId to set
	 */
	public void setIngredientId(int ingredientId) {
		this.ingredientId = ingredientId;
	}

	/**
	 * @return the ingredientAmount
	 */
	public int getIngredientAmount() {
		return ingredientAmount;
	}

	/**
	 * @param ingredientAmount the ingredientAmount to set
	 */
	public void setIngredientAmount(int ingredientAmount) {
		this.ingredientAmount = ingredientAmount;
	}

	/**
	 * @return the optional
	 */
	public boolean isOptional() {
		return optional;
	}

	/**
	 * @param optional the optional to set
	 */
	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ingredientAmount;
		result = prime * result + ingredientId;
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
