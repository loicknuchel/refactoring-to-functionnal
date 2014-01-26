package org.knuchel.refactorToFunctionnal.functionnal.validation

import scala.annotation.tailrec
import scala.util.Random

import org.knuchel.refactorToFunctionnal.functionnal.KMeans
import org.knuchel.refactorToFunctionnal.functionnal.beans.Iris
import org.knuchel.refactorToFunctionnal.functionnal.utils.Print
import org.knuchel.refactorToFunctionnal.functionnal.utils.Util

object Validate {
  def evaluatePredictionAccuracy(dataset: List[Iris]) {
    println("*****************************************")
    println("* Evaluate algorithm :")
    println("* ")

    // split dataset in 2 parts : one part to learn, the other part to test
    val datasetBySpecie = dataset.groupBy(iris => iris.getSpecie)
    val learningData = datasetBySpecie.flatMap({ case (_, irisList) => Random.shuffle(irisList).take(irisList.length / 2) }).toList
    val testData = datasetBySpecie.flatMap({ case (_, irisList) => Random.shuffle(irisList).drop(irisList.length / 2) }).toList

    /**
     * Test accuracy of algorithm
     */
    // get clusters for the dataset
    val clusters = KMeans.fitClusters(Util.generateRandomClusters(3), learningData)
    val clusteredDataset = KMeans.getClusteredDataset(clusters, learningData)
    Print.clusteredDataStats("* ", clusteredDataset)

    // determine a specie for each cluster (majority)
    def mostPopular[T, U](list: List[T])(group: T => U): U = list.groupBy(group).map({ case (key, list) => (key, list.length) }).toList.sortBy({ case (_, length) => -length }).head._1
    val clusterSpecies = clusteredDataset.map({ case (cluster, irisList) => (cluster, mostPopular(irisList)(iris => iris.getSpecie)) }).toMap

    // make predictions and count errors
    val a = testData.map(iris => {
      val cluster = KMeans.getClusterFor(iris, clusters)
      val predictedSpecie = clusterSpecies.get(cluster)
      predictedSpecie.getOrElse("").equals(iris.getSpecie)
    })
    val errors = a.count(bool => !bool)
    val successPc = 100 * (testData.length - errors.doubleValue()) / testData.length
    println("*  - " + errors + " errors for " + testData.length + " tested data (" + successPc + "% of success)")
    println("*****************************************")
  }
}