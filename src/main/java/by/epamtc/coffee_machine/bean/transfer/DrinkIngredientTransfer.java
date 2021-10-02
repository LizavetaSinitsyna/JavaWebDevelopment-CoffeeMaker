/**
 * 
 */
package by.epamtc.coffee_machine.bean.transfer;

import java.io.Serializable;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class DrinkIngredientTransfer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int ingredientId;
	private String ingredientName;
	private int ingredientAmount;
	private boolean isOptional;
	
	public DrinkIngredientTransfer() {
		
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
	 * @return the ingredientName
	 */
	public String getIngredientName() {
		return ingredientName;
	}

	/**
	 * @param ingredientName the ingredientName to set
	 */
	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
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
	 * @return the isOptional
	 */
	public boolean isOptional() {
		return isOptional;
	}

	/**
	 * @param isOptional the isOptional to set
	 */
	public void setOptional(boolean isOptional) {
		this.isOptional = isOptional;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ingredientAmount;
		result = prime * result + ingredientId;
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
		return getClass().getSimpleName() + " [ingredientId=" + ingredientId + ", ingredientName=" + ingredientName
				+ ", ingredientAmount=" + ingredientAmount + ", isOptional=" + isOptional + "]";
	}

	
}

