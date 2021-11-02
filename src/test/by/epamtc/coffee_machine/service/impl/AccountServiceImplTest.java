package by.epamtc.coffee_machine.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.internal.WhiteboxImpl;

import by.epamtc.coffee_machine.bean.Account;
import by.epamtc.coffee_machine.dao.AccountDAO;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.service.AccountService;
import by.epamtc.coffee_machine.service.ServiceException;

public class AccountServiceImplTest {
	private AccountDAO accountDAO;
	private static AccountService accountService = new AccountServiceImpl();

	@BeforeClass
	public static void init() {
		accountService = new AccountServiceImpl();
	}

	@Before
	public void initMock() {
		accountDAO = Mockito.mock(AccountDAO.class);
		WhiteboxImpl.setInternalState(accountService, "accountDAO", accountDAO);
	}

	@Test
	public void testCreateAccount() throws ServiceException, DAOException {
		Account account = new Account();
		long accountId = 1;
		Mockito.when(accountDAO.add(account)).thenReturn(accountId);
		Account expected = new Account();
		expected.setId(accountId);
		Account actual = accountService.createAccount();
		Assert.assertEquals(expected, actual);
	}

	@Test(expected = ServiceException.class)
	public void testCreateAccountWithDAOException() throws ServiceException, DAOException {
		Account account = new Account();
		Mockito.when(accountDAO.add(account)).thenThrow(new DAOException());
		accountService.createAccount();
	}

	@Test
	public void testObtainAccountByUserIdWithValidId() throws ServiceException, DAOException {
		long userId = 1;
		Account account = new Account();
		Mockito.when(accountDAO.readByUserId(Mockito.anyLong())).thenReturn(account);
		Account actual = accountService.obtainAccountByUserId(userId);
		Account expected = account;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testObtainAccountByUserIdWithInvalidId() throws ServiceException {
		long userId = 0;
		Account actual = accountService.obtainAccountByUserId(userId);
		Assert.assertNull(actual);
	}

	@Test(expected = ServiceException.class)
	public void testObtainAccountByUserIdWithDAOException() throws ServiceException, DAOException {
		long userId = 1;
		Mockito.when(accountDAO.readByUserId(Mockito.anyLong())).thenThrow(new DAOException());
		accountService.obtainAccountByUserId(userId);

	}

}
