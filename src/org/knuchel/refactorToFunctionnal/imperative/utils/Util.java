package org.knuchel.refactorToFunctionnal.imperative.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.knuchel.refactorToFunctionnal.imperative.beans.Cluster;
import org.knuchel.refactorToFunctionnal.imperative.beans.DataVector;
import org.knuchel.refactorToFunctionnal.imperative.beans.Iris;

public class Util {
	public static Double distance(DataVector v1, DataVector v2) {
		// compute distance between cluster and some element in similar space
		return Math.sqrt(Math.pow(v1.getSepalLength() - v2.getSepalLength(), 2) + Math.pow(v1.getSepalWidth() - v2.getSepalWidth(), 2)
				+ Math.pow(v1.getPetalLength() - v2.getPetalLength(), 2) + Math.pow(v1.getSepalLength() - v2.getPetalWidth(), 2));
	}

	public static List<Cluster> generateRandomClusters(Integer numberOfClusters) {
		List<Cluster> clusters = new ArrayList<Cluster>();
		for (Integer i = 0; i < numberOfClusters; i++) {
			clusters.add(Cluster.getRandom(i.toString()));
		}
		return clusters;
	}

	public static List<Cluster> generateChoosenClusters() {
		List<Cluster> clusters = new ArrayList<Cluster>();
		clusters.add(new Cluster("0", 0.39711338612424296, 2.864731051919733, 0.5443930651973621, 1.8620635270803276));
		clusters.add(new Cluster("1", 0.5141683440768627, 4.303230176059085, 6.592530788783834, 0.8277904911929196));
		clusters.add(new Cluster("2", 1.2102698872529725, 0.3206628169206544, 6.440813271336938, 1.0773097552115785));
		return clusters;
	}

	public static List<Iris> loadDataset(String file, String separator) {
		List<Iris> dataset = new ArrayList<Iris>();
		BufferedReader br = null;
		String line;

		try {
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				if (line.length() > 0) {
					String[] cell = line.split(separator);
					dataset.add(new Iris(Double.parseDouble(cell[0]), Double.parseDouble(cell[1]), Double.parseDouble(cell[2]), Double.parseDouble(cell[3]),
							cell[4]));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return dataset;
	}
}
