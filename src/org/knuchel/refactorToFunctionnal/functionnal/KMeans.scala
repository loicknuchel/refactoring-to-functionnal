package org.knuchel.refactorToFunctionnal.functionnal

import scala.annotation.tailrec

import org.knuchel.refactorToFunctionnal.functionnal.beans.Cluster
import org.knuchel.refactorToFunctionnal.functionnal.beans.Iris
import org.knuchel.refactorToFunctionnal.functionnal.utils.Util

object KMeans {
  @tailrec
  def fitClusters(clusters: List[Cluster], dataset: List[Iris]): List[Cluster] = {
    // split dataset in clusters
    val clusteredDataset = getClusteredDataset(clusters, dataset)
    // calculate new clusters with the splited dataset
    val newClusters = impoveClusters(clusteredDataset)

    if (clusters == newClusters)
      clusters
    else
      fitClusters(newClusters, dataset)
  }

  def getClusteredDataset(clusters: List[Cluster], dataset: List[Iris]): List[(Cluster, List[Iris])] = {
    // create lists associated to clusters and merge it with data in the cluster (nearest)
    (clusters.map(c => (c, List())).toMap ++ dataset.groupBy(iris => getClusterFor(iris, clusters))).toList
  }

  def getClusterFor(iris: Iris, clusters: List[Cluster]): Cluster = {
    // return the nearest cluster
    clusters.sortBy(cluster => Util.distance(cluster, iris)).head
  }

  private def impoveClusters(clusteredDataset: List[(Cluster, List[Iris])]): List[Cluster] = {
    def centerOfCluster(cluster: Cluster, list: List[Iris]): Cluster = {
      if (list.length > 0) {
        // sum iris positions
        val sum = list.map(iris => iris.toData).foldLeft((0d, 0d, 0d, 0d)) {
          (acc, num) => (acc._1 + num._1, acc._2 + num._2, acc._3 + num._3, acc._4 + num._4)
        }
        // new position is in the center of the cluster elements
        new Cluster(cluster.getName, sum._1 / list.length, sum._2 / list.length, sum._3 / list.length, sum._4 / list.length)
      } else
        // if no data, new position is random
        Cluster.getRandom(cluster.getName)
    }

    clusteredDataset.map({ case (cluster, irisList) => centerOfCluster(cluster, irisList) }).toList
  }

}

