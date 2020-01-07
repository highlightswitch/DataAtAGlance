import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;
import org.hl7.fhir.r4.model.Patient;

import java.util.Collections;

import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.eq;

public class App {

	private static FhirContext _CONTEXT;
	private static MongoClient _MONGO;

	private App(){

		//Try to focus on storage and server first.
		// String json = getJSONofF001();
		Patient f001 = findPatientsByName("Pieter");
		System.out.println(f001);

		MONGO().close();

		// At first, we auto populate patients from a local JSON file
		// Then we will auto connect to a database.
		// Build login page later

	}

	private Patient findPatientsByName(String name){
		//Connect to DB
		MongoClient mongoClient = MONGO();
		MongoDatabase database = mongoClient.getDatabase("testDatabase");
		MongoCollection<Document> collection = database.getCollection("people");

		MongoIterable<Document> docs = collection.aggregate(
				Collections.singletonList(match(eq("name.given", "Pieter")))
		);

		return parsePatient(docs.first().toJson());

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

	public static void main(String[] args){
		new App();
	}



}
