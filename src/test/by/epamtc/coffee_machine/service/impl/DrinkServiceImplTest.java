package by.epamtc.coffee_machine.service.impl;

import org.junit.Assert;
import org.junit.Before;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import org.mockito.Mockito;
import org.powermock.reflect.internal.WhiteboxImpl;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.DrinkInfo;
import by.epamtc.coffee_machine.bean.DrinkIngredient;
import by.epamtc.coffee_machine.bean.transfer.DrinkMessageTransfer;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DrinkDAO;
import by.epamtc.coffee_machine.dao.DrinkIngredientDAO;
import by.epamtc.coffee_machine.service.DrinkIngredientMessage;
import by.epamtc.coffee_machine.service.DrinkMessage;
import by.epamtc.coffee_machine.service.DrinkService;
import by.epamtc.coffee_machine.service.ServiceException;

public class DrinkServiceImplTest {
	private static DrinkDAO drinkDao;
	private static Drink drink;
	private static DrinkIngredientDAO drinkIngredientDao;
	private static List<DrinkTransfer> drinksTransfer;
	private static DrinkService drinkService;
	private static List<DrinkIngredient> drinkIngredients;

	@BeforeClass
	public static void init() {
		drink = new Drink();
		DrinkInfo drinkInfo = new DrinkInfo();
		drinkInfo.setDescription("milk coffee drink");
		drinkInfo.setImagePath("cappuccino.png");
		drinkInfo.setName("Cappuccino");
		drinkInfo.setPrice(new BigDecimal("5.0"));
		long drinId = 1;
		drink.setId(drinId);
		drink.setInfo(drinkInfo);

		drinksTransfer = new ArrayList<>();
		drinksTransfer.add(new DrinkTransfer());
		drinksTransfer.add(new DrinkTransfer());

		DrinkIngredient drinkIngredient1 = new DrinkIngredient();
		drinkIngredient1.setIngredientAmount(20);
		drinkIngredient1.setIngredientId(1);
		drinkIngredient1.setOptional(false);
		DrinkIngredient drinkIngredient2 = new DrinkIngredient();
		drinkIngredient2.setIngredientAmount(100);
		drinkIngredient2.setIngredientId(2);
		drinkIngredient2.setOptional(false);
		drinkIngredients = new ArrayList<>();
		drinkIngredients.add(drinkIngredient1);
		drinkIngredients.add(drinkIngredient2);

		drinkService = new DrinkServiceImpl();
	}

	@Before
	public void initMock() {
		drinkDao = Mockito.mock(DrinkDAO.class);
		drinkIngredientDao = Mockito.mock(DrinkIngredientDAO.class);
		WhiteboxImpl.setInternalState(drinkService, "drinkDao", drinkDao);
		WhiteboxImpl.setInternalState(drinkService, "drinkIngredientDao", drinkIngredientDao);
	}

	@Test
	public void testObtainDrinkPassingValidDrinkId() throws DAOException, ServiceException {
		long drinkId = 1;
		Mockito.when(drinkDao.read(drinkId)).thenReturn(drink);
		Assert.assertEquals(drink, drinkService.obtainDrink(drinkId));
	}

	@Test
	public void testObtainDrinkPassingInvalidDrinkId() throws DAOException, ServiceException {
		long drinkId = -1;
		Assert.assertEquals(null, drinkService.obtainDrink(drinkId));
	}

	@Test(expected = ServiceException.class)
	public void testObtainDrinkWithDAOException() throws DAOException, ServiceException {
		long drinkId = 1;
		Mockito.when(drinkDao.read(drinkId)).thenThrow(new DAOException());
		drinkService.obtainDrink(drinkId);
	}

	@Test
	public void testObtainMenu() throws DAOException, ServiceException {
		Mockito.when(drinkDao.obtainDrinks(Mockito.anyInt(), Mockito.anyInt())).thenReturn(drinksTransfer);
		Assert.assertEquals(drinksTransfer, drinkService.obtainMenu(1));
	}

	@Test
	public void testObtainMenuPassingNegativePage() throws DAOException, ServiceException {
		Mockito.when(drinkDao.obtainDrinks(Mockito.anyInt(), Mockito.anyInt())).thenReturn(drinksTransfer);
		Assert.assertEquals(drinksTransfer, drinkService.obtainMenu(-1));
	}

	@Test
	public void testObtainMenuPassingNonExistedPage() throws DAOException, ServiceException {
		List<DrinkTransfer> drinksTransfer = new ArrayList<>();
		Mockito.when(drinkDao.obtainDrinks(Mockito.anyInt(), Mockito.anyInt())).thenReturn(drinksTransfer);
		Assert.assertEquals(drinksTransfer, drinkService.obtainMenu(10000));
	}

	@Test(expected = ServiceException.class)
	public void testObtainMenuWithDAOException() throws DAOException, ServiceException {
		int pageNumber = 1;
		Mockito.when(drinkDao.obtainDrinks(Mockito.anyInt(), Mockito.anyInt())).thenThrow(new DAOException());
		drinkService.obtainMenu(pageNumber);
	}

	@Test
	public void testObtainMenuPagesAmount() throws DAOException, ServiceException {
		Mockito.when(drinkDao.obtainGeneralDrinksAmount()).thenReturn(10);
		Assert.assertEquals(4, drinkService.obtainMenuPagesAmount());
	}

	@Test(expected = ServiceException.class)
	public void testObtainMenuPagesAmountWithDAOException() throws DAOException, ServiceException {
		Mockito.when(drinkDao.obtainGeneralDrinksAmount()).thenThrow(new DAOException());
		drinkService.obtainMenuPagesAmount();
	}

	@Test
	public void testEdit() throws DAOException, ServiceException {
		long drinkId = 1;
		Set<DrinkMessage> messages = new HashSet<>();
		Mockito.when(drinkDao.update(Mockito.any())).thenReturn(true);
		Assert.assertEquals(messages,
				drinkService.editDrink(null, null, "cappuccino.png", drinkId, new BigDecimal("5.0"), "milk coffee drink"));
	}

	@Test
	public void testEditPassingInvalidDrinkId() throws DAOException, ServiceException {
		long drinkId = -1;
		Set<DrinkMessage> messages = new HashSet<>();
		messages.add(DrinkMessage.INVALID_DRINK_ID);
		Assert.assertEquals(messages,
				drinkService.editDrink(null, null, "cappuccino.png", drinkId, new BigDecimal("5.0"), "milk coffee drink"));
	}

	@Test(expected = ServiceException.class)
	public void testEditWithDAOException() throws DAOException, ServiceException {
		long drinkId = 1;
		Mockito.when(drinkDao.update(Mockito.any())).thenThrow(new DAOException());
		drinkService.editDrink(null, null, "cappuccino.png", drinkId, new BigDecimal("5.0"), "milk coffee drink");
	}

	@Test
	public void testEditWithDaoUnableEdit() throws DAOException, ServiceException {
		long drinkId = 1;
		Set<DrinkMessage> messages = new HashSet<>();
		Mockito.when(drinkDao.update(Mockito.any())).thenReturn(false);
		messages.add(DrinkMessage.UNABLE_EDIT);
		Assert.assertEquals(messages,
				drinkService.editDrink(null, null, "cappuccino.png", drinkId, new BigDecimal("5.0"), "milk coffee drink"));
	}

	@Test
	public void testAdd() throws DAOException, ServiceException {
		DrinkMessageTransfer messages = new DrinkMessageTransfer();
		long drinkId = 1;
		messages.setDrinkId(drinkId);
		Mockito.when(drinkDao.add(Mockito.any(), Mockito.eq(drinkIngredients))).thenReturn(drinkId);
		Mockito.when(drinkDao.containsDrinkName(Mockito.any())).thenReturn(false);
		Assert.assertEquals(messages, drinkService.add(null, null, "cappuccino.png", "Cappuccino",
				new BigDecimal("5.0"), "milk coffee drink", drinkIngredients));
	}

	@Test(expected = ServiceException.class)
	public void testAddWithDAOException() throws DAOException, ServiceException {
		Mockito.when(drinkDao.add(Mockito.any(), Mockito.eq(drinkIngredients))).thenThrow(new DAOException());
		Mockito.when(drinkDao.containsDrinkName(Mockito.any())).thenReturn(false);
		drinkService.add(null, null, "cappuccino.png", "Cappuccino", new BigDecimal("5.0"), "milk coffee drink",
				drinkIngredients);
	}

	@Test
	public void testAddWithDublicatedDrinkName() throws DAOException, ServiceException {
		DrinkMessageTransfer messages = new DrinkMessageTransfer();
		Set<DrinkMessage> drinkMessages = new HashSet<>();
		Set<DrinkIngredientMessage> drinkIngredientMessages = new HashSet<>();
		drinkMessages.add(DrinkMessage.DUBLICATE_DRINK_NAME);
		messages.setDrinkMessages(drinkMessages);
		messages.setDrinkIngredientMessages(drinkIngredientMessages);
		Mockito.when(drinkDao.containsDrinkName(Mockito.any())).thenReturn(true);
		Assert.assertEquals(messages, drinkService.add(null, null, "cappuccino.png", "Cappuccino",
				new BigDecimal("5.0"), "milk coffee drink", drinkIngredients));
	}
	
	@Test
	public void testEditDrinkCompositionPassingValidDrinkId() throws DAOException, ServiceException {
		long drinkId = 1;
		Set<DrinkIngredientMessage> messages = new HashSet<>();
		Mockito.when(drinkIngredientDao.update(Mockito.any())).thenReturn(true);
		Assert.assertEquals(messages, drinkService.editDrinkComposition(drinkId, drinkIngredients));

	}

	@Test
	public void testEditDrinkCompositionPassingInvalidDrinkId() throws DAOException, ServiceException {
		long drinkId = -1;
		Set<DrinkIngredientMessage> messages = new HashSet<>();
		messages.add(DrinkIngredientMessage.INVALID_DRINK_ID);
		Mockito.when(drinkIngredientDao.update(Mockito.any())).thenReturn(true);
		Assert.assertEquals(messages, drinkService.editDrinkComposition(drinkId, drinkIngredients));

	}

	@Test
	public void testEditDrinkCompositionPassingInvalidDrinkIngredients() throws DAOException, ServiceException {
		long drinkId = 1;
		Set<DrinkIngredientMessage> messages = new HashSet<>();
		messages.add(DrinkIngredientMessage.ILLEGAL_DRINK_INGREDIENT_AMOUNT);
		Mockito.when(drinkIngredientDao.update(Mockito.any())).thenReturn(true);
		Assert.assertEquals(messages, drinkService.editDrinkComposition(drinkId, null));

	}

	@Test
	public void testEditDrinkCompositionFailedUpdate() throws DAOException, ServiceException {
		long drinkId = 1;
		Set<DrinkIngredientMessage> messages = new HashSet<>();
		messages.add(DrinkIngredientMessage.UNABLE_EDIT);
		Mockito.when(drinkIngredientDao.update(Mockito.any())).thenReturn(false);
		Assert.assertEquals(messages, drinkService.editDrinkComposition(drinkId, drinkIngredients));

	}
}
