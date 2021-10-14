/**
 * 
 */
package by.epamtc.coffee_machine.bean.transfer;

import java.io.Serializable;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class IngredientDrinkAvailabilityTransfer implements Serializable {

	private static final long serialVersionUID = 1L;

	private long ingredientId;
	private String ingredientName;
	private long drinkId;
	private int drinkAmount;

	public IngredientDrinkAvailabilityTransfer() {

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

	public long getDrinkId() {
		return drinkId;
	}

	public void setDrinkId(long drinkId) {
		this.drinkId = drinkId;
	}

	public int getDrinkAmount() {
		return drinkAmount;
	}

	public void setDrinkAmount(int drinkAmount) {
		this.drinkAmount = drinkAmount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + drinkAmount;
		result = prime * result + (int) (drinkId ^ (drinkId >>> 32));
		result = prime * result + (int) (ingredientId ^ (ingredientId >>> 32));
		result = prime * result + ((ingredientName == null) ? 0 : ingredientName.hashCode());
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
		IngredientDrinkAvailabilityTransfer other = (IngredientDrinkAvailabilityTransfer) obj;
		if (drinkAmount != other.drinkAmount)
			return false;
		if (drinkId != other.drinkId)
			return false;
		if (ingredientId != other.ingredientId)
			return false;
		if (ingredientName == null) {
			if (other.ingredientName != null)
				return false;
		} else if (!ingredientName.equals(other.ingredientName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IngredientDrinkAvailabilityTransfer [ingredientId=");
		builder.append(ingredientId);
		builder.append(", ingredientName=");
		builder.append(ingredientName);
		builder.append(", drinkId=");
		builder.append(drinkId);
		builder.append(", drinkAmount=");
		builder.append(drinkAmount);
		builder.append("]");
		return builder.toString();
	}

}
