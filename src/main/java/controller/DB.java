package controller;

import database.DatabaseImpl;

public class DB {

	private static IDatabase DB;

	/**
	 * This method returns the implementation of IDatabase
	 */
	public static IDatabase get(){
		if(DB == null)
			DB = new DatabaseImpl();
		return DB;
	}

}
