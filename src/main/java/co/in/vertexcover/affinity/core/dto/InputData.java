package co.in.vertexcover.affinity.core.dto;

import java.util.List;
import java.util.Map;

import co.in.vertexcover.affinity.client.process.ProcessData;
import co.in.vertexcover.affinity.core.pojo.Entity;
import co.in.vertexcover.affinity.core.pojo.Term;

public class InputData extends ProcessData {
	private Map<String, Entity> entityData;
	private Map<String, Term> termData;
	private List<String> entityList;
	private List<String> termList;
	private double termDistributionRatio;
	
	public InputData(final Map<String, Entity> entityData, final Map<String, Term> termData, final List<String> entityList, final List<String> termList) {
		this.entityData = entityData;
		this.termData = termData;
		this.entityList = entityList;
		this.termList = termList;
		
		double termDistributionScores = 0;
		for (Term term : termData.values()) {
			termDistributionScores += term.getEntityExistenceStrength();
		}
		this.termDistributionRatio = (termDistributionScores) / (this.entityList.size() * this.termList.size());
	}
	
	public Map<String, Entity> getEntityData() {
		return entityData;
	}
	public void setEntityData(Map<String, Entity> entityData) {
		this.entityData = entityData;
	}
	public Map<String, Term> getTermData() {
		return termData;
	}
	public void setTermData(Map<String, Term> termData) {
		this.termData = termData;
	}

	public List<String> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<String> entityList) {
		this.entityList = entityList;
	}

	public List<String> getTermList() {
		return termList;
	}

	public void setTermList(List<String> termList) {
		this.termList = termList;
	}

	public double getTermDistributionRatio() {
		return termDistributionRatio;
	}

	public void setTermDistributionRatio(double termDistributionRatio) {
		this.termDistributionRatio = termDistributionRatio;
	}
	

}
