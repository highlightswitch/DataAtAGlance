package model;

import ca.uhn.fhir.context.FhirContext;
import controller.DatabaseController;
import org.hl7.fhir.r4.model.Patient;

public class Model {

	private final FhirContext _CONTEXT = FhirContext.forR4();
	private final String defaultPatientID = "urn:uuid:34e1fbd6-2e38-4a7d-a416-f309c447cab0";

	private Patient currentLoggedInPatient;

	public Model(){

	}

	public Patient login( ){
		this.loadDefaultPatient();
		return currentLoggedInPatient;
	}

	private void loadDefaultPatient( ){
		String json = DatabaseController.getJSONDocumentByID("patients", defaultPatientID);
		currentLoggedInPatient = _CONTEXT.newJsonParser().parseResource(Patient.class, json);
	}

	public Patient getLoggedInPatient( ){
		return currentLoggedInPatient;
	}
}
