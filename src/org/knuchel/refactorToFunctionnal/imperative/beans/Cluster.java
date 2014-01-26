package org.knuchel.refactorToFunctionnal.imperative.beans;

public class Cluster implements DataVector {
	private String name;
	private Double sepalLength;
	private Double sepalWidth;
	private Double petalLength;
	private Double petalWidth;

	public Cluster(String name, Double sepalLength, Double sepalWidth, Double petalLength, Double petalWidth) {
		this.sepalLength = sepalLength;
		this.sepalWidth = sepalWidth;
		this.petalLength = petalLength;
		this.petalWidth = petalWidth;
		this.name = name;
	}

	public static Cluster getRandom(String name) {
		// generate a random cluster in dataset range
		Double sepalLength = Math.random() * 7.9;
		Double sepalWidth = Math.random() * 4.4;
		Double petalLength = Math.random() * 6.9;
		Double petalWidth = Math.random() * 2.5;
		return new Cluster(name, sepalLength, sepalWidth, petalLength, petalWidth);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSepalLength() {
		return sepalLength;
	}

	public void setSepalLength(Double sepalLength) {
		this.sepalLength = sepalLength;
	}

	public Double getSepalWidth() {
		return sepalWidth;
	}

	public void setSepalWidth(Double sepalWidth) {
		this.sepalWidth = sepalWidth;
	}

	public Double getPetalLength() {
		return petalLength;
	}

	public void setPetalLength(Double petalLength) {
		this.petalLength = petalLength;
	}

	public Double getPetalWidth() {
		return petalWidth;
	}

	public void setPetalWidth(Double petalWidth) {
		this.petalWidth = petalWidth;
	}

	@Override
	public String toString() {
		return "Cluster " + name + " (" + sepalLength + ", " + sepalWidth + ", " + petalLength + ", " + petalWidth + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((petalLength == null) ? 0 : petalLength.hashCode());
		result = prime * result + ((petalWidth == null) ? 0 : petalWidth.hashCode());
		result = prime * result + ((sepalLength == null) ? 0 : sepalLength.hashCode());
		result = prime * result + ((sepalWidth == null) ? 0 : sepalWidth.hashCode());
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
		Cluster other = (Cluster) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (petalLength == null) {
			if (other.petalLength != null)
				return false;
		} else if (!petalLength.equals(other.petalLength))
			return false;
		if (petalWidth == null) {
			if (other.petalWidth != null)
				return false;
		} else if (!petalWidth.equals(other.petalWidth))
			return false;
		if (sepalLength == null) {
			if (other.sepalLength != null)
				return false;
		} else if (!sepalLength.equals(other.sepalLength))
			return false;
		if (sepalWidth == null) {
			if (other.sepalWidth != null)
				return false;
		} else if (!sepalWidth.equals(other.sepalWidth))
			return false;
		return true;
	}
}
