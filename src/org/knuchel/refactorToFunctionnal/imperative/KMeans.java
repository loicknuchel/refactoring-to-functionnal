package org.knuchel.refactorToFunctionnal.imperative;

import java.util.ArrayList;
import java.util.List;

import org.knuchel.refactorToFunctionnal.imperative.beans.Cluster;
import org.knuchel.refactorToFunctionnal.imperative.beans.Iris;
import org.knuchel.refactorToFunctionnal.imperative.beans.Pair;
import org.knuchel.refactorToFunctionnal.imperative.utils.Util;

public class KMeans {

	public static List<Cluster> fitClusters(List<Cluster> clusters, List<Iris> dataset) {
		List<Cluster> testClusters = null, newClusters = clusters;
		while (!newClusters.equals(testClusters)) {
			testClusters = newClusters;

			// split dataset in clusters
			List<Pair<Cluster, List<Iris>>> clusteredDataset = getClusteredDataset(testClusters, dataset);

			// calculate new clusters with the splited dataset
			newClusters = improveClusters(clusteredDataset);
		}

		return testClusters;
	}

	public static List<Pair<Cluster, List<Iris>>> getClusteredDataset(List<Cluster> clusters, List<Iris> dataset) {
		List<Pair<Cluster, List<Iris>>> clusteredIris = new ArrayList<Pair<Cluster, List<Iris>>>();
		// create cluster lists
		for (Cluster cluster : clusters) {
			clusteredIris.add(new Pair<Cluster, List<Iris>>(cluster, new ArrayList<Iris>()));
		}

		// add data in corresponding cluster (nearest)
		for (Iris iris : dataset) {
			Cluster irisCluster = getClusterFor(iris, clusters);
			for (Pair<Cluster, List<Iris>> pair : clusteredIris) {
				if (pair.getKey().equals(irisCluster)) {
					pair.getValue().add(iris);
					break;
				}
			}
		}

		return clusteredIris;
	}

	public static Cluster getClusterFor(Iris iris, List<Cluster> clusters) {
		Cluster nearestCluster = null;
		Double smallestDistance = null;
		// return the nearest cluster
		for (Cluster cluster : clusters) {
			Double clusterDistance = Util.distance(cluster, iris);
			if (smallestDistance == null || smallestDistance > clusterDistance) {
				smallestDistance = clusterDistance;
				nearestCluster = cluster;
			}
		}
		return nearestCluster;
	}

	private static List<Cluster> improveClusters(List<Pair<Cluster, List<Iris>>> clusteredDataset) {
		List<Cluster> clusters = new ArrayList<Cluster>();
		// calculate new positions for each cluster
		for (Pair<Cluster, List<Iris>> entry : clusteredDataset) {
			Cluster cluster = entry.getKey();
			List<Iris> clusterSet = entry.getValue();
			if (clusterSet != null && clusterSet.size() > 0) {
				// new cluster position is in the center of the cluster elements
				Double sepalLengthSum = 0d, sepalWidthSum = 0d, petalLengthSum = 0d, petalWidthSum = 0d;
				for (Iris iris : clusterSet) {
					if (iris.getSepalLength() != null) {
						sepalLengthSum += iris.getSepalLength();
					}
					if (iris.getSepalWidth() != null) {
						sepalWidthSum += iris.getSepalWidth();
					}
					if (iris.getPetalLength() != null) {
						petalLengthSum += iris.getPetalLength();
					}
					if (iris.getPetalWidth() != null) {
						petalWidthSum += iris.getPetalWidth();
					}
				}
				clusters.add(new Cluster(cluster.getName(), sepalLengthSum / clusterSet.size(), sepalWidthSum / clusterSet.size(), petalLengthSum
						/ clusterSet.size(), petalWidthSum / clusterSet.size()));
			} else {
				// if cluster has no element, his new position is random
				clusters.add(Cluster.getRandom(cluster.getName()));
			}
		}
		return clusters;
	}
}
