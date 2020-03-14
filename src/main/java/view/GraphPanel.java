package view;

import model.ObservationData;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GraphPanel extends JPanel {

	private JFreeChart chart;
	private TimeSeriesCollection collection;

	public GraphPanel(){
		JPanel panel = new ChartPanel(createChart());
		panel.setPreferredSize(new Dimension(400, 600));
		this.add(panel);
	}

	public void updateData(ObservationData[] datas){
		//Delete all data points
		collection.removeAllSeries();

		//Add new data points
		try{
			for(ObservationData data : datas){
				if(data.getIsComposite()){
					Date date = parseDateWithFormat(data.getDateString(), data.getDateFormat());
					for(int i = 0; i<data.getValueString().size(); i++){
						addDataPoint(date,
									 Double.parseDouble(data.getValueString().get(i)),
									 data.getCodeNameString().get(i+1));
					}
				} else{
					addDataPoint(parseDateWithFormat(data.getDateString(), data.getDateFormat()),
								 Double.parseDouble(data.getValueString().get(0)),
								 data.getCodeNameString().get(0)
					);
				}
			}
		} catch(ParseException e){
			e.printStackTrace();
		}

		//Edit title and y-axis name
		this.chart.setTitle(datas[0].getCodeNameString().get(0));
		ValueAxis axis = ((XYPlot) this.chart.getPlot()).getRangeAxis();
		axis.setLabel(datas[0].getUnitString().get(0));
	}


	private void addDataPoint(Date date, double value, String name){
		TimeSeries series = collection.getSeries(name);
		if(series == null){
			series = new TimeSeries(name);
			collection.addSeries(series);
		}
		series.add(new Day(date), value);
	}

	private Date parseDateWithFormat(String str, String format) throws ParseException {
		return new SimpleDateFormat(format).parse(str);
	}

	private JFreeChart createChart(){
		//Create a time series collection
		this.collection = new TimeSeriesCollection();

		//Create a chart pointing to the collection
		this.chart = ChartFactory.createXYLineChart("title", "x", "y", collection);

		//Set X axis
		DateAxis axis = new DateAxis("Time");
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setDomainAxis(axis);

		return chart;
	}

}
