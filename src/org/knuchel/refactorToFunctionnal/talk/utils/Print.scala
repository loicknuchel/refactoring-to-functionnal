package org.knuchel.refactorToFunctionnal.talk.utils

import org.knuchel.refactorToFunctionnal.talk.beans.Iris
import org.knuchel.refactorToFunctionnal.talk.beans.Cluster

object Print {
  def clusters(pad: String, label: String, clusters: List[Cluster]) {
    println(pad + label + " :")
    for (cluster <- clusters) {
      println(pad + "   " + cluster)
    }
    println(pad)
  }

  def clusteredDataStats(pad: String, clusteredData: List[(Cluster, List[Iris])]) {
    for (data <- clusteredData) {
      println(pad + data._1 + " :")
      datasetStats(pad + "   ", data._2)
    }
    println(pad)
  }

  def datasetStats(pad: String, dataset: List[Iris]) {
    if (!dataset.isEmpty)
      dataset.groupBy(iris => iris.getSpecie).map({ case (specie, irisList) => println(pad + irisList.size + " " + specie) })
    else
      println(pad + "dataset is empty...")
  }
}