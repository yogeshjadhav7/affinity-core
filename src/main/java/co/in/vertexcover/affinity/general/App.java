package co.in.vertexcover.affinity.general;

import co.in.vertexcover.affinity.client.session.Session;

public class App {

	 public static void main( String[] args ) throws Exception {
		 
		 Session session = new Session("MyFirstSession", "54321");
		 session.startProcess("Y:\\New Folder\\output.txt");
		 
	 }
	
}
