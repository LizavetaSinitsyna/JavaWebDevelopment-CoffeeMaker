package by.epamtc.coffee_machine.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DrinkIngredientMap implements Serializable {

	private static final long serialVersionUID = 1L;

	private long drinkId;
	private List<DrinkIngredient> ingredients;

	public DrinkIngredientMap() {
		ingredients = new ArrayList<>();
	}

	public long getDrinkId() {
		return drinkId;
	}

	public void setDrinkId(long drinkId) {
		this.drinkId = drinkId;
	}

	public List<DrinkIngredient> getIngredients() {
		return new ArrayList<>(ingredients);
	}

	public void setIngredients(List<DrinkIngredient> drinkIngredients) {
		if (drinkIngredients == null) {
			this.ingredients = new ArrayList<>();
		} else {
			this.ingredients = new ArrayList<>(drinkIngredients);
		}
	}

	public boolean addIngredient(DrinkIngredient ingredient) {
		if (ingredients == null || ingredient == null) {
			return false;
		}
		return ingredients.add(ingredient);

	}

	public boolean removeIngredient(DrinkIngredient ingredient) {
		if (ingredients == null || ingredient == null) {
			return false;
		}
		return ingredients.remove(ingredient);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (drinkId ^ (drinkId >>> 32));
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
		StringBuilder builder = new StringBuilder();
		builder.append("DrinkIngredientMap [drinkId=");
		builder.append(drinkId);
		builder.append(", ingredients=");
		builder.append(ingredients);
		builder.append("]");
		return builder.toString();
	}

}
