package co.in.vertexcover.affinity.core.pojo;

import java.util.HashMap;
import java.util.Map;

public class Entity {
	
	private String entityName;
	private Map<String, Integer> termWeights;
	private int sumOfTermWeights;
	private int termStrength;
	
	public Entity() {}
	
	public Entity(final String entityName, final String term, final int weight) {
		this.entityName = entityName;
		this.termWeights = new HashMap<>();
		this.sumOfTermWeights = 0;
		addTermWeight(term, weight);
	}
	
	
	public void addTermWeight(final String term, int weight) {
		this.sumOfTermWeights += weight;
		weight = weight + getTermWeightOf(term);
		this.termWeights.put(term, weight);
		this.termStrength = this.termWeights.size();
	}
	
	
	public void removeTermWeight(final String term) {
		if(termWeights.containsKey(term)) {
			final int termWeight = termWeights.get(term);
			termWeights.remove(term);
			this.termStrength = this.termWeights.size();
			this.sumOfTermWeights -= termWeight;
		}
	}
	
	
	public int getTermWeightOf(final String term) {
		if(!this.termWeights.containsKey(term))
			return 0;
		return this.termWeights.get(term);
	}
	
	
	public Map<String, Integer> getTermWeight() {
		return termWeights;
	}
	
	public void setTermWeight(Map<String, Integer> termWeights) {
		this.termWeights = termWeights;
	}
	
	public int getTermStrength() {
		return termStrength;
	}
	
	public void setTermStrength(int termStrength) {
		this.termStrength = termStrength;
	}
	
	public String getEntityName() {
		return entityName;
	}
	
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}


	public int getSumOfTermWeights() {
		return sumOfTermWeights;
	}


	public void setSumOfTermWeights(int sumOfTermWeights) {
		this.sumOfTermWeights = sumOfTermWeights;
	}

}
