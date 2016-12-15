package co.in.vertexcover.affinity.client;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import co.in.vertexcover.affinity.core.dto.InputData;
import co.in.vertexcover.affinity.core.dto.InputValidationData;
import co.in.vertexcover.affinity.core.processors.InputProcessor;
import co.in.vertexcover.affinity.helpers.JsonObjectMapper;

public class InputValidation extends Process {
	
	final static private String PROCESS_NAME = "INPUT_VALIDATION";
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
		this.processInProgress = true;
		this.processStartedAt = new Date().getTime();
		
		if(inputFile == null || !inputFile.exists()) {
			this.processException = new Exception("Input file at path " + inputFile.getAbsolutePath() + " doesn't exist");
			throw this.processException;
		}
	}
	
	
	@Override
	public void mainProcess(final Affinity affinity) throws Exception {
    	InputValidationData inputValidationData = new InputProcessor().validate(inputFile, affinity.getConfigurations().getMIN_TERM_OCCURENCE_PERCENTAGE());
    	if(!inputValidationData.isValid()) {
    		this.processException = new Exception(inputValidationData.getErrorMessage());
    		throw this.processException;
    	}
    	
    	this.processData = inputValidationData.getInputData();
	}
	
	
	
	@Override
	public void postProcess(final Affinity affinity) throws Exception {
		try {
			final String fileContent = JsonObjectMapper.toJsonString(this.processData, true);
			FileUtils.writeStringToFile(this.outputFile, fileContent, false);
		} catch(Exception e) {
			this.processException = e;
		}
		
		this.processCompleted = true;
		this.processCompletedAt = new Date().getTime();
		if(this.processException != null) {
			throw this.processException;
		}
	}
	

}
