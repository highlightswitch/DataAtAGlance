package model;

import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Observation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ObservationDataImpl implements ObservationData {

	private boolean isComposite;

	private List<String> nameString;
	private List<String> codeString;
	private List<String> valueString;
	private List<String> unitString;
	private String dateString;

	private List<String> filters;

	public ObservationDataImpl(Observation obs){
		setIsComposite(obs);
		setCode(obs);
		setValue(obs);
		setDateString(obs);
		setFilters(obs);
	}

	public boolean getIsComposite(){ return isComposite; }
	public List<String> getCodeNameString(){ return nameString; }
	public List<String> getCodeString(){ return codeString; }
	public List<String> getValueString(){ return valueString; }
	public List<String> getUnitString(){ return unitString; }
	public String getDateString(){ return dateString; }

	public List<String> getFilters(){ return filters; }

	public void setIsComposite(Observation obs){
		this.isComposite = obs.hasComponent();
	}

	private void setCode(Observation obs){
		this.nameString = new ArrayList<>();
		this.codeString = new ArrayList<>();

		nameString.add(obs.getCode().getText());
		codeString.add(obs.getCode().getCodingFirstRep().getCode());

		if(isComposite){
			for(Observation.ObservationComponentComponent comp : obs.getComponent()){
				nameString.add(comp.getCode().getText());
				codeString.add(comp.getCode().getCodingFirstRep().getCode());
			}
		}
	}

	private void setValue(Observation obs){
		this.valueString = new ArrayList<>();
		this.unitString = new ArrayList<>();

		if(isComposite){
			for(Observation.ObservationComponentComponent comp : obs.getComponent()){
				valueString.add(comp.getValueQuantity().getValue().toString());
				unitString.add(comp.getValueQuantity().getUnit());
			}
		} else {
			valueString.add(obs.getValueQuantity().getValue().toString());
			unitString.add(obs.getValueQuantity().getUnit());
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
