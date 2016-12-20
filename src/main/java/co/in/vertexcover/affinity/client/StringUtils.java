package co.in.vertexcover.affinity.client;

import java.io.File;

public class StringUtils {

	final private static String pseudoString = "qwertyuiopasdfghjklzxcvbnm1234567890QWERTYUIOPASDFGHJKLZXCVBNM";
	
	public static String createAffinityId(final String... elements) {
		String id = "";
		for(String element : elements) {
			if(id.equals(""))
				id = element;
			else
				id = id + "-" + element;
		}
		return id;
	}
	
	public static String generateRandomString(final int stringLength) {
		String randomString = "";
		int pseudoStringLength = pseudoString.length();
		for(int i = 0; i < stringLength; i++) {
			int index = (int) (Math.random() * (pseudoStringLength - 1));
			randomString += Character.toString(pseudoString.charAt(index));
		}
		
		return randomString;
	}
	
    public static String getDefaultPath(final String affinityId) {
    	String projectPath = new File(".").getAbsolutePath();
    	projectPath = projectPath.substring(0, projectPath.length() - 1);
    	return projectPath 
    			+ StringConstants.AFFINITY_DIRECTORY_NAME
    			+ File.separator
    			+ affinityId
    			+ File.separator;
    } 
	
}
