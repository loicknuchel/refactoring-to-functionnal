package org.knuchel.refactorToFunctionnal.functionnal

import scala.annotation.tailrec

import org.knuchel.refactorToFunctionnal.functionnal.utils.Print
import org.knuchel.refactorToFunctionnal.functionnal.utils.Util
import org.knuchel.refactorToFunctionnal.functionnal.validation.Validate

/*
 * find 3 clusters in iris data
 */
object Main {
  def main(args: Array[String]) {
    // load dataset
    val dataset = Util.loadDataset("data/refactorToFunctionnal/iris.data.csv", ",")

    // get some random clusters
    val initClusters = Util.generateRandomClusters(3)
    //    val initClusters = generateChoosenClusters
    Print.clusters("", "Start with clusters", initClusters)

    // adjust clusters to the dataset (algorithm !)
    val clusters = KMeans.fitClusters(initClusters, dataset)
    Print.clusters("", "Adjusted clusters", clusters)

    // show results of clustering
    val clusteredDataset = KMeans.getClusteredDataset(clusters, dataset)
    Print.clusteredDataStats("", clusteredDataset)

    // restart from beginning and try to evaluate accuracy
    Validate.evaluatePredictionAccuracy(dataset)
  }
}