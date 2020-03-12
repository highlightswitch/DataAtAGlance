package view;

import controller.ViewController;
import model.ObservationData;
import org.hl7.fhir.r4.model.Observation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainView extends AppView {

	private ViewController vc;

	private ObservationsPanel obPanel;
	private TablePanel tPanel;

	public MainView(ViewController vc){
		this.vc = vc;
		this.obPanel = new ObservationsPanel();
		this.tPanel = new TablePanel();
	}

	public JPanel makePanel(){
		vc.getFrame().setJMenuBar(makeMenuBar());

		JPanel panel = new JPanel(new GridLayout(1,2));
		// panel.add(new JLabel("Logged in as: " + vc.getLoggedInPatient().getName().get(0).getNameAsSingleString()));

		for(Observation obs : vc.getRetrievedObservations())
			obPanel.addObservation(new ObservationData(obs));

		for(Observation obs : vc.getRetrievedObservations())
			tPanel.addObservation(obs);

		panel.add(obPanel);
		panel.add(tPanel);


		// panel.setMinimumSize(new Dimension(700, 500));

		return panel;
	}

	private JMenuBar makeMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		ArrayList<JMenuItem> menuItems = new ArrayList<>();

		JMenu mMenu = new JMenu("Menu");

		JMenuItem logOut = new JMenuItem("Log Out");
		logOut.setActionCommand("Log Out");
		menuItems.add(logOut);

		JMenuItem quit = new JMenuItem("Quit");
		quit.setActionCommand("Quit");
		menuItems.add(quit);


		mMenu.add(logOut);
		mMenu.add(quit);

		menuBar.add(mMenu);

		return menuBar;
	}

}
