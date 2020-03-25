package controller;

import java.util.List;

public interface IDatabase {

	boolean ensureDatabaseConnection();

	String getJSONDocumentByID(String collection, String id);

	String[] getJSONDocumentsByID(String collection, String[] ids);

	List<String> getAllObservationsBySubjectID(String id);

	void closeDatabaseConnection();

}
