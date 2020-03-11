package model;

import org.hl7.fhir.r4.model.Observation;

import java.util.ArrayList;
import java.util.List;

public class ObservationData {

	private Observation observation;
	private boolean hasComponent;

	public ObservationData(Observation obs){
		this.observation = obs;
		this.hasComponent = observation.hasComponent();
	}

	public String[] getCodeString(){
		List<String> list = new ArrayList<>();
		if(hasComponent){
			for(Observation.ObservationComponentComponent comp : observation.getComponent())
				list.add(comp.getCode().getText());
			return list.toArray(new String[0]);
		} else{
			return new String[]{observation.getCode().getText()};
		}
	}

	public String[] getValueString(){
		if(hasComponent){
			List<String> list = new ArrayList<>();
			for(Observation.ObservationComponentComponent comp : observation.getComponent()){
				StringBuilder sb = new StringBuilder();
				sb.append(comp.getValueQuantity().getValue().toString());
				sb.append(comp.getValueQuantity().getUnit());
				list.add(sb.toString());
			}
			return list.toArray(new String[0]);
		} else{
			StringBuilder sb = new StringBuilder();
			sb.append(observation.getValueQuantity().getValue().toString());
			sb.append(observation.getValueQuantity().getUnit());
			return new String[]{sb.toString()};
		}
	}

	public String getDateString(){
		return observation.getEffectiveDateTimeType().getValue().toString();
	}

}
