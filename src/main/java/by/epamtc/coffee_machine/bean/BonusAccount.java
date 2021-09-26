/**
 * 
 */
package by.epamtc.coffee_machine.bean;

import java.io.Serializable;

/**
 * @author Dell
 *
 */
public class BonusAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private int balance;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + balance;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BonusAccount other = (BonusAccount) obj;
		if (balance != other.balance)
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", balance=" + balance + "]";
	}

}
