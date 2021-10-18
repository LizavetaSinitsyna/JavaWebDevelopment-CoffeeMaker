package by.epamtc.coffee_machine.bean;

import java.io.Serializable;

public class Ingredient implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private int currentAmount;
	private IngredientInfo info;

	public Ingredient() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(int currentAmount) {
		this.currentAmount = currentAmount;
	}

	public IngredientInfo getInfo() {
		return info == null ? info : infoCopy(info);
	}

	public void setInfo(IngredientInfo info) {
		if (info != null) {
			this.info = infoCopy(info);
		}
	}

	private IngredientInfo infoCopy(IngredientInfo info) {
		IngredientInfo copy = new IngredientInfo();
		copy.setName(info.getName());
		copy.setImagePath(info.getImagePath());
		return copy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentAmount;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Ingredient other = (Ingredient) obj;
		if (currentAmount != other.currentAmount)
			return false;
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
		StringBuilder builder = new StringBuilder();
		builder.append("Ingredient [id=");
		builder.append(id);
		builder.append(", currentAmount=");
		builder.append(currentAmount);
		builder.append(", info=");
		builder.append(info);
		builder.append("]");
		return builder.toString();
	}

}
