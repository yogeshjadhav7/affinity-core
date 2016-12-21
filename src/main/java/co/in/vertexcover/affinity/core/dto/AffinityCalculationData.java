package co.in.vertexcover.affinity.core.dto;

import java.util.HashMap;
import java.util.Map;

import co.in.vertexcover.affinity.client.process.ProcessData;

public class AffinityCalculationData extends ProcessData {

	private Map<String, SvmTermData> data;
	
	public AffinityCalculationData() {
		data = new HashMap<String, SvmTermData>();
	}

	public Map<String, SvmTermData> getData() {
		return data;
	}
	
	public void addToData(SvmTermData svmData) {
		if(this.data == null)
			data = new HashMap<String, SvmTermData>();
		
		data.put(svmData.getTerm(), svmData);
	}
}
