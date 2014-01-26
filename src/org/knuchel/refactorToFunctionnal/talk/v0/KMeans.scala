package org.knuchel.refactorToFunctionnal.talk.v0

import java.util.ArrayList
import java.util.List

import scala.collection.JavaConversions.asScalaBuffer

import org.knuchel.refactorToFunctionnal.talk.beans.Cluster
import org.knuchel.refactorToFunctionnal.talk.beans.Iris
import org.knuchel.refactorToFunctionnal.talk.utils.Util

object KMeans {

  def fitClusters(clusters: List[Cluster], dataset: List[Iris]): List[Cluster] = {
    var testClusters: List[Cluster] = null
    var newClusters = clusters
    while (newClusters != testClusters) {
      testClusters = newClusters
      val clusteredDataset = getClusteredDataset(testClusters, dataset)
      newClusters = improveClusters(clusteredDataset)
    }
    testClusters
  }

  def getClusteredDataset(clusters: List[Cluster], dataset: List[Iris]): List[(Cluster, List[Iris])] = {
    val clusteredIris = new ArrayList[(Cluster, List[Iris])]()
    for (cluster <- clusters) {
      clusteredIris.add((cluster, new ArrayList[Iris]()))
    }
    for (iris <- dataset) {
      val irisCluster = getClusterFor(iris, clusters)
      for (pair <- clusteredIris if pair._1 == irisCluster) {
        pair._2.add(iris)
        //break
      }
    }
    clusteredIris
  }

  def getClusterFor(iris: Iris, clusters: List[Cluster]): Cluster = {
    var nearestCluster: Cluster = null
    var smallestDistance: java.lang.Double = null
    for (cluster <- clusters) {
      val clusterDistance = Util.distance(cluster, iris)
      if (smallestDistance == null || smallestDistance > clusterDistance) {
        smallestDistance = clusterDistance
        nearestCluster = cluster
      }
    }
    nearestCluster
  }

  private def improveClusters(clusteredDataset: List[(Cluster, List[Iris])]): List[Cluster] = {
    val clusters = new ArrayList[Cluster]()
    for (entry <- clusteredDataset) {
      val cluster = entry._1
      val clusterSet = entry._2
      if (clusterSet != null && clusterSet.size > 0) {
        var sepalLengthSum = 0d
        var sepalWidthSum = 0d
        var petalLengthSum = 0d
        var petalWidthSum = 0d
        for (iris <- clusterSet) {
          if (iris.getSepalLength != null) {
            sepalLengthSum += iris.getSepalLength
          }
          if (iris.getSepalWidth != null) {
            sepalWidthSum += iris.getSepalWidth
          }
          if (iris.getPetalLength != null) {
            petalLengthSum += iris.getPetalLength
          }
          if (iris.getPetalWidth != null) {
            petalWidthSum += iris.getPetalWidth
          }
        }
        clusters.add(new Cluster(cluster.getName, sepalLengthSum / clusterSet.size, sepalWidthSum / clusterSet.size,
          petalLengthSum / clusterSet.size, petalWidthSum / clusterSet.size))
      } else {
        clusters.add(Cluster.getRandom(cluster.getName))
      }
    }
    clusters
  }
}
