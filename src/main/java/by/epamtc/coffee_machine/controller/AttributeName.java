package by.epamtc.coffee_machine.controller;

/**
 * Contains attribute names for jsp pages
 *
 */
public class AttributeName {
	public static final String COMMAND = "command";

	// Attribute name for user entity
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final String REPEAT_PASSWORD = "repeat-password";
	public static final String USERNAME = "username";
	public static final String PHONE = "phone";
	public static final String LOGIN = "login";
	public static final String INCORRECT_CREDENTIALS = "incorrect_credentials";
	public static final String USER = "user";
	public static final String ACCOUNT = "account";
	public static final String BONUS_ACCOUNT = "bonusAccount";

	// General
	public static final String NAME = "name";
	public static final String ERRORS = "errors";
	public static final String CURRENT_PAGE = "currentPage";
	public static final String NEXT_PAGE = "nextPage";

	// Language
	public static final String NEW_LANGUAGE = "new_lang";
	public static final String LANGUAGE = "lang";

	// Attribute name for menu
	public static final String PAGE = "page";
	public static final String MENU = "menu";
	public static final String DRINK_ID = "drink_id";
	public static final String DRINK = "drink";
	public static final String PAGES_AMOUNT = "pages_amount";
	public static final String DRINK_INGREDIENTS = "drink_ingredients";
	public static final String INGREDIENTS = "ingredients";

	// Attribute name for product
	public static final String IMAGE_PATH = "imagePath";
	public static final String IMAGE = "image";
	public static final String PRICE = "price";
	public static final String DESCRIPTION = "description";
	public static final String INGREDIENT = "ingredient";
	public static final String INGREDIENT_AMOUNT = "ingredientAmount";
	public static final String OPTIONAL = "optional";
	public static final String INGREDIENT_ERRORS = "ingredientErrors";
	public static final String SUCCESS_DRINK_UPDATE = "successDrinkUpdate";
	public static final String SUCCESS_INGREDIENTS_UPDATE = "successIngredientsUpdate";

	// Basket
	public static final String BASKET = "basket";
	public static final String DRINK_AMOUNT = "drinkAmount";

	// Order
	public static final String UNAVAILABLE_INGREDIENT = "unavailableIngredient";
	public static final String ORDER = "order";
	public static final String ORDER_ID = "orderId";
	public static final String FAILED_PAYMENT = "failedPayment";
	public static final String FAILED_ORDER_CANCEL = "failedOrderCancel";

	private AttributeName() {

	}
}
