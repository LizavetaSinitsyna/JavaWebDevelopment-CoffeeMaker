package by.epamtc.coffee_machine.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.internal.WhiteboxImpl;

import by.epamtc.coffee_machine.bean.Account;
import by.epamtc.coffee_machine.bean.BonusAccount;
import by.epamtc.coffee_machine.bean.transfer.UserLoginPasswordTransfer;
import by.epamtc.coffee_machine.bean.transfer.UserLoginTransfer;
import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.UserDAO;
import by.epamtc.coffee_machine.service.AccountService;
import by.epamtc.coffee_machine.service.BonusAccountService;
import by.epamtc.coffee_machine.service.ServiceException;
import by.epamtc.coffee_machine.service.UserService;
import by.epamtc.coffee_machine.service.UserValidationError;

public class UserServiceImplTest {
	private UserDAO userDao;
	private static UserService userService = new UserServiceImpl();
	private AccountService accountService;
	private BonusAccountService bonusAccountService;
	private static String password = "Password1!";

	@Before
	public void initMock() {
		userDao = Mockito.mock(UserDAO.class);
		accountService = Mockito.mock(AccountService.class);
		bonusAccountService = Mockito.mock(BonusAccountService.class);
		WhiteboxImpl.setInternalState(userService, "userDao", userDao);
		WhiteboxImpl.setInternalState(userService, "accountService", accountService);
		WhiteboxImpl.setInternalState(userService, "bonusAccountService", bonusAccountService);
	}

	@Test
	public void testLoginWithNullLogin() throws ServiceException {
		UserLoginTransfer actual = userService.login(null, password);
		Assert.assertNull(actual);
	}

	@Test
	public void testLoginWithNullPassword() throws ServiceException {
		String login = "login";
		UserLoginTransfer actual = userService.login(login, null);
		Assert.assertNull(actual);
	}

	@Test(expected = ServiceException.class)
	public void testLoginWithDAOException() throws ServiceException, DAOException {
		String login = "login";
		Mockito.when(userDao.login(Mockito.any())).thenThrow(new DAOException());
		userService.login(login, password);
	}

	@Test
	public void testLoginWithNullUserReturnedByDAO() throws ServiceException, DAOException {
		UserLoginPasswordTransfer userLoginTransfer = null;
		String login = "login";
		Mockito.when(userDao.login(Mockito.any())).thenReturn(userLoginTransfer);
		UserLoginTransfer actual = userService.login(login, password);
		Assert.assertNull(actual);
	}

	@Test
	public void testRegistration() throws ServiceException, DAOException {
		Set<UserValidationError> expected = new HashSet<>();
		long userId = 1;
		Account account = new Account();
		BonusAccount bonusAccount = new BonusAccount();
		String email = "name@mail.ru";
		String login = "login";
		String phone = "+375251112233";
		String name = "TempUser";
		Mockito.when(userDao.add(Mockito.any())).thenReturn(userId);
		Mockito.when(accountService.createAccount()).thenReturn(account);
		Mockito.when(bonusAccountService.createAccount()).thenReturn(bonusAccount);
		Assert.assertEquals(expected, userService.registration(email, password, password, login, name, phone));
	}

	@Test
	public void testRegistrationWithInvalidLogin() throws ServiceException, DAOException {
		Set<UserValidationError> expected = new HashSet<>();
		expected.add(UserValidationError.ILLEGAL_USERNAME);
		long userId = 1;
		Account account = new Account();
		BonusAccount bonusAccount = new BonusAccount();
		String email = "name@mail.ru";
		String login = "name@mail.ru";
		String phone = "+375251112233";
		String password = "Password1!";
		String name = "TempUser";
		Mockito.when(userDao.add(Mockito.any())).thenReturn(userId);
		Mockito.when(accountService.createAccount()).thenReturn(account);
		Mockito.when(bonusAccountService.createAccount()).thenReturn(bonusAccount);
		Assert.assertEquals(expected, userService.registration(email, password, password, login, name, phone));
	}

	@Test(expected = ServiceException.class)
	public void testRegistrationWithNullAccount() throws ServiceException, DAOException {
		long userId = 1;
		Account account = null;
		BonusAccount bonusAccount = new BonusAccount();
		String email = "name@mail.ru";
		String login = "login";
		String phone = "+375251112233";
		String password = "Password1!";
		String name = "TempUser";
		Mockito.when(userDao.add(Mockito.any())).thenReturn(userId);
		Mockito.when(accountService.createAccount()).thenReturn(account);
		Mockito.when(bonusAccountService.createAccount()).thenReturn(bonusAccount);
		userService.registration(email, password, password, login, name, phone);
	}

	@Test(expected = ServiceException.class)
	public void testRegistrationWithNullBonusAccount() throws ServiceException, DAOException {
		long userId = 1;
		Account account = new Account();
		BonusAccount bonusAccount = null;
		String email = "name@mail.ru";
		String login = "login";
		String phone = "+375251112233";
		String password = "Password1!";
		String name = "TempUser";
		Mockito.when(userDao.add(Mockito.any())).thenReturn(userId);
		Mockito.when(accountService.createAccount()).thenReturn(account);
		Mockito.when(bonusAccountService.createAccount()).thenReturn(bonusAccount);
		userService.registration(email, password, password, login, name, phone);
	}

	@Test(expected = ServiceException.class)
	public void testRegistrationWithDAOException() throws ServiceException, DAOException {
		Account account = new Account();
		BonusAccount bonusAccount = new BonusAccount();
		String email = "name@mail.ru";
		String login = "login";
		String phone = "+375251112233";
		String password = "Password1!";
		String name = "TempUser";
		Mockito.when(userDao.add(Mockito.any())).thenThrow(new DAOException());
		Mockito.when(accountService.createAccount()).thenReturn(account);
		Mockito.when(bonusAccountService.createAccount()).thenReturn(bonusAccount);
		userService.registration(email, password, password, login, name, phone);
	}

}
