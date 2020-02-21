package view;

import controller.ViewController;

import javax.swing.*;
import java.util.ArrayList;

public class MainView implements AppView {

	private ViewController vc;

	public MainView(ViewController vc){
		this.vc = vc;
	}

	public JPanel getPanel(){
		JPanel panel = new JPanel();
		vc.getFrame().setJMenuBar(makeMenuBar());
		panel.add(new JLabel("Logged in as: " + vc.getLoggedInPatient().getName().get(0).getNameAsSingleString()));

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
