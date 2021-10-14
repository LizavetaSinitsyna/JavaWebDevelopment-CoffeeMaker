/**
 * 
 */
package by.epamtc.coffee_machine.bean;

import java.io.Serializable;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private long roleId;
	private long bonusAccountId;
	private long accountId;
	private UserInfo info;

	public User() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public long getBonusAccountId() {
		return bonusAccountId;
	}

	public void setBonusAccountId(long bonusAccountId) {
		this.bonusAccountId = bonusAccountId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public UserInfo getInfo() {
		return info;
	}

	public void setInfo(UserInfo info) {
		this.info = info;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (accountId ^ (accountId >>> 32));
		result = prime * result + (int) (bonusAccountId ^ (bonusAccountId >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + (int) (roleId ^ (roleId >>> 32));
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
		User other = (User) obj;
		if (accountId != other.accountId)
			return false;
		if (bonusAccountId != other.bonusAccountId)
			return false;
		if (id != other.id)
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (roleId != other.roleId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", roleId=");
		builder.append(roleId);
		builder.append(", bonusAccountId=");
		builder.append(bonusAccountId);
		builder.append(", accountId=");
		builder.append(accountId);
		builder.append(", info=");
		builder.append(info);
		builder.append("]");
		return builder.toString();
	}

}
