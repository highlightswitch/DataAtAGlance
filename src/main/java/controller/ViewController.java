package controller;

import model.Model;
import view.*;

import javax.swing.*;
import java.awt.*;


public class ViewController {

	private Model model;
	private String defaultUserName;

	private JFrame frame;

	ViewController(Model model, String defaultUserName){

		this.model = model;
		this.defaultUserName = defaultUserName;

		frame = new JFrame("Data at a Glance");
		switchView(ViewType.LOGIN);

		// frame.getContentPane().add(new JTable(new Object[][]{{"Joe", "Briggs"},{"Kathy", "Smith"}}, new String[]{"First Name", "Family Name"}));
	}



	private void drawFrame(AppView view){

		JPanel activePanel = view.getPanel();

		frame.setContentPane(activePanel);
		frame.pack();
		frame.setVisible(true);

		frame.setMinimumSize(new Dimension(550, 650));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public void switchView(ViewType currentView){

		switch(currentView){
			case LOGIN:
				drawFrame(new LoginView(this, defaultUserName));
				break;
			case MAIN:
				drawFrame(new TableView());
				break;
		}

	}

	public void login(){
		model.login();
		switchView(ViewType.MAIN);
	}
}
