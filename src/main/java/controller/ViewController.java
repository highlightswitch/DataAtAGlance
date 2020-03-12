package controller;

import model.Model;
import view.ObservationData;
import model.ObservationDataImpl;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import view.AppView;
import view.LoginView;
import view.MainView;
import view.ViewType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;


public class ViewController {

	private Model model;
	private String defaultUserName;

	private JFrame frame;
	private ViewType currentView = null;

	private MenuActionListener menu;

	ViewController(Model model, String defaultUserName){

		this.model = model;
		this.defaultUserName = defaultUserName;

		this.menu = new MenuActionListener();

		// frame.getContentPane().add(new JTable(new Object[][]{{"Joe", "Briggs"},{"Kathy", "Smith"}}, new String[]{"First Name", "Family Name"}));
	}

	public void start(){
		frame = new JFrame("Data at a Glance");
		switchView(ViewType.LOGIN);
	}

	public void startWithFakeLogin(){
		frame = new JFrame("Data at a Glance");
		model.fakeLogin();
		switchView(ViewType.MAIN);
	}


	private void drawFrame(AppView view){

		JPanel activePanel = view.getPanel();

		//TODO: Remove before submission
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
					System.exit(0);
				}
			}
		});

		frame.setContentPane(activePanel);
		frame.pack();
		frame.setVisible(true);

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public JFrame getFrame( ){
		return frame;
	}

	public void switchView(ViewType currentView){

		this.currentView = currentView;

		switch(currentView){
			case LOGIN:
				frame.setJMenuBar(null);
				frame.setMinimumSize(new Dimension(300, 500));
				drawFrame(new LoginView(this, defaultUserName));
				break;
			case MAIN:
				frame.setMinimumSize(new Dimension(800, 600));
				drawFrame(new MainView(this));
				break;
		}

	}

	public void loginButtonPressed(){
		model.login();
		switchView(ViewType.MAIN);
	}

	public Patient getLoggedInPatient(){
		return model.getLoggedInPatient();
	}

	public Set<Observation> getRetrievedObservations( ){
		// return model.getObservationsByLoincCode("85354-9"); // Blood Pressure
		return model.getObservationsByLoincCode("29463-7"); // Body Weight
		// return model.getAllRetrievedObservations();
	}

	public Set<ObservationData> getObsDataForPanel(){
		Set<ObservationData> data = new HashSet<>();
		for(Observation obs : getRetrievedObservations())
			data.add(new ObservationDataImpl(obs));
		return data;
	}

	public ActionListener getActionListener(String type) {
		if(type.equals("Menu"))
			return menu;
		return (e -> System.out.println("Cannot find type"));
	}

}
