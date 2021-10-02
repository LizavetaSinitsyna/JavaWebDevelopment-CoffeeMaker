/**
 * 
 */
package by.epamtc.coffee_machine.bean;

import java.io.Serializable;

import by.epamtc.coffee_machine.validation.ValidationHelper;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class Drink implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private DrinkInfo info;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DrinkInfo getInfo() {
		return ValidationHelper.isNull(info) ? info : infoCopy(info);
	}

	public void setInfo(DrinkInfo info) {
		if (!ValidationHelper.isNull(info)) {
			this.info = infoCopy(info);
		}
	}

	private DrinkInfo infoCopy(DrinkInfo info) {
		DrinkInfo copy = new DrinkInfo();
		copy.setName(info.getName());
		copy.setImagePath(info.getImagePath());
		copy.setPrice(info.getPrice());
		copy.setDescription(info.getDescription());
		return copy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((info == null) ? 0 : info.hashCode());
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
		Drink other = (Drink) obj;
		if (id != other.id)
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", info=" + info + "]";
	}

}
