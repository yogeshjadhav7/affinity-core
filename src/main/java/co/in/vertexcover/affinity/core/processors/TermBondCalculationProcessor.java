package co.in.vertexcover.affinity.core.processors;

import co.in.vertexcover.affinity.core.dto.SvmTermData;

import java.util.HashMap;
import java.util.Map;

import co.in.vertexcover.affinity.core.dto.AffinityCalculationData;
import co.in.vertexcover.affinity.core.dto.TermBondCalculationData;

public class TermBondCalculationProcessor {
	
	public TermBondCalculationData getTermBonds(final AffinityCalculationData affinityCalculationData, final int termBondScaleLength) {
		TermBondCalculationData termBondCalculationData = new TermBondCalculationData();
		double highestTermBondStrength = 0;
    	for(SvmTermData D1 : affinityCalculationData.getData().values()) {
    		for(SvmTermData D2 : affinityCalculationData.getData().values()) {
    			if(termBondCalculationData.doesBondExist(D1.getTerm(), D2.getTerm()))
    				continue;
    			
    			double angularDifferenceValue = getAngularDifference(D1, D2, termBondScaleLength);
    			termBondCalculationData.addBondStrength(D1.getTerm(), D2.getTerm(), angularDifferenceValue);
    			if(highestTermBondStrength < angularDifferenceValue)
    				highestTermBondStrength = angularDifferenceValue;
    		}
    	}
		
		return scaleBondStrengths(termBondCalculationData, highestTermBondStrength, termBondScaleLength);
	}
	
	
	private double getAngularDifference(SvmTermData D1, SvmTermData D2, final int termBondScaleLength) {
		if(D1.getTerm().equals(D2.getTerm()))
			return termBondScaleLength;
		
		double A = 0;
		double B = 0;
		double C = 0;
		int numberOfCoordinates = D1.getCoordinatesOfW().length;
		for(int i = 0; i < numberOfCoordinates; i++) {
			double value1 = D1.getCoordinatesOfW()[i];
			double value2 = D2.getCoordinatesOfW()[i];
			A += (value1 * value2);
			B += (value1 * value1);
			C += (value2 * value2);
		}
		
		return 100 * Math.abs(A) / (Math.sqrt(B) * Math.sqrt(C));
	}

	
	private TermBondCalculationData scaleBondStrengths(final TermBondCalculationData termBondCalculationData, final double highestTermBondStrength, 
			final int termBondScaleLength) {
		
		final TermBondCalculationData scaledTermBondCalculationData = new TermBondCalculationData();
		final double scalingRatio = (double)(termBondScaleLength) / highestTermBondStrength;
		HashMap<String, HashMap<String, Double>> bondStrengths = termBondCalculationData.getBondStrengths();
		for (Map.Entry<String, HashMap<String, Double>> entry : bondStrengths.entrySet()) {
		    String term1 = entry.getKey();
		    HashMap<String, Double> termOneMap = entry.getValue();
		    for(Map.Entry<String, Double> entry2 : termOneMap.entrySet()) {
		    	String term2 = entry2.getKey();
		    	if(scaledTermBondCalculationData.doesBondExist(term1, term2))
		    		continue;
		    	
		    	double bondStrength = entry2.getValue() * scalingRatio;
		    	scaledTermBondCalculationData.addBondStrength(term1, term2, bondStrength);
		    }
		    
		}
		
		return scaledTermBondCalculationData;
	}
	
}
