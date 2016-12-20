package co.in.vertexcover.affinity.core.processors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.in.vertexcover.affinity.core.dto.InputData;
import co.in.vertexcover.affinity.core.dto.MdsData;
import co.in.vertexcover.affinity.core.pojo.Entity;
import co.in.vertexcover.affinity.helpers.JsonObjectMapper;
import mdsj.MDSJ;

public class InputProcessingProcessor {

	private InputData inputData;
	
	public InputProcessingProcessor(final InputData inputData) {
		this.inputData = inputData;
	}
	
	public MdsData process(final int mdsDimensions) {
		System.out.println("Building ppmi matrix...");
		Map<String, HashMap<String, Double>> ppmiMatrix = buildPPMIMatrix();
		
		System.out.println("Building angular difference matrix...");
		Map<String, HashMap<String, Double>> angularDifferenceMatrix = buildAngularDifferenceMatrix(ppmiMatrix);
		
		System.out.println("Building distance matrix...");
		double[][] distanceMatrix = buildDistanceMatrix(angularDifferenceMatrix);
		
		System.out.println("Building mds matrix...");
		double[][] mdsMatrix = MDSJ.classicalScaling(distanceMatrix, mdsDimensions);
		MdsData mdsData = new MdsData(transposeOf(mdsMatrix), this.inputData.getEntityList(), this.inputData.getTermList(),
				this.inputData.getEntityData(), mdsDimensions);
		return mdsData;
	}
	
	
	private double[][] transposeOf(double[][] matrix) {
		final int rows = matrix.length;
		final int columns = matrix[0].length;
		double[][] transposedMatrix = new double[columns][rows];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++)
				transposedMatrix[j][i] = matrix[i][j];
		}
		
		return transposedMatrix; 
	}
	
	
	private Map<String, HashMap<String, Double>> buildPPMIMatrix() {
		double sumOfAllWeights = getSumOfTermWeightsForAllEntities();
		Map<String, HashMap<String, Double>> ppmiMatrix = new HashMap<>();
		for(String entity : this.inputData.getEntityList()) {
			HashMap<String, Double> termPPMI = new HashMap<>();
			double PWI = getSumOfTermWeightsForEntity(entity) / sumOfAllWeights;
			for(String term : this.inputData.getTermList()) {
				double PW = getTermWeightForEntity(entity, term) / sumOfAllWeights;
				double PS = getSumOfTermWeightsOfTermForAllEntities(term) / sumOfAllWeights;
				double ppmiValue = Math.log( PW / (PWI * PS) );
				if(ppmiValue < 0)
					ppmiValue = 0;
				
				termPPMI.put(term, ppmiValue);
			}
			ppmiMatrix.put(entity, termPPMI);
		}
		return ppmiMatrix;
	}
	

	private Map<String, HashMap<String, Double>> buildAngularDifferenceMatrix(final Map<String, HashMap<String, Double>> ppmiMatrix) {
		Map<String, HashMap<String, Double>> angularDifferenceMatrix = new HashMap<>();
		HashMap<String, Double> entityMagnitudeStore = new HashMap<>();
		
		for(String entity : this.inputData.getEntityList())
			angularDifferenceMatrix.put(entity, new HashMap<String, Double>());
		
		for(String entityOne : this.inputData.getEntityList()) {
			for(String entityTwo : this.inputData.getEntityList()) {
				if(angularDifferenceMatrix.get(entityOne).containsKey(entityTwo))
					continue;
				
				final double angularDifferenceValue = getAngularDifferenceBetweenEntities(entityOne, entityTwo, ppmiMatrix, entityMagnitudeStore);
				angularDifferenceMatrix.get(entityOne).put(entityTwo, angularDifferenceValue);
				angularDifferenceMatrix.get(entityTwo).put(entityOne, angularDifferenceValue);
			}
		}
		
		return angularDifferenceMatrix;
	}
	
	
	private double[][] buildDistanceMatrix(final Map<String, HashMap<String, Double>> angularDifferenceMatrix) {
		List<String> entityList = this.inputData.getEntityList();
		int dimensions = entityList.size();
		double[][] distanceMatrix = new double[dimensions][dimensions];
		for(int i = 0; i < dimensions; i++) {
			final String entityOne = entityList.get(i);
			for(int j = i + 1; j < dimensions; j++) {				
				final String entityTwo = entityList.get(j);
				final double angularDifferenceValue = angularDifferenceMatrix.get(entityOne).get(entityTwo);
				distanceMatrix[i][j] = angularDifferenceValue;
				distanceMatrix[j][i] = angularDifferenceValue;
			}
		}
		
		return distanceMatrix;
	}
	
	
	private double getAngularDifferenceBetweenEntities(final String entityOne, final String entityTwo, 
			final Map<String, HashMap<String, Double>> ppmiMatrix, HashMap<String, Double> entityMagnitudeStore) {
		if(entityOne.equals(entityTwo))
			return 0;
		
		final double magnitudeOfEntityOne = calculateMagnitudeOfEntity(entityOne, ppmiMatrix, entityMagnitudeStore);
		final double magnitudeOfEntityTwo = calculateMagnitudeOfEntity(entityTwo, ppmiMatrix, entityMagnitudeStore);
		final double dotProductOfEntities = calculateDotProduct(entityOne, entityTwo, ppmiMatrix);
		double theta = dotProductOfEntities / (magnitudeOfEntityOne * magnitudeOfEntityTwo);
		if(theta > 1)
			theta = 1;
		
		final double angularDifference = (7 * Math.acos(theta)) / 11;;
		return angularDifference;
	}
	
	
	private double calculateMagnitudeOfEntity(final String entity, final Map<String, HashMap<String, Double>> ppmiMatrix, HashMap<String, Double> entityMagnitudeStore) {
		if(entityMagnitudeStore.containsKey(entity))
			return entityMagnitudeStore.get(entity);
		
		double magnitude = 0;
		for(String term : this.inputData.getTermList()) {
			double ppmiValue = ppmiMatrix.get(entity).get(term); 
			magnitude += Math.pow(ppmiValue, 2);
		}
		
		magnitude = Math.sqrt(magnitude);
		entityMagnitudeStore.put(entity, magnitude);
		return magnitude;
	}
	
	
	private double calculateDotProduct(final String entityOne, final String entityTwo, final Map<String, HashMap<String, Double>> ppmiMatrix) {
		double dotProduct = 0;
		for(String term : this.inputData.getTermList()) 
			dotProduct += (ppmiMatrix.get(entityOne).get(term) * ppmiMatrix.get(entityTwo).get(term));
		
		return dotProduct;
	}
	
	
	
	private int getSumOfTermWeightsOfTermForAllEntities(final String term) {
		return this.inputData.getTermData().get(term).getSumOfTermWeights();
	}
	
	private int getTermWeightForEntity(final String entity, final String term) {
		return this.inputData.getEntityData().get(entity).getTermWeightOf(term);
	}
	
	private int getSumOfTermWeightsForEntity(final String entity) {
		return this.inputData.getEntityData().get(entity).getSumOfTermWeights();
	}
	
	private int getSumOfTermWeightsForAllEntities() {
		int sum = 0;
		for (Entity entity : this.inputData.getEntityData().values())
			sum += entity.getSumOfTermWeights();

		return sum;
	}
	
}
