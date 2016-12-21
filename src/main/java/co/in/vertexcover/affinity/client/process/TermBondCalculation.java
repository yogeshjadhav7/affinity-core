package co.in.vertexcover.affinity.client.process;

import co.in.vertexcover.affinity.client.constants.StringConstants;
import co.in.vertexcover.affinity.client.dto.Affinity;
import co.in.vertexcover.affinity.core.dto.AffinityCalculationData;
import co.in.vertexcover.affinity.core.processor.TermBondCalculationProcessor;

public class TermBondCalculation extends Process {
	
	final static public String PROCESS_NAME = "TERM_BOND_CALCULATION";
	private AffinityCalculationData affinityCalculationData;
	
	public TermBondCalculation() {
		// TODO Auto-generated constructor stub
	}
	
	public TermBondCalculation(final Affinity affinity, final String outputDirectoryPath) throws Exception {
		super(PROCESS_NAME, outputDirectoryPath 
				+ PROCESS_NAME 
				+ StringConstants.OUTPUT_FILE_EXTENSION);
		
		affinity.addProcess(PROCESS_NAME, this);
		this.affinityCalculationData = (AffinityCalculationData) affinity.getProcess(AffinityCalculation.PROCESS_NAME).processData;
	}
	
	
	@Override
	public void mainProcess(final Affinity affinity) throws Exception {
    	this.processData = new TermBondCalculationProcessor().getTermBonds(this.affinityCalculationData, affinity.getConfigurations().getTERM_BOND_SCALE_LENGTH());
	}
	
}
