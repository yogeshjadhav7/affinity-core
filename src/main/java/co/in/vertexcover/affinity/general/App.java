package co.in.vertexcover.affinity.general;

import co.in.vertexcover.affinity.client.session.Session;
import co.in.vertexcover.affinity.utils.StringUtils;

public class App {

	 public static void main( String[] args ) throws Exception {
		 
		 Session session = new Session("MyFirstSession", StringUtils.generateRandomString(10));
		 session.startProcess("C:\\YogiBear\\Development\\Eclipse\\Workspace\\affinity-core\\affinity-data\\input.txt");
		 
	 }
	
}
