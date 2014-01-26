package org.knuchel.refactorToFunctionnal.talk.beans

class Cluster(name: String, sepalLength: Double, sepalWidth: Double, petalLength: Double, petalWidth: Double) extends DataVector {
  def getName = name
  def getSepalLength = sepalLength
  def getSepalWidth = sepalWidth
  def getPetalLength = petalLength
  def getPetalWidth = petalWidth

  override def toString: String = "Cluster " + name + " (" + sepalLength + ", " + sepalWidth + ", " + petalLength + ", " + petalWidth + ")"

  override def equals(other: Any): Boolean =
    other match {
      case that: Cluster =>
        (that canEqual this) &&
          name == that.getName &&
          sepalLength == that.getSepalLength &&
          sepalWidth == that.getSepalWidth &&
          petalLength == that.getPetalLength &&
          petalWidth == that.getPetalWidth
      case _ => false
    }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[Cluster]

  override def hashCode: Int = 41 * (41 * (41 * (41 * (41 + name.hashCode) + sepalLength.hashCode) + sepalWidth.hashCode) + petalLength.hashCode) + petalWidth.hashCode
}

object Cluster {
  // generate a random cluster in dataset range
  def getRandom(name: String): Cluster = new Cluster(name, Math.random() * 7.9, Math.random() * 4.4, Math.random() * 6.9, Math.random() * 2.5)
}
