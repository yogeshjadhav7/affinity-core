package co.in.vertexcover.affinity.core.dto;

import co.in.vertexcover.affinity.client.ProcessData;

public class InputValidationData extends ProcessData {

	private boolean isValid;
	private String errorMessage;
	private InputData inputData;
	
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public InputData getInputData() {
		return inputData;
	}
	public void setInputData(InputData inputData) {
		this.inputData = inputData;
	}
	
	
}
