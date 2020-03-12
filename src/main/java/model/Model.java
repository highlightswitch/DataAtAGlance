package model;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import controller.DatabaseController;
import org.hl7.fhir.r4.model.*;

import java.util.*;

public class Model {

	private final FhirContext _CONTEXT = FhirContext.forR4();
	private final String defaultPatientID = "urn:uuid:34e1fbd6-2e38-4a7d-a416-f309c447cab0";

	private MostRecentComparator byMostRecent;

	private Patient           currentLoggedInPatient;
	private Set<Observation> allCurrentPatientObservations;

	public Model(){
		byMostRecent = new MostRecentComparator();
		allCurrentPatientObservations = new TreeSet<>(byMostRecent);
	}

	public Patient login(){
		this.loadDefaultPatient();
		return currentLoggedInPatient;
	}

	public Patient fakeLogin(){
		currentLoggedInPatient = new Patient().addName(new HumanName().addPrefix("Mr.").addGiven("Elliot").setFamily("Alderson"));
		addFakeObservations(20);
		return currentLoggedInPatient;
	}

	public void addFakeObservations(int quantity){
		for(int i = 0; i < quantity; i++){
			Observation obs = new Observation();
			obs.setCode(new CodeableConcept().setText("Body Height"));
			obs.setValue(new Quantity().setValue(181 + i).setUnit("cm"));
			obs.setEffective(new DateTimeType(new Date(2020, Calendar.JANUARY, 1 + i)));
			allCurrentPatientObservations.add(obs);
		}
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
				allCurrentPatientObservations.add(obs);;
		}
	}

	public Patient getLoggedInPatient( ){
		return currentLoggedInPatient;
	}

	public Set<Observation> getAllRetrievedObservations( ){
		return allCurrentPatientObservations;
	}

	public Set<Observation> getObservationsByLoincCode(String code){
		Set<Observation> list =  new TreeSet<>(byMostRecent);;
		for(Observation obs : allCurrentPatientObservations){
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
