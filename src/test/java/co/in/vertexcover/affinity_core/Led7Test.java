package co.in.vertexcover.affinity_core;

import co.in.vertexcover.affinity.client.dto.Configurations;
import co.in.vertexcover.affinity.client.session.Session;

public class Led7Test {

	public static void main(String[] args) throws Exception {
    	String inputFileLocation = "/Users/yogesh/Documents/workspace/led7_svm/input/input.csv";
    	String sessionName = "Led7_SVM";
    	String sessionId = "777";
    	
    	Configurations configurations = new Configurations();
    	configurations.setMDS_DIMENSIONS(4);
    	configurations.setDoClassification(true);
    	configurations.setSCALE_LENGTH(100);
    	
    	Session session = new Session(sessionName, sessionId);
    	session.startProcess(inputFileLocation, configurations);

	}

}
