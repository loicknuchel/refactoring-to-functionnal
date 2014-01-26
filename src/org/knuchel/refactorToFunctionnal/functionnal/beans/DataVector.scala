package org.knuchel.refactorToFunctionnal.functionnal.beans

trait DataVector {
  type VectorSpace = (Double, Double, Double, Double)

  def getSepalLength(): Double
  def getSepalWidth(): Double
  def getPetalLength(): Double
  def getPetalWidth(): Double
  def toData: VectorSpace = (getSepalLength, getSepalWidth, getPetalLength, getPetalWidth)
}
