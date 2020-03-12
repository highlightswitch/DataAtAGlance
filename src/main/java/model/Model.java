package model;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import controller.DatabaseController;
import org.hl7.fhir.r4.model.*;

import java.util.*;

public class Model {

	private final FhirContext _CONTEXT = FhirContext.forR4();
	private final String defaultPatientID = "urn:uuid:34e1fbd6-2e38-4a7d-a416-f309c447cab0";

	private Patient currentLoggedInPatient;
	private List<Observation> retrievedObservations;

	private MostRecentComparator byMostRecent;

	public Model(){
		retrievedObservations = new ArrayList<>();
		byMostRecent = new MostRecentComparator();
	}

	public Patient login(){
		this.loadDefaultPatient();
		return currentLoggedInPatient;
	}

	public Patient fakeLogin(){
		currentLoggedInPatient = new Patient().addName(new HumanName().addPrefix("Mr.").addGiven("Elliot").setFamily("Alderson"));
		addObservation(getFakeObservation1());
		addObservation(getFakeObservation2());
		return currentLoggedInPatient;
	}

	public Observation getFakeObservation1(){
		Observation obs = new Observation();
		obs.setCode(new CodeableConcept().setText("Body Height"));
		obs.setValue(new Quantity().setValue(183.1).setUnit("cm"));
		obs.setEffective(new DateTimeType(new Date(2000, Calendar.MARCH, 1)));
		return obs;
	}

	public Observation getFakeObservation2(){
		Observation obs = new Observation();
		obs.setCode(new CodeableConcept().setText("Body Height"));
		obs.setValue(new Quantity().setValue(200.1).setUnit("cm"));
		obs.setEffective(new DateTimeType(new Date(2000, Calendar.MARCH, 1)));
		return obs;
	}

	private void loadDefaultPatient( ){
		IParser parser = _CONTEXT.newJsonParser();
		String patientJson = DatabaseController.getJSONDocumentByID("patients", defaultPatientID);
		currentLoggedInPatient = parser.parseResource(Patient.class, patientJson);

		List<String> docs = DatabaseController.getAllObservationsBySubjectID(defaultPatientID);
		for(String obsJson : docs){
			//TODO: Change this to omit category:survey instead of text for smoking
			Observation obs = parser.parseResource(Observation.class, obsJson);
			if(!obs.getCode().getText().equals("Tobacco smoking status NHIS"))
				addObservation(obs);
		}
	}

	public Patient getLoggedInPatient( ){
		return currentLoggedInPatient;
	}

	public void addObservation(Observation obv){
		retrievedObservations.add(obv);
		retrievedObservations.sort(byMostRecent);
	}

	public List<Observation> getAllRetrievedObservations( ){
		return retrievedObservations;
	}

	public List<Observation> getObservationsByLoincCode(String code){
		List<Observation> list = new ArrayList<>();
		for(Observation obs : retrievedObservations){
			for(Coding coding : obs.getCode().getCoding()){
				if(coding.getSystem().equals("http://loinc.org") && coding.getCode().equals(code))
					list.add(obs);
			}
		}
		return list;
	}

	private static class MostRecentComparator implements Comparator<Observation> {
		@Override
		public int compare(Observation o1, Observation o2){
			if(o1.hasEffectiveDateTimeType() && o2.hasEffectiveDateTimeType()){
				if(o1.getEffectiveDateTimeType().getValue().before(o2.getEffectiveDateTimeType().getValue()))
					return 1;
				else
					return -1;
			}
			return 0;
		}
	}
}
