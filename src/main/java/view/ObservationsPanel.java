package view;

import model.ObservationData;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ObservationsPanel extends JPanel {

	private MainView mainView;

	private DefaultListModel<ObservationData> listModel;
	private final static String               MOST_RECENT = "Most Recent";
	private final static String               FILTERED = "Filtered";
	private final static String               FAVOURITES = "Favourites";

	private JPanel cards;

	public ObservationsPanel(MainView mainView){

		this.mainView = mainView;
		this.listModel = new DefaultListModel<>();
		updateListModelToMostRecent();

		this.setLayout(new BorderLayout());

		this.cards = new JPanel(new CardLayout());
		cards.add(createMostRecentPanel(), MOST_RECENT);
		cards.add(createFilteredPanel(), FILTERED);
		cards.add(createFavouritesPanel(), FAVOURITES);

		this.add(createViewTypeComboBoxPanel(), BorderLayout.PAGE_START);
		this.add(cards);
	}

	private JPanel createViewTypeComboBoxPanel(){
		JPanel panel = new JPanel(new BorderLayout());

		String[] comboBoxItems = { MOST_RECENT, FILTERED, FAVOURITES };
		JComboBox<String> comboBox = new JComboBox<>(comboBoxItems);
		comboBox.setEditable(false);

		comboBox.addItemListener(e -> {
			String cardShowing =  (String) e.getItem();
			switch(cardShowing){
				case MOST_RECENT:
					updateListModelToMostRecent();
					break;
				case FILTERED:
					updateListModelToFilter("");
					break;
				case FAVOURITES:
					break;
				default:
					System.out.println("ERROR with comboBox");
			}
			CardLayout cardLayout = (CardLayout) cards.getLayout();
			cardLayout.show(cards, (String) e.getItem());
		});

		panel.add(comboBox, BorderLayout.PAGE_START);
		return panel;
	}

	private JPanel createMostRecentPanel(){
		JPanel panel = new JPanel(new BorderLayout());
		JScrollPane scrollPane = createObservationList();
		scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.add(scrollPane);
		return panel;
	}

	private JPanel createFilteredPanel(){
		JPanel panel = new JPanel(new BorderLayout());
		JPanel inner = new JPanel(new BorderLayout());
		inner.setBorder(new EmptyBorder(10, 10, 10, 10));
		inner.add(createFilterComboBoxPanel(), BorderLayout.PAGE_START);
		inner.add(createObservationList());
		panel.add(inner);
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

		return new JScrollPane(jList);
	}

	public JPanel createFilterComboBoxPanel(){
		JPanel panel = new JPanel(new BorderLayout());

		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setEditable(false);

		List<String> comboBoxItems = getPossibleFilters();
		comboBoxItems.add(0, "(choose a filter)");
		for(String item : comboBoxItems)
			comboBox.addItem(item);

		comboBox.addItemListener(e -> updateListModelToFilter((String) e.getItem()));

		panel.add(comboBox, BorderLayout.PAGE_START);
		return panel;
	}

	public void updateListModelToMostRecent(){
		listModel.removeAllElements();
		for(ObservationData data : mainView.getAllObservationsSortedByMostRecent()){
			listModel.addElement(data);
			// System.out.println(data.getFilters());
		}
		// listModel.addAll(mainView.getAllObservationsSortedByMostRecent());
	}

	public List<String> getPossibleFilters(){
		Set<String> set = new HashSet<>();
		for(ObservationData data : mainView.getAllObservationsSortedByMostRecent())
			set.addAll(data.getFilters());
		return new ArrayList<>(set);
	}

	public void updateListModelToFilter(String category){
		listModel.removeAllElements();
		for(ObservationData data : mainView.getAllObservationsSortedByMostRecent()){
			if(data.getFilters().contains(category))
				listModel.addElement(data);
		}
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
			sb.append(value.getCodeNameString().get(0));
			sb.append("<br/");
			sb.append(value.getValueString().get(0));

			for(int i = 1; i < value.getCodeNameString().size(); i++){
				sb.append(value.getCodeNameString().get(i));
				sb.append("<br/");
				sb.append(value.getValueString().get(i));
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