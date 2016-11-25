package co.in.vertexcover.affinity.core.dto;

import java.util.LinkedHashMap;
import java.util.List;

public class SvmData {
	
	private String term;
	private double kappaScore;
	private LinkedHashMap<String, Double> entityAffinity;
	private double[] coordinatesOfW;
	
	
	public SvmData() {}
	
	public SvmData(final String term, final double[] affinityScore, final List<String> entityList, final double kappaScore,
			final double[] cooredinatesOfW) {
		
		this.term = term;
		this.entityAffinity = new LinkedHashMap<>();
		this.kappaScore = kappaScore;
		int entityCounter = 0;
		for(String entity : entityList) {
			this.entityAffinity.put(entity, affinityScore[entityCounter]);
			entityCounter++;
		}
	}
	
	
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public double getKappaScore() {
		return kappaScore;
	}
	public void setKappaScore(double kappaScore) {
		this.kappaScore = kappaScore;
	}
	public LinkedHashMap<String, Double> getEntityAffinity() {
		return entityAffinity;
	}
	public void setEntityAffinity(LinkedHashMap<String, Double> entityAffinity) {
		this.entityAffinity = entityAffinity;
	}
	public double[] getCoordinatesOfW() {
		return coordinatesOfW;
	}
	public void setCoordinatesOfW(double[] coordinatesOfW) {
		this.coordinatesOfW = coordinatesOfW;
	}

}
