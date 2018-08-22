package co.in.vertexcover.affinity_core;

import co.in.vertexcover.affinity.client.dto.Configurations;
import co.in.vertexcover.affinity.client.session.Session;
import co.in.vertexcover.affinity.utils.StringUtils;

public class Led7Test {

	public static void main(String[] args) throws Exception {
		
	String sessionId = "777";
	String sessionName = "Led7";
    
    	Configurations configurations = new Configurations();
    	configurations.setMDS_DIMENSIONS(20);
    	configurations.setDoClassification(true);
    	configurations.setSCALE_LENGTH(10);
    	
    	Session session = new Session(sessionName, sessionId);
    	
    final String inputFileLocation = "input.txt";
    	
    	session.startProcess(inputFileLocation, configurations);

	}

}
