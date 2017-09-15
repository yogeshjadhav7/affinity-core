package co.in.vertexcover.affinity.client.dto;

import java.util.LinkedHashMap;

import co.in.vertexcover.affinity.client.process.Process;
import co.in.vertexcover.affinity.utils.StringUtils;

public class Affinity {
	
	public static enum PROCESS {INPUT_PROCESSING, AFFINITY_CALCULATION, TERM_BOND_CALCULATION};
	private String ID;
	private Configurations configurations;
	private LinkedHashMap<String, Process> processes;
	
	public Affinity(){}
	
	public Affinity(final String id, final Configurations configurations) {
		this.ID = id;
		this.configurations = configurations;
		this.configurations.setROOT_PATH(StringUtils.getDefaultPath(this.ID));
		this.processes = new LinkedHashMap<>();
	}
	
	public Affinity(final String id) {
		this.ID = id;
		this.configurations = new Configurations(StringUtils.getDefaultPath(this.ID));
		this.processes = new LinkedHashMap<>();
	}
	
	
	public Configurations getConfigurations() {
		return configurations;
	}
	
	public void setConfigurations(Configurations configurations) {
		this.configurations = configurations;
	}

	public LinkedHashMap<String, Process> getProcesses() {
		return processes;
	}
	
	public void addProcess(final String processName, final Process process) {
		this.processes.put(processName, process);
	}
	
	public Process getProcess(final String processName) {
		if(this.processes.containsKey(processName))
			return this.processes.get(processName);
		else
			return null;
	}
	
	
	public String getID() {
		return this.ID;
	}
	
	

}
