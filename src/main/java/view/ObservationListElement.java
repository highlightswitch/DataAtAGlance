package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ObservationListElement {

	public ObservationListElement(){

	}

	public String getName(){
		return "iubhfgdeirfgbs";
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

				JPanel innerPanel = new JPanel();
				innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
				innerPanel.add(new JLabel(value.getName()));
				innerPanel.add(new JLabel(value.getName()));
				innerPanel.add(new JLabel(value.getName()));

				if(isSelected)
					innerPanel.setBorder(new LineBorder(Color.BLUE));

				outerPanel.add(innerPanel);
				return outerPanel;
			}
		};
	}

}
