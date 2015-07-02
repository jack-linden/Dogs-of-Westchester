package services;

import java.util.Set;
import java.util.HashSet;
import dataaccess.DogDAO;
import model.Dog;

public class RecordRetrievalService {
	
	public RecordRetrievalService() {
		
	}

	/**
	 * RecordRetrievalService.queryRecordsByName
	 * 
	 * This function will run queries against google's datastore
	 * 
	 * @param propertyType
	 * 			The property of the dog to search for. (e.g. Name, Breed, City)
	 * @param name
	 *          The dog name to search for.
	 * @param inTestingMode
	 *          Boolean flag true if for testing purposes
	 * @return The list of records matching that name.
	 */
	public Set<Dog> queryDogRecords(String propertyType, String query, boolean inTestingMode) {
		if (propertyType == null || query == null) {
			throw new IllegalArgumentException("Did not expect name to be null.");
		}
		
		if (!propertyType.equals("Name") && !propertyType.equals("Condition") && !propertyType.equals("Sex") && !propertyType.equals("Breed")
				&& !propertyType.equals("Color") && !propertyType.equals("City")) {
			throw new IllegalArgumentException("Property type can only be Name, Condition, Sex, Breed, Color, or City.");
		}

		Set<Dog> dogRecords = new HashSet<Dog>();
		query = query.toLowerCase();
		
		if (!inTestingMode) {
			dogRecords = DogDAO.getDogsFromQuery(propertyType, query);			
		}

		return dogRecords;
	}
}
