package org.knuchel.refactorToFunctionnal.talk.utils

import scala.io.Source

import org.knuchel.refactorToFunctionnal.talk.beans.Cluster
import org.knuchel.refactorToFunctionnal.talk.beans.DataVector
import org.knuchel.refactorToFunctionnal.talk.beans.Iris

object Util {
  type VectorSpace = (Double, Double, Double, Double)

  // compute distance between elements in similar space
  def distance(v1: VectorSpace, v2: VectorSpace): Double =
    Math.sqrt(Math.pow(v1._1 - v2._1, 2) + Math.pow(v1._2 - v2._2, 2) + Math.pow(v1._3 - v2._3, 2) + Math.pow(v1._4 - v2._4, 2))

  def distance(v1: DataVector, v2: DataVector): Double = distance(v1.toData, v2.toData)
  def distance(v1: VectorSpace, v2: DataVector): Double = distance(v1, v2.toData)
  def distance(v1: DataVector, v2: VectorSpace): Double = distance(v1.toData, v2)

  def generateRandomClusters(numberOfClusters: Int): List[Cluster] = {
    (0 to numberOfClusters - 1).toList.map(i => Cluster.getRandom(i.toString))
  }

  def generateChoosenClusters: List[Cluster] =
    List(new Cluster("0", 0.39711338612424296, 2.864731051919733, 0.5443930651973621, 1.8620635270803276),
      new Cluster("1", 0.5141683440768627, 4.303230176059085, 6.592530788783834, 0.8277904911929196),
      new Cluster("2", 1.2102698872529725, 0.3206628169206544, 6.440813271336938, 1.0773097552115785))

  def loadDataset(file: String, separator: String): List[Iris] = {
    val f = Source.fromFile(file)
    val dataset = f.getLines.filter(s => s.length > 0).map(line => {
      val cell = line.split(separator)
      new Iris(cell(0).toDouble, cell(1).toDouble, cell(2).toDouble, cell(3).toDouble, cell(4))
    }).toList
    f.close
    dataset
  }
}