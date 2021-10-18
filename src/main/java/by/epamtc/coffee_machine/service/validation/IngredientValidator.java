package by.epamtc.coffee_machine.service.validation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import by.epamtc.coffee_machine.bean.DrinkIngredient;
import by.epamtc.coffee_machine.service.DrinkIngredientMessage;

public class IngredientValidator {
	private IngredientValidator() {

	}

	public static boolean checkAmount(int amount) {
		return amount > 0;
	}

	public static Set<DrinkIngredientMessage> validateFields(List<DrinkIngredient> drinkIngredients) {
		Set<DrinkIngredientMessage> messages = new HashSet<>();

		Set<Long> usedIngredients = new HashSet<>();

		if (drinkIngredients == null || drinkIngredients.isEmpty()) {
			messages.add(DrinkIngredientMessage.ILLEGAL_DRINK_INGREDIENT_AMOUNT);
		} else {
			for (DrinkIngredient element : drinkIngredients) {
				long ingredientId = element.getIngredientId();
				if (ingredientId <= 0) {
					messages.add(DrinkIngredientMessage.INVALID_INGREDIENT_ID);
					break;
				}
				if (!checkAmount(element.getIngredientAmount())) {
					messages.add(DrinkIngredientMessage.ILLEGAL_INGREDIENT_AMOUNT);
					break;
				}
				if (usedIngredients.contains(ingredientId)) {
					messages.add(DrinkIngredientMessage.DUPLICATE_INGREDIENT);
					break;
				}
				usedIngredients.add(ingredientId);
			}
		}
		return messages;

	}
}
