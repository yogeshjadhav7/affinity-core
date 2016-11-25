package co.in.vertexcover.affinity;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import co.in.vertexcover.affinity.core.dto.MdsData;
import co.in.vertexcover.affinity.core.dto.SvmData;
import co.in.vertexcover.affinity.core.processors.InputProcessor;
import co.in.vertexcover.affinity.core.processors.MatrixProcessor;
import co.in.vertexcover.affinity.core.processors.SvmProcessor;
import co.in.vertexcover.affinity.core.response.InputProcessorResponse;
import co.in.vertexcover.affinity.helpers.JsonObjectMapper;

public class App {
    public static void main( String[] args ) throws IOException {
    	
		final String inputFileName = "input.txt";
		final String inputFilePath = App.getProjectPath() + "data" + File.separator + inputFileName;  
    	File inputFile = new File(inputFilePath);
    	InputProcessorResponse ipResponse = new InputProcessor().validate(inputFile, 0);
    	if(!ipResponse.isValid()) {
    		System.out.println(ipResponse.getErrorMessage());
    		return;
    	}
    	System.out.println("Validation done...");
    	
    	MdsData mdsData = new MatrixProcessor(ipResponse.getInputData()).process((int)(ipResponse.getInputData().getEntityList().size() * 0.5));
    	System.out.println("MDS done" + mdsData.getTermList().size());
    	
    	final String mdsOutputFileName = "mdsOutput.txt";
    	final String mdsOutputFilePath = App.getProjectPath() + "data" + File.separator + mdsOutputFileName;
    	File mdsOutputFile = new File(mdsOutputFilePath);
    	FileUtils.writeStringToFile(mdsOutputFile, "", false);
    	String line = JsonObjectMapper.toJsonString(mdsData, true);
    	FileUtils.writeStringToFile(mdsOutputFile, line, true);
    	
    	
		final String outputFileName = "output.txt";
		final String outputFilePath = App.getProjectPath() + "data" + File.separator + outputFileName;  
    	File outputFile = new File(outputFilePath);
    	FileUtils.writeStringToFile(outputFile, "", false);
    	for(String term : mdsData.getTermList()) {
    		System.out.println("For term : " + term);
    		SvmData svmData = new SvmProcessor(10, term, mdsData).process();
    		String outputLine = JsonObjectMapper.toJsonString(svmData, true) + "\n\n";
    		FileUtils.writeStringToFile(outputFile, outputLine, true);
    		System.out.print(outputLine);
    	}
    }
    
    public static String getProjectPath() {
    	String projectPath = new File(".").getAbsolutePath();
    	projectPath = projectPath.substring(0, projectPath.length() - 1);
    	return projectPath;
    } 
}
