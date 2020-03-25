package controller;

import java.util.List;

public interface IDatabase {

	boolean ensureValidDatabaseConnection();
	void closeDatabaseConnection();

	List<String> getPatientsByPatientIDs(String[] ids);
	List<String> getAllObservationsBySubjectID(String id);

}
