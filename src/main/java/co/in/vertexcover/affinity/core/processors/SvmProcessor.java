package co.in.vertexcover.affinity.core.processors;

import java.util.List;
import java.util.Map;

import co.in.vertexcover.affinity.core.dto.MdsData;
import co.in.vertexcover.affinity.core.dto.SvmData;
import co.in.vertexcover.affinity.core.dto.SvmPredictData;
import co.in.vertexcover.affinity.core.pojo.Entity;
import co.in.vertexcover.affinity.helpers.JsonObjectMapper;
import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

public class SvmProcessor {

	private int scaleLength;
	private String term;
	private int numberOfEntity;
	private int featureVectorRowSize;
	private int mdsDimensions;
	private double[][] featureVectorMatrix;
	private double[] labels;
	private double[] predictedLabels;
	private svm_node[][] instances;
	private double[] decisionValues;
	private String lableDenoter;
	private int numberOfCoordinates;
	
	

	private double[][] mdsMatrix;
	private List<String> entityList;
	private List<String> termList;
	private Map<String, Entity> entityData;
	
	
	public SvmProcessor() {}
	
	public SvmProcessor(final int scaleLength, final String term, final MdsData mdsData) {
		this.scaleLength = scaleLength;
		this.term = term;
		this.numberOfEntity = mdsData.getEntityList().size();
		this.featureVectorRowSize = this.numberOfEntity;
		this.numberOfCoordinates = mdsData.getMdsMatrix()[0].length;

		this.lableDenoter = null;
		this.featureVectorMatrix = new double[featureVectorRowSize][numberOfCoordinates + 1];
		this.labels = new double[featureVectorRowSize];
		this.predictedLabels = new double[featureVectorRowSize];
		this.instances = new svm_node[featureVectorRowSize][numberOfCoordinates];
		this.decisionValues = new double[featureVectorRowSize];
		this.mdsMatrix = mdsData.getMdsMatrix();
		this.entityList = mdsData.getEntityList();
		this.termList = mdsData.getTermList();
		this.entityData = mdsData.getEntityData();
	}
	
	public SvmData process() {
		int zeroCounter = 0;
		int currentRowNumber = 0;
		
		for(String entity : this.entityList) {
			
			for(int fv = 0; fv < this.numberOfCoordinates; fv++)
				featureVectorMatrix[currentRowNumber][fv] = this.mdsMatrix[currentRowNumber][fv];
			
			if(this.entityData.get(entity).getTermWeightOf(this.term) != 0) {
				this.featureVectorMatrix[currentRowNumber][numberOfCoordinates] = 1;
				if(this.lableDenoter == null)
					this.lableDenoter = "1";
			} else {
				zeroCounter++;
				this.featureVectorMatrix[currentRowNumber][numberOfCoordinates] = 0;
				if(this.lableDenoter == null)
					this.lableDenoter = "-1";
			}
			
			currentRowNumber++;
		}
		
		svm_model model = performTraining(zeroCounter);
		performPrediction(model);
		processDecisionValues();
		final double kappaScore = calculateKappaScore(labels, predictedLabels);
		System.out.println("kS " + kappaScore);
		return new SvmData(this.term, this.decisionValues, this.entityList, kappaScore, null);
	}
	
	
	private void processDecisionValues() {
		int lableDenoterValue = Integer.parseInt(this.lableDenoter);
		this.decisionValues[0] *= lableDenoterValue;
		double MIN = this.decisionValues[0];
		double MAX = this.decisionValues[0];
	
		for(int i = 1; i < this.decisionValues.length; i++) {
			this.decisionValues[i] *= lableDenoterValue;
			if(MIN > this.decisionValues[i])
				MIN = this.decisionValues[i];
			if(MAX < this.decisionValues[i])
				MAX = this.decisionValues[i];
		}
		
		for(int i = 0; i < this.decisionValues.length; i++)
			this.decisionValues[i] -= MIN;
		
		MAX -= MIN;
		MIN = 0;
		
		final double multiplier = scaleLength / MAX;
		for(int i = 0; i < this.decisionValues.length; i++)
			this.decisionValues[i] *= multiplier;
	}
	
	
	private svm_model performTraining(final int zeroCounter) {
		populateLabels();
		svm_problem probs = getSvmProblem();
		svm_parameter params = getSvmParam(zeroCounter);
		return svm.svm_train(probs, params);
	}
	
	
	private void performPrediction(final svm_model model) {
		
		for(int entityCounter = 0; entityCounter < this.entityList.size(); entityCounter++) {
			SvmPredictData svmPredictData = svm_predict(model, this.instances[entityCounter]);
			this.predictedLabels[entityCounter] = svmPredictData.pred_result;
			this.decisionValues[entityCounter] = svmPredictData.dec_values;
		}
	}
	
	
	
	private svm_parameter getSvmParam(final int zeroCounter) {
		svm_parameter params = new svm_parameter();
		params.C = getCostRatio(zeroCounter, this.entityList.size());
		params.kernel_type = svm_parameter.LINEAR;
		return params;
	}
	
	
	private svm_problem getSvmProblem() {
		svm_problem probs = new svm_problem();
		probs.l = this.labels.length;
		probs.y = this.labels;
		probs.x = new svm_node[probs.l][this.numberOfCoordinates];
		
		for(int i = 0; i < probs.l; i++) {
			for(int j = 0; j < this.numberOfCoordinates; j++) {
				svm_node node = new svm_node();
				node.index = j;
				node.value = this.featureVectorMatrix[i][j];
				probs.x[i][j] = node;
				this.instances[i][j] = node;
			}
		}
			
		return probs;
	}
	
	
	
	
	private void populateLabels() {
		for(int i = 0; i < this.featureVectorRowSize; i++)
			this.labels[i] = this.featureVectorMatrix[i][this.numberOfCoordinates];
	}
	
	
	private double getCostRatio(final int zeroCounter, final int totalCount) {
		return (double)(zeroCounter) / (totalCount - zeroCounter);
	}
	

	private SvmPredictData svm_predict(svm_model model, svm_node[] x) {
		int nr_class = model.nr_class;
		double[] dec_values;
		if(model.param.svm_type == svm_parameter.ONE_CLASS ||
				model.param.svm_type == svm_parameter.EPSILON_SVR ||
				model.param.svm_type == svm_parameter.NU_SVR)
			dec_values = new double[1];
		else
			dec_values = new double[nr_class*(nr_class-1)/2];
		double pred_result = svm.svm_predict_values(model, x, dec_values);
		if(dec_values == null)
			System.out.println("Null");
		else
			System.out.println(dec_values.length + "  " + dec_values[0] + "  " + pred_result);
			
		return new SvmPredictData(pred_result, dec_values[0]);
	}
	
	
	private double calculateKappaScore(final double[] labels, double[] predictedLabels) {
		
		System.out.println(JsonObjectMapper.toJsonString(labels, true));
		System.out.println(JsonObjectMapper.toJsonString(predictedLabels, true));
		
		
		double[][] kappaMatrix = new double[3][3];
		int value = 0;
		for(int i = 0; i < labels.length; i++) {
			if(labels[i] == 1 && predictedLabels[i] == 1)
				kappaMatrix[0][0] += 1;
			else if(labels[i] == 0 && predictedLabels[i] == 0)
				kappaMatrix[1][1] += 1;
			else if(labels[i] == 1 && predictedLabels[i] == 0)
				kappaMatrix[1][0] += 1;
			else
				kappaMatrix[0][1] += 1;
		}
		
		
		kappaMatrix[0][2] = kappaMatrix[0][0] + kappaMatrix[0][1];
		kappaMatrix[1][2] = kappaMatrix[1][0] + kappaMatrix[1][1];
		kappaMatrix[2][2] = kappaMatrix[0][2] + kappaMatrix[1][2];
		kappaMatrix[2][0] = kappaMatrix[0][0] + kappaMatrix[1][0];
		kappaMatrix[2][1] = kappaMatrix[0][1] + kappaMatrix[1][1];
		
		System.out.println(JsonObjectMapper.toJsonString(kappaMatrix, true));
		
		double PO = (kappaMatrix[0][0] + kappaMatrix[1][1]) / kappaMatrix[2][2];
		double PE = ((kappaMatrix[2][0] * kappaMatrix[0][2]) + (kappaMatrix[2][1] * kappaMatrix[1][2])) / (kappaMatrix[2][2] * kappaMatrix[2][2]);
		System.out.println("PO " + PO + " # PE " + PE);
		
		if(PE != 1)
			return (PO - PE) / (1 - PE);
		
		return 0;
	}
}
