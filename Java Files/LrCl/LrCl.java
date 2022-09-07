package org.cloudbus.cloudsim.examples.power.random;

import java.io.IOException;

public class LrCl {

	public static void main(String[] args) throws IOException {
		boolean enableOutput = true;
		boolean outputToFile = true;
		String inputFolder = "";
		String outputFolder = "C:/Users/deanc/Cloudsim/cloudsim-3.0.3/output";
		String workload = "random"; // Random workload
		String vmAllocationPolicy = "lr"; // Local Regression (LR) VM allocation policy
		String vmSelectionPolicy = "cl"; // Classification (CL) VM selection policy
		String parameter = "1.7"; // the safety parameter of the LR policy
		// LrCl
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
