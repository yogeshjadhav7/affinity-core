package co.in.vertexcover.affinity.client.api;

import co.in.vertexcover.affinity.client.session.Session;

public class AffinityAPI {

	// This will have methods to initiate and restore existing sessions and to perform tasks of affinity core
	public static Session createNewSession(final String sessionName, final String sessionID) {
		 Session session = new Session(sessionName, sessionID);
		 return session;
	}
	
	
	public static void startProcess(final Session session, final String inputFilePath) throws Exception {
		session.startProcess(inputFilePath);
	}
	
}
