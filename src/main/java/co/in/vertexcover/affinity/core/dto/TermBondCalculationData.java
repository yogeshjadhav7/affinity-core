package co.in.vertexcover.affinity.core.dto;

import java.util.HashMap;

import co.in.vertexcover.affinity.client.ProcessData;

public class TermBondCalculationData extends ProcessData {

	private HashMap<String, HashMap<String, Double>> bondStrengths;
	
	public TermBondCalculationData() {
		bondStrengths = new HashMap<String, HashMap<String,Double>>();
	}

	public boolean doesBondExist(String term1, String term2) {
		if(!bondStrengths.containsKey(term1))
			return false;
					
		if(!bondStrengths.containsKey(term2))
			return false;
		
		if(!bondStrengths.get(term1).containsKey(term2))
			return false;
		
		if(!bondStrengths.get(term2).containsKey(term1))
			return false;
			
		return true;
	}
	
	public HashMap<String, HashMap<String, Double>> getBondStrengths() {
		return this.bondStrengths;
	}
	
	public double getBondStrength(String term1, String term2) {
		return bondStrengths.get(term1).get(term2);
	}

	public void addBondStrength(String term1, String term2, double bondStrength) {
		HashMap<String, Double> termOneBonds = null;
		HashMap<String, Double> termTwoBonds = null;
		
		if(bondStrengths.containsKey(term1))
			termOneBonds = bondStrengths.get(term1);
		else
			termOneBonds = new HashMap<String, Double>();
		
		if(bondStrengths.containsKey(term2))
			termTwoBonds = bondStrengths.get(term2);
		else
			termTwoBonds = new HashMap<String, Double>();		
	
		
		termOneBonds.put(term2, bondStrength);
		termTwoBonds.put(term1, bondStrength);
		
		bondStrengths.put(term1, termOneBonds);
		bondStrengths.put(term2, termTwoBonds);
	}
	
	
	
}
