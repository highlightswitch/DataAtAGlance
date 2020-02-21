package controller;

import model.Model;

public class MainController {

	private static MainController _INSTANCE;
	public static MainController get(){
		if(_INSTANCE== null)
			_INSTANCE = new MainController();
		return _INSTANCE;
	}

	private Model model;
	private ViewController view;

	private MainController(){

		//Create model
		this.model = new Model();
		this.view = new ViewController(model, "defaultUsername");

		if(ensureMongoAtlasConnection()){
			setupShutdownHook();


		} else {
			System.out.println("----");
			System.out.println("ERR - Could not connect to database");
			System.out.println("----");
		}

	}

	private boolean ensureMongoAtlasConnection(){
		try{
			DB.get();
		} catch(ExceptionInInitializerError e){
			return false;
		}
		return true;
	}

	private void setupShutdownHook( ){
		//On shutdown, close database connection
		Runtime.getRuntime().addShutdownHook(new Thread(DB::close));
	}

}
