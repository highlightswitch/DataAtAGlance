package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ObservationsPanel extends JPanel {

	private MainView mainView;

	private DefaultListModel<ObservationData> listModel;
	private final static String MOST_RECENT = "Most Recent";
	private final static String CATEGORY = "Category";
	private final static String FAVOURITES = "Favourites";

	private JPanel cards;

	public ObservationsPanel(MainView mainView){

		this.mainView = mainView;
		this.listModel = new DefaultListModel<>();
		updateListModelToMostRecent();

		this.setLayout(new BorderLayout());

		this.cards = new JPanel(new CardLayout());
		cards.add(createMostRecentPanel(), MOST_RECENT);
		cards.add(createCategoryPanel(), CATEGORY);
		cards.add(createFavouritesPanel(), FAVOURITES);

		this.add(createComboBoxPanel(), BorderLayout.PAGE_START);
		this.add(cards);
	}

	private JPanel createComboBoxPanel(){
		JPanel panel = new JPanel(new BorderLayout());

		String[] comboBoxItems = { MOST_RECENT, CATEGORY, FAVOURITES };
		JComboBox<String> comboBox = new JComboBox<>(comboBoxItems);
		comboBox.setEditable(false);

		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e){
				String cardShowing =  (String) e.getItem();
				switch(cardShowing){
					case MOST_RECENT:
						updateListModelToMostRecent();
						break;
					case CATEGORY:
						break;
					case FAVOURITES:
						break;
					default:
						System.out.println("ERROR with comboBox");
				}
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, (String) e.getItem());
			}
		});

		panel.add(comboBox, BorderLayout.PAGE_START);
		return panel;
	}

	private JPanel createMostRecentPanel(){
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createObservationList());
		return panel;
	}

	private JPanel createCategoryPanel(){
		JPanel panel = new JPanel();
		panel.add(new JLabel("Category Panel Here"));
		panel.setBorder(new LineBorder(Color.BLUE));
		return panel;
	}

	private JPanel createFavouritesPanel(){
		JPanel panel = new JPanel();
		panel.add(new JLabel("Favourites Panel Here"));
		panel.setBorder(new LineBorder(Color.BLUE));
		return panel;
	}

	public JScrollPane createObservationList(){

		JList<ObservationData> jList = new JList<>(listModel);
		jList.setCellRenderer(new ObservationListCellRenderer());

		JScrollPane scrollPane = new JScrollPane(jList);
		scrollPane.setBorder(new EmptyBorder(10,10,10,10));
		return scrollPane;
	}

	public void addObservation(ObservationData obs){
		listModel.addElement(obs);
	}

	public void updateListModelToMostRecent(){
		listModel.removeAllElements();
		for(ObservationData data : mainView.getAllObservationsSortedByMostRecent()){
			listModel.addElement(data);
		}
		// listModel.addAll(mainView.getAllObservationsSortedByMostRecent());
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