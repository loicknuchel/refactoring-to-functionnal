package org.knuchel.refactorToFunctionnal.functionnal.beans

class Iris(sepalLength: Double, sepalWidth: Double, petalLength: Double, petalWidth: Double, specie: String) extends DataVector {
  def getSepalLength() = sepalLength
  def getSepalWidth() = sepalWidth
  def getPetalLength() = petalLength
  def getPetalWidth() = petalWidth
  def getSpecie = specie

  override def toString = "Iris[" + sepalLength + "," + sepalWidth + "," + petalLength + "," + petalWidth + "]"

  override def equals(other: Any): Boolean =
    other match {
      case that: Iris =>
        (that canEqual this) &&
          sepalLength == that.getSepalLength &&
          sepalWidth == that.getSepalWidth &&
          petalLength == that.getPetalLength &&
          petalWidth == that.getPetalWidth &&
          specie == that.getSpecie
      case _ => false
    }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[Iris]

  override def hashCode: Int = 41 * (41 * (41 * (41 * (41 + specie.hashCode) + sepalLength.hashCode) + sepalWidth.hashCode) + petalLength.hashCode) + petalWidth.hashCode
}
