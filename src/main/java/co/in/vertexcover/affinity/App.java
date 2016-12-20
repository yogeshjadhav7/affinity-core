package co.in.vertexcover.affinity;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import co.in.vertexcover.affinity.core.dto.InputValidationData;
import co.in.vertexcover.affinity.core.dto.MdsData;
import co.in.vertexcover.affinity.core.dto.SvmTermData;
import co.in.vertexcover.affinity.core.dto.AffinityCalculationData;
import co.in.vertexcover.affinity.core.dto.TermBondCalculationData;
import co.in.vertexcover.affinity.core.pojo.Term;
import co.in.vertexcover.affinity.core.processors.InputValidationProcessor;
import co.in.vertexcover.affinity.core.processors.InputProcessingProcessor;
import co.in.vertexcover.affinity.core.processors.AffinityCalculationProcessor;
import co.in.vertexcover.affinity.core.processors.TermBondCalculationProcessor;
import co.in.vertexcover.affinity.helpers.JsonObjectMapper;

public class App {
    public static void main( String[] args ) throws IOException {
    
    	/*
		final String inputFileName = "input.txt";
		final String inputFilePath = App.getProjectPath() + "affinity-data" + File.separator + inputFileName;  
    	File inputFile = new File(inputFilePath);
    	InputValidationData ipResponse = new InputValidationProcessor().validate(inputFile, 0);
    	if(!ipResponse.isValid()) {
    		System.out.println(ipResponse.getErrorMessage());
    		return;
    	}
    	
    	System.out.println("Validation done...");
    	
    	
    	final String inputDataFileName = "input-data.txt";
		final String inputDataFilePath = App.getProjectPath() + "affinity-data" + File.separator + inputDataFileName;  
    	File inputDataFile = new File(inputDataFilePath);
    	FileUtils.writeStringToFile(inputDataFile, JsonObjectMapper.toJsonString(ipResponse, true), false);
    	
    	int entityStrength = (ipResponse.getInputData().getEntityList().size() >= 50)?(50):(ipResponse.getInputData().getEntityList().size());
    	int mdsDimensions = (int) ((entityStrength / 2) *(1 + ipResponse.getInputData().getTermDistributionRatio()));
    	MdsData mdsData = new InputProcessingProcessor(ipResponse.getInputData()).process(mdsDimensions);
    	System.out.println("MDS done" + mdsData.getTermList().size());
    	
    	final String mdsOutputFileName = "mdsOutput.txt";
    	final String mdsOutputFilePath = App.getProjectPath() + "affinity-data" + File.separator + mdsOutputFileName;
    	File mdsOutputFile = new File(mdsOutputFilePath);
    	FileUtils.writeStringToFile(mdsOutputFile, "", false);
    	String line = JsonObjectMapper.toJsonString(mdsData, true);
    	FileUtils.writeStringToFile(mdsOutputFile, line, true);
    	*/
    
    	
    	/*
    	final String mdsOutputFileName = "mdsOutput.txt";
    	final String mdsOutputFilePath = App.getProjectPath() + "affinity-data" + File.separator + mdsOutputFileName;
    	File mdsOutputFile = new File(mdsOutputFilePath);
    	String mdsDataString = FileUtils.readFileToString(mdsOutputFile);
    	MdsData mdsData = (MdsData) JsonObjectMapper.toObject(mdsDataString, MdsData.class);
		final String outputFileName = "output.txt";
		final String outputFilePath = App.getProjectPath() + "affinity-data" + File.separator + outputFileName;  
    	File outputFile = new File(outputFilePath);
    	FileUtils.writeStringToFile(outputFile, "", false);
    	AffinityCalculationData processData = new AffinityCalculationData();
    	
    	for(String term : mdsData.getTermList()) {
    		System.out.println("Processing for term " + term);
    		SvmTermData svmData = new AffinityCalculationProcessor(10, term, mdsData).process();
    		processData.addToData(svmData);
    		FileUtils.writeStringToFile(outputFile, JsonObjectMapper.toJsonString(processData, true), false);
    	}
    	*/
    	
    	
    	final String outputFileName = "output.txt";
    	final String outputFilePath = App.getProjectPath() + "affinity-data" + File.separator + outputFileName;
    	File outputFile = new File(outputFilePath);
    	String outputDataString = FileUtils.readFileToString(outputFile);
    	AffinityCalculationData svmProcessData = (AffinityCalculationData) JsonObjectMapper.toObject(outputDataString, AffinityCalculationData.class);
		final String termRelationFileName = "term-relation.txt";
		final String termRelationFilePath = App.getProjectPath() + "affinity-data" + File.separator + termRelationFileName;  
    	File termRelationFile = new File(termRelationFilePath);
    	FileUtils.writeStringToFile(termRelationFile, "", false);
    	TermBondCalculationData termRelationData = new TermBondCalculationProcessor().getTermBonds(svmProcessData, 100);
    	FileUtils.writeStringToFile(termRelationFile, JsonObjectMapper.toJsonString(termRelationData, true), false);
    	
    	
    }
    
    
    public static String getProjectPath() {
    	String projectPath = new File(".").getAbsolutePath();
    	projectPath = projectPath.substring(0, projectPath.length() - 1);
    	return projectPath;
    } 
}
