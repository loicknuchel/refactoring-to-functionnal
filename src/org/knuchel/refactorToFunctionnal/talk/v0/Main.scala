package org.knuchel.refactorToFunctionnal.talk.v0

import org.knuchel.refactorToFunctionnal.talk.beans.Cluster
import org.knuchel.refactorToFunctionnal.talk.beans.Iris
import org.knuchel.refactorToFunctionnal.talk.utils.Print
import org.knuchel.refactorToFunctionnal.talk.utils.Util
//remove if not needed
import scala.collection.JavaConverters._

object Main {

  def main(args: Array[String]) {
    val a = List()
    
    val dataset = Util.loadDataset("data/iris.data.csv", ",")
    val initClusters = Util.generateRandomClusters(3)
    Print.clusters("", "Start with clusters", initClusters)
    val clusters = KMeans.fitClusters(initClusters.asJava, dataset.asJava)
    Print.clusters("", "Adjusted clusters", clusters.asScala.toList)
    val clusteredDataset = KMeans.getClusteredDataset(clusters, dataset.asJava).asScala.toList.map(c => (c._1, c._2.asScala.toList))
    Print.clusteredDataStats("", clusteredDataset)
  }
}
