/**
 * 
 */
package by.epamtc.coffee_machine.bean.transfer;

import java.io.Serializable;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class UserLoginTransfer implements Serializable {
	private static final long serialVersionUID = 1L;

	private int roleId;
	private int id;

	public UserLoginTransfer() {
	}

	/**
	 * @return the roleId
	 */
	public int getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + roleId;
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
		UserLoginTransfer other = (UserLoginTransfer) obj;
		if (id != other.id)
			return false;
		if (roleId != other.roleId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserLoginTransfer [roleId=" + roleId + ", id=" + id + "]";
	}

}
