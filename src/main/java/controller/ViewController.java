package controller;

import model.Model;
import org.hl7.fhir.r4.model.Patient;
import view.*;

import javax.swing.*;
import java.awt.*;


public class ViewController {

	private Model model;
	private String defaultUserName;

	private JFrame frame;
	private ViewType currentView = null;

	ViewController(Model model, String defaultUserName){

		this.model = model;
		this.defaultUserName = defaultUserName;

		// frame.getContentPane().add(new JTable(new Object[][]{{"Joe", "Briggs"},{"Kathy", "Smith"}}, new String[]{"First Name", "Family Name"}));
	}

	public void start(){
		frame = new JFrame("Data at a Glance");
		switchView(ViewType.LOGIN);
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

		this.currentView = currentView;

		switch(currentView){
			case LOGIN:
				drawFrame(new LoginView(this, defaultUserName));
				break;
			case MAIN:
				drawFrame(new TableView(this));
				break;
		}

	}

	public void login(){
		model.login();
		switchView(ViewType.MAIN);
	}

	public Patient getLoggedInPatient(){
		return model.getLoggedInPatient();
	}
}
