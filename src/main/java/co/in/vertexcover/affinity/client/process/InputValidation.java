package co.in.vertexcover.affinity.client.process;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import co.in.vertexcover.affinity.client.constants.StringConstants;
import co.in.vertexcover.affinity.client.dto.Affinity;
import co.in.vertexcover.affinity.core.dto.InputData;
import co.in.vertexcover.affinity.core.dto.InputValidationData;
import co.in.vertexcover.affinity.core.processor.InputValidationProcessor;
import co.in.vertexcover.affinity.utils.JsonObjectMapper;

public class InputValidation extends Process {
	
	final static public String PROCESS_NAME = "INPUT_VALIDATION";
	private File inputFile;
	
	
	public InputValidation() {
		// TODO Auto-generated constructor stub
	}
	
	public InputValidation(final Affinity affinity, final String inputFilePath, final String outputDirectoryPath) throws Exception {
		super(PROCESS_NAME, outputDirectoryPath 
				+ PROCESS_NAME 
				+ StringConstants.OUTPUT_FILE_EXTENSION);
		
		affinity.addProcess(PROCESS_NAME, this);
		this.inputFile = new File(inputFilePath);
	}
	
	
	@Override
	public void preProcess(final Affinity affinity) throws Exception {
		super.preProcess(affinity);
		if(inputFile == null || !inputFile.exists()) {
			this.processException = new Exception("Input file at path " + inputFile.getAbsolutePath() + " doesn't exist");
			throw this.processException;
		}
	}
	
	
	@Override
	public void mainProcess(final Affinity affinity) throws Exception {
    	InputValidationData inputValidationData = new InputValidationProcessor().validate(inputFile, affinity.getConfigurations());
    	if(!inputValidationData.isValid()) {
    		this.processException = new Exception(inputValidationData.getErrorMessage());
    		throw this.processException;
    	}
    	
    	this.processData = inputValidationData.getInputData();
	}

}
