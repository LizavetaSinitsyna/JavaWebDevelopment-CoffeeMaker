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
}
