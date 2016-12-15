package co.in.vertexcover.affinity.client;

public class AffinityStarter {

	public static void start(final String inputFilePath, final Affinity affinity) {
		try {
			
			Process inputValidationProcess = new InputValidation(affinity, 
					inputFilePath, 
					StringUtils.getDefaultPath(affinity.getID()));
			final boolean inputValidationStatus = inputValidationProcess.process(affinity);
			
		} catch(Exception e) {
			
		}
	}
	
	
}
