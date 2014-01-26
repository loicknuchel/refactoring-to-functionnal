package org.knuchel.refactorToFunctionnal

import scala.annotation.tailrec

import org.knuchel.refactorToFunctionnal.functionnal.beans.Cluster
import org.knuchel.refactorToFunctionnal.functionnal.beans.Iris
import org.knuchel.refactorToFunctionnal.functionnal.utils.Print
import org.knuchel.refactorToFunctionnal.functionnal.utils.Util
import org.knuchel.refactorToFunctionnal.functionnal.validation.Validate

object KMeansGen {
  type VectorSpace = (Double, Double, Double, Double)

  val irisVector = (i: Iris) => i.toData
  val clusterName = (c: Cluster) => c.getName
  val createCluster = (name: String, v: VectorSpace) => new Cluster(name, v._1, v._2, v._3, v._4)
  val randomCluster = (name: String) => Cluster.getRandom(name)

  def main(args: Array[String]) {
    // load dataset
    val dataset = Util.loadDataset("data/refactorToFunctionnal/iris.data.csv", ",")

    // get some random clusters
    val initClusters = generateRandom(3)(randomCluster)
    //    val initClusters = generateChoosenClusters
    Print.clusters("", "Start with clusters", initClusters)

    // adjust clusters to the dataset (algorithm !)
    val clusters = fitClusters(initClusters, dataset)(Util.distance, irisVector, clusterName, createCluster, randomCluster)
    Print.clusters("", "Adjusted clusters", clusters)

    // show results of clustering
    val clusteredDataset = getClusteredDataset(clusters, dataset)(Util.distance)
    Print.clusteredDataStats("", clusteredDataset)

    // restart from beginning and try to evaluate accuracy
    Validate.evaluatePredictionAccuracy(dataset)
  }

  def generateRandom[T](numberOfClusters: Int)(implicit random: String => T): List[T] = {
    (0 to numberOfClusters - 1).toList.map(i => random(i.toString))
  }

  @tailrec
  def fitClusters[T, U](clusters: List[U], dataset: List[T])(
    implicit distance: (T, U) => Double, tVector: T => VectorSpace, uName: U => String, createU: (String, VectorSpace) => U, randomU: String => U): List[U] = {
    // split dataset in clusters
    val clusteredDataset = getClusteredDataset(clusters, dataset)
    // calculate new clusters with the splited dataset
    val newClusters = impoveClusters(clusteredDataset)

    if (clusters == newClusters)
      clusters
    else
      fitClusters(newClusters, dataset)
  }

  def getClusteredDataset[T, U](clusters: List[U], dataset: List[T])(implicit distance: (T, U) => Double): List[(U, List[T])] = {
    // create lists associated to clusters and merge it with data in the cluster (nearest)
    (clusters.map(c => (c, List())).toMap ++ dataset.groupBy(iris => getClusterFor(iris, clusters))).toList
  }

  def getClusterFor[T, U](item: T, list: List[U])(implicit distance: (T, U) => Double): U = {
    // return the nearest cluster
    list.sortBy(elt => distance(item, elt)).head
  }

  def impoveClusters[T, U](dataset: List[(U, List[T])])(
    implicit tVector: T => VectorSpace, uName: U => String, createU: (String, VectorSpace) => U, randomU: String => U): List[U] = {

    def calcCenter(cluster: U, list: List[T]): U = {
      if (list.length > 0) {
        // sum iris positions
        val sum = list.map(tVector).foldLeft((0d, 0d, 0d, 0d)) {
          (acc, num) => (acc._1 + num._1, acc._2 + num._2, acc._3 + num._3, acc._4 + num._4)
        }
        // new position is in the center of the cluster elements
        createU(uName(cluster), (sum._1 / list.length, sum._2 / list.length, sum._3 / list.length, sum._4 / list.length))
      } else
        // if no data, new position is random
        randomU(uName(cluster))
    }

    dataset.map({ case (cluster, irisList) => calcCenter(cluster, irisList) }).toList
  }

}

