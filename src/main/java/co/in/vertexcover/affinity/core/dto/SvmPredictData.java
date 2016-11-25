package co.in.vertexcover.affinity.core.dto;

public class SvmPredictData {

	public double pred_result;
	public double dec_values;
	
	public SvmPredictData(double pred_result, double dec_values) {
		this.pred_result = pred_result;
		this.dec_values = dec_values;
	}
}

