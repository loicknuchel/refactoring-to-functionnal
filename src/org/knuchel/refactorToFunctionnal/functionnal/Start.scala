package org.knuchel.refactorToFunctionnal

import org.knuchel.refactorToFunctionnal.functionnal.beans.Iris
import org.knuchel.refactorToFunctionnal.functionnal.beans.Cluster
import org.knuchel.refactorToFunctionnal.functionnal.utils.Util

object Start {
  // basic collection operations : filter, map et fold

  case class Elt(id: String, group: String)

  def toInt(s: String): Option[Int] = {
    try {
      Some(s.toInt)
    } catch {
      case e: Exception => None
    }
  }

  def sumIds(elts: List[Elt]): Int = {
    elts
      .map(elt => toInt(elt.id))
      .filter(id => !id.isEmpty)
      .foldLeft(0)((acc, id) => acc + id.get)
  }

  def sumIdsByGroup(elts: List[Elt], f: List[Elt] => Int): Map[String, Int] = {
    elts
      .groupBy(elt => elt.group)
      .map { case (group, elts) => (group, f(elts)) }
  }

  def main(args: Array[String]) {
    val elts = List(Elt("1", "table"), Elt("10", "chair"), Elt("20", "table"), Elt("15", "desk"), Elt("toto", "chair"))
    println(sumIds(elts))
    println(sumIdsByGroup(elts, sumIds))
  }

  /* ****************************************************************************************** */

  // KMeansScala.distance(): Double
  // head, sorted/sortBy
  def getClusterFor(iris: Iris, clusters: List[Cluster]): Cluster = {
    clusters.sortBy(cluster => Util.distance(cluster, iris)).head
  }

  // getClusterFor(Iris, List[Cluster]): Cluster
  // groupBy
  def getClusteredDataset(clusters: List[Cluster], dataset: List[Iris]): Map[Cluster, List[Iris]] = {
    dataset.groupBy(iris => getClusterFor(iris, clusters))
  }

  // KMeansScala.distance(): Double
  // head, take, sorted/sortBy, map, groupBy
  def predictSpecie(dataset: List[Iris], k: Int, features: (Double, Double, Double, Double)) = {
    dataset
      .map(iris => (Util.distance(iris, features), iris.getSpecie)).sorted.take(k)
      .groupBy(_._2).map(elt => (elt._2.length, elt._1)).toList
      .sortBy(-_._1).head._2
  }

  // take/drop, groupBy, flatMap
  def split(dataset: List[Iris]): (List[Iris], List[Iris]) = {
    val datasetBySpecie = dataset.groupBy(iris => iris.getSpecie)
    val learningData = datasetBySpecie.flatMap({ case (_, irisList) => irisList.take(irisList.length / 2) }).toList
    val testData = datasetBySpecie.flatMap({ case (_, irisList) => irisList.drop(irisList.length / 2) }).toList
    (learningData, testData)
  }

}