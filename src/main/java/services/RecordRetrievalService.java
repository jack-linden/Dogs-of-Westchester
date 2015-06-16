 package services;

import java.util.ArrayList;
import java.util.List;

import model.Dog;

public class RecordRetrievalService {

	public RecordRetrievalService( ){
		
	}
	
	/**
	 * 
	 * @param name 
	 * 		The dog name to search for.
	 * @return
	 * 		The list of records matching that name.
	 */
	public List<Dog> queryRecordsByName( String name ){
		
		if( name == null ){
			throw new IllegalArgumentException("Did not expect name to be null.");
		}
		
		List<Dog> dogRecords = new ArrayList<Dog>();
		//TODO Insert code to query database and populate List of records.

		return dogRecords;
	}
}
