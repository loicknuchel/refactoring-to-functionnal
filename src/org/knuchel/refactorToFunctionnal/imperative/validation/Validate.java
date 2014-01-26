package org.knuchel.refactorToFunctionnal.imperative.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.knuchel.refactorToFunctionnal.imperative.KMeans;
import org.knuchel.refactorToFunctionnal.imperative.beans.Cluster;
import org.knuchel.refactorToFunctionnal.imperative.beans.Iris;
import org.knuchel.refactorToFunctionnal.imperative.beans.Pair;
import org.knuchel.refactorToFunctionnal.imperative.utils.Print;
import org.knuchel.refactorToFunctionnal.imperative.utils.Util;

public class Validate {
	public static void evaluatePredictionAccuracy(List<Iris> dataset) {
		System.out.println("*****************************************");
		System.out.println("* Evaluate algorithm :");
		System.out.println("* ");

		// split dataset in 2 parts : one part to learn, the other part to test
		Map<String, List<Iris>> datasetBySpecie = new HashMap<String, List<Iris>>();
		for (Iris iris : dataset) {
			if (datasetBySpecie.get(iris.getSpecie()) == null)
				datasetBySpecie.put(iris.getSpecie(), new ArrayList<Iris>());
			datasetBySpecie.get(iris.getSpecie()).add(iris);
		}

		List<Iris> learningData = new ArrayList<Iris>(), testData = new ArrayList<Iris>();
		for (List<Iris> irisList : datasetBySpecie.values()) {
			Collections.shuffle(irisList);
			for (Integer i = 0; i < irisList.size(); i++) {
				if (i < irisList.size() / 2)
					learningData.add(irisList.get(i));
				else
					testData.add(irisList.get(i));
			}
		}

		/*******
		 * Test accuracy of algorithm
		 *******/
		// get clusters for the dataset
		List<Cluster> clusters = KMeans.fitClusters(Util.generateRandomClusters(3), learningData);
		List<Pair<Cluster, List<Iris>>> clusteredDataset = KMeans.getClusteredDataset(clusters, learningData);
		Print.clusteredDataStats("* ", clusteredDataset);

		// determine a specie for each cluster (majority)
		Map<Cluster, String> clusterSpecies = new HashMap<Cluster, String>();
		for (Pair<Cluster, List<Iris>> e1 : clusteredDataset) {
			Cluster cluster = e1.getKey();
			Map<String, Integer> specieCount = new HashMap<String, Integer>();
			for (Iris iris : e1.getValue()) {
				Integer cpt = specieCount.get(iris.getSpecie());
				specieCount.put(iris.getSpecie(), cpt != null ? cpt + 1 : 1);
			}
			String clusterSpecie = null;
			Integer clusterSpecieCount = null;
			for (Entry<String, Integer> e2 : specieCount.entrySet()) {
				if (clusterSpecieCount == null || clusterSpecieCount < e2.getValue()) {
					clusterSpecie = e2.getKey();
					clusterSpecieCount = e2.getValue();
				}
			}
			clusterSpecies.put(cluster, clusterSpecie);
		}

		// make predictions and count errors
		Integer errors = 0;
		for (Iris iris : testData) {
			Cluster cluster = KMeans.getClusterFor(iris, clusters);
			String predictedSpecie = clusterSpecies.get(cluster);
			if (!predictedSpecie.equals(iris.getSpecie())) {
				errors++;
			}
		}
		Double successPc = 100 * (testData.size() - errors.doubleValue()) / testData.size();
		System.out.println("*  - " + errors + " errors for " + testData.size() + " tested data (" + successPc + "% of success)");
		System.out.println("*****************************************");
	}
}
