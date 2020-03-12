package view;

import org.hl7.fhir.r4.model.Observation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class TablePanel extends JPanel {

	private TimeSeries series;

	public TablePanel(){

		JFreeChart chart = getChart();

		DateAxis axis = new DateAxis("Time");
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setDomainAxis(axis);

		JPanel panel = new ChartPanel(chart);
		panel.setPreferredSize(new Dimension(400, 600));
		this.add(panel);

	}

	private XYDataset createDataset() {
		TimeSeries series1 = new TimeSeries("Data");
		Date date0 = new Date(2000, 1, 1);
		Date date1 = new Date(2000, 2, 1);
		series1.add(new Day(date0), 46.6);
		series1.add(new Day(date1), 86.6);
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(series1);
		return dataset;
	}

	private JFreeChart getChart(){
		series = new TimeSeries("Data");
		TimeSeriesCollection col = new TimeSeriesCollection();
		col.addSeries(series);
		return ChartFactory.createXYLineChart("title", "x", "y", col);
	}

	// private XYDataset setDataSet(){
	// 	series1 = new TimeSeries("Data");
	// 	for(Observation obs : data){
	// 		Date date = obs.getEffectiveDateTimeType().getValue();
	// 		series1.add(new Day(date), obs.getValueQuantity().getValue());
	// 		// System.out.println("Date added: " + date + " - " + obs.getValueQuantity().getValue());
	// 	}
	// 	TimeSeriesCollection dataset = new TimeSeriesCollection();
	// 	dataset.addSeries(series1);
	// 	return dataset;
	// }

	public void addObservation(Observation obs){
		Date date = obs.getEffectiveDateTimeType().getValue();
		series.add(new Day(date), obs.getValueQuantity().getValue());
	}

}
