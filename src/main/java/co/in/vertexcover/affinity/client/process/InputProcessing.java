package co.in.vertexcover.affinity.client.process;

import java.util.Date;
import org.apache.commons.io.FileUtils;

import co.in.vertexcover.affinity.client.constants.StringConstants;
import co.in.vertexcover.affinity.client.dto.Affinity;
import co.in.vertexcover.affinity.client.dto.Configurations;
import co.in.vertexcover.affinity.core.dto.InputData;
import co.in.vertexcover.affinity.core.dto.MdsData;
import co.in.vertexcover.affinity.core.processor.InputProcessingProcessor;
import co.in.vertexcover.affinity.utils.JsonObjectMapper;

public class InputProcessing extends Process {

	final static public String PROCESS_NAME = "INPUT_PROCESSING";
	private InputData inputData;
	
	public InputProcessing() {
		// TODO Auto-generated constructor stub
	}
	
	public InputProcessing(final Affinity affinity, final String outputDirectoryPath) throws Exception {
		super(PROCESS_NAME, outputDirectoryPath 
				+ PROCESS_NAME 
				+ StringConstants.OUTPUT_FILE_EXTENSION);
		
		affinity.addProcess(PROCESS_NAME, this);
		this.inputData = (InputData) affinity.getProcess(InputValidation.PROCESS_NAME).processData;
		final Configurations newConfigurations = new Configurations(this.inputData, affinity.getConfigurations());
		affinity.setConfigurations(newConfigurations);
	}
	
	
	@Override
	public void mainProcess(final Affinity affinity) throws Exception {
    	MdsData mdsData = new InputProcessingProcessor(inputData).process(affinity.getConfigurations().getMDS_DIMENSIONS());
    	if(mdsData == null || mdsData.getMdsMatrix() == null) {
    		this.processException = new Exception("Input couldn't be processed");
    		throw this.processException;
    	}
    	
    	this.processData = mdsData;
	}
	
}
