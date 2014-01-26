package org.knuchel.refactorToFunctionnal.imperative.beans;

public class Iris implements DataVector {
	public static final String VERSICOLOR = "Iris-versicolor";
	public static final String VIRGINICA = "Iris-virginica";
	public static final String SETOSA = "Iris-setosa";
	private Double sepalLength;
	private Double sepalWidth;
	private Double petalLength;
	private Double petalWidth;
	private String specie;

	public Iris(Double sepalLength, Double sepalWidth, Double petalLength, Double petalWidth, String specie) {
		this.sepalLength = sepalLength;
		this.sepalWidth = sepalWidth;
		this.petalLength = petalLength;
		this.petalWidth = petalWidth;
		this.specie = specie;
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

	public String getSpecie() {
		return specie;
	}

	public void setSpecie(String specie) {
		this.specie = specie;
	}

	@Override
	public String toString() {
		return "Iris[" + sepalLength + "," + sepalWidth + "," + petalLength + "," + petalWidth + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((petalLength == null) ? 0 : petalLength.hashCode());
		result = prime * result + ((petalWidth == null) ? 0 : petalWidth.hashCode());
		result = prime * result + ((sepalLength == null) ? 0 : sepalLength.hashCode());
		result = prime * result + ((sepalWidth == null) ? 0 : sepalWidth.hashCode());
		result = prime * result + ((specie == null) ? 0 : specie.hashCode());
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
		Iris other = (Iris) obj;
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
		if (specie == null) {
			if (other.specie != null)
				return false;
		} else if (!specie.equals(other.specie))
			return false;
		return true;
	}
}
