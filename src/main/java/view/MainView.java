package view;

import controller.ViewController;
import model.ObservationData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainView extends AppView {

	private ViewController vc;

	private ObservationsPanel obPanel;
	private GraphPanel        gPanel;

	private ObservationData selectedObs;

	public MainView(ViewController vc){
		this.vc = vc;
		this.obPanel = new ObservationsPanel(this);
		this.gPanel = new GraphPanel();
	}

	public JPanel makePanel(){
		vc.getFrame().setJMenuBar(makeMenuBar());

		JPanel panel = new JPanel(new GridLayout(1,2));

		panel.add(obPanel);
		panel.add(gPanel);

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

	String getPatientName(){
		return vc.getPatientName();
	}

	ObservationData[] getAllObservationsSortedByMostRecent(){
		return vc.getAllObsData().toArray(new ObservationData[0]);
	}

	void setSelectedObs(ObservationData data){
		this.selectedObs = data;
	}

	void updateGraph(){
		ObservationData[] datas = vc.getDataForGraph(selectedObs.getCodeString().get(0)).toArray(new ObservationData[0]);
		gPanel.updateData(datas);
	}

}
