package co.in.vertexcover.affinity.client.dto;

import org.apache.commons.io.FileUtils;

import co.in.vertexcover.affinity.core.dto.InputData;
import co.in.vertexcover.affinity.core.pojo.Term;
import co.in.vertexcover.affinity.utils.JsonObjectMapper;

public class Configurations {

	private double MDS_DIMENSIONS_REDUCTION_FACTOR = 1;
	final private int MAX_MDS_DIMENSIONS = 50;
	final private double MIN_TERM_OCCURENCE_PERCENTAGE_DEFAULT = 0;
	private double TERM_OVERLAPPING_RATIO;
	private int MDS_DIMENSIONS;
	private double MIN_TERM_OCCURENCE_PERCENTAGE = MIN_TERM_OCCURENCE_PERCENTAGE_DEFAULT;
	private String ROOT_PATH;
	private Integer SCALE_LENGTH = 10;
	private int TERM_BOND_SCALE_LENGTH = 100;
	private boolean doClassification = false;
	
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
		
		transferConfigurableConfigurations(configurations);
		computeTermOverLappingRatio(inputData);
		
		// If Dimensions is set to 0 then set Dimensions on the basis of nature of input data
		if(this.MDS_DIMENSIONS == 0) {
			setMdsDimensions(inputData);
		}
	}
	
	private void computeTermOverLappingRatio(final InputData inputData) {
		this.TERM_OVERLAPPING_RATIO = 0;
		for (Term term : inputData.getTermData().values()) {
			this.TERM_OVERLAPPING_RATIO += term.getEntityExistenceStrength();
		}
		this.TERM_OVERLAPPING_RATIO /= (inputData.getEntityList().size() * inputData.getTermList().size());
	}
	
	private void setMdsDimensions(final InputData inputData) {
		this.MDS_DIMENSIONS = (inputData.getEntityList().size() < this.MAX_MDS_DIMENSIONS)?(inputData.getEntityList().size() - 1) : this.MAX_MDS_DIMENSIONS;
		this.MDS_DIMENSIONS *=  (0.75 + 0.25 * TERM_OVERLAPPING_RATIO);
		this.MDS_DIMENSIONS *= this.MDS_DIMENSIONS_REDUCTION_FACTOR;
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

	public double getMDS_DIMENSIONS_REDUCTION_FACTOR() {
		return MDS_DIMENSIONS_REDUCTION_FACTOR;
	}

	public void setMDS_DIMENSIONS_REDUCTION_FACTOR(double mDS_DIMENSIONS_REDUCTION_FACTOR) {
		MDS_DIMENSIONS_REDUCTION_FACTOR = mDS_DIMENSIONS_REDUCTION_FACTOR;
	}

	public double getTERM_OVERLAPPING_RATIO() {
		return TERM_OVERLAPPING_RATIO;
	}

	public void setTERM_OVERLAPPING_RATIO(double tERM_OVERLAPPING_RATIO) {
		TERM_OVERLAPPING_RATIO = tERM_OVERLAPPING_RATIO;
	}

	public int getMDS_DIMENSIONS() {
		return MDS_DIMENSIONS;
	}

	public void setMDS_DIMENSIONS(int mDS_DIMENSIONS) {
		MDS_DIMENSIONS = mDS_DIMENSIONS;
	}

	public double getMIN_TERM_OCCURENCE_PERCENTAGE() {
		return MIN_TERM_OCCURENCE_PERCENTAGE;
	}

	public void setMIN_TERM_OCCURENCE_PERCENTAGE(double mIN_TERM_OCCURENCE_PERCENTAGE) {
		MIN_TERM_OCCURENCE_PERCENTAGE = mIN_TERM_OCCURENCE_PERCENTAGE;
	}

	public String getROOT_PATH() {
		return ROOT_PATH;
	}

	public void setROOT_PATH(String rOOT_PATH) {
		ROOT_PATH = rOOT_PATH;
	}

	public Integer getSCALE_LENGTH() {
		return SCALE_LENGTH;
	}

	public void setSCALE_LENGTH(Integer sCALE_LENGTH) {
		SCALE_LENGTH = sCALE_LENGTH;
	}

	public boolean isDoClassification() {
		return doClassification;
	}

	public void setDoClassification(boolean doClassification) {
		this.doClassification = doClassification;
	}

	public int getMAX_MDS_DIMENSIONS() {
		return MAX_MDS_DIMENSIONS;
	}

	public double getMIN_TERM_OCCURENCE_PERCENTAGE_DEFAULT() {
		return MIN_TERM_OCCURENCE_PERCENTAGE_DEFAULT;
	}

	public int getTERM_BOND_SCALE_LENGTH() {
		return TERM_BOND_SCALE_LENGTH;
	}
	
	public void transferConfigurableConfigurations(final Configurations configurations) {
		this.MDS_DIMENSIONS_REDUCTION_FACTOR = configurations.MDS_DIMENSIONS_REDUCTION_FACTOR;
		this.MDS_DIMENSIONS = configurations.MDS_DIMENSIONS;
		this.ROOT_PATH = configurations.ROOT_PATH;
		this.SCALE_LENGTH = configurations.SCALE_LENGTH;
		this.TERM_BOND_SCALE_LENGTH = configurations.TERM_BOND_SCALE_LENGTH;
		this.doClassification = configurations.doClassification;
	}


}
