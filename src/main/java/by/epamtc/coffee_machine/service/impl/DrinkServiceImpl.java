package by.epamtc.coffee_machine.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.DrinkInfo;
import by.epamtc.coffee_machine.bean.DrinkIngredient;
import by.epamtc.coffee_machine.bean.DrinkIngredientMap;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;
import by.epamtc.coffee_machine.bean.transfer.DrinkMessageTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DAOProvider;
import by.epamtc.coffee_machine.dao.DrinkDAO;
import by.epamtc.coffee_machine.dao.DrinkIngredientDAO;
import by.epamtc.coffee_machine.service.DrinkService;
import by.epamtc.coffee_machine.service.CommonExceptionMessage;
import by.epamtc.coffee_machine.service.DrinkIngredientMessage;
import by.epamtc.coffee_machine.service.DrinkMessage;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.utility.MenuParameter;
import by.epamtc.coffee_machine.service.utility.MenuPropertyProvider;
import by.epamtc.coffee_machine.service.validation.DrinkValidator;
import by.epamtc.coffee_machine.service.validation.IngredientValidator;

/**
 * Provides access to {@link by.epamtc.coffee_machine.dao.DrinkDAO} and support
 * for working with entities {@link Drink}, {@link DrinkInfo},
 * {@link DrinkTransfer}.
 */
public class DrinkServiceImpl implements DrinkService {
	private final DrinkDAO drinkDao = DAOProvider.getInstance().getDrinkDAO();
	private final DrinkIngredientDAO drinkIngredientDao = DAOProvider.getInstance().getDrinkIngredientDAO();
	private final MenuPropertyProvider menuPropertyProvider = MenuPropertyProvider.getInstance();
	private static final int DEFAULT_PAGE = 1;
	private static final String IMAGE_PATH_FOR_SAVING = "/CoffeeMachine/images/";

	/**
	 * Obtains existed drink by its id.
	 * 
	 * @param drinkId {@code long} value which uniquely indicates the drink.
	 * @return {@code Drink} with specified id or {@code null} if {@code drinkId} is
	 *         invalid.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */
	@Override
	public Drink obtainDrink(long drinkId) throws ServiceException {
		Drink drink = null;
		if (drinkId <= 0) {
			return drink;
		}

		try {
			drink = drinkDao.read(drinkId);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return drink;
	}

	/**
	 * Obtains drinks for showing on specified page.
	 * 
	 * @param pageNumber {@code int} value which represents page number.
	 * @return {@code List} of {@code DrinkTransfer} objects representing drinks for
	 *         the specified page or for the {@code DEFAULT_PAGE} if
	 *         {@code pageNumber} is invalid.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */

	@Override
	public List<DrinkTransfer> obtainMenu(int pageNumber) throws ServiceException {
		List<DrinkTransfer> drinks = null;
		if (pageNumber <= 0) {
			pageNumber = DEFAULT_PAGE;
		}

		int amount = Integer.parseInt(menuPropertyProvider.retrieveValue(MenuParameter.DRINKS_AMOUNT_PER_PAGE));
		int startIndex = 0;
		if (pageNumber > 1) {
			startIndex += (pageNumber - 1) * amount;
		}

		try {
			drinks = drinkDao.obtainDrinks(startIndex, amount);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return drinks;
	}

	/**
	 * Calculates general amount of menu pages using drinks' amount per page
	 * provided by {@link MenuPropertyProvider}.
	 * 
	 * @return {@code int} value representing amount of pages.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer
	 *                          or drinks' amount per page is invalid.
	 */

	@Override
	public int obtainMenuPagesAmount() throws ServiceException {
		int generalDrinksAmount = 0;
		int drinksAmountPerPage = Integer
				.parseInt(menuPropertyProvider.retrieveValue(MenuParameter.DRINKS_AMOUNT_PER_PAGE));
		if (drinksAmountPerPage < 0) {
			throw new ServiceException(CommonExceptionMessage.NEGATIVE_PARAM);
		}
		try {
			generalDrinksAmount = drinkDao.obtainGeneralDrinksAmount();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		int addPage = generalDrinksAmount % drinksAmountPerPage > 0 ? 1 : 0;
		int result = generalDrinksAmount / drinksAmountPerPage + addPage;
		return result;
	}

	/**
	 * Edit existed Drink.
	 * 
	 * @param realPathForImage {@code String} value representing path to image
	 *                         folder.
	 * @param imageContent     {@code InputStream} value representing stream with
	 *                         image content.
	 * @param imageName        {@code String} value representing image name with
	 *                         extension.
	 * @param drinkId          {@code long} value which uniquely indicates the
	 *                         existed drink.
	 * @param price            {@code BigDecimal} value representing new price.
	 * @param description      {@code String} value representing new description.
	 * @return {@code Set} of {@link DrinkMessage} objects.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */

	@Override
	public Set<DrinkMessage> editDrink(String realPathForImage, InputStream imageContent, String imageName, long drinkId,
			BigDecimal price, String description) throws ServiceException {
		Set<DrinkMessage> messages = new HashSet<>();

		if (drinkId <= 0) {
			messages.add(DrinkMessage.INVALID_DRINK_ID);
			return messages;
		}

		messages = DrinkValidator.validateEditableFields(imageName, price, description);

		if (messages == null || messages.isEmpty()) {
			Drink drink = new Drink();
			DrinkInfo info = new DrinkInfo();
			info.setDescription(description);
			String imagePath = saveImage(realPathForImage, imageContent, imageName);
			info.setImagePath(imagePath);
			info.setPrice(price);
			drink.setId(drinkId);
			drink.setInfo(info);
			try {
				boolean result = drinkDao.update(drink);
				if (!result) {
					messages.add(DrinkMessage.UNABLE_EDIT);
				}
			} catch (DAOException e) {
				throw new ServiceException(e.getMessage(), e);
			}
		}

		return messages;
	}

	/**
	 * Creates drink with passed parameters.
	 * 
	 * @param realPathForImage {@code String} value representing path to image
	 *                         folder.
	 * @param imageContent     {@code InputStream} value representing stream with
	 *                         image content.
	 * @param imageName        {@code String} value representing image name with
	 *                         extension.
	 * @param drinkName        {@code String} value representing drink name.
	 * @param price            {@code BigDecimal} value representing new price.
	 * @param description      {@code String} value representing new description.
	 *                         drinkIngredients {@code List} of
	 * @param drinkIngredients {@code DrinkIngredient} objects which represent drink
	 *                         composition.
	 * @return {@code DrinkMessageTransfer} object.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */
	@Override
	public DrinkMessageTransfer add(String realPathForImage, InputStream imageContent, String imageName,
			String drinkName, BigDecimal price, String description, List<DrinkIngredient> drinkIngredients)
			throws ServiceException {
		DrinkMessageTransfer generalDrinkMessages = new DrinkMessageTransfer();
		Set<DrinkMessage> drinkMessages = DrinkValidator.validateAllFields(drinkDao, drinkName, imageName, price,
				description);
		Set<DrinkIngredientMessage> drinkIngredientMessages = IngredientValidator.validateFields(drinkIngredients);

		if ((drinkMessages == null || drinkMessages.isEmpty())
				&& (drinkIngredientMessages == null || drinkIngredientMessages.isEmpty())) {
			Drink drink = new Drink();
			DrinkInfo info = new DrinkInfo();
			info.setName(drinkName);
			info.setDescription(description);
			String imagePath = saveImage(realPathForImage, imageContent, imageName);
			if (imagePath == null || imagePath.isBlank()) {
				imagePath = MenuPropertyProvider.getInstance().retrieveValue(MenuParameter.DEFAULT_IMAGE_PATH);
			}
			info.setImagePath(imagePath);
			info.setPrice(price);
			drink.setInfo(info);

			try {
				generalDrinkMessages.setDrinkId(drinkDao.add(drink, drinkIngredients));
			} catch (DAOException e) {
				throw new ServiceException(e.getMessage(), e);
			}
		} else {
			generalDrinkMessages.setDrinkIngredientMessages(drinkIngredientMessages);
			generalDrinkMessages.setDrinkMessages(drinkMessages);
		}

		return generalDrinkMessages;
	}

	private String saveImage(String realPathForImage, InputStream imageContent, String imageName)
			throws ServiceException {
		if (realPathForImage == null || imageContent == null || imageName == null || imageName.isBlank()) {
			return null;
		}
		File path = new File(realPathForImage);
		File newImage = new File(path, imageName);
		try {
			Files.copy(imageContent, newImage.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return IMAGE_PATH_FOR_SAVING + imageName;

	}

	/**
	 * Edit ingredients for specified drink.
	 * 
	 * @param drinkId          {@code long} value which uniquely indicates the
	 *                         drink.
	 * @param drinkIngredients {@code List} of {@code DrinkIngredient} objects which
	 *                         represent new drink composition.
	 * @return {@code Set} of {@link DrinkIngredientMessage} objects.
	 * @throws ServiceException If problem occurs during interaction with DAO-layer.
	 */

	@Override
	public Set<DrinkIngredientMessage> editDrinkComposition(long drinkId, List<DrinkIngredient> drinkIngredients)
			throws ServiceException {
		Set<DrinkIngredientMessage> messages = new HashSet<>();

		if (drinkId <= 0) {
			messages.add(DrinkIngredientMessage.INVALID_DRINK_ID);
			return messages;
		}

		messages = IngredientValidator.validateFields(drinkIngredients);

		if (messages == null || messages.isEmpty()) {
			DrinkIngredientMap drinkIngredientMap = new DrinkIngredientMap();
			drinkIngredientMap.setDrinkId(drinkId);
			drinkIngredientMap.setIngredients(drinkIngredients);
			try {
				boolean result = drinkIngredientDao.update(drinkIngredientMap);
				if (!result) {
					messages.add(DrinkIngredientMessage.UNABLE_EDIT);
				}
			} catch (DAOException e) {
				throw new ServiceException(e.getMessage(), e);
			}
		}

		return messages;
	}

}
