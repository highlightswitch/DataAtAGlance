package model;

import java.util.List;

public interface ObservationData {

	String getDateFormat();

	boolean getIsComposite();

	List<String> getCodeNameString();
	List<String> getCodeString();
	List<String> getValueString();
	List<String> getUnitString();
	String getDateString();

	List<String> getFilters();

}
