package co.in.vertexcover.affinity.core.processors;

import co.in.vertexcover.affinity.core.dto.SvmData;
import co.in.vertexcover.affinity.core.dto.SvmProcessData;
import co.in.vertexcover.affinity.core.dto.TermRelationData;

public class TermRelationProcessor {
	
	public TermRelationData getTermRelation(SvmProcessData svmProcessData) {
		TermRelationData termRelationData = new TermRelationData();
    	for(SvmData D1 : svmProcessData.getData().values()) {
    		for(SvmData D2 : svmProcessData.getData().values()) {
    			if(termRelationData.relationScoreExists(D1.getTerm(), D2.getTerm()))
    				continue;
    			
    			double angularDifferenceValue = getAngularDifference(D1, D2);
    			termRelationData.addRelationScore(D1.getTerm(), D2.getTerm(), angularDifferenceValue);
    		}
    	}
		
		return termRelationData;
	}
	
	
	private double getAngularDifference(SvmData D1, SvmData D2) {
		if(D1.getTerm().equals(D2.getTerm()))
			return 0;
		
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

}
