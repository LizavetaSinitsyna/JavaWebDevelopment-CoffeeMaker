package by.epamtc.coffee_machine.service.validation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import by.epamtc.coffee_machine.bean.DrinkIngredient;
import by.epamtc.coffee_machine.service.DrinkIngredientMessage;

public class IngredientValidatorTest {

	@Test
	public void testCheckAmountWithValidAmount() {
		int amount = 10;
		Assert.assertTrue(IngredientValidator.checkAmount(amount));
	}

	@Test
	public void testCheckAmountWithInvalidAmount() {
		int amount = -10;
		Assert.assertFalse(IngredientValidator.checkAmount(amount));
	}

	@Test
	public void testValidateFieldsWithEmptyIngredientsList() {
		List<DrinkIngredient> drinkIngredients = new ArrayList<>();
		Set<DrinkIngredientMessage> expected = new HashSet<>();
		expected.add(DrinkIngredientMessage.ILLEGAL_DRINK_INGREDIENT_AMOUNT);
		Assert.assertEquals(expected, IngredientValidator.validateFields(drinkIngredients));

	}

	@Test
	public void testValidateFieldsWithNullIngredientsList() {
		List<DrinkIngredient> drinkIngredients = null;
		Set<DrinkIngredientMessage> expected = new HashSet<>();
		expected.add(DrinkIngredientMessage.ILLEGAL_DRINK_INGREDIENT_AMOUNT);
		Assert.assertEquals(expected, IngredientValidator.validateFields(drinkIngredients));

	}

	@Test
	public void testValidateFieldsWithInvalidIngredientAmount() {
		int amount = -5;
		int id = 1;
		List<DrinkIngredient> drinkIngredients = new ArrayList<>();
		DrinkIngredient drinkIngredient = new DrinkIngredient();
		drinkIngredient.setIngredientAmount(amount);
		drinkIngredient.setIngredientId(id);
		drinkIngredients.add(drinkIngredient);
		Set<DrinkIngredientMessage> expected = new HashSet<>();
		expected.add(DrinkIngredientMessage.ILLEGAL_INGREDIENT_AMOUNT);
		Assert.assertEquals(expected, IngredientValidator.validateFields(drinkIngredients));

	}

	@Test
	public void testValidateFieldsWithInvalidIngredientId() {
		List<DrinkIngredient> drinkIngredients = new ArrayList<>();
		DrinkIngredient drinkIngredient = new DrinkIngredient();
		drinkIngredients.add(drinkIngredient);
		Set<DrinkIngredientMessage> expected = new HashSet<>();
		expected.add(DrinkIngredientMessage.INVALID_INGREDIENT_ID);
		Assert.assertEquals(expected, IngredientValidator.validateFields(drinkIngredients));

	}

	@Test
	public void testValidateFieldsWithDublicatedIngredient() {
		List<DrinkIngredient> drinkIngredients = new ArrayList<>();
		DrinkIngredient drinkIngredient1 = new DrinkIngredient();
		drinkIngredient1.setIngredientAmount(2);
		drinkIngredient1.setIngredientId(1);
		drinkIngredients.add(drinkIngredient1);
		DrinkIngredient drinkIngredient2 = new DrinkIngredient();
		drinkIngredient2.setIngredientAmount(4);
		drinkIngredient2.setIngredientId(1);
		drinkIngredients.add(drinkIngredient2);
		Set<DrinkIngredientMessage> expected = new HashSet<>();
		expected.add(DrinkIngredientMessage.DUPLICATE_INGREDIENT);
		Assert.assertEquals(expected, IngredientValidator.validateFields(drinkIngredients));

	}

}
