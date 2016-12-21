package co.in.vertexcover.affinity.client.process;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import co.in.vertexcover.affinity.client.dto.Affinity;
import co.in.vertexcover.affinity.utils.JsonObjectMapper;

public class Process {
	
	final static private String AFFINITY_OBJECT_FILENAME = "AFFINITY.txt"; 
	public String processName;
	public Exception processException;
	public boolean processCompleted;
	public long processCompletedAt;
	public long processStartedAt;
	public boolean processInProgress;
	public File outputFile;
	public ProcessData processData;
	
	
	public Process() {}
	
	public Process(final String processName, final String outputFilePath) throws Exception {
		this.processName = processName;
		this.processException = null;
		this.processCompleted = false;
		this.processCompletedAt = 0;
		this.processInProgress = false;
		this.processStartedAt = 0;
		
		outputFile = new File(outputFilePath);
		FileUtils.writeStringToFile(outputFile, "", false);
	}
	

	public boolean process(final Affinity affinity) {
		boolean processStatus = true;
		try {
			preProcess(affinity);
			mainProcess(affinity);
			postProcess(affinity);
		} catch(Exception e) {
			processStatus = false;
			processException = e;
		}
		return processStatus;
	}
	
	
	public void preProcess(final Affinity affinity) throws Exception {
		this.processInProgress = true;
		this.processStartedAt = new Date().getTime();
	}
	
	
	public void mainProcess(final Affinity affinity) throws Exception { }
	
	
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
		
		saveAffinityObject(affinity);
	}
	
	public static void saveAffinityObject(final Affinity affinity) throws Exception {
		final String affinityObjectFilePath = affinity.getConfigurations().getROOT_PATH()
				+ AFFINITY_OBJECT_FILENAME;
		
		File file = new File(affinityObjectFilePath);
		try {
			final String fileContent = JsonObjectMapper.toJsonString(affinity, true);
			FileUtils.writeStringToFile(file, fileContent, false);
		} catch(Exception e) {
			throw e;
		}
	}
	
}
