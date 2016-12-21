package co.in.vertexcover.affinity.client.dto;

import co.in.vertexcover.affinity.core.dto.InputData;
import co.in.vertexcover.affinity.core.pojo.Term;

public class Configurations {

	final private int MAX_MDS_DIMENSIONS = 50;
	final private double MIN_TERM_OCCURENCE_PERCENTAGE_DEFAULT = 2;
	private double TERM_OVERLAPPING_RATIO;
	private int MDS_DIMENSIONS;
	private double MIN_TERM_OCCURENCE_PERCENTAGE = MIN_TERM_OCCURENCE_PERCENTAGE_DEFAULT;
	private String ROOT_PATH;
	private int SCALE_LENGTH = 10;
	private int TERM_BOND_SCALE_LENGTH = 100;
	
	public Configurations() { }
	
	public Configurations(final String rootPath) {
		this.ROOT_PATH = rootPath;
	}
	
	public Configurations(final double minTermOccurencePercentage) { 
		this.MIN_TERM_OCCURENCE_PERCENTAGE = Math.abs(minTermOccurencePercentage) % 101;
	}
	
	
	public Configurations(final InputData inputData) {
		if(inputData == null || inputData.getEntityList() == null || inputData.getTermList() == null || inputData.getTermData() == null
				|| inputData.getEntityList().size() == 0 || inputData.getTermList().size() == 0)
			return;
	
		setMdsDimensions(inputData);
	}
	
	
	public Configurations(final InputData inputData, final Configurations configurations) {
		if(inputData == null || inputData.getEntityList() == null || inputData.getTermList() == null || inputData.getTermData() == null
				|| inputData.getEntityList().size() == 0 || inputData.getTermList().size() == 0)
			return;
	
		setMdsDimensions(inputData);
		this.MIN_TERM_OCCURENCE_PERCENTAGE = Math.abs(configurations.getMIN_TERM_OCCURENCE_PERCENTAGE()) % 101;
		this.ROOT_PATH = configurations.getROOT_PATH();
	}
	
	
	private void setMdsDimensions(final InputData inputData) {
		this.TERM_OVERLAPPING_RATIO = 0;
		for (Term term : inputData.getTermData().values()) {
			this.TERM_OVERLAPPING_RATIO += term.getEntityExistenceStrength();
		}
		this.TERM_OVERLAPPING_RATIO /= (inputData.getEntityList().size() * inputData.getTermList().size());
		this.MDS_DIMENSIONS = (inputData.getEntityList().size() < this.MAX_MDS_DIMENSIONS)?inputData.getEntityList().size():this.MAX_MDS_DIMENSIONS;
		this.MDS_DIMENSIONS *=  (0.5 * (1 + TERM_OVERLAPPING_RATIO));
	}

	
	public int getMAX_MDS_DIMENSIONS() {
		return MAX_MDS_DIMENSIONS;
	}

	public double getTERM_OVERLAPPING_RATIO() {
		return TERM_OVERLAPPING_RATIO;
	}

	public int getMDS_DIMENSIONS() {
		return MDS_DIMENSIONS;
	}

	public double getMIN_TERM_OCCURENCE_PERCENTAGE() {
		return MIN_TERM_OCCURENCE_PERCENTAGE;
	}
	
	public String getROOT_PATH() {
		return ROOT_PATH;
	}

	public int getSCALE_LENGTH() {
		return SCALE_LENGTH;
	}

	public void setSCALE_LENGTH(int sCALE_LENGTH) {
		SCALE_LENGTH = sCALE_LENGTH;
	}

	public int getTERM_BOND_SCALE_LENGTH() {
		return TERM_BOND_SCALE_LENGTH;
	}

	public void setTERM_BOND_SCALE_LENGTH(int tERM_BOND_SCALE_LENGTH) {
		if(tERM_BOND_SCALE_LENGTH < 10)
			TERM_BOND_SCALE_LENGTH = 10;
		else
		if(tERM_BOND_SCALE_LENGTH > 1000000)
			TERM_BOND_SCALE_LENGTH = 1000000;
		else
			TERM_BOND_SCALE_LENGTH = tERM_BOND_SCALE_LENGTH;
	}


	
	
	
	
}
