package services;

import java.util.ArrayList;
import java.util.List;

import model.DogRecord;

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
	public List<DogRecord> queryRecordsByName( String name ){
		
		if( name == null ){
			throw new IllegalArgumentException("Did not expect name to be null.");
		}
		
		List<DogRecord> dogRecords = new ArrayList<DogRecord>();
		//TODO Insert code to query database and populate List of records.

		return dogRecords;
	}
}
