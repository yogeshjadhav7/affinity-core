package co.in.vertexcover.affinity.core.dto;

import java.util.List;
import java.util.Map;

import co.in.vertexcover.affinity.client.process.ProcessData;
import co.in.vertexcover.affinity.core.pojo.Entity;

public class MdsData extends ProcessData {

	private int mdsDimensions;
	private double[][] mdsMatrix;
	private List<String> entityList;
	private List<String> termList;
	private Map<String, Entity> entityData;
	
	public MdsData() {}
	
	public MdsData(final double[][] mdsMatrix, final List<String> entityList, final List<String> termList, final Map<String, Entity> entityData, final int mdsDimensions) {
		this.mdsMatrix = mdsMatrix;
		this.entityList = entityList;
		this.termList = termList;
		this.setEntityData(entityData);
		this.mdsDimensions = mdsDimensions;
	}
	
	public double[][] getMdsMatrix() {
		return mdsMatrix;
	}
	public void setMdsMatrix(double[][] mdsMatrix) {
		this.mdsMatrix = mdsMatrix;
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

	public Map<String, Entity> getEntityData() {
		return entityData;
	}

	public void setEntityData(Map<String, Entity> entityData) {
		this.entityData = entityData;
	}

	public int getMdsDimensions() {
		return mdsDimensions;
	}

	public void setMdsDimensions(int mdsDimensions) {
		this.mdsDimensions = mdsDimensions;
	}
	
	
}
