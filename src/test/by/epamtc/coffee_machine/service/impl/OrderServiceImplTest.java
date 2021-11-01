package by.epamtc.coffee_machine.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.internal.WhiteboxImpl;

import by.epamtc.coffee_machine.bean.Account;
import by.epamtc.coffee_machine.bean.BonusAccount;
import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.DrinkInfo;
import by.epamtc.coffee_machine.bean.Ingredient;
import by.epamtc.coffee_machine.bean.IngredientInfo;
import by.epamtc.coffee_machine.bean.Order;
import by.epamtc.coffee_machine.bean.OrderDrink;
import by.epamtc.coffee_machine.bean.OrderInfo;
import by.epamtc.coffee_machine.bean.OrderStatus;
import by.epamtc.coffee_machine.bean.transfer.DrinkIngredientTransfer;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;
import by.epamtc.coffee_machine.bean.transfer.OrderTransfer;
import by.epamtc.coffee_machine.bean.transfer.UnavailableIngredientTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DrinkDAO;
import by.epamtc.coffee_machine.dao.DrinkIngredientDAO;
import by.epamtc.coffee_machine.dao.IngredientDAO;
import by.epamtc.coffee_machine.dao.OrderDAO;
import by.epamtc.coffee_machine.dao.OrderDrinkDAO;
import by.epamtc.coffee_machine.service.AccountService;
import by.epamtc.coffee_machine.service.BonusAccountService;
import by.epamtc.coffee_machine.service.OrderService;
import by.epamtc.coffee_machine.service.ServiceException;

public class OrderServiceImplTest {
	private OrderDAO orderDao;
	private DrinkDAO drinkDao;
	private IngredientDAO ingredientDao;
	private static OrderService orderService;
	private static Drink drink;
	private static DrinkIngredientDAO drinkIngredientDao;
	private static List<DrinkIngredientTransfer> drinkIngredientsTransfer;
	private OrderDrinkDAO orderDrinkDao;
	private static List<DrinkTransfer> drinks;
	private AccountService accountService;
	private BonusAccountService bonusAccountService;
	private OrderDrink orderDrink;
	private static Account account;
	private static BonusAccount bonusAccount;
	private static long drinkId = 1;
	private static String imagePath = "cappuccino.png";
	private static String name = "Cappuccino";
	private static BigDecimal price = new BigDecimal("5.0");

	@BeforeClass
	public static void init() {
		drink = new Drink();
		DrinkInfo drinkInfo = new DrinkInfo();
		drinkInfo.setDescription("milk coffee drink");
		drinkInfo.setImagePath(imagePath);
		drinkInfo.setName(name);
		drinkInfo.setPrice(price);

		drink.setId(drinkId);
		drink.setInfo(drinkInfo);

		orderService = new OrderServiceImpl();

		DrinkIngredientTransfer drinkIngredientTransfer1 = new DrinkIngredientTransfer();
		DrinkIngredientTransfer drinkIngredientTransfer2 = new DrinkIngredientTransfer();
		drinkIngredientsTransfer = new ArrayList<>();
		drinkIngredientsTransfer.add(drinkIngredientTransfer1);
		drinkIngredientsTransfer.add(drinkIngredientTransfer2);

		account = new Account();
		account.setBalance(price);
		bonusAccount = new BonusAccount();
		bonusAccount.setBalance(price);
	}

	@Before
	public void initMock() {
		orderDao = Mockito.mock(OrderDAO.class);
		drinkDao = Mockito.mock(DrinkDAO.class);
		ingredientDao = Mockito.mock(IngredientDAO.class);
		drinkIngredientDao = Mockito.mock(DrinkIngredientDAO.class);
		orderDrinkDao = Mockito.mock(OrderDrinkDAO.class);
		accountService = Mockito.mock(AccountService.class);
		bonusAccountService = Mockito.mock(BonusAccountService.class);
		WhiteboxImpl.setInternalState(orderService, "orderDao", orderDao);
		WhiteboxImpl.setInternalState(orderService, "drinkDao", drinkDao);
		WhiteboxImpl.setInternalState(orderService, "ingredientDao", ingredientDao);
		WhiteboxImpl.setInternalState(orderService, "drinkIngredientDao", drinkIngredientDao);
		WhiteboxImpl.setInternalState(orderService, "orderDrinkDao", orderDrinkDao);
		WhiteboxImpl.setInternalState(orderService, "accountService", accountService);
		WhiteboxImpl.setInternalState(orderService, "bonusAccountService", bonusAccountService);
	}

	@Before
	public void createResultEntities() {
		long drinkId = 1;
		int drinkAmount = 1;
		String imagePath = "cappuccino.png";
		String name = "Cappuccino";
		BigDecimal price = new BigDecimal("5.0");

		DrinkTransfer drinkTransfer = new DrinkTransfer();
		drinkTransfer.setId(drinkId);
		drinkTransfer.setImagePath(imagePath);
		drinkTransfer.setName(name);
		drinkTransfer.setPrice(price);

		orderDrink = new OrderDrink();
		orderDrink.addDrink(drinkTransfer, drinkAmount);

	}

	@Test
	public void testPlaceOrder() throws DAOException, ServiceException {
		long orderId = 1;
		long userId = 1;
		String[] drinkId = { "1" };
		String[] drinksAmount = { "1" };

		Order order = new Order();
		order.setOrderId(orderId);
		order.setUserId(userId);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCost(price);
		orderInfo.setStatus(OrderStatus.CREATED);
		order.setInfo(orderInfo);

		Ingredient ingredient = new Ingredient();
		DrinkIngredientTransfer drinkIngredientTransfer = new DrinkIngredientTransfer();
		List<DrinkIngredientTransfer> drinkIngredients = new ArrayList<>();
		drinkIngredients.add(drinkIngredientTransfer);

		OrderTransfer expected = new OrderTransfer();
		expected.setOrder(order);
		expected.setOrderDrink(orderDrink);

		Mockito.when(drinkDao.read(Mockito.anyLong())).thenReturn(drink);
		Mockito.when(orderDao.add(Mockito.any())).thenReturn(orderId);
		Mockito.when(ingredientDao.readAvailable(Mockito.anyLong())).thenReturn(ingredient);
		Mockito.when(drinkIngredientDao.readIngredientsForSpecificDrink(Mockito.anyLong()))
				.thenReturn(drinkIngredients);

		OrderTransfer actual = orderService.placeOrder(drinkId, drinksAmount, userId);
		Assert.assertEquals(expected.getOrderDrink(), actual.getOrderDrink());
		Assert.assertEquals(expected.getUnavailableIngredient(), actual.getUnavailableIngredient());
		Assert.assertEquals(expected.getOrder().getOrderId(), actual.getOrder().getOrderId());
		Assert.assertEquals(expected.getOrder().getUserId(), actual.getOrder().getUserId());
		Assert.assertEquals(expected.getOrder().getInfo().getCost(), actual.getOrder().getInfo().getCost());
		Assert.assertEquals(expected.getOrder().getInfo().getStatus(), actual.getOrder().getInfo().getStatus());
	}

	@Test
	public void testPlaceOrderWithUnavailableIngredient() throws DAOException, ServiceException {
		long orderId = 1;
		long userId = 1;
		String[] drinkId = { "1" };
		String[] drinksAmount = { "1" };

		Order order = new Order();
		order.setOrderId(orderId);
		order.setUserId(userId);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCost(price);
		orderInfo.setStatus(OrderStatus.CREATED);
		order.setInfo(orderInfo);

		Ingredient ingredient = new Ingredient();
		ingredient.setId(1);
		ingredient.setCurrentAmount(15);
		IngredientInfo ingredientInfo = new IngredientInfo();
		ingredientInfo.setName(name);
		ingredient.setInfo(ingredientInfo);

		DrinkIngredientTransfer drinkIngredientTransfer = new DrinkIngredientTransfer();
		drinkIngredientTransfer.setIngredientAmount(20);
		drinkIngredientTransfer.setIngredientId(1);
		List<DrinkIngredientTransfer> drinkIngredients = new ArrayList<>();
		drinkIngredients.add(drinkIngredientTransfer);

		UnavailableIngredientTransfer unavailableIngredientTransfer = new UnavailableIngredientTransfer();
		unavailableIngredientTransfer.setIngredientId(1);
		unavailableIngredientTransfer.setAvailableDrinkAmount(0);
		unavailableIngredientTransfer.setDrinkId(1);
		unavailableIngredientTransfer.setIngredientName(name);

		OrderTransfer expected = new OrderTransfer();
		expected.setOrderDrink(orderDrink);
		expected.setUnavailableIngredient(unavailableIngredientTransfer);

		Mockito.when(drinkDao.read(Mockito.anyLong())).thenReturn(drink);
		Mockito.when(ingredientDao.readAvailable(Mockito.anyLong())).thenReturn(ingredient);
		Mockito.when(drinkIngredientDao.readIngredientsForSpecificDrink(Mockito.anyLong()))
				.thenReturn(drinkIngredients);
		OrderTransfer actual = orderService.placeOrder(drinkId, drinksAmount, userId);
		Assert.assertEquals(expected.getOrderDrink(), actual.getOrderDrink());
		Assert.assertEquals(expected.getUnavailableIngredient(), actual.getUnavailableIngredient());
	}

	@Test(expected = ServiceException.class)
	public void testPlaceOrderWithNullDrinkIdArray() throws DAOException, ServiceException {
		long userId = 1;
		String[] drinkId = null;
		String[] drinksAmount = { "1" };
		orderService.placeOrder(drinkId, drinksAmount, userId);
	}

	@Test(expected = ServiceException.class)
	public void testPlaceOrderWithNullDrinkAmountArray() throws DAOException, ServiceException {
		long userId = 1;
		String[] drinkId = { "1" };
		String[] drinksAmount = null;
		orderService.placeOrder(drinkId, drinksAmount, userId);
	}

	@Test(expected = ServiceException.class)
	public void testPlaceOrderWithNotEqualDrinkAmountAndDrinkIdArray() throws DAOException, ServiceException {
		long userId = 1;
		String[] drinkId = { "1" };
		String[] drinksAmount = { "1", "2" };
		orderService.placeOrder(drinkId, drinksAmount, userId);
	}

	@Test
	public void testRemoveUnpaidOrders() throws DAOException, ServiceException {
		orderService.removeUnpaidOrders();
		Mockito.verify(orderDao, Mockito.times(1)).removeExpiredOrders(Mockito.any(), Mockito.eq(OrderStatus.CREATED));

	}

	@Test
	public void testRead() throws DAOException, ServiceException {
		long orderId = 1;
		long userId = 1;
		Order order = new Order();
		order.setOrderId(orderId);
		order.setUserId(userId);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCost(price);
		orderInfo.setStatus(OrderStatus.CREATED);
		order.setInfo(orderInfo);

		Mockito.when(orderDao.read(orderId)).thenReturn(order);
		Assert.assertEquals(order, orderService.read(orderId));
	}

	@Test
	public void testReadWithInvalidOrderId() throws DAOException, ServiceException {
		long orderId = -1;
		Assert.assertEquals(null, orderService.read(orderId));
	}

	@Test(expected = ServiceException.class)
	public void testReadWithDAOException() throws DAOException, ServiceException {
		long orderId = 1;
		Mockito.when(orderDao.read(orderId)).thenThrow(new DAOException());
		orderService.read(orderId);
	}

	@Test
	public void testPay() throws DAOException, ServiceException {
		long orderId = 1;
		long userId = 1;
		Order order = new Order();
		order.setOrderId(orderId);
		order.setUserId(userId);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCost(price);
		orderInfo.setStatus(OrderStatus.CREATED);
		order.setInfo(orderInfo);

		Mockito.when(accountService.obtainAccountByUserId(Mockito.anyLong())).thenReturn(account);
		Mockito.when(bonusAccountService.obtainAccountByUserId(Mockito.anyLong())).thenReturn(bonusAccount);
		Mockito.when(orderDao.read(orderId)).thenReturn(order);
		Mockito.when(orderDao.pay(Mockito.anyLong(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
		boolean actual = orderService.pay(orderId, userId);
		Assert.assertTrue(actual);
	}

	@Test
	public void testCancel() throws DAOException, ServiceException {
		long orderId = 1;

		Mockito.when(orderDao.delete(Mockito.anyLong())).thenReturn(true);
		boolean actual = orderService.cancel(orderId);
		Assert.assertTrue(actual);
	}

	@Test
	public void testObtainIngredientsForSpecificDrinkPassingValidDrinkId() throws DAOException, ServiceException {
		long drinkId = 1;
		Mockito.when(drinkIngredientDao.readIngredientsForSpecificDrink(drinkId)).thenReturn(drinkIngredientsTransfer);
		Assert.assertEquals(drinkIngredientsTransfer, orderService.obtainIngredientsForSpecificDrink(drinkId));
	}

	@Test
	public void testObtainIngredientsForSpecificDrinkPassingInvalidDrinkId() throws DAOException, ServiceException {
		long drinkId = -1;
		Assert.assertEquals(null, orderService.obtainIngredientsForSpecificDrink(drinkId));
	}

	@Test
	public void testSelectPopularDrinks() throws DAOException, ServiceException {
		Mockito.when(orderDrinkDao.selectPopularDrinks(Mockito.anyInt())).thenReturn(drinks);
		Assert.assertEquals(drinks, orderService.selectPopularDrinks());
	}

	@Test(expected = ServiceException.class)
	public void testSelectPopularDrinksWithDAOException() throws DAOException, ServiceException {
		Mockito.when(orderDrinkDao.selectPopularDrinks(Mockito.anyInt())).thenThrow(new DAOException());
		orderService.selectPopularDrinks();
	}

}
