package by.epamtc.coffee_machine.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.internal.WhiteboxImpl;

import by.epamtc.coffee_machine.bean.transfer.IngredientTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.IngredientDAO;
import by.epamtc.coffee_machine.service.IngredientService;
import by.epamtc.coffee_machine.service.ServiceException;

public class IngredientServiceImplTest {
	private static IngredientDAO ingredientDao;
	private static IngredientService ingredientService;
	private static List<IngredientTransfer> ingredients;

	@BeforeClass
	public static void init() {
		ingredientService = new IngredientServiceImpl();
		ingredients = new ArrayList<>();
	}

	@Before
	public void initMock() {
		ingredientDao = Mockito.mock(IngredientDAO.class);
		WhiteboxImpl.setInternalState(ingredientService, "ingredientDao", ingredientDao);
	}

	@Test
	public void testObtainAllIngredients() throws DAOException, ServiceException {
		Mockito.when(ingredientDao.readAll()).thenReturn(ingredients);
		Assert.assertEquals(ingredients, ingredientService.obtainAllIngredients());
	}

	@Test(expected = ServiceException.class)
	public void testObtainAllIngredientsWithDAOException() throws DAOException, ServiceException {
		Mockito.when(ingredientDao.readAll()).thenThrow(new DAOException());
		ingredientService.obtainAllIngredients();
	}

}
