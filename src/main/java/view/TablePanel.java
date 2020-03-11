package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class TablePanel extends JPanel {

	public TablePanel(){

		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		dataset.addValue( 15 , "bpm" , "1970" );
		dataset.addValue( 30 , "bpm" , "1980" );
		dataset.addValue( 60 , "bpm" ,  "1990" );
		dataset.addValue( 120 , "bpm" , "2000" );
		dataset.addValue( 240 , "bpm" , "2010" );
		dataset.addValue( 300 , "bpm" , "2014" );

		JFreeChart chart = ChartFactory.createLineChart("Heart Rate", "Date", "Beats per minute", dataset);

		JPanel panel = new ChartPanel(chart);
		panel.setPreferredSize(new Dimension(400, 600));
		this.add(panel);

	}

}
