package co.in.vertexcover.affinity.client;

import java.util.Date;

public class Session {

	private long createdOn;
	private String sessionName;
	private String sessionId;
	private Affinity affinity;
	
	public Session(final String sessionName, final String sessionId) {
		this.createdOn = new Date().getTime();
		this.sessionName = sessionName;
		this.sessionId = sessionId;
	}
	
	
	public void startProcess(final String inputFilePath) {
		this.affinity = new Affinity(StringUtils.createAffinityId(this.sessionName, this.sessionId));
		AffinityStarter.start(inputFilePath, affinity);
		
	}
	
	public void startProcess(final String inputFilePath, final Configurations configurations) {
		this.affinity = new Affinity(StringUtils.createAffinityId(this.sessionName, this.sessionId));
		AffinityStarter.start(inputFilePath, affinity);
	}
	
	
	
	
	
	
	
}
