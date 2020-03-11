package view;

import model.ObservationData;

public class ObservationListElement {

	private ObservationData data;

	public ObservationListElement(ObservationData data){
		this.data = data;
	}

	public String[] getCodeString(){
		return data.getCodeString();
	}

	public String[] getValueString(){
		return data.getValueString();
	}

	public String getDateString(){
		return data.getDateString();
	}

}
