package view;

import javax.swing.*;

class TableView implements AppView {

	private JPanel panel;

	TableView(){
		this.panel = new JPanel();
	}

	public JPanel getPanel( ){
		return panel;
	}

}
