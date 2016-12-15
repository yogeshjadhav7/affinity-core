package co.in.vertexcover.affinity.core.dto;

import java.util.HashMap;

import co.in.vertexcover.affinity.client.ProcessData;

public class TermRelationData extends ProcessData {

	private HashMap<String, HashMap<String, Double>> relationScores;
	
	public TermRelationData() {
		relationScores = new HashMap<String, HashMap<String,Double>>();
	}

	public boolean relationScoreExists(String term1, String term2) {
		if(!relationScores.containsKey(term1))
			return false;
					
		if(!relationScores.containsKey(term2))
			return false;
		
		if(!relationScores.get(term1).containsKey(term2))
			return false;
		
		if(!relationScores.get(term2).containsKey(term1))
			return false;
			
		return true;
	}
	
	public HashMap<String, HashMap<String, Double>> getRelationScores() {
		return this.relationScores;
	}
	
	public double getRelationScore(String term1, String term2) {
		return relationScores.get(term1).get(term2);
	}

	public void addRelationScore(String term1, String term2, double relationScore) {
		HashMap<String, Double> termOneRelations = null;
		HashMap<String, Double> termTwoRelations = null;
		
		if(relationScores.containsKey(term1))
			termOneRelations = relationScores.get(term1);
		else
			termOneRelations = new HashMap<String, Double>();
		
		if(relationScores.containsKey(term2))
			termTwoRelations = relationScores.get(term2);
		else
			termTwoRelations = new HashMap<String, Double>();		
	
		
		termOneRelations.put(term2, relationScore);
		termTwoRelations.put(term1, relationScore);
		
		relationScores.put(term1, termOneRelations);
		relationScores.put(term2, termTwoRelations);
	}
	
	
	
}
