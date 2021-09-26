/**
 * 
 */
package by.epamtc.coffee_machine.bean;

/**
 * @author Dell
 *
 */
public class User {
	private int id;
	private Role role;
	private BonusAccount bonusAccount;
	private Account account;
	UserInfo info;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public BonusAccount getBonusAccount() {
		return bonusAccount;
	}
	public void setBonusAccount(BonusAccount bonusAccount) {
		this.bonusAccount = bonusAccount;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public UserInfo getInfo() {
		return info;
	}
	public void setInfo(UserInfo info) {
		this.info = info;
	}
	
	
}
