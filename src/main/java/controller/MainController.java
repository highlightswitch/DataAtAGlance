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

	private MainController(){

		//Create model
		this.model = new Model();

		//Setup connection with DB
		connectToMongoAtlas();

		//Create view


	}

	private void connectToMongoAtlas(){
		System.out.println(DB.get().listCollectionNames().first());

	}

}
