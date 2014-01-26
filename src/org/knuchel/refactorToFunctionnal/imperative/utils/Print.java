package org.knuchel.refactorToFunctionnal.imperative.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.knuchel.refactorToFunctionnal.imperative.beans.Cluster;
import org.knuchel.refactorToFunctionnal.imperative.beans.Iris;
import org.knuchel.refactorToFunctionnal.imperative.beans.Pair;

public class Print {
	public static void clusters(String pad, String label, List<Cluster> clusters) {
		System.out.println(pad + label + " :");
		for (Cluster cluster : clusters) {
			System.out.println(pad + "   " + cluster);
		}
		System.out.println(pad);
	}

	public static void clusteredDataStats(String pad, List<Pair<Cluster, List<Iris>>> clusteredData) {
		for (Pair<Cluster, List<Iris>> entry : clusteredData) {
			System.out.println(pad + entry.getKey() + " :");
			datasetStats(pad + "   ", entry.getValue());
		}
		System.out.println(pad);
	}

	public static void datasetStats(String pad, List<Iris> dataset) {
		if (dataset != null && !dataset.isEmpty()) {
			Map<String, Integer> types = new HashMap<String, Integer>();
			for (Iris data : dataset) {
				Integer cpt = types.get(data.getSpecie());
				types.put(data.getSpecie(), cpt != null ? cpt + 1 : 1);
			}
			for (Entry<String, Integer> entry : types.entrySet()) {
				System.out.println(pad + entry.getValue() + " " + entry.getKey());
			}
		} else {
			System.out.println(pad + "dataset is empty...");
		}
	}
}
