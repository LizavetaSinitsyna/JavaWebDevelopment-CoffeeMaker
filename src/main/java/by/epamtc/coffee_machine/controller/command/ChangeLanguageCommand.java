package by.epamtc.coffee_machine.controller.command;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epamtc.coffee_machine.controller.AttributeName;

public class ChangeLanguageCommand implements Command {
	private static final Logger LOG = LogManager.getLogger(ChangeLanguageCommand.class.getName());
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			String language = request.getParameter(AttributeName.NEW_LANGUAGE);
			String path = request.getParameter(AttributeName.CURRENT_PAGE);
			Cookie cookie = new Cookie(AttributeName.LANGUAGE, language);
			response.addCookie(cookie);
			response.sendRedirect(request.getContextPath() + path);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}

	}

}
