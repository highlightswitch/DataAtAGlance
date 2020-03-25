package controller;

import model.Model;

public class MainController {

	private static MainController _INSTANCE;
	public static void start(){
		if(_INSTANCE== null)
			_INSTANCE = new MainController();
	}

	private MainController(){

		//Create model and view
		Model model = new Model();
		ViewController view = new ViewController(model, "defaultUsername");

		if(DB.get().ensureValidDatabaseConnection()){
			//On shutdown, close database connection
			Runtime.getRuntime().addShutdownHook(new Thread(DB.get()::closeDatabaseConnection));

			//Start the view
			view.start();

		} else {
			System.out.println("----");
			System.out.println("ERR - Could not connect to database");
			System.out.println("----");
		}

	}

}
