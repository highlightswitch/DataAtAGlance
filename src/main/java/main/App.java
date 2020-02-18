package main;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import controller.MainController;
import org.bson.Document;
import org.hl7.fhir.r4.model.Patient;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.text;

public class App {

	private static FhirContext _CONTEXT;
	private static MongoClient _MONGO;

	public static void main(String[] args){
		new App();
	}

	private App(){
		MainController.get();
	}

	// private void oldCode(){
	// 	String json = getJSONofF001();
	// 	Patient f001 = findPatientsByName("van");
	// 	if(f001 != null)
	// 		System.out.println(f001.getName().get(0).getGivenAsSingleString());
	//
	// 	MONGO().close();
	// }

	private Patient findPatientsByName(String name){
		//Connect to DB
		MongoClient mongoClient = MONGO();
		MongoDatabase database = mongoClient.getDatabase("testDatabase");
		MongoCollection<Document> collection = database.getCollection("people");

		MongoIterable<Document> docs = collection.find(text(name));

		return docs.first() == null ? null : parsePatient(docs.first().toJson());

	}

	public String getJSONofF001(){

		//Connect to DB
		MongoClient mongoClient = MONGO();
		MongoDatabase database = mongoClient.getDatabase("testDatabase");

		MongoCollection<Document> collection = database.getCollection("people");
		// Document doc = collection.find(eq("id", "f001"))
		// 		.projection(fields(include("name"), excludeId()))
		// 		.first();
		FindIterable<Document> docs = collection.find(eq("id", "f001"));
		Document doc = docs.first();

		return doc.toJson();
	}

	private Patient parsePatient(String json){
		IParser parser = CONTEXT().newJsonParser();
		return parser.parseResource(Patient.class, json);
	}

	private static MongoClient MONGO(){
		if(_MONGO== null)
			_MONGO = new MongoClient();
		return _MONGO;
	}

	private static FhirContext CONTEXT(){
		if(_CONTEXT == null)
			_CONTEXT = FhirContext.forR4();
		return _CONTEXT;
	}



}
