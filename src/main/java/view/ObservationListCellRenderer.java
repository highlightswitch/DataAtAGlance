package view;

import model.ObservationData;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

class ObservationListCellRenderer
		extends JLabel
		implements ListCellRenderer<ObservationData> {

	public ObservationListCellRenderer( ){
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(
			JList<? extends ObservationData> list,
			ObservationData value, int index,
			boolean isSelected, boolean cellHasFocus){

		Border border = BorderFactory.createLineBorder(Color.GRAY);
		this.setBorder(border);

		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append(value.getDateString());
		sb.append("<br/");
		sb.append(value.getCodeNameString().get(0));
		if(!value.getIsComposite()){
			sb.append("<br/");
			sb.append(value.getValueString().get(0)).append(" ").append(value.getUnitString().get(0));
		}else{
			for(int i = 0; i<value.getValueString().size(); i++){
				sb.append("<br/");
				sb.append(value.getCodeNameString().get(i + 1));
				sb.append("<br/");
				sb.append(value.getValueString().get(i)).append(" ").append(value.getUnitString().get(i));
			}
		}
		this.setText(sb.toString());

		Color background;
		Color foreground;

		if(isSelected){
			background = Color.LIGHT_GRAY;
			foreground = Color.WHITE;
		}else{
			background = Color.WHITE;
			foreground = Color.BLACK;
		}

		setBackground(background);
		setForeground(foreground);

		return this;

	}
}
