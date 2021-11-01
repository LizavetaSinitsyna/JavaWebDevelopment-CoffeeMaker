package by.epamtc.coffee_machine.controller.custom_tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class PaginationTagHandler extends TagSupport {
	private static final long serialVersionUID = 1L;

	private static final int MIN_PAGE_NUMBER = 1;
	private static final String BUTTON = "<button class=\"%s\" type=\"submit\" name=\"page\" value=\"%d\">%d</button>";

	private int current;
	private int total;
	private String generalClass;
	private String currentPageClass;

	public void setCurrent(int current) {
		this.current = current;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setGeneralClass(String generalClass) {
		this.generalClass = generalClass;
	}

	public void setCurrentPageClass(String currentPageClass) {
		this.currentPageClass = currentPageClass;
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		if (total != MIN_PAGE_NUMBER && current <= total) {
			for (int i = MIN_PAGE_NUMBER; i <= total; i++) {
				String btnClass = i == current ? currentPageClass : generalClass;
				try {
					out.write(String.format(BUTTON, btnClass, i, i));
				} catch (IOException e) {
					throw new JspException(e.getMessage(), e);
				}
			}

		}
		return SKIP_BODY;
	}
}
