package co.in.vertexcover.affinity.client;

import co.in.vertexcover.affinity.core.dto.InputData;
import co.in.vertexcover.affinity.core.pojo.Term;

public class Configurations {

	final private int MAX_MDS_DIMENSIONS = 50;
	final private double MIN_TERM_OCCURENCE_PERCENTAGE_DEFAULT = 2;
	private double TERM_OVERLAPPING_RATIO;
	private double MDS_DIMENSIONS;
	private double MIN_TERM_OCCURENCE_PERCENTAGE = MIN_TERM_OCCURENCE_PERCENTAGE_DEFAULT;
	private String ROOT_PATH;
	
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
	
	
	public Configurations(final InputData inputData, final double minTermOccurencePercentage) {
		if(inputData == null || inputData.getEntityList() == null || inputData.getTermList() == null || inputData.getTermData() == null
				|| inputData.getEntityList().size() == 0 || inputData.getTermList().size() == 0)
			return;
	
		setMdsDimensions(inputData);
		this.MIN_TERM_OCCURENCE_PERCENTAGE = Math.abs(minTermOccurencePercentage) % 101;
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

	public double getMDS_DIMENSIONS() {
		return MDS_DIMENSIONS;
	}

	public double getMIN_TERM_OCCURENCE_PERCENTAGE() {
		return MIN_TERM_OCCURENCE_PERCENTAGE;
	}
	
	public String getROOT_PATH() {
		return ROOT_PATH;
	}
	
	
}
