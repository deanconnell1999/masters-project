package org.cloudbus.cloudsim.examples.power.random;

import java.io.IOException;
import java.util.Random;

public class ParameterTesting {

	public static void main(String[] args) throws IOException {
		String[] parameters = new String[] {"0.7","0.8","0.9","1.0","1.1","1.2","1.3","1.4","1.5","1.6","1.7"};
		String[] thresholds = new String[] {"0.4","0.5","0.6","0.7","0.8","0.9"};
		for(int i=0;i<1001;i++) {
			boolean enableOutput = true;
			boolean outputToFile = true;
			String inputFolder = "";
			String outputFolder = "C:/Users/deanc/Cloudsim/cloudsim-3.0.3/output";
			String workload = "random"; // Random workload
			
			// LlrMmt
			String vmAllocationPolicy = "lrr"; // Robust Local Regression (LRR) VM allocation policy
			String vmSelectionPolicy = "mmt"; // Minimum Migration Time (MMT) VM selection policy
			int rnd = new Random().nextInt(parameters.length);
			String parameter = parameters[rnd];
			new RandomRunner(
					enableOutput,
					outputToFile,
					inputFolder,
					outputFolder,
					workload,
					vmAllocationPolicy,
					vmSelectionPolicy,
					parameter);
			
			// LrMmt
			vmAllocationPolicy = "lr"; // Local Regression (LR) VM allocation policy
			vmSelectionPolicy = "mmt"; // Minimum Migration Time (MMT) VM selection policy
			rnd = new Random().nextInt(parameters.length);
			parameter = parameters[rnd];
			new RandomRunner(
					enableOutput,
					outputToFile,
					inputFolder,
					outputFolder,
					workload,
					vmAllocationPolicy,
					vmSelectionPolicy,
					parameter);
			
			// ThrMmt
			vmAllocationPolicy = "thr"; // Threshold (THR) VM allocation policy
			vmSelectionPolicy = "mmt"; // Minimum Migration Time (MMT) VM selection policy
			rnd = new Random().nextInt(thresholds.length);
			parameter = thresholds[rnd];
			new RandomRunner(
					enableOutput,
					outputToFile,
					inputFolder,
					outputFolder,
					workload,
					vmAllocationPolicy,
					vmSelectionPolicy,
					parameter);
			
			// ThrRs
			vmAllocationPolicy = "thr"; // Threshold (THR) VM allocation policy
			vmSelectionPolicy = "rs"; // Random Selection (RS) VM selection policy
			rnd = new Random().nextInt(thresholds.length);
			parameter = thresholds[rnd];
			new RandomRunner(
					enableOutput,
					outputToFile,
					inputFolder,
					outputFolder,
					workload,
					vmAllocationPolicy,
					vmSelectionPolicy,
					parameter);
		}
	}	
}