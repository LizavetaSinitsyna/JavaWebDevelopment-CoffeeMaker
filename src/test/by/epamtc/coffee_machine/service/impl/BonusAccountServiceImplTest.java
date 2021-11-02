package by.epamtc.coffee_machine.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.internal.WhiteboxImpl;

import by.epamtc.coffee_machine.bean.BonusAccount;
import by.epamtc.coffee_machine.dao.BonusAccountDAO;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.service.BonusAccountService;
import by.epamtc.coffee_machine.service.ServiceException;

public class BonusAccountServiceImplTest {

	private BonusAccountDAO accountDAO;
	private static BonusAccountService bonusAccountService = new BonusAccountServiceImpl();

	@BeforeClass
	public static void init() {
		bonusAccountService = new BonusAccountServiceImpl();
	}

	@Before
	public void initMock() {
		accountDAO = Mockito.mock(BonusAccountDAO.class);
		WhiteboxImpl.setInternalState(bonusAccountService, "accountDAO", accountDAO);
	}

	@Test
	public void testCreateAccount() throws ServiceException, DAOException {
		BonusAccount bonusAccount = new BonusAccount();
		long accountId = 1;
		Mockito.when(accountDAO.add(bonusAccount)).thenReturn(accountId);
		BonusAccount expected = new BonusAccount();
		expected.setId(accountId);
		BonusAccount actual = bonusAccountService.createAccount();
		Assert.assertEquals(expected, actual);
	}

	@Test(expected = ServiceException.class)
	public void testCreateAccountWithDAOException() throws ServiceException, DAOException {
		BonusAccount bonusAccount = new BonusAccount();
		Mockito.when(accountDAO.add(bonusAccount)).thenThrow(new DAOException());
		bonusAccountService.createAccount();
	}

	@Test
	public void testObtainAccountByUserIdWithValidId() throws ServiceException, DAOException {
		long userId = 1;
		BonusAccount bonusAccount = new BonusAccount();
		Mockito.when(accountDAO.readByUserId(Mockito.anyLong())).thenReturn(bonusAccount);
		BonusAccount actual = bonusAccountService.obtainAccountByUserId(userId);
		BonusAccount expected = bonusAccount;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testObtainAccountByUserIdWithInvalidId() throws ServiceException {
		long userId = 0;
		BonusAccount actual = bonusAccountService.obtainAccountByUserId(userId);
		Assert.assertNull(actual);
	}

	@Test(expected = ServiceException.class)
	public void testObtainAccountByUserIdWithDAOException() throws ServiceException, DAOException {
		long userId = 1;
		Mockito.when(accountDAO.readByUserId(Mockito.anyLong())).thenThrow(new DAOException());
		bonusAccountService.obtainAccountByUserId(userId);

	}

}
