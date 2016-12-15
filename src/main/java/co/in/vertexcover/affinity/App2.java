package co.in.vertexcover.affinity;

import co.in.vertexcover.affinity.client.Session;

public class App2 {

	 public static void main( String[] args ) throws Exception {
		 
		 Session session = new Session("MyFirstSession", "123345");
		 session.startProcess("C:\\YogiBear\\Development\\Eclipse\\Workspace\\affinity-core\\affinity-data\\input.txt");
		 
	 }
	
}
