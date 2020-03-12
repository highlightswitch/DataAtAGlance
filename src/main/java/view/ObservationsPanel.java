package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ObservationsPanel extends JPanel {

	private DefaultListModel<ObservationData> listModel;

	public ObservationsPanel(){
		this.setLayout(new BorderLayout());
		createViewDropDownPanel();
		createObservationList();
	}

	private void createViewDropDownPanel(){
		JPanel panel = new JPanel();

		String[] viewTypes = { "Most Recent", "Category", "Favourites" };
		JComboBox<String> comboBox = new JComboBox<>(viewTypes);

		panel.add(comboBox);
		this.add(comboBox, BorderLayout.PAGE_START);
	}

	public void createObservationList(){

		this.listModel = new DefaultListModel<>();
		JList<ObservationData> jList = new JList<>(listModel);
		jList.setCellRenderer(new ObservationListCellRenderer());

		JScrollPane scrollPane = new JScrollPane(jList);
		scrollPane.setBorder(new EmptyBorder(10,10,10,10));
		this.add(scrollPane);
	}

	public void addObservation(ObservationData obs){
		listModel.addElement(obs);
	}

	private static class ObservationListCellRenderer extends JLabel implements ListCellRenderer<ObservationData> {

		public ObservationListCellRenderer(){
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
			sb.append(value.getCodeString()[0]);
			sb.append("<br/");
			sb.append(value.getValueString()[0]);

			for(int i = 1; i < value.getCodeString().length; i++){
				sb.append(value.getCodeString()[i]);
				sb.append("<br/");
				sb.append(value.getValueString()[i]);
			}
			setText(sb.toString());

			Color background;
			Color foreground;

			if (isSelected) {
				background = Color.LIGHT_GRAY;
				foreground = Color.WHITE;
			} else {
				background = Color.WHITE;
				foreground = Color.BLACK;
			}

			setBackground(background);
			setForeground(foreground);

			return this;

		}

	}


}