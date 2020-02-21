package view;

import model.ObservationData;

import javax.swing.*;
import java.awt.*;

public class ObservationsPanel extends JPanel {

	private DefaultListModel<ObservationListElement> listModel;

	public ObservationsPanel(){

		this.setLayout(new BorderLayout());

		this.listModel = new DefaultListModel<>();

		JList<ObservationListElement> jList = new JList<>(listModel);
		jList.setCellRenderer(ObservationListElement.getListCellRenderer());

		JScrollPane scrollPane = new JScrollPane(jList);
		this.add(scrollPane);
	}

	public void addObservation(ObservationData obs){
		listModel.addElement(new ObservationListElement(obs));
	}

}
