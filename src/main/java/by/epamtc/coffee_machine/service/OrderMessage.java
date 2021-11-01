package by.epamtc.coffee_machine.service;

public enum OrderMessage {
	NO_SUCH_ORDER("no_such_order"), NOT_ENOUGH_MONEY("not_enough_money");

	private String value;

	OrderMessage(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
