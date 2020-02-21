package view;

import controller.ViewController;

import javax.swing.*;
import java.util.ArrayList;

public class TableView implements AppView {

	private JPanel panel;
	private JMenuBar menuBar;

	private ViewController vc;

	public TableView(ViewController vc){
		this.vc = vc;

		this.panel = new JPanel();
		this.panel.add(new JLabel("Logged in as: " + vc.getLoggedInPatient().getName().get(0).getNameAsSingleString()));
	}

	public JPanel getPanel( ){
		return panel;
	}

	private void setUpMenuBar(){
		menuBar = new JMenuBar();
		ArrayList<JMenuItem> menuItems = new ArrayList<>();

		JMenu mFile = new JMenu("File");

		JMenuItem load = new JMenuItem("Load");
		load.setActionCommand("Load");
		menuItems.add(load);

		JMenuItem save = new JMenuItem("Save");
		save.setActionCommand("Save");
		menuItems.add(save);

		JMenuItem quit = new JMenuItem("Quit");
		quit.setActionCommand("Quit");
		menuItems.add(quit);


		mFile.add(load);
		mFile.add(save);
		mFile.add(quit);

		menuBar.add(mFile);
	}

}
