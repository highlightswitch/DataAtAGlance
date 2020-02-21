package controller;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

class DB {

	private static MongoClient _MONGO;
	private static MongoClientURI uri = new MongoClientURI("mongodb://admin:admin@cluster0-shard-00-00-nofrp.mongodb.net:27017,cluster0-shard-00-01-nofrp.mongodb.net:27017,cluster0-shard-00-02-nofrp.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority");

	static MongoDatabase get(){
		if(_MONGO==null)
			_MONGO = new MongoClient(uri);
		return _MONGO.getDatabase("testDatabase");
	}

	static void close(){
		if(_MONGO != null){
			System.out.println("xcgvs");
			_MONGO.close();
			_MONGO = null;
		}
	}

}
