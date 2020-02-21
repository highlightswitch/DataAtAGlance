package view;

import model.ObservationData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ObservationListElement {

	private ObservationData data;

	public ObservationListElement(ObservationData data){
		this.data = data;
	}

	public String[] getCodeString(){
		return data.getCodeString();
	}

	public String[] getValueString(){
		return data.getValueString();
	}

	public static ListCellRenderer<ObservationListElement> getListCellRenderer(){
		return new ListCellRenderer<ObservationListElement>() {
			@Override
			public Component getListCellRendererComponent(
					JList<? extends ObservationListElement> list,
					ObservationListElement value, int index,
					boolean isSelected, boolean cellHasFocus){

				JPanel outerPanel = new JPanel();
				outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.Y_AXIS));
				outerPanel.setBorder(new EmptyBorder(10,10,10,10));
				outerPanel.setBackground(new Color(0,0,0,0));

				int numOfMeasurements = value.getCodeString().length;
				assert numOfMeasurements == value.getValueString().length;
				for(int i = 0; i < numOfMeasurements; i++){
					JPanel innerPanel = new JPanel();
					innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
					innerPanel.add(new JLabel(value.getCodeString()[i]));
					innerPanel.add(new JLabel(value.getValueString()[i]));

					if(isSelected)
						innerPanel.setBorder(new LineBorder(Color.BLUE));

					outerPanel.add(innerPanel);
				}
				return outerPanel;
			}
		};
	}

}
