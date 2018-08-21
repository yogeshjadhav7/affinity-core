package co.in.vertexcover.affinity_core;

import co.in.vertexcover.affinity.client.session.Session;

public class RecipesTest {

	public static void main(String[] args) throws Exception {
    	String inputFileLocation = "input.txt";
    	String sessionName = "Recipes";
    	String sessionId = "12345";
    	
    	Session session = new Session(sessionName, sessionId);
    	session.startProcess(inputFileLocation);

	}

}
