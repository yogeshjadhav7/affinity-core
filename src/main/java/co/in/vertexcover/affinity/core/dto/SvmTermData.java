package co.in.vertexcover.affinity.core.dto;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import co.in.vertexcover.affinity.client.process.ProcessData;

public class SvmTermData extends ProcessData {
	
	private String term;
	private double kappaScore;
	private LinkedHashMap<String, Double> entityAffinity;
	private double[] coordinatesOfW;
	
	
	public SvmTermData() {}
	
	public SvmTermData(final String term, final double[] affinityScore, final List<String> entityList, final double kappaScore,
			final double[] cooredinatesOfW) {
		
		this.term = term;
		this.entityAffinity = new LinkedHashMap<>();
		this.kappaScore = kappaScore;
		int entityCounter = 0;
		for(String entity : entityList) {
			this.entityAffinity.put(entity, affinityScore[entityCounter]);
			entityCounter++;
		}
		
		sortMapByValues();
		this.coordinatesOfW = cooredinatesOfW;
	}
	
	private void sortMapByValues() {
        List<Map.Entry<String, Double>> list =
                new LinkedList<Map.Entry<String, Double>>(entityAffinity.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> object1, Map.Entry<String, Double> object2) {
                return (object2.getValue()).compareTo(object1.getValue());
            }
        });

        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        this.entityAffinity = (LinkedHashMap<String, Double>) sortedMap;
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
