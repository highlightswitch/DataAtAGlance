package model;

import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Observation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ObservationDataImpl implements ObservationData {

	private boolean hasComponent;

	private List<String> nameString;
	private List<String> codeString;
	private List<String> valueString;
	private String dateString;

	private List<String> filters;

	public ObservationDataImpl(Observation obs){
		this.hasComponent = obs.hasComponent();
		setCode(obs);
		setValueString(obs);
		setDateString(obs);
		setFilters(obs);
	}

	public List<String> getCodeNameString(){ return nameString; }
	public List<String> getCodeString(){ return codeString; }
	public List<String> getValueString(){ return valueString; }
	public String getDateString(){ return dateString; }

	public List<String> getFilters(){ return filters; }

	private void setCode(Observation obs){
		this.nameString = new ArrayList<>();
		this.codeString = new ArrayList<>();
		if(hasComponent){
			for(Observation.ObservationComponentComponent comp : obs.getComponent()){
				nameString.add(comp.getCode().getText());
				codeString.add(comp.getCode().getCodingFirstRep().getCode());
			}
		} else{
			nameString.add(obs.getCode().getText());
			codeString.add(obs.getCode().getCodingFirstRep().getCode());
		}
	}

	private void setValueString(Observation obs){
		this.valueString = new ArrayList<>();
		if(hasComponent){
			for(Observation.ObservationComponentComponent comp : obs.getComponent()){
				StringBuilder sb = new StringBuilder();
				sb.append(comp.getValueQuantity().getValue().toString());
				sb.append(comp.getValueQuantity().getUnit());
				valueString.add(sb.toString());
			}
		} else{
			StringBuilder sb = new StringBuilder();
			try{
				sb.append(obs.getValueQuantity().getValue().toString());
				sb.append(obs.getValueQuantity().getUnit());
			} catch(FHIRException e){
				e.printStackTrace();
			}
			valueString.add(sb.toString());
		}
	}

	private void setDateString(Observation obs){
		dateString = obs.getEffectiveDateTimeType().getValue().toString();
	}

	private void setFilters(Observation obs){

		Set<String> set = new HashSet<>();

		for(CodeableConcept concept : obs.getCategory() ){
			for(Coding code : concept.getCoding()){
				if(code.getSystem().equals("http://terminology.hl7.org/CodeSystem/observation-category")){
					switch(code.getCode()){
						case "social-history":
							set.add("Social History");
							break;
						case "vital-signs":
							set.add("Vital Signs");
							break;
						case "imaging":
							set.add("Imaging");
							break;
						case "laboratory":
							set.add("Laboratory");
							break;
						case "procedure":
							set.add("Procedure");
							break;
						case "survey":
							set.add("Survey");
							break;
						case "exam":
							set.add("Exam");
							break;
						case "therapy":
							set.add("Therapy");
							break;
						case "activity":
							set.add("Activity");
							break;

					}
				}
			}
		}

		for(String value : getValueString()){
			switch(value){

			}
		}

		this.filters = new ArrayList<>(set);
	}

}
