package controller;

import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.or;

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

	public static String[] getJSONDocumentsByID(String collection, String[] ids){

		//Create the query that gets all the documents by the given ids
		Bson query;
		if(ids.length>1){
			Iterator<String> it = Arrays.asList(ids).iterator();
			query = or(eq("_id", it.next()), eq("_id", it.next()));
			while(it.hasNext()){
				query = or(eq("_id", it.next()), query);
			}
		} else{
			query = eq("_id", ids[0]);
		}

		//Get a list of all the documents
		MongoCursor<Document> it =  DB.get()
				.getCollection(collection)
				.find(query)
				.iterator();

		//Convert those documents into json strings
		Vector<String> jsons = new Vector<>();
		while(it.hasNext())
			jsons.add(it.next().toJson());

		return jsons.toArray(new String[0]);
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
