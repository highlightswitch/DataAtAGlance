package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ObservationsPanel extends JPanel {

	private List<ObservationListElement> elements;

	public ObservationsPanel(){

		this.setLayout(new BorderLayout());

		JList<ObservationListElement> jList = new JList<>(getListModel());
		jList.setCellRenderer(ObservationListElement.getListCellRenderer());

		JScrollPane scrollPane = new JScrollPane(jList);
		this.add(scrollPane);
	}

	private ListModel<ObservationListElement> getListModel(){
		DefaultListModel<ObservationListElement> model = new DefaultListModel<>();
		model.addElement(new ObservationListElement());
		model.addElement(new ObservationListElement());
		model.addElement(new ObservationListElement());
		return model;
	}

}
