/**
 * 
 */
package by.epamtc.coffee_machine.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.coffee_machine.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class DrinkIngredientMap implements Serializable {

	private static final long serialVersionUID = 1L;

	private int drinkId;
	private List<DrinkIngredient> ingredients;

	public DrinkIngredientMap() {
		ingredients = new ArrayList<>();
	}

	/**
	 * @return the drinkId
	 */
	public int getDrinkId() {
		return drinkId;
	}

	/**
	 * @param drinkId the drinkId to set
	 */
	public void setDrinkId(int drinkId) {
		this.drinkId = drinkId;
	}

	/**
	 * @return the ingredients
	 */
	public List<DrinkIngredient> getIngredients() {
		return new ArrayList<>(ingredients);
	}

	/**
	 * @param drinkIngredients the ingredients to set
	 */
	public void setIngredients(List<DrinkIngredient> drinkIngredients) {
		if (ValidationHelper.isNull(drinkIngredients)) {
			this.ingredients = new ArrayList<>();
		} else {
			this.ingredients = new ArrayList<>(drinkIngredients);
		}
	}

	public boolean addIngredient(DrinkIngredient ingredient) {
		if (ValidationHelper.isNull(ingredients) || ValidationHelper.isNull(ingredient)) {
			return false;
		}
		return ingredients.add(ingredient);

	}

	public boolean removeIngredient(DrinkIngredient ingredient) {
		if (ValidationHelper.isNull(ingredients) || ValidationHelper.isNull(ingredient)) {
			return false;
		}
		return ingredients.remove(ingredient);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + drinkId;
		result = prime * result + ((ingredients == null) ? 0 : ingredients.hashCode());
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
		DrinkIngredientMap other = (DrinkIngredientMap) obj;
		if (drinkId != other.drinkId)
			return false;
		if (ingredients == null) {
			if (other.ingredients != null)
				return false;
		} else if (!ingredients.equals(other.ingredients))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [drinkId=" + drinkId + ", ingredients=" + ingredients + "]";
	}

}
