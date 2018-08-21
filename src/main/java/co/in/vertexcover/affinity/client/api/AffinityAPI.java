package co.in.vertexcover.affinity.client.api;

import java.util.HashMap;
import java.util.Map;

import co.in.vertexcover.affinity.client.dto.Configurations;
import co.in.vertexcover.affinity.client.session.Session;

public class AffinityAPI {

	public static void main(String[] args) throws Exception {
    	
		Configurations configurations = null;
		final Map<String, String> argsMap = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if(arg.isEmpty() || !arg.contains("=")) continue;
            String[] argParts = arg.split("=");
            if(argParts.length != 2) continue;
            argsMap.put(argParts[0], argParts[1]);
        }
        
        // Mandatory arguments 
        final String sessionName = argsMap.getOrDefault("sessionName", null);
        if(sessionName == null) throw new Exception("Argument 'sessionName' was not specified");
        
        final String sessionId = argsMap.getOrDefault("sessionId", null);
        if(sessionId == null) throw new Exception("Argument 'sessionId' was not specified");
        
        final String inputFile = argsMap.getOrDefault("inputFile", null);
        if(inputFile == null) throw new Exception("Argument 'inputFile' was not specified");
        
        
        final String configScaleLength = argsMap.getOrDefault("configScaleLength", null);
        if(configScaleLength != null) {
            try {
            		int scaleLength = Integer.parseInt(configScaleLength);
        			if(configurations == null) configurations = new Configurations();
        			configurations.setSCALE_LENGTH(scaleLength);
            } catch(Exception e) {
            		System.out.println("Warning: Unsupported scale length provided. Switching to default scale length.");
            }
        }
        
        final String configClassificationJob = argsMap.getOrDefault("configClassificationJob", null);
        if(configClassificationJob != null) {
        		try {
        			boolean classificationJob = Boolean.parseBoolean(configClassificationJob);
        			if(configurations == null) configurations = new Configurations();
        			configurations.setDoClassification(classificationJob);
        		} catch(Exception e) {
        			System.out.println("Warning: Unsupported classification job flag provided. Switching to default job flag.");
        		}
        }
        
        
        final String configMdsDimensions = argsMap.getOrDefault("configMdsDimensions", null);
        if(configMdsDimensions != null) {
            try {
            		int mdsDimensions = Integer.parseInt(configMdsDimensions);
        			if(configurations == null) configurations = new Configurations();
        			configurations.setMDS_DIMENSIONS(mdsDimensions);
            } catch(Exception e) {
            		System.out.println("Warning: Unsupported MDS Dimensions provided. Switching to default number of dimensions.");
            }
        }
        
    		final Session session = new Session(sessionName, sessionId);
    		if(configurations == null) session.startProcess(inputFile);
    		else session.startProcess(inputFile, configurations);
    }

    
	
}
