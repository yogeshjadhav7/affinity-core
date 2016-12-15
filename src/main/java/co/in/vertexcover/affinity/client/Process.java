package co.in.vertexcover.affinity.client;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Process {
	
	protected String processName;
	protected Exception processException;
	protected boolean processCompleted;
	protected long processCompletedAt;
	protected long processStartedAt;
	protected boolean processInProgress;
	protected File outputFile;
	protected ProcessData processData;
	
	
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
		
	}
	
	
	public void mainProcess(final Affinity affinity) throws Exception {
		
	}
	
	
	public void postProcess(final Affinity affinity) throws Exception {
		
	}
	
	
	
}
