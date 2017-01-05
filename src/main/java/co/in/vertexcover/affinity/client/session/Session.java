package co.in.vertexcover.affinity.client.session;

import java.util.Date;

import co.in.vertexcover.affinity.client.dto.Affinity;
import co.in.vertexcover.affinity.client.dto.Configurations;
import co.in.vertexcover.affinity.client.process.AffinityStarter;
import co.in.vertexcover.affinity.utils.StringUtils;

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
	
	
	public void startProcess(final String inputFilePath) throws Exception {
		this.affinity = new Affinity(StringUtils.createAffinityId(this.sessionName, this.sessionId));
		AffinityStarter.start(inputFilePath, affinity);
		
	}
	
	public void startProcess(final String inputFilePath, final Configurations configurations) throws Exception {
		this.affinity = new Affinity(StringUtils.createAffinityId(this.sessionName, this.sessionId));
		AffinityStarter.start(inputFilePath, affinity);
	}


	public long getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(long createdOn) {
		this.createdOn = createdOn;
	}
	
	
}
