package co.in.vertexcover.affinity;

import co.in.vertexcover.affinity.client.Session;
import co.in.vertexcover.affinity.client.StringUtils;

public class App2 {

	 public static void main( String[] args ) throws Exception {
		 
		 Session session = new Session("MyFirstSession", StringUtils.generateRandomString(10));
		 session.startProcess("C:\\YogiBear\\Development\\Eclipse\\Workspace\\affinity-core\\affinity-data\\input.txt");
		 
	 }
	
}
