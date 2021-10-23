package by.epamtc.coffee_machine.service.utility;

import java.math.BigDecimal;

public class DecimalExchange {
	/**
	 * The constant which is used for conversions of integer and BigDecimal values
	 */
	private static final BigDecimal PRICE_DIVISOR = new BigDecimal(
			MenuPropertyProvider.getInstance().retrieveValue(MenuParameter.DRINK_PRICE_DIVISOR));

	/**
	 * Converts integer value into BigDecimal using PRICE_DIVISOR
	 * 
	 * @param amount an integer value which should be converted
	 * @return BigDecimal value obtained after conversion
	 */

	public static BigDecimal obtainFromInt(int amount) {

		BigDecimal initialAmount = new BigDecimal(amount);
		BigDecimal result = initialAmount.divide(PRICE_DIVISOR);

		return result;

	}

	/**
	 * Converts BigDecimal value into integer value using PRICE_DIVISOR
	 * 
	 * @param amount a BigDecimal value which should be converted
	 * @return integer value obtained after conversion
	 */

	public static int revertToInt(BigDecimal amount) {
		if (amount == null) {
			return 0;
		}
		BigDecimal subResult = amount.multiply(PRICE_DIVISOR);

		return subResult.intValue();

	}
}
