package view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import model.ObservationData;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class GraphPanel extends JPanel {

	private JFreeChart chart;
	private TimeSeriesCollection collection;

	private ObservationData[] displayedData;
	private LocalDate fromDate;
	private LocalDate toDate;

	public GraphPanel(){

		this.setLayout(new BorderLayout());

		JPanel chartPanel = new ChartPanel(createChart());
		chartPanel.setPreferredSize(new Dimension(400, 600));

		this.add(createDatePickerPanel(), BorderLayout.PAGE_START);
		this.add(chartPanel);
	}

	private JPanel createDatePickerPanel(){
		JPanel outer = new JPanel(new GridLayout(2,1));
		outer.setBorder(new EmptyBorder(5,10,5,10));

		JPanel from = new JPanel(new BorderLayout());
		DatePicker fromDatePicker = createDatePicker();
		fromDatePicker.addDateChangeListener(e -> {
			System.out.println("ping");
			fromDate = e.getNewDate();
			updateData(displayedData);
		});
		from.add(new JLabel("From :"));
		from.add(fromDatePicker, BorderLayout.PAGE_END);

		JPanel to = new JPanel(new BorderLayout());
		DatePicker toDatePicker = createDatePicker();
		toDatePicker.addDateChangeListener(e -> {
			System.out.println("ping");
			toDate = e.getNewDate();
			updateData(displayedData);
		});
		to.add(new JLabel("To :"));
		to.add(toDatePicker, BorderLayout.PAGE_END);

		outer.add(from);
		outer.add(to);

		return outer;
	}

	private DatePicker createDatePicker(){

		DatePickerSettings settings = new DatePickerSettings();
		settings.setAllowKeyboardEditing(false);
		DatePicker datePicker = new DatePicker(settings);

		JButton button = datePicker.getComponentToggleCalendarButton();
		URL dateImageURL = GraphPanel.class.getResource("/images/datepickerbutton1.png");
		Image image = Toolkit.getDefaultToolkit().getImage(dateImageURL);
		ImageIcon icon = new ImageIcon(image);
		button.setText("");
		button.setIcon(icon);

		return datePicker;
	}

	public void updateData(ObservationData[] data){
		if(data != null){
			//Delete all data points
			collection.removeAllSeries();

			//Set data
			this.displayedData = data;

			//Add new data points
			try{
				for(ObservationData obs : data){
					if(obs.getIsComposite()){
						Date date = parseDateWithFormat(obs.getDateString(), obs.getDateFormat());
						for(int i = 0; i<obs.getValueString().size(); i++){
							addDataPoint(date,
										 Double.parseDouble(obs.getValueString().get(i)),
										 obs.getCodeNameString().get(i + 1));
						}
					}else{
						addDataPoint(parseDateWithFormat(obs.getDateString(), obs.getDateFormat()),
									 Double.parseDouble(obs.getValueString().get(0)),
									 obs.getCodeNameString().get(0)
						);
					}
				}
			}catch(ParseException e){
				e.printStackTrace();
			}

			//Scale x-axis to dates
			if(fromDate!=null && toDate!=null){
				((DateAxis) ((XYPlot) chart.getPlot()).getDomainAxis()).setMinimumDate(java.sql.Date.valueOf(fromDate));
				((DateAxis) ((XYPlot) chart.getPlot()).getDomainAxis()).setMaximumDate(java.sql.Date.valueOf(toDate));
			} else {
				((XYPlot)chart.getPlot()).getDomainAxis().setAutoRange(true);
			}

			//Auto scale y-axis
			((NumberAxis) ((XYPlot) chart.getPlot()).getRangeAxis()).setAutoRangeIncludesZero(false);
			((XYPlot) chart.getPlot()).getRangeAxis().setAutoRange(true);

			//Check if there is data between the dates
			//TODO: if there is no data between the dates, tell user

			//Edit title and y-axis name
			this.chart.setTitle(data[0].getCodeNameString().get(0));
			ValueAxis axis = ((XYPlot) this.chart.getPlot()).getRangeAxis();
			axis.setLabel(data[0].getUnitString().get(0));
		}
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
