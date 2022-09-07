package org.cloudbus.cloudsim.examples.power.random;

import java.io.IOException;
import java.util.Random;

public class ComparisonTesting {

	public static void main(String[] args) throws IOException {
		String[] parameters = new String[] {"1.7"};
		String[] thresholds = new String[] {"0.4"};
		for(int i=0;i<51;i++) {
			boolean enableOutput = true;
			boolean outputToFile = true;
			String inputFolder = "";
			String outputFolder = "C:/Users/deanc/Cloudsim/cloudsim-3.0.3/output";
			String workload = "random"; // Random workload
			
			// LlrMmt
			String vmAllocationPolicy = "lrr"; // Local Regression (LR) VM allocation policy
			String vmSelectionPolicy = "mmt"; // Minimum Migration Time (MMT) VM selection policy
			int rnd = new Random().nextInt(parameters.length);
			String parameter = parameters[rnd]; // the safety parameter of the LR policy
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
			parameter = parameters[rnd]; // the safety parameter of the LR policy
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
			vmAllocationPolicy = "thr"; // Local Regression (LR) VM allocation policy
			vmSelectionPolicy = "mmt"; // Minimum Migration Time (MMT) VM selection policy
			rnd = new Random().nextInt(thresholds.length);
			parameter = thresholds[rnd]; // the safety parameter of the LR policy
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
			vmAllocationPolicy = "thr"; // Local Regression (LR) VM allocation policy
			vmSelectionPolicy = "rs"; // Minimum Migration Time (MMT) VM selection policy
			rnd = new Random().nextInt(thresholds.length);
			parameter = thresholds[rnd]; // the safety parameter of the LR policy
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