package model;

import java.util.List;

public interface ObservationData {

	List<String> getCodeNameString();
	List<String> getCodeString();
	List<String> getValueString();
	String getDateString();

	List<String> getFilters();

}
