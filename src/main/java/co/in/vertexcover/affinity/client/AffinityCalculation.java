package co.in.vertexcover.affinity.client;

import co.in.vertexcover.affinity.core.dto.AffinityCalculationData;
import co.in.vertexcover.affinity.core.dto.MdsData;
import co.in.vertexcover.affinity.core.dto.SvmTermData;
import co.in.vertexcover.affinity.core.processors.AffinityCalculationProcessor;

public class AffinityCalculation extends Process {
	
	final static public String PROCESS_NAME = "AFFINITY_CALCULATION";
	private MdsData mdsData;
	
	public AffinityCalculation() {
		// TODO Auto-generated constructor stub
	}
	
	public AffinityCalculation(final Affinity affinity, final String outputDirectoryPath) throws Exception {
		super(PROCESS_NAME, outputDirectoryPath 
				+ PROCESS_NAME 
				+ StringConstants.OUTPUT_FILE_EXTENSION);
		
		affinity.addProcess(PROCESS_NAME, this);
		this.mdsData = (MdsData) affinity.getProcess(InputProcessing.PROCESS_NAME).processData;
	}
	
	
	
	@Override
	public void mainProcess(final Affinity affinity) throws Exception {
    	this.processData = new AffinityCalculationData();
    	for(String term : this.mdsData.getTermList()) {
    		System.out.println("Processing for term " + term);
    		SvmTermData svmTermData = new AffinityCalculationProcessor(affinity.getConfigurations().getSCALE_LENGTH(), 
    				term, 
    				mdsData).process();
    		((AffinityCalculationData) this.processData).addToData(svmTermData);
    		this.postProcess(affinity);
    	}
	}
	
}
