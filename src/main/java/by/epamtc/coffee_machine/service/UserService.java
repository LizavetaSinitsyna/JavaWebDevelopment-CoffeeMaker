package by.epamtc.coffee_machine.service;

import java.util.Set;

import by.epamtc.coffee_machine.bean.transfer.UserLoginTransfer;

public interface UserService {
	Set<UserValidationError> registration(String email, String password, String repeatPassword, String username, String name,
			String phone) throws ServiceException;

	UserLoginTransfer login(String login, String password) throws ServiceException;

}
