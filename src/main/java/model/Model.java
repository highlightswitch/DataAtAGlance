package model;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import controller.DB;
import org.hl7.fhir.r4.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class Model {

	//Alfonso has 6000+ obs

	private final FhirContext _CONTEXT = FhirContext.forR4();
	private final String[] patientIDs = new String[]{
			"urn:uuid:7268261c-8c61-44b4-8704-e3cf3f5eb555",
			"urn:uuid:5bb919d8-78fe-4423-867c-b4d81d67aae5",
			"urn:uuid:34e1fbd6-2e38-4a7d-a416-f309c447cab0",
			"urn:uuid:e35ba847-5350-4d16-9baa-927cd7df8ec2",
			"urn:uuid:e41be0d1-b6c5-4f06-9346-3f50085eda07",
			"urn:uuid:284dac2a-bc05-4633-84b5-21cd31b9fc70",
			"urn:uuid:d7e2730a-3880-4638-bf9a-ad847fd1f71d",
			"urn:uuid:5ffb10c5-4b5c-46a5-ace7-e80001fe1a21",
			"urn:uuid:4d19b061-f0c0-428b-b220-5236649149e7",
			"urn:uuid:579c2be6-f400-4e70-a468-3f0c57b4cd22",
			"urn:uuid:feb5d9c8-3901-41d0-89f5-1fd0823af6f8",
			"urn:uuid:4739aad4-573b-4eed-854c-79e4a518481a",
			"urn:uuid:8f38de73-f6d0-4514-aae3-3ca96d27e449",
			"urn:uuid:fcdb3c29-0b6c-4272-a013-d64831cc2b85",
			"urn:uuid:5a026c6a-5f44-457d-8ead-a35d26285f80"
	};

	private Map<String, String> mapIdToName;
	private Map<String, String> mapNameToId;

	private MostRecentComparator byMostRecent;

	private Patient           currentLoggedInPatient;
	private Set<Observation> allCurrentPatientObservations;

	public Model(){
		byMostRecent = new MostRecentComparator();
		allCurrentPatientObservations = new TreeSet<>(byMostRecent);
		setNameIDMaps();
	}

	private void setNameIDMaps(){
		this.mapIdToName = new HashMap<>();
		this.mapNameToId = new HashMap<>();
		List<String> patientJsons = DB.get().getPatientsByPatientIDs(patientIDs);
		for(String json : patientJsons){
			IParser parser = _CONTEXT.newJsonParser();
			Patient patient = parser.parseResource(Patient.class, json);
			String name = patient.getName().get(0).getNameAsSingleString();
			String id = "urn:uuid:" + patient.getIdentifier().get(1).getValue();
			//TODO: The above is really hacky.
			//This should be fixed when i sort out the IDs of the testPatients
			//The shouldnt start with urn:uuid
			//I don't know if i should be giving them an Id of their own or what

			mapIdToName.put(id, name);
			mapNameToId.put(name, id);

		}
	}

	public void login(String userName){
		this.loadPatientWithId(mapNameToId.get(userName));
	}

	public void fakeLogin(){
		currentLoggedInPatient = new Patient().addName(new HumanName().addPrefix("Mr.").addGiven("Elliot").setFamily("Alderson"));
		addFakeObservations(10);
	}

	public void addFakeObservations(int quantity){
		for(int i = 0; i < quantity; i++){
			Observation obs = new Observation();

			CodeableConcept concept = new CodeableConcept().setText("Body Height");
			concept.getCodingFirstRep().setCode("8302-2");
			concept.getCodingFirstRep().setSystem("http://loinc.org");
			obs.setCode(concept);

			obs.setValue(new Quantity().setValue(181 + i).setUnit("cm"));

			LocalDate date = LocalDate.of(2020, Month.MARCH, 15 + i);
			obs.setEffective(new DateTimeType(java.sql.Date.valueOf(date)));

			allCurrentPatientObservations.add(obs);
		}
	}

	private void loadPatientWithId(String id){
		IParser parser = _CONTEXT.newJsonParser();
		String patientJson = DB.get().getPatientsByPatientIDs(new String[]{id}).get(0);
		currentLoggedInPatient = parser.parseResource(Patient.class, patientJson);

		List<String> docs = DB.get().getAllObservationsBySubjectID(id);
		for(String obsJson : docs){
			Observation obs = parser.parseResource(Observation.class, obsJson);
			if(observationHasQuantityValues(obs) && notDuplicateObservation(obs)){
				allCurrentPatientObservations.add(obs);
			}
		}
	}

	/**
	 * Ensures the observation only has quantity values. If the observation is
	 * composite, it checks each component.
	 * @param obs
	 * @return
	 */
	private boolean observationHasQuantityValues(Observation obs){
		if(obs.hasComponent()){
			boolean bool = true;
			for(Observation.ObservationComponentComponent comp : obs.getComponent())
				bool = bool && comp.getValue() instanceof Quantity;
			return bool;
		} else{
			return obs.getValue() instanceof Quantity;
		}
	}

	/**
	 * If the observation has the same code and the same date as an already added
	 * observation, it returns false.
	 * This is required since the generated data will sometimes produce two
	 * observations with different encounters, but otherwise identical elements.
	 * This is referenced in the following github issue for Synthea:
	 * https://github.com/synthetichealth/synthea/issues/556
	 * @param obs
	 * @return
	 */
	private boolean notDuplicateObservation(Observation obs){
		String code = obs.getCode().getCodingFirstRep().getCode();
		Date date = obs.getEffectiveDateTimeType().getValue();
		Observation[] arr = allCurrentPatientObservations.toArray(new Observation[0]);

		boolean found = false;
		for(int i = 0; i < arr.length && !found; i++){
			found = arr[i].getCode().getCodingFirstRep().getCode().equals(code)
					&& arr[i].getEffectiveDateTimeType().getValue().equals(date);
		}

		return !found;
	}

	public Vector<String> getAvailableUserNames(){
		return new Vector<>(mapNameToId.keySet());
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
