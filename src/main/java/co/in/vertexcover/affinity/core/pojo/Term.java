package co.in.vertexcover.affinity.core.pojo;

import java.util.HashMap;
import java.util.Map;

public class Term {

	private String termName;
	private Map<String, Boolean> entityExistence;
	private int entityExistenceStrength;
	private int sumOfTermWeights;
	
	public Term() {}
	
	public Term(final String term) {
		createTerm(term);
	}
	
	public Term(final String term, final String entity, final int termWeight) {
		createTerm(term);
		addEntityExistence(entity, termWeight);
	}
	
	
	private void createTerm(final String term) {
		this.termName = term;
		this.entityExistence = new HashMap<>();
		this.entityExistenceStrength = 0;
		this.sumOfTermWeights = 0;
	}
	
	
	public void addEntityExistence(final String entity, final int termWeight) {
		this.entityExistence.put(entity, (termWeight > 0)?true:false);
		this.setEntityExistenceStrength(this.entityExistenceStrength + ((termWeight > 0)?1:0)); 
		this.sumOfTermWeights += termWeight;
	}
	
	
	public String getTermName() {
		return termName;
	}
	
	public void setTermName(String termName) {
		this.termName = termName;
	}
	
	public Map<String, Boolean> getEntityExistence() {
		return entityExistence;
	}
	
	public void setEntityExistence(Map<String, Boolean> entityExistence) {
		this.entityExistence = entityExistence;
	}

	public int getEntityExistenceStrength() {
		return entityExistenceStrength;
	}

	public void setEntityExistenceStrength(int entityExistenceStrength) {
		this.entityExistenceStrength = entityExistenceStrength;
	}

	public int getSumOfTermWeights() {
		return sumOfTermWeights;
	}

	public void setSumOfTermWeights(int sumOfTermWeights) {
		this.sumOfTermWeights = sumOfTermWeights;
	}
	

}
