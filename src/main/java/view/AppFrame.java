package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AppFrame {

	private JFrame frame;
	private JMenuBar menuBar;

	private void makeFrame(){

		frame = new JFrame("Data at a Glance");
		setUpMenuBar();
		drawFrame(new TableView());

		// frame.getContentPane().add(new JTable(new Object[][]{{"Joe", "Briggs"},{"Kathy", "Smith"}}, new String[]{"First Name", "Family Name"}));
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

	private void drawFrame(AppView view){

		JPanel activePanel = view.getPanel();

		frame.setContentPane(activePanel);
		frame.setJMenuBar(menuBar);
		frame.setVisible(true);

		frame.setMinimumSize(new Dimension(550, 650));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}


}
