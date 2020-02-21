package controller;

import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class DatabaseController {

	public static boolean ensureDatabaseConnection(){
		try{
			DB.get();
		} catch(ExceptionInInitializerError e){
			return false;
		}
		return true;
	}

	public static String getJSONDocumentByID(String collection, String id){

		Document doc =  DB.get()
				.getCollection(collection)
				.find(eq("_id", id))
				.first();

		if(doc != null)
			return doc.toJson();
		return null;
	}

	public static List<String> getAllObservationsBySubjectID(String id){

		MongoCursor<Document> it = DB.get()
				.getCollection("observations")
				.find(eq("subject.reference", id)).iterator();

		List<String> docs = new ArrayList<>();

		while(it.hasNext())
			docs.add(it.next().toJson());

		return docs;
	}

}
