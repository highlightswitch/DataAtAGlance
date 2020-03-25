package database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import controller.IDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.or;

public class DatabaseImpl implements IDatabase {

	private final String uri = "mongodb://admin:admin@cluster0-shard-00-00-nofrp.mongodb.net:27017,cluster0-shard-00-01-nofrp.mongodb.net:27017,cluster0-shard-00-02-nofrp.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority";

	private MongoClient MONGO;

	public DatabaseImpl(){
		this.MONGO = new MongoClient(new MongoClientURI(uri));
	}

	private MongoDatabase getDB(){
		return MONGO.getDatabase("testDatabase");
	}

	@Override
	public boolean ensureDatabaseConnection(){
		try{
			getDB();
		} catch(ExceptionInInitializerError e){
			return false;
		}
		return true;
	}

	@Override
	public String getJSONDocumentByID(String collection, String id){

		Document doc =  getDB()
				.getCollection(collection)
				.find(eq("_id", id))
				.first();

		if(doc != null)
			return doc.toJson();
		return null;
	}

	@Override
	public String[] getJSONDocumentsByID(String collection, String[] ids){

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
		MongoCursor<Document> it =  getDB()
				.getCollection(collection)
				.find(query)
				.iterator();

		//Convert those documents into json strings
		Vector<String> jsons = new Vector<>();
		while(it.hasNext())
			jsons.add(it.next().toJson());

		return jsons.toArray(new String[0]);
	}

	@Override
	public List<String> getAllObservationsBySubjectID(String id){

		MongoCursor<Document> it = getDB()
				.getCollection("observations")
				.find(eq("subject.reference", id)).iterator();

		List<String> docs = new ArrayList<>();

		while(it.hasNext())
			docs.add(it.next().toJson());

		return docs;
	}

	@Override
	public void closeDatabaseConnection( ){
		this.MONGO.close();
	}

}
