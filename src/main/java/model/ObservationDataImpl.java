package model;

import org.hl7.fhir.r4.model.Observation;
import view.ObservationData;

import java.util.ArrayList;
import java.util.List;

public class ObservationDataImpl implements ObservationData {

	private boolean hasComponent;

	private String[] codeString;
	private String[] valueString;
	private String dateString;

	public ObservationDataImpl(Observation obs){
		this.hasComponent = obs.hasComponent();
		setCodeString(obs);
		setValueString(obs);
		setDateString(obs);
	}

	public String[] getCodeString(){ return codeString; }
	public String[] getValueString( ){ return valueString; }
	public String getDateString( ){ return dateString; }

	private void setCodeString(Observation obs){
		List<String> list = new ArrayList<>();
		if(hasComponent){
			for(Observation.ObservationComponentComponent comp : obs.getComponent())
				list.add(comp.getCode().getText());
			codeString = list.toArray(new String[0]);
		} else{
			codeString = new String[]{obs.getCode().getText()};
		}
	}

	private void setValueString(Observation obs){
		if(hasComponent){
			List<String> list = new ArrayList<>();
			for(Observation.ObservationComponentComponent comp : obs.getComponent()){
				StringBuilder sb = new StringBuilder();
				sb.append(comp.getValueQuantity().getValue().toString());
				sb.append(comp.getValueQuantity().getUnit());
				list.add(sb.toString());
			}
			valueString = list.toArray(new String[0]);
		} else{
			StringBuilder sb = new StringBuilder();
			sb.append(obs.getValueQuantity().getValue().toString());
			sb.append(obs.getValueQuantity().getUnit());
			valueString = new String[]{sb.toString()};
		}
	}

	private void setDateString(Observation obs){
		dateString = obs.getEffectiveDateTimeType().getValue().toString();
	}

}
