package by.epamtc.coffee_machine.service;

import java.util.List;

import by.epamtc.coffee_machine.bean.transfer.IngredientTransfer;

public interface IngredientService {
	List<IngredientTransfer> obtainAllIngredients() throws ServiceException;
}
