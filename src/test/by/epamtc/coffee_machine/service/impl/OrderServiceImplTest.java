package by.epamtc.coffee_machine.service.impl;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.internal.WhiteboxImpl;

import by.epamtc.coffee_machine.bean.Drink;
import by.epamtc.coffee_machine.bean.DrinkInfo;
import by.epamtc.coffee_machine.bean.Ingredient;
import by.epamtc.coffee_machine.bean.Order;
import by.epamtc.coffee_machine.bean.OrderDrink;
import by.epamtc.coffee_machine.bean.OrderInfo;
import by.epamtc.coffee_machine.bean.OrderStatus;
import by.epamtc.coffee_machine.bean.transfer.DrinkIngredientTransfer;
import by.epamtc.coffee_machine.bean.transfer.DrinkTransfer;
import by.epamtc.coffee_machine.bean.transfer.OrderTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DrinkDAO;
import by.epamtc.coffee_machine.dao.DrinkIngredientDAO;
import by.epamtc.coffee_machine.dao.IngredientDAO;
import by.epamtc.coffee_machine.dao.OrderDAO;
import by.epamtc.coffee_machine.dao.OrderDrinkDAO;
import by.epamtc.coffee_machine.service.OrderService;
import by.epamtc.coffee_machine.service.ServiceException;

public class OrderServiceImplTest {
	private static OrderDAO orderDao;
	private static DrinkDAO drinkDao;
	private static IngredientDAO ingredientDao;
	private static OrderService orderService;
	private static Drink drink;
	private static OrderTransfer orderTransfer;
	private static List<DrinkIngredientTransfer> drinkIngredients;
	private static DrinkIngredientDAO drinkIngredientDao;
	private static Ingredient ingredient;
	private static List<DrinkIngredientTransfer> drinkIngredientsTransfer;
	private static OrderDrinkDAO orderDrinkDao;
	private static List<DrinkTransfer> drinks;
	private static Order order;

	@BeforeClass
	public static void init() {
		long drinkId = 1;
		long orderId = 1;
		long userId = 1;
		int drinkAmount = 1;
		String imagePath = "cappuccino.png";
		String name = "Cappuccino";
		BigDecimal price = new BigDecimal("5.0");

		drink = new Drink();
		DrinkInfo drinkInfo = new DrinkInfo();
		drinkInfo.setDescription("milk coffee drink");
		drinkInfo.setImagePath(imagePath);
		drinkInfo.setName(name);
		drinkInfo.setPrice(price);

		drink.setId(drinkId);
		drink.setInfo(drinkInfo);

		DrinkTransfer drinkTransfer = new DrinkTransfer();
		drinkTransfer.setId(drinkId);
		drinkTransfer.setImagePath(imagePath);
		drinkTransfer.setName(name);
		drinkTransfer.setPrice(price);

		order = new Order();
		order.setOrderId(orderId);
		order.setUserId(userId);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCost(price);
		orderInfo.setStatus(OrderStatus.CREATED);
		order.setInfo(orderInfo);

		OrderDrink orderDrink = new OrderDrink();
		orderDrink.addDrink(drinkTransfer, drinkAmount);

		orderTransfer = new OrderTransfer();
		orderTransfer.setOrder(order);
		orderTransfer.setOrderDrink(orderDrink);

		DrinkIngredientTransfer drinkIngredientTransfer = new DrinkIngredientTransfer();
		drinkIngredients = new ArrayList<>();
		drinkIngredients.add(drinkIngredientTransfer);

		ingredient = new Ingredient();
		orderService = new OrderServiceImpl();

		DrinkIngredientTransfer drinkIngredientTransfer1 = new DrinkIngredientTransfer();
		DrinkIngredientTransfer drinkIngredientTransfer2 = new DrinkIngredientTransfer();
		drinkIngredientsTransfer = new ArrayList<>();
		drinkIngredientsTransfer.add(drinkIngredientTransfer1);
		drinkIngredientsTransfer.add(drinkIngredientTransfer2);
	}

	@Before
	public void initMock() {
		orderDao = Mockito.mock(OrderDAO.class);
		drinkDao = Mockito.mock(DrinkDAO.class);
		ingredientDao = Mockito.mock(IngredientDAO.class);
		drinkIngredientDao = Mockito.mock(DrinkIngredientDAO.class);
		orderDrinkDao = Mockito.mock(OrderDrinkDAO.class);
		WhiteboxImpl.setInternalState(orderService, "orderDao", orderDao);
		WhiteboxImpl.setInternalState(orderService, "drinkDao", drinkDao);
		WhiteboxImpl.setInternalState(orderService, "ingredientDao", ingredientDao);
		WhiteboxImpl.setInternalState(orderService, "drinkIngredientDao", drinkIngredientDao);
		WhiteboxImpl.setInternalState(orderService, "orderDrinkDao", orderDrinkDao);
	}

	@Test
	public void testPlaceOrder() throws DAOException, ServiceException {
		long orderId = 1;
		long userId = 1;
		String[] drinkId = { "1" };
		String[] drinksAmount = { "1" };

		Mockito.when(drinkDao.read(Mockito.anyLong())).thenReturn(drink);
		Mockito.when(orderDao.add(Mockito.any())).thenReturn(orderId);
		Mockito.when(ingredientDao.readAvailable(Mockito.anyLong())).thenReturn(ingredient);
		Mockito.when(drinkIngredientDao.readIngredientsForSpecificDrink(Mockito.anyLong()))
				.thenReturn(drinkIngredients);
		OrderTransfer actual = orderService.placeOrder(drinkId, drinksAmount, userId);
		Assert.assertEquals(orderTransfer.getOrderDrink(), actual.getOrderDrink());
		Assert.assertEquals(orderTransfer.getUnavailableIngredient(), actual.getUnavailableIngredient());
		Assert.assertEquals(orderTransfer.getOrder().getOrderId(), actual.getOrder().getOrderId());
		Assert.assertEquals(orderTransfer.getOrder().getUserId(), actual.getOrder().getUserId());
		Assert.assertEquals(orderTransfer.getOrder().getInfo().getCost(), actual.getOrder().getInfo().getCost());
		Assert.assertEquals(orderTransfer.getOrder().getInfo().getStatus(), actual.getOrder().getInfo().getStatus());
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
	public void testRemoveUnpaidOrders() {
		fail("Not yet implemented");
	}

	@Test
	public void testRead() throws DAOException, ServiceException {
		long orderId = 1;
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
	public void testPay() {
		long orderId = 1;
		long userId = 1;
		String[] drinkId = { "1" };
		String[] drinksAmount = { "1" };

		Mockito.when(drinkDao.read(Mockito.anyLong())).thenReturn(drink);
		Mockito.when(orderDao.add(Mockito.any())).thenReturn(orderId);
		Mockito.when(ingredientDao.readAvailable(Mockito.anyLong())).thenReturn(ingredient);
		Mockito.when(drinkIngredientDao.readIngredientsForSpecificDrink(Mockito.anyLong()))
				.thenReturn(drinkIngredients);
		OrderTransfer actual = orderService.placeOrder(drinkId, drinksAmount, userId);
		Assert.assertEquals(orderTransfer.getOrderDrink(), actual.getOrderDrink());
		Assert.assertEquals(orderTransfer.getUnavailableIngredient(), actual.getUnavailableIngredient());
		Assert.assertEquals(orderTransfer.getOrder().getOrderId(), actual.getOrder().getOrderId());
		Assert.assertEquals(orderTransfer.getOrder().getUserId(), actual.getOrder().getUserId());
		Assert.assertEquals(orderTransfer.getOrder().getInfo().getCost(), actual.getOrder().getInfo().getCost());
		Assert.assertEquals(orderTransfer.getOrder().getInfo().getStatus(), actual.getOrder().getInfo().getStatus());
	}

	@Test
	public void testCancel() {
		fail("Not yet implemented");
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
