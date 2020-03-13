package view;

import controller.ViewController;
import model.ObservationData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainView extends AppView {

	private ViewController vc;

	private ObservationsPanel obPanel;
	private TablePanel tPanel;

	public MainView(ViewController vc){
		this.vc = vc;
		this.obPanel = new ObservationsPanel(this);
		this.tPanel = new TablePanel();
	}

	public JPanel makePanel(){
		vc.getFrame().setJMenuBar(makeMenuBar());

		JPanel panel = new JPanel(new GridLayout(1,2));

		panel.add(obPanel);
		panel.add(tPanel);

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

	ObservationData[] getAllObservationsSortedByMostRecent(){
		return vc.getAllObsData().toArray(new ObservationData[0]);
	}

}
