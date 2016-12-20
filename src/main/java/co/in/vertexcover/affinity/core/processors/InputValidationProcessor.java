package co.in.vertexcover.affinity.core.processors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import co.in.vertexcover.affinity.core.constants.InputProcessorConstants;
import co.in.vertexcover.affinity.core.dto.InputData;
import co.in.vertexcover.affinity.core.dto.InputValidationData;
import co.in.vertexcover.affinity.core.pojo.Entity;
import co.in.vertexcover.affinity.core.pojo.Term;

public class InputValidationProcessor {
	
	private InputValidationData response;
	private Map<String, Entity> entityData;
	private Map<String, Term> termData;
	private HashSet<String> termSet;
	
	public InputValidationProcessor() {
		this.response = new InputValidationData();
		this.response.setValid(true);
		this.entityData = new HashMap<String, Entity>();
		this.termData = new HashMap<String, Term>();
		this.termSet = new HashSet<>();
	}

	public InputValidationData validate(final File inputFile, final double termMinimumOccurrencePercentage) {
		int lineNumber = 1;
		try(BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
		    for(String inputLine; (inputLine = br.readLine()) != null; ) {
		    	inputLine = inputLine.trim();
		    	if(inputLine.equals(""))
		    		continue;
		    	
		        if(!isLineValid(inputLine, lineNumber))
		        	return response;
		        addToEntityData(inputLine);
		        lineNumber++;
		    }
		    
		    buildTermData();
		    final int minimumEntityExistenceStrengthAllowed = (int) (termMinimumOccurrencePercentage * entityData.size() / 100);
		    filterData(minimumEntityExistenceStrengthAllowed);
		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.response.setErrorMessage(e.getMessage());
			this.response.setValid(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.response.setErrorMessage(e.getMessage());
			this.response.setValid(false);
		}
		
		List<String> entityList = new ArrayList<>(entityData.keySet());
		List<String> termList = new ArrayList<>(termData.keySet());
		Collections.sort(entityList);
		Collections.sort(termList);
		this.response.setInputData(new InputData(entityData, termData, entityList, termList));
		return response;
	}
	
	
	private List<String> getSortedListOfValues(final HashMap<String, Object> map) {
		List<String> list = new ArrayList<>(map.keySet());
		Collections.sort(list);
		return list;
	}
	
	private void filterData(final int minimumEntityExistenceStrengthAllowed) {
		for(String termName : this.termSet) {
			if(termData.get(termName).getEntityExistenceStrength() < minimumEntityExistenceStrengthAllowed) {
				termData.remove(termName);
				for (Entity entityObj : entityData.values())
					entityObj.removeTermWeight(termName);
			}
		}	
	}
	
	
	
	private void buildTermData() {
		for(String termName : termSet) {
			Term term = new Term(termName);
			for (Map.Entry<String, Entity> entry : entityData.entrySet()) {
			    final String entityName = entry.getKey();
			    final Entity entityObj = entry.getValue();
			    term.addEntityExistence(entityName, entityObj.getTermWeightOf(termName));
			}
			this.termData.put(termName, term);
		}
	}
	
	
	private void addToEntityData(final String line) {
		final String[] lineParts = line.split(InputProcessorConstants.INPUT_DELIMITER);
		final String entity = lineParts[0];
		final String term = lineParts[1];
		final int weight = Integer.parseInt(lineParts[2]);
		
		if(!entityData.containsKey(entity))
			entityData.put(entity, new Entity(entity, term, weight));
		else
			entityData.get(entity).addTermWeight(term, weight);

		termSet.add(term);
	}
	
	
	private boolean isLineValid(final String line, final int lineNumber) {
		final String[] lineParts = line.split(InputProcessorConstants.INPUT_DELIMITER);
		if(lineParts.length != 3) {
			String errorMessage = "Input format error at line number " + lineNumber;
			errorMessage += ". Format accepted : ENTITY" + InputProcessorConstants.INPUT_DELIMITER + "TERM" + InputProcessorConstants.INPUT_DELIMITER + "WEIGHT";
			this.response.setErrorMessage(errorMessage);
			this.response.setValid(false);
			return false;
		}
		
		if(!isEntityAndTermValid(lineParts[0], lineParts[1])) {
			String errorMessage = "Input format error at line number " + lineNumber;
			errorMessage += ". Only alpha numeric, _ and - are allowed in entities and terms";
			this.response.setErrorMessage(errorMessage);
			this.response.setValid(false);
			return false;
		}
		
		if(!isWeightValid(lineParts[2])) {
			String errorMessage = "Input format error at line number " + lineNumber;
			errorMessage += ". Only non negative greater than 0 integer values are allowed as weights. Floats will be floored to integers";
			this.response.setErrorMessage(errorMessage);
			this.response.setValid(false);
			return false;
		}
		
		return true;
	}
	
	
	private boolean isEntityAndTermValid(final String entity, final String term) {
		return InputProcessorConstants.INPUT_PATTERN.matcher(entity + term).matches();
	}
	
	
	private boolean isWeightValid(final String weight) {
		try {
			return (Integer.parseInt(weight) > 0)?true:false;
		} catch(Exception e) {
			return false;
		}
	}
}
