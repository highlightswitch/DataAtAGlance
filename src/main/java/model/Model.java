package model;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import controller.DatabaseController;
import org.hl7.fhir.r4.model.*;

import java.util.ArrayList;
import java.util.List;

public class Model {

	private final FhirContext _CONTEXT = FhirContext.forR4();
	private final String defaultPatientID = "urn:uuid:34e1fbd6-2e38-4a7d-a416-f309c447cab0";

	private Patient currentLoggedInPatient;
	private List<Observation> retrievedObservations;

	public Model(){
		retrievedObservations = new ArrayList<>();
	}

	public Patient login(){
		this.loadDefaultPatient();
		return currentLoggedInPatient;
	}

	public Patient fakeLogin(){
		currentLoggedInPatient = new Patient().addName(new HumanName().addPrefix("Mr.").addGiven("Elliot").setFamily("Alderson"));
		retrievedObservations.add(getFakeObservation());
		return currentLoggedInPatient;
	}

	public Observation getFakeObservation(){
		Observation obs = new Observation();
		obs.setCode(new CodeableConcept().setText("Body Height"));
		obs.setValue(new Quantity().setValue(183.1).setUnit("cm"));
		return obs;
	}

	private void loadDefaultPatient( ){
		IParser parser = _CONTEXT.newJsonParser();
		String patientJson = DatabaseController.getJSONDocumentByID("patients", defaultPatientID);
		currentLoggedInPatient = parser.parseResource(Patient.class, patientJson);

		List<String> docs = DatabaseController.getAllObservationsBySubjectID(defaultPatientID);
		for(String obsJson : docs){
			Observation obs = parser.parseResource(Observation.class, obsJson);
			if(obs.getCode().getText().equals("Tobacco smoking status NHIS"))
				System.out.println("Smoking ommited");
			else
				retrievedObservations.add(obs);
		}
	}

	public Patient getLoggedInPatient( ){
		return currentLoggedInPatient;
	}

	public List<Observation> getRetrievedObservations( ){
		return retrievedObservations;
	}
}
