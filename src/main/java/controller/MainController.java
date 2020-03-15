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

	private final String defaultPatientID = "urn:uuid:284dac2a-bc05-4633-84b5-21cd31b9fc70";

	private MainController(){

		//Create model
		this.model = new Model();
		this.view = new ViewController(model, "defaultUsername");

		if(DatabaseController.ensureDatabaseConnection()){
			setupShutdownHook();
			this.view.startWithFakeLogin();

		} else {
			System.out.println("----");
			System.out.println("ERR - Could not connect to database");
			System.out.println("----");
		}

	}

	private void setupShutdownHook( ){
		//On shutdown, close database connection
		Runtime.getRuntime().addShutdownHook(new Thread(DB::close));
	}

}
