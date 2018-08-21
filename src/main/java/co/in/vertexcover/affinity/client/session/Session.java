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
	private String affinityId;
	private Affinity affinity;
	
	public Session(final String sessionName, final String sessionId) {
		this.createdOn = new Date().getTime();
		this.sessionName = sessionName;
		this.sessionId = sessionId;
		this.affinityId = StringUtils.createAffinityId(this.sessionName, this.sessionId);
	}
	
	
	public void startProcess(final String inputFilePath) throws Exception {
		this.affinity = new Affinity(this.affinityId);
		AffinityStarter.start(inputFilePath, affinity);
		
	}
	
	public void startProcess(final String inputFilePath, final Configurations configurations) throws Exception {
		this.affinity = new Affinity(this.affinityId, configurations);
		AffinityStarter.start(inputFilePath, affinity);
	}


	public long getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(long createdOn) {
		this.createdOn = createdOn;
	}
	
	public Affinity getAffinity() {
		return this.affinity;
	}


	public String getAffinityId() {
		return affinityId;
	}


	public void setAffinityId(String affinityId) {
		this.affinityId = affinityId;
	}
	
	
	
}
