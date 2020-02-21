package view;

import javax.swing.*;
import java.util.ArrayList;

public class TableView implements AppView {

	private JPanel panel;
	private JMenuBar menuBar;


	public TableView( ){
		this.panel = new JPanel();
		this.panel.add(new JLabel("dfgs"));
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
