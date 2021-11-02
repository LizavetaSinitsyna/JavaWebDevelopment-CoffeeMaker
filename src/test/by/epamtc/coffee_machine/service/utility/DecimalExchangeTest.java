package by.epamtc.coffee_machine.service.utility;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class DecimalExchangeTest {

	@Test
	public void testObtainFromInt() {
		int input = 500;
		BigDecimal expected = new BigDecimal("5");
		Assert.assertEquals(expected, DecimalExchange.obtainFromInt(input));
	}

	@Test
	public void testRevertToInt() {
		int expected = 500;
		BigDecimal input = new BigDecimal("5");
		Assert.assertEquals(expected, DecimalExchange.revertToInt(input));
	}

	@Test
	public void testRevertToIntWithNullParameter() {
		int expected = 0;
		BigDecimal input = null;
		Assert.assertEquals(expected, DecimalExchange.revertToInt(input));
	}

}
