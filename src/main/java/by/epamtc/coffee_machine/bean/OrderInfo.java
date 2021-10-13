/**
 * 
 */
package by.epamtc.coffee_machine.bean;

import java.time.LocalDateTime;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class OrderInfo {
	private LocalDateTime date_time;
	private OrderStatus status;
	private int cost;
	
	public enum OrderStatus {
		FORMED, PAID, READY
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderInfo [date_time=");
		builder.append(date_time);
		builder.append(", status=");
		builder.append(status);
		builder.append(", cost=");
		builder.append(cost);
		builder.append("]");
		return builder.toString();
	}
	
	
}
