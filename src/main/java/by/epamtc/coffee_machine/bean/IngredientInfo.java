/**
 * 
 */
package by.epamtc.coffee_machine.bean;

import java.io.Serializable;

/**
 * @author Lizaveta Sinitsyna
 *
 */
public class IngredientInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String imagePath;

	public IngredientInfo() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		IngredientInfo other = (IngredientInfo) obj;
		if (imagePath == null) {
			if (other.imagePath != null)
				return false;
		} else if (!imagePath.equals(other.imagePath))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IngredientInfo [name=");
		builder.append(name);
		builder.append(", imagePath=");
		builder.append(imagePath);
		builder.append("]");
		return builder.toString();
	}

}
