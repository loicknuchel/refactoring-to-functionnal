package org.knuchel.refactorToFunctionnal.imperative;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.knuchel.refactorToFunctionnal.imperative.beans.Cluster;
import org.knuchel.refactorToFunctionnal.imperative.beans.Iris;
import org.knuchel.refactorToFunctionnal.imperative.beans.Pair;
import org.knuchel.refactorToFunctionnal.imperative.utils.Util;

public class Start {
	public static class Elt {
		public String id;
		public String group;

		public Elt(String id, String group) {
			this.id = id;
			this.group = group;
		}
	}

	public static Integer toInt(String s) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Integer sumIds(List<Elt> elts) {
		Integer sum = 0;
		for (Elt elt : elts) {
			Integer id = toInt(elt.id);
			if (id != null) {
				sum += id;
			}
		}
		return sum;
	}

	public static void main(String[] args) {
		List<Elt> elts = Arrays.asList(new Elt[] { new Elt("1", "table"), new Elt("10", "chair"), new Elt("20", "table"), new Elt("15", "desk"),
				new Elt("toto", "chair") });
		System.out.println(sumIds(elts));
	}

	/* ****************************************************************************************** */

	public static Cluster getClusterFor(Iris iris, List<Cluster> clusters) {
		Cluster nearestCluster = null;
		Double smallestDistance = null;

		for (Cluster cluster : clusters) {
			Double clusterDistance = Util.distance(cluster, iris);
			if (smallestDistance == null || smallestDistance > clusterDistance) {
				smallestDistance = clusterDistance;
				nearestCluster = cluster;
			}
		}

		return nearestCluster;
	}

	public static Map<Cluster, List<Iris>> getClusteredDataset(List<Cluster> clusters, List<Iris> dataset) {
		Map<Cluster, List<Iris>> clusteredIris = new HashMap<Cluster, List<Iris>>();

		for (Iris iris : dataset) {
			Cluster irisCluster = getClusterFor(iris, clusters);
			if (clusteredIris.get(irisCluster) == null)
				clusteredIris.put(irisCluster, new ArrayList<Iris>());
			clusteredIris.get(irisCluster).add(iris);
		}

		return clusteredIris;
	}

	// Double KMeansJava.distance()
	public static String predictSpecie(List<Iris> dataset, Integer k, Double sepalLength, Double sepalWidth, Double petalLength, Double petalWidth) {
		Iris unknownIris = new Iris(sepalLength, sepalWidth, petalLength, petalWidth, null);
		List<Pair<Double, String>> scores = new ArrayList<Pair<Double, String>>();

		for (Iris iris : dataset) {
			scores.add(new Pair<Double, String>(Util.distance(unknownIris, iris), iris.getSpecie()));
		}
		Collections.sort(scores, new Comparator<Pair<Double, String>>() {
			@Override
			public int compare(Pair<Double, String> o1, Pair<Double, String> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});

		Map<String, Integer> occurenceCount = new HashMap<String, Integer>();
		for (Integer i = 0; i < scores.size(); i++) {
			String specie = scores.get(i).getValue();
			if (occurenceCount.containsKey(specie)) {
				occurenceCount.put(specie, occurenceCount.get(specie) + 1);
			} else {
				occurenceCount.put(specie, 1);
			}

			if (i >= k - 1) {
				break;
			}
		}

		String mostFrequentSpecie = null;
		Integer nbOccurence = 0;
		for (Entry<String, Integer> entry : occurenceCount.entrySet()) {
			if (nbOccurence < entry.getValue()) {
				nbOccurence = entry.getValue();
				mostFrequentSpecie = entry.getKey();
			}
		}
		return mostFrequentSpecie;
	}

	public static Pair<List<Iris>, List<Iris>> split(List<Iris> dataset) {
		Map<String, List<Iris>> datasetBySpecie = new HashMap<String, List<Iris>>();
		for (Iris iris : dataset) {
			if (datasetBySpecie.get(iris.getSpecie()) == null)
				datasetBySpecie.put(iris.getSpecie(), new ArrayList<Iris>());
			datasetBySpecie.get(iris.getSpecie()).add(iris);
		}

		List<Iris> learningData = new ArrayList<Iris>(), testData = new ArrayList<Iris>();
		for (List<Iris> irisList : datasetBySpecie.values()) {
			for (Integer i = 0; i < irisList.size(); i++) {
				if (i < irisList.size() / 2)
					learningData.add(irisList.get(i));
				else
					testData.add(irisList.get(i));
			}
		}
		return new Pair<List<Iris>, List<Iris>>(learningData, testData);
	}
}
