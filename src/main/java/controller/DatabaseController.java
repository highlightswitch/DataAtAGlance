package controller;

import org.bson.Document;

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

}
