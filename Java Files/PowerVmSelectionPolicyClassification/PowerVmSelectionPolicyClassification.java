package org.cloudbus.cloudsim.power;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.*;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.examples.power.Helper;

public class PowerVmSelectionPolicyClassification extends PowerVmSelectionPolicy {

	protected static boolean trained = true;
	
	@Override
	public Vm getVmToMigrate(PowerHost host) {
		List<PowerVm> migratableVms = getMigratableVms(host);
		List<Integer> hostUtil = getHostUtil(host, migratableVms);
		List<Double> sla = Helper.getSla(migratableVms);
		if (trained == false) {
			trained = true;
			//setting up MLP
			try{
				//Reading training arff file
				FileReader trainreader = new FileReader("training.arff");
				Instances train = new Instances(trainreader);
				train.setClassIndex(train.numAttributes()-1);
				
				//Instance of NN
				MultilayerPerceptron mlp = new MultilayerPerceptron();
				
				//setting parameters
				mlp.setLearningRate(0.05);
				mlp.setMomentum(0.99);
				mlp.setTrainingTime(3000);
				mlp.setHiddenLayers("8");
				
				//building classifier
				mlp.buildClassifier(train);
				
				//evaluating training data
				Evaluation eval = new Evaluation(train);
				eval.evaluateModel(mlp, train);
				System.out.println(eval.toSummaryString()); //Summary of Training
				System.out.println(Arrays.deepToString(eval.confusionMatrix()).replace("], ", "]\n").replace("[[", "[").replace("]]", "]")); // Confusion Matrix
				
				//saving model
				weka.core.SerializationHelper.write("mlp.model", mlp);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if (migratableVms.isEmpty()) {
			return null;
		}
		Vm vmToMigrate = null;
		for (Vm vm : migratableVms) {
			if (vm.isInMigration()) {
				continue;
			}
			// grab all info about migrations
			int ram = vm.getRam();
			double cpuutil = vm.getTotalUtilizationOfCpuMips(CloudSim.clock())/vm.getMips();
			int vmhostutil = hostUtil.get(migratableVms.indexOf(vm));
			double vmsla = sla.get(migratableVms.indexOf(vm));
			try {
				MultilayerPerceptron mlp = (MultilayerPerceptron)weka.core.SerializationHelper.read("mlp.model");
				Instance inst = new DenseInstance(5);
				inst.setValue(0, ram);
				inst.setValue(1, cpuutil);
				inst.setValue(2, vmhostutil);
				inst.setValue(3, vmsla);
				double clsLabel = mlp.classifyInstance(inst);
				if (clsLabel == 1) {
					vmToMigrate = vm;
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return vmToMigrate;
	}
	
	public static List<Integer> getHostUtil(PowerHost host, List<PowerVm> migratableVms){
		List<Integer> scores = new ArrayList<Integer>(); 
		List<PowerVm> hostVmList = host.getVmList();
		double migratableVmUtilization = 0.0;
		double totalHostUtilization = 0.0;
		for(PowerVm vm: hostVmList) {
			totalHostUtilization += vm.getTotalUtilizationOfCpuMips(CloudSim.clock()) / host.getTotalMips();
		}
		
		for(PowerVm vm: migratableVms) {
			migratableVmUtilization = vm.getTotalUtilizationOfCpuMips(CloudSim.clock()) / host.getTotalMips();
			double hostPercentUtil = (migratableVmUtilization/totalHostUtilization) * 100.00;
	        int hostPercentUtilInt = (int) Math.round(hostPercentUtil);
			scores.add(hostPercentUtilInt);		
		}
		return scores;
	}
}
