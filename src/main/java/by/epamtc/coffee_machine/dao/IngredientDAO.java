/**
 * 
 */
package by.epamtc.coffee_machine.dao;

import by.epamtc.coffee_machine.bean.Ingredient;
import by.epamtc.coffee_machine.bean.IngredientInfo;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface IngredientDAO extends GenericDAO<Ingredient> {
	Ingredient read(int ingredient_id);

	@Override
	int add(Ingredient ingredient);

	boolean remove(int ingredient_id);

	boolean updateAmount(int ingredient_id, int amount);

	boolean update(int ingredient_id, IngredientInfo ingredientInfo);

}
