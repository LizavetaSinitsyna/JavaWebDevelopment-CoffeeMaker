package by.epamtc.coffee_machine.service.validation;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import by.epamtc.coffee_machine.dao.DAOException;
import by.epamtc.coffee_machine.dao.DrinkDAO;
import by.epamtc.coffee_machine.service.DrinkMessage;
import by.epamtc.coffee_machine.service.ServiceException;

public class DrinkValidatorTest {
	private DrinkDAO drinkDAO;

	@Before
	public void initMock() {
		drinkDAO = Mockito.mock(DrinkDAO.class);
	}

	@Test
	public void testCheckImageWithEmptyImage() {
		String imageName = "";
		Assert.assertTrue(DrinkValidator.checkImage(imageName));
	}

	@Test
	public void testCheckImageWithNullImage() {
		String imageName = "";
		Assert.assertTrue(DrinkValidator.checkImage(imageName));
	}

	@Test
	public void testCheckImageWithInvalidExtension() {
		String imageName = "image.pdf";
		Assert.assertFalse(DrinkValidator.checkImage(imageName));
	}

	@Test
	public void testCheckImageWithValidNotEmptyImage() {
		String imageName = "image.png";
		Assert.assertTrue(DrinkValidator.checkImage(imageName));
	}

	@Test
	public void testCheckPriceWithNullPrice() {
		BigDecimal price = null;
		Assert.assertFalse(DrinkValidator.checkPrice(price));
	}

	@Test
	public void testCheckPriceWithNegativePrice() {
		BigDecimal price = new BigDecimal("-5");
		Assert.assertFalse(DrinkValidator.checkPrice(price));
	}

	@Test
	public void testCheckPriceWithInvalidLargePrice() {
		BigDecimal price = new BigDecimal("150");
		Assert.assertFalse(DrinkValidator.checkPrice(price));
	}

	@Test
	public void testCheckPriceWithValidPrice() {
		BigDecimal price = new BigDecimal("10");
		Assert.assertTrue(DrinkValidator.checkPrice(price));
	}

	@Test
	public void testCheckDescriptionWithNullDescription() {
		String description = null;
		Assert.assertTrue(DrinkValidator.checkDescription(description));
	}

	@Test
	public void testCheckDescriptionWithEmptyDescription() {
		String description = "";
		Assert.assertTrue(DrinkValidator.checkDescription(description));
	}

	@Test
	public void testCheckDescriptionWithValidDescription() {
		String description = "tasty";
		Assert.assertTrue(DrinkValidator.checkDescription(description));
	}

	@Test
	public void testCheckNameWithValidName() {
		String name = "cappuccino";
		Assert.assertTrue(DrinkValidator.checkName(name));
	}

	@Test
	public void testCheckNameWithNullName() {
		String name = null;
		Assert.assertFalse(DrinkValidator.checkName(name));
	}

	@Test
	public void testCheckNameWithEmptyName() {
		String name = "";
		Assert.assertFalse(DrinkValidator.checkName(name));
	}

	@Test
	public void testCheckNameWithBlankName() {
		String name = " ";
		Assert.assertFalse(DrinkValidator.checkName(name));
	}

	@Test
	public void testCheckDublicatedNameWithDublicatedName() throws DAOException, ServiceException {
		String name = "cappuccino";
		Mockito.when(drinkDAO.containsDrinkName(Mockito.any())).thenReturn(true);
		Assert.assertTrue(DrinkValidator.checkDublicatedName(drinkDAO, name));
	}

	@Test
	public void testCheckDublicatedNameWithUniqueName() throws DAOException, ServiceException {
		String name = "cappuccino";
		Mockito.when(drinkDAO.containsDrinkName(Mockito.any())).thenReturn(false);
		Assert.assertFalse(DrinkValidator.checkDublicatedName(drinkDAO, name));
	}

	@Test(expected = ServiceException.class)
	public void testCheckDublicatedNameWithDAOException() throws DAOException, ServiceException {
		String name = "cappuccino";
		Mockito.when(drinkDAO.containsDrinkName(Mockito.any())).thenThrow(new DAOException());
		DrinkValidator.checkDublicatedName(drinkDAO, name);
	}

	@Test
	public void testValidateAllFieldsWithValidParameters() throws ServiceException {
		Set<DrinkMessage> expected = new HashSet<>();
		String drinkName = "cappuccino";
		String imageName = "image.png";
		BigDecimal price = new BigDecimal("5");
		String description = null;
		Set<DrinkMessage> actual = DrinkValidator.validateAllFields(drinkDAO, drinkName, imageName, price, description);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testValidateAllFieldsWithInvalidNameAndPrice() throws ServiceException {
		Set<DrinkMessage> expected = new HashSet<>();
		expected.add(DrinkMessage.ILLEGAL_DRINK_NAME);
		expected.add(DrinkMessage.ILLEGAL_PRICE);
		String drinkName = "";
		String imageName = "image.png";
		BigDecimal price = new BigDecimal("-5");
		String description = null;
		Set<DrinkMessage> actual = DrinkValidator.validateAllFields(drinkDAO, drinkName, imageName, price, description);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testValidateEditableFieldsWithInvalidImageExtensionAndLargeDescription() {
		Set<DrinkMessage> expected = new HashSet<>();
		expected.add(DrinkMessage.ILLEGAL_IMAGE_PATH);
		expected.add(DrinkMessage.ILLEGAL_DESCRIPTION);
		String imageName = "image.pdf";
		BigDecimal price = new BigDecimal("5");
		String descriptionExceedingMaxLength = "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
		Set<DrinkMessage> actual = DrinkValidator.validateEditableFields(imageName, price,
				descriptionExceedingMaxLength);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testValidateEditableFieldsWithValidFields() {
		Set<DrinkMessage> expected = new HashSet<>();
		String imageName = "image.png";
		BigDecimal price = new BigDecimal("5");
		String description = null;
		Set<DrinkMessage> actual = DrinkValidator.validateEditableFields(imageName, price, description);
		Assert.assertEquals(expected, actual);
	}

}
