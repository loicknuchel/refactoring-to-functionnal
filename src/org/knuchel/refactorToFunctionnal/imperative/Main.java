package org.knuchel.refactorToFunctionnal.imperative;

import java.util.List;

import org.knuchel.refactorToFunctionnal.imperative.beans.Cluster;
import org.knuchel.refactorToFunctionnal.imperative.beans.Iris;
import org.knuchel.refactorToFunctionnal.imperative.beans.Pair;
import org.knuchel.refactorToFunctionnal.imperative.utils.Print;
import org.knuchel.refactorToFunctionnal.imperative.utils.Util;
import org.knuchel.refactorToFunctionnal.imperative.validation.Validate;

/*
 * find 3 clusters in iris data
 */
public class Main {
	public static void main(String[] args) {
		List<Iris> dataset = Util.loadDataset("data/iris.data.csv", ",");

		// get some random clusters
		List<Cluster> initClusters = Util.generateRandomClusters(3);
		// List<Cluster> initClusters = Util.generateChoosenClusters();
		Print.clusters("", "Start with clusters", initClusters);

		// adjust clusters to the dataset (algorithm !)
		List<Cluster> clusters = KMeans.fitClusters(initClusters, dataset);
		Print.clusters("", "Adjusted clusters", clusters);

		// show results of clustering
		List<Pair<Cluster, List<Iris>>> clusteredDataset = KMeans.getClusteredDataset(clusters, dataset);
		Print.clusteredDataStats("", clusteredDataset);

		// restart from beginning and try to evaluate accuracy
		Validate.evaluatePredictionAccuracy(dataset);
	}
}
