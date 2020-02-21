package view;

import javax.swing.*;
import java.awt.*;

public class TablePanel extends JPanel {

	private JScrollPane scrollPane;
	private JList<String> obList;

	public TablePanel(){

		this.setLayout(new BorderLayout());

		obList = new JList<>(new String[]{"name0", "name1", "name2"});
		scrollPane = new JScrollPane(obList);

		this.add(scrollPane);
	}

}
