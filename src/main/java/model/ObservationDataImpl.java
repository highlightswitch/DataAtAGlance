package model;

import org.hl7.fhir.r4.model.Observation;
import view.ObservationData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ObservationDataImpl implements ObservationData {

	private boolean hasComponent;

	private String[] nameString;
	private String[] codeString;
	private String[] valueString;
	private String dateString;

	private String[] filters;

	public ObservationDataImpl(Observation obs){
		this.hasComponent = obs.hasComponent();
		setCode(obs);
		setValueString(obs);
		setDateString(obs);
		setFilters(obs);
	}

	public String[] getCodeNameString(){ return nameString; }
	public String[] getCodeString(){ return codeString; }
	public String[] getValueString(){ return valueString; }
	public String getDateString(){ return dateString; }

	public String[] getFilters(){ return filters; }

	private void setCode(Observation obs){
		List<String> nameList = new ArrayList<>();
		List<String> codeList = new ArrayList<>();
		if(hasComponent){
			for(Observation.ObservationComponentComponent comp : obs.getComponent()){
				nameList.add(comp.getCode().getText());
				codeList.add(comp.getCode().getCodingFirstRep().getCode());
			}
			nameString = nameList.toArray(new String[0]);
			codeString = codeList.toArray(new String[0]);
		} else{
			nameString = new String[]{obs.getCode().getText()};
			codeString = new String[]{obs.getCode().getCodingFirstRep().getCode()};
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

	private void setFilters(Observation obs){
		Set<String> set = new HashSet<>();
		for(String value : getValueString()){
			switch(value){

			}
		}
	}

}
