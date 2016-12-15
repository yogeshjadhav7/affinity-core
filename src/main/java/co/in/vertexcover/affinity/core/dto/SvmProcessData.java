package co.in.vertexcover.affinity.core.dto;

import java.util.HashMap;
import java.util.Map;

import co.in.vertexcover.affinity.client.ProcessData;

public class SvmProcessData extends ProcessData {

	private Map<String, SvmData> data;
	
	public SvmProcessData() {
		data = new HashMap<String, SvmData>();
	}

	public Map<String, SvmData> getData() {
		return data;
	}
	
	public void addToData(SvmData svmData) {
		if(this.data == null)
			data = new HashMap<String, SvmData>();
		
		data.put(svmData.getTerm(), svmData);
	}
}
