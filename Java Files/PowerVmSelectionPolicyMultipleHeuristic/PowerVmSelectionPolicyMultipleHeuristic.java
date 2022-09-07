package org.cloudbus.cloudsim.power;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.examples.power.Helper;

public class PowerVmSelectionPolicyMultipleHeuristic extends PowerVmSelectionPolicy {

	protected static List<Double> sla;
	protected static List<Integer> hostPercentUtilScores;

	@Override
	public Vm getVmToMigrate(PowerHost host) {
		List<PowerVm> migratableVms = getMigratableVms(host); // gets set of all VMs for migration
		if (migratableVms.isEmpty()) {
			return null;
		}
		sla = Helper.getSla(migratableVms); // prioritize moving lowest of these
		hostPercentUtilScores = getHostUtil(host, migratableVms);
		/* try {
			  Thread.sleep(2000);
			} catch (InterruptedException e) {
			  Thread.currentThread().interrupt();
			}
		*/
		List<PowerVm> actualMigratableVms = new ArrayList<PowerVm>();

		// getting total energy used
		
		for (PowerVm vm : migratableVms) {
			if (vm.isInMigration()) {
				continue;
			}
			// algorithm goes here
			int ram = vm.getRam();
			double mips = vm.getMips();
			double reqmips = vm.getCurrentRequestedTotalMips();
			double cpuutil = vm.getTotalUtilizationOfCpu(CloudSim.clock());
			actualMigratableVms.add(vm);
		}
		order(actualMigratableVms);
		PowerVm migrate = actualMigratableVms.get((int)(actualMigratableVms.size()/2));
		return migrate;
	}
	
	public static List<Integer> getHostUtil(PowerHost host, List<PowerVm> migratableVms){
		double totalEnergy = 0.0;
		List<Integer> scores = new ArrayList<Integer>(); 
		List<PowerHost> hostList = host.getDatacenter().getHostList();
		
		for(PowerHost h: hostList) {
			totalEnergy = totalEnergy + h.getPower();
		}
		
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
	
	private static void order(List<PowerVm> vms) {
	    Collections.sort(vms, new Comparator() {
			public int compare(Object o1, Object o2) {
	            double y1 = ((PowerVm)o1).getTotalUtilizationOfCpuMips(CloudSim.clock())/((PowerVm)o1).getMips();
	            double y2 = ((PowerVm)o2).getTotalUtilizationOfCpuMips(CloudSim.clock())/((PowerVm)o2).getMips();
	            int cComp = (int)((y2-y1)*100);
	            if (Math.abs(cComp) > 10) {
	               return cComp;
	            }
	            int x1 = ((PowerVm)o1).getRam();
	            int x2 = ((PowerVm)o2).getRam();
	            int rComp = x1-x2;
	            if(rComp != 0) {
					return rComp;
				}
	        	int w1 = hostPercentUtilScores.get(vms.indexOf((PowerVm)o1));
	        	int w2 = hostPercentUtilScores.get(vms.indexOf((PowerVm)o2));
	        	int pComp = w2-w1;
	            if (Math.abs(pComp) > 10) {
	            	return pComp;
	            }
	        	double z1 = sla.get(vms.indexOf((PowerVm)o1));
	        	double z2 = sla.get(vms.indexOf((PowerVm)o2));
	        	int sComp = (int)((z2-z1)*1000);
	            if (Math.abs(sComp) > 10) {
	            	return sComp;
		        }
	            return rComp;
	    }});
	}

}