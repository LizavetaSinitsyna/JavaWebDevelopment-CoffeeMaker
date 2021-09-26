/**
 * 
 */
package by.epamtc.coffee_machine.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public interface Command {
	void execute(HttpServletRequest request, HttpServletResponse response);

}
