package co.in.vertexcover.affinity.client;

import java.util.LinkedHashMap;

public class Affinity {
	
	private String ID;
	private Configurations configurations;
	private LinkedHashMap<String, Process> processes;
	
	public Affinity(){}
	
	public Affinity(final String id, final Configurations configurations) {
		this.ID = id;
		this.configurations = configurations;
		this.processes = new LinkedHashMap<>();
	}
	
	public Affinity(final String id) {
		this.ID = id;
		this.configurations = new Configurations(this.ID);
		this.processes = new LinkedHashMap<>();
	}
	
	
	public Configurations getConfigurations() {
		return configurations;
	}

	public LinkedHashMap<String, Process> getProcesses() {
		return processes;
	}
	
	public void addProcess(final String processName, final Process process) {
		this.processes.put(processName, process);
	}
	
	public String getID() {
		return this.ID;
	}
	
	

}
